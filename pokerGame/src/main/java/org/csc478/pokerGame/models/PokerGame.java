/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PokerGame.java
 *  created: 2018-11-09 13:09:08
 *       by: Gino Canessa
 * modified: 2018-12-11
 *       by: Gino Canessa
 *
 *  summary: Internal representation of a single game of poker
 */

package org.csc478.pokerGame.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import javax.activity.InvalidActivityException;

import org.csc478.pokerGame.models.GameAction.GameActionTypes;

/**
 * PokerGame class to track game state and implement game logic.
 * @csc478.req REQ020800 - Humans cannot borrow while game is in progress
 * @csc478.req REQ020900 - Humans cannot bet more than they have
 * @csc478.req REQ030300 - Computer players cannot borrow while game is in progress
 * @csc478.req REQ030400 - Computer players cannot bet more than they have
 * @csc478.req REQ050100 - Uniquely track games
 * @csc478.req REQ050300 - Track players for each game
 * @csc478.req REQ050400 - Track game objects for game
 * @csc478.req REQ050500 - Ante
 * @csc478.req REQ050600 - Low stake limit
 * @csc478.req REQ050700 - High stake limit
 * @csc478.req REQ050800 - Number of players
 * @csc478.req REQ050900 - Position of players in game
 * @csc478.req REQ051000 - Position of dealer in game
 * @csc478.req REQ060100 - Shuffle deck before dealing
 * @csc478.req REQ060200 - Ante payment
 * @csc478.req REQ060300 - Burn first card of deal
 * @csc478.req REQ060400 - Deal face down
 * @csc478.req REQ060401 - Card 1 is dealt face down
 * @csc478.req REQ060402 - Card 2 is dealt face down
 * @csc478.req REQ060403 - Card 7 is dealt face down
 * @csc478.req REQ060500 - Deal face up
 * @csc478.req REQ060501 - Card 3 is dealt face up
 * @csc478.req REQ060502 - Card 4 is dealt face up
 * @csc478.req REQ060503 - Card 5 is dealt face up
 * @csc478.req REQ060504 - Card 6 is dealt face up
 * @csc478.req REQ060600 - Determine first player to take action each round
 * @csc478.req REQ060601 - Round 1
 * @csc478.req REQ060602 - Round 2
 * @csc478.req REQ060603 - Round 3
 * @csc478.req REQ060604 - Round 4
 * @csc478.req REQ060605 - Round 5
 * @csc478.req REQ060606 - Showdown with no bets in round 5
 * @csc478.req REQ060607 - Showdown with bets in round 5
 * @csc478.req REQ060608 - Ties go to player closer to dealer
 * @csc478.req REQ060700 - Process Round 1 Actions
 * @csc478.req REQ060701 - Forced bet
 * @csc478.req REQ060702 - Raise (any)
 * @csc478.req REQ060703 - Call (match)
 * @csc478.req REQ060704 - Fold
 * @csc478.req REQ060800 - Process Round 2 Actions
 * @csc478.req REQ060801 - Forced bet
 * @csc478.req REQ060802 - Raise only with showing pair
 * @csc478.req REQ060803 - Raise only in multiples of stakes
 * @csc478.req REQ060804 - Raise multiple restrictions
 * @csc478.req REQ060805 - Call (match)
 * @csc478.req REQ060806 - Fold
 * @csc478.req REQ060900 - Process Round 3 Actions
 * @csc478.req REQ060901 - Forced bet
 * @csc478.req REQ060902 - Raise not allowed
 * @csc478.req REQ060903 - Call (match)
 * @csc478.req REQ060904 - Fold
 * @csc478.req REQ061000 - Process Round 4 Actions
 * @csc478.req REQ061001 - Raise (any)
 * @csc478.req REQ061002 - Call (match)
 * @csc478.req REQ061003 - Fold
 * @csc478.req REQ061100 - Process Round 5 Actions
 * @csc478.req REQ061101 - Raise (any)
 * @csc478.req REQ061102 - Call (match)
 * @csc478.req REQ061103 - Fold
 * @csc478.req REQ061200 - Process Showdown Actions
 * @csc478.req REQ061201 - Show Cards
 * @csc478.req REQ061202 - Muck Cards
 * @csc478.req REQ061400 - Compare player scores to find winner
 * @csc478.req REQ061500 - Process pot
 */

public class PokerGame {

  //#region Public Constants . . .

  public static final int GameRoundStateInvalid = 0;
  public static final int GameRoundStateWaitingForPlayer = 1;
  public static final int GameRoundStateDealing = 2;
  public static final int GameRoundStateComputerPlayer = 3;
  public static final int GameRoundStateGameOver = 4;

  //#endregion Public Constants . . .

  //#region Private Constants

  private static final int _computerLoanAmount = 1900;
  private static final int _computerLoanThreshold = 1500;

  //#endregion Private Constants

  //#region Public Variables . . .

  //#endregion Public Variables . . .

  //#region Object Variables . . .

  /** Unique identifier for this game */
  private final UUID _pokerGameId;

  /** Number of actions take in this game so far */
  private int _gameActionCount;

  /** List of actions performed in this game */
  private List<GameAction> _gameActions;

  /** Array of players in this game */
  private PokerPlayer _players[];

  /** Array of GameActions with the last action from each player */
  private GameAction _playerLastActions[];

  /** Array of flags for if a player is still in the game */
  private boolean _playerActiveFlags[];
  
  /** Number of players currently active in this hand */
  private int _handActivePlayerCount;

  /** Number of Calls in a row for this round */
  private int _consecutiveCallCount;

  /** Number of players showing their cards in this round */
  private int _playerShowingCount;

  /** Number of players in game (to avoid frequently accessing list size) */
  private int _playerCount;

  /** Winning player index, -1 for no winner yet */
  private int _winningPlayerIndex;

  /** Array of player hands in this game */
  private PlayerHand _playerHands[];

  /** The ante for this table (in dollars), can be zero */
  private final int _ante;

  /** The low stake limit for this table (in dollars) */
  private final int _lowStake;

  /** The high stake limit for this table (in dollars) */
  private final int _highStake;

  /** Current round of the game */
  private int _currentRound;

  /** State of the current round of play */
  private int _currentRoundState;

  /** Deck of cards in use */
  private CardDeck _gameDeck;

  /** Number of cards from the deck which have been used */
  private int _numberOfCardsUsed;

  /** Number of cards which have been dealt to each player */
  private int _cardsDealtPerPlayer = 0;

  /** Index of the first player that got to take action this round */
  private int _firstPlayerThisRound = 0;

  /** Number of players which have had a turn this round */
  private int _playerTurnsThisRound;

  /** Amount (in dollars) of the current pot */
  private int _potValue;

  /** Minumum bet amount to stay in the game */
  private int _currentBetToStayIn;

  private int _playerBetTotalsThisRound[];

  /** Queue for holding requested actions */
  private Queue<GameAction> _requestedActions;

  /** Thread object that runs our game */
  private Thread _gameProcessThread;

  /** Object for notifiying the UI on property changes */
  private PropertyChangeSupport _propertyChangeSupport;


  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Gets the Ante (in dollars) for this game
   * @return The ante (in dollars) for this game
   */
  public int getAnte() { return _ante; }

  /**
   * Gets the current pot (in dollars) for this game.
   * @return The amount in the pot (in dollars) at the time of calling.
   */
  public int getCurrentPot() { return _potValue; }

  /**
   * Gets the Low Stake Limit (in dollars) for this game
   * @return The Low Stake Limit (in dollars) for this game
   */
  public int getLowStake() { return _lowStake; }

  /**
   * Gets the High Stake Limit (in dollars) for this game
   * @return The High Stake Limit (in dollars) for this game
   */
  public int getHighStake() { return _highStake; }

  /**
   * Gets the Number of Players in this game
   * @return The Number of Players in this game
   */
  public int getNumberOfPlayers() { return _playerCount; }

  /**
   * Gets the Number of Active Players in this game
   * @return The Number of Active Players in this game
   */
  public int getNumberOfActivePlayers() { return _handActivePlayerCount; }

  /**
   * Get the current round number of the game
   * @return The current round number of the game
   */
  public int getRoundNumber() { return _currentRound; }

  /**
   * Get the winning player index for the hand
   * @return -1 if there is no winner yet, player index otherwise
   */
  public int getWinnerIndex() { return _winningPlayerIndex; }

  /**
   * Get the state of the current round of play
   * @return The state of the current round of play
   */
  public int getRoundState() { return _currentRoundState; }

  public String getRoundStateName() {
    switch (_currentRoundState)
    {
      case (GameRoundStateWaitingForPlayer):
      {
        return "Your turn!";
      }
      //break;
      case (GameRoundStateDealing):
      {
        return "Dealing...";
      }
      //break;
      case (GameRoundStateComputerPlayer):
      {
        return "Computers playing...";
      }
      //break;
      case (GameRoundStateGameOver):
      {
        return "Hand Over!";
      }
      //break;
      default:
      {
        return "Waiting...";
      }
      //break;
    }
  }

  /**
   * Get the current total bet required to stay in the game
   * @return The TOTAL amount of bet required to stay in the game
   */
  public int getCurrentMinumumTotalBet() { return _currentBetToStayIn; }

  /**
   * Get the suit for the card at the specified index
   * @param deckIndex Index of the card within the deck (0-51)
   * @return The Suit of the card (e.g., PlayingCard.CardSuitClubs)
   */
  public int getSuitForCard(int deckIndex) {
    return _gameDeck.CardAt(deckIndex).getCardSuit();
  }

  /**
   * Get the rank for the card at the specified index
   * @param deckIndex Index of the card within the deck (0-51)
   * @return The Rank of the card (e.g., PlayingCard.CardRankAce)
   */
  public int getRankForCard(int deckIndex) {
    return _gameDeck.CardAt(deckIndex).getCardRank();
  }

  /**
   * Gets the current minimum bet required by a given player to stay in the game
   * @param playerIndex Game Player index for the player
   * @return Minumum bet required, in dollars
   */
  public int getCurrentMinumumBetForPlayerIndex(int playerIndex) { 
    return (_currentBetToStayIn > _playerBetTotalsThisRound[playerIndex]) 
      ? _currentBetToStayIn - _playerBetTotalsThisRound[playerIndex]
      : 0 ;
  }

  /**
   * Gets the name of the specified player
   * @param playerIndex Game Player index for the player
   * @return Name of the player
   */
  public String getPlayerName(int playerIndex) {
    return _players[playerIndex].getPlayerName();
  }

  /**
   * Gets the number of wins for the specified player
   * @param playerIndex Game Player index for the player
   * @return Number of wins for the player
   */
  public int getPlayerWins(int playerIndex) {
    return _players[playerIndex].getWins();
  }

  /**
   * Gets the number of losses for the specified player
   * @param playerIndex Game Player index for the player
   * @return Number of losses for the player
   */
  public int getPlayerLosses(int playerIndex) {
    return _players[playerIndex].getLosses();
  }

  /**
   * Gets the face up scores of all other players, excluding the one passed
   * @param gamePlayerIndex Game player index for the player
   * @return Array of scores
   */
  public int[] getOtherPlayerFaceUpScores(int gamePlayerIndex) {
    int faceUpScores[] = new int[_playerCount - 1];

    int scoresAdded = 0;
    for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
    {
      if (playerIndex != gamePlayerIndex)
      {
        faceUpScores[scoresAdded++] = _playerHands[playerIndex].getScoreFaceUp();
      }
    }

    return faceUpScores;
  }

  /**
   * Gets the face up scores of all players
   * @return Array of scores, in game player order
   */
  public int[] getFaceUpScores() {
    int faceUpScores[] = new int[_playerCount];

    for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
    {
      faceUpScores[playerIndex] = _playerHands[playerIndex].getScoreFaceUp();
    }

    return faceUpScores;
  }

  /**
   * Get the score name for the given player (used in game over)
   * @param playerIndex Array index of the player to get the score type of
   * @return Name for UI display of the score type of this player's hand
   */
  public String getScoreName(int playerIndex) {
    return PokerScorer.getScoreName(_playerHands[playerIndex].getScoreTotal());
  }

  /**
   * Get the number of dollars a player has
   * @param playerIndex Array index of the player to get the dollar amount of
   * @return Current number of dollars the player has
   */
  public int getPlayerDollars(int playerIndex) {
    return _players[playerIndex].getDollars();
  }

  /**
   * Get the total debt a player has accrued
   * @param playerIndex Array index of the player to get the debt of
   * @return Current  dollars in debt the player is
   */
  public int getPlayerDebt(int playerIndex) {
    return _players[playerIndex].getDebt();
  }

  /**
   * Lend a player a specified amount of money
   * @param playerIndex Array index of the player to lend money to
   * @param amount Amount of money being lent to the player
   * @return Array containing two int values, money player now has and debt player now has
   */
  public int[] lendMoneyToPlayer(int playerIndex, int amount) {
    // **** get the current debt of the player ****

    int debt = _players[playerIndex].getDebt();

    // **** add to the debt ****

    debt += amount;

    // **** set new debt ****

    _players[playerIndex].setDebt(debt);

    // **** get current amount of money ****

    int dollars = _players[playerIndex].getDollars();

    // **** add our lending amount ****

    dollars += amount;

    // **** give the money to the player ****

    _players[playerIndex].setDollars(dollars);

    // **** build our return value (dollars, debt) ****

    int retVal[] = {dollars, debt};

    // **** return our array ****

    return retVal;
  }

  /**
   * Get the current hand for a player
   * @param playerIndex Index of the player within the game
   * @return PlayerHand object with the specified player's current hand
   */
  public PlayerHand getPlayerHand(int playerIndex) {
    return _playerHands[playerIndex];
  }

  /**
   * Get the current active player flags
   * @return Array of booleans, True if the player is active, False otherwise
   */
  public boolean[] getActivePlayers() {
    return _playerActiveFlags;
  }

  /**
   * Get the last action for the given player
   * @param playerIndex Array player index of the player to reference
   * @return GameAction representing the last action taken by the player
   */
  public GameAction getPlayerLastAction(int playerIndex) {
    return _playerLastActions[playerIndex];
  }

  /**
   * Get the closest valid bet to a requested bet value
   * @param requestedBet Amount the caller would like to be
   * @return Nearest valid bet to the requested bet
   */
  public int getClosestValidBet(int requestedBet) {

    int validAmount = 0;

    // **** act depending on current round ****

    switch (_currentRound)
    {
      case 1:
        // **** round 1 starts at low stake, but can be any amount ****

        validAmount = _lowStake > requestedBet ? _lowStake : requestedBet;
      break;

      case 2:

        // **** bet is low stake unless there is a showing pair ****

        validAmount = _lowStake;

        // **** traverse players looking for pair ****

        for (int handIndex = 0; handIndex < _playerCount; handIndex++)
        {
          if (_playerHands[handIndex].getHandTypeFaceUp() == PokerScorer.ScoreTypeOnePair)
          {
            // **** check for multiples of low/high ****

            if (_currentBetToStayIn % _highStake == 0)
            {
              // **** multiples of high stake ****

              validAmount = (requestedBet / _highStake) * _highStake;
            }
            else
            {
              // **** multiples of low stake ****

              validAmount = (requestedBet / _lowStake) * _lowStake;
            }
          }
        }

      break;

      case 3:

        // **** bet is high stake ****

        validAmount = _highStake;

      break;

      case 4:

        // **** any bet is valid ****

        validAmount = requestedBet;

      break;

      case 5:

        // **** any bet is valid ****

        validAmount = requestedBet;

      break;
    }

    // **** make sure it's at least the current bet ****

    if (validAmount < _currentBetToStayIn)
    {
      validAmount = _currentBetToStayIn;
    }

    // **** return our amount ****

    return validAmount;
  }

  //#endregion Accessors and Mutators . . .

  //#region Constructors . . .

  /**
   * Create a game of poker with the specified ante and stakes
   * @param ante The ante for this game (0 or more)
   * @param lowStake The low stake for this game (1 or more, must be lower than highStake)
   * @param highStake The high stake for this game (2 or more, must be higher than lowStake)
   */
  public PokerGame(
    final int ante,
    final int lowStake,
    final int highStake
    )
  {
    // **** sanity checks ***

    if (ante < 0)
    {
      throw new InvalidParameterException(String.format("Invalid table ante: %d", ante));
    }

    if (lowStake < 1)
    {
      throw new InvalidParameterException(String.format("Invalid table lowStake: %d", lowStake));
    }

    if (highStake < 2)
    {
      throw new InvalidParameterException(String.format("Invalid table highStake: %d", highStake));
    }

    if (lowStake >= highStake)
    {
      throw new InvalidParameterException(
        String.format("Invalid table stakes, low: %d must be smaller than high: %d", lowStake, highStake));
    }

    _requestedActions = new LinkedList<GameAction>();

    // **** generate a UUID ****

    _pokerGameId = UUID.randomUUID();

    _gameActionCount = 0;
    _gameActions = new ArrayList<GameAction>();

    // **** set basic game information ****

    _ante = ante;
    _lowStake = lowStake;
    _highStake = highStake;
    
    // **** no players have been added yet ****

    _players = new PokerPlayer[7];
    _playerHands = new PlayerHand[7];
    _playerLastActions = new GameAction[7];
    _playerCount = 0;
    _playerTurnsThisRound = 0;

    _currentRound = 0;

    // **** set up our cards ****

    _gameDeck = new CardDeck();
    _numberOfCardsUsed = 0;

    _potValue = 0;

    _gameProcessThread = null;

    _propertyChangeSupport = new PropertyChangeSupport(this);
  }

  //#endregion Constructors . . .

  //#region Public Interface . . .


  /**
   * Add a game event listener for this game
   * @param pcl The property change listener which will listen for game events
   */
  public void AddGameEventListener(PropertyChangeListener pcl) {
    _propertyChangeSupport.addPropertyChangeListener(pcl);
}

  /**
   * Remove a game event listener for this game
   * @param pcl The property change listener which will NO LONGER listen for game events
   */
  public void RemoveGameEventListener(PropertyChangeListener pcl) {
    _propertyChangeSupport.removePropertyChangeListener(pcl);
  }

  /**
   * Add a player to the game
   * @param player Player to add to this game
   * @return Number of players in the game
   * @throws InvalidActivityException Attempt to exceed the allowed number of players
   */
  public int AddPlayer(PokerPlayer player) throws InvalidActivityException
  {
    // **** sanity checks ****

    if (_playerCount > 6)
    {
      throw new InvalidActivityException(String.format("Cannot add another player to the game."));
    }

    // **** append this player ****

    _players[_playerCount] = player;
    _playerHands[_playerCount] = new PlayerHand(player.getPlayerId(), _playerCount);
    player.setGamePlayerIndex(_playerCount);

    // **** increment our number of players ****

    _playerCount++;

    // **** return the number of players ****

    return _playerCount;
  }

  /**
   * Start this game of poker
   */
  public void StartGame()
  {
    // **** start with round 0 ****

    _currentRound = 0;
    _cardsDealtPerPlayer = 0;
    _numberOfCardsUsed = 0;

    _playerTurnsThisRound = 0;

    _gameActionCount = 0;
    _gameActions = new ArrayList<GameAction>();

    _playerShowingCount = 0;
    _potValue = 0;

    // **** all players are assumed active ****

    _playerActiveFlags = new boolean[_playerCount];
    for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
    {
      // **** check to see if this player has enough money to play ****

      int dollars = _players[playerIndex].getDollars();

      // **** need to check computer players for enough money to play ****

      if ((_players[playerIndex].getIsComputer() == true) && (dollars < _computerLoanThreshold)) {
        // **** lend money to this player ****

        lendMoneyToPlayer(playerIndex, _computerLoanAmount);
      }

      // **** player is in the game ****
      
      _playerActiveFlags[playerIndex] = true;

      // **** create an empty hand for this player ****

      _playerHands[playerIndex] = new PlayerHand(_players[playerIndex].getPlayerId(), playerIndex);
    }

    _handActivePlayerCount = _playerCount;
    _consecutiveCallCount = 0;
    _playerShowingCount = 0;

    _winningPlayerIndex = -1;

    // **** game state is not valid yet ****

    _currentRoundState = GameRoundStateInvalid;

    // **** request that our deck gets shuffled ****

    RequestShuffleDeck();

    // **** make sure a game thread is running ****

    if (_gameProcessThread == null)
    {
      Runnable procRunnable = () -> {GameThreadProc();};
      _gameProcessThread = new Thread(procRunnable);
      _gameProcessThread.start();
    }
    else
    {
      if (!_gameProcessThread.isAlive())
      {
        _gameProcessThread.start();
      }
    }
  }

  /**
   * Function to process game actions
   */
  private void GameThreadProc()
  {
    while (!Thread.currentThread().isInterrupted())
    {
      // **** check for no actions in our queue ****

      if (_requestedActions.size() == 0)
      {
        try {
          Thread.sleep(100);
        } catch (Exception e) {
          // **** ignore the exception for now ****
        }

        // **** go to next loop ****

        continue;
      }

      // **** grab our next action ****

      GameAction action = _requestedActions.remove();

      // **** process this action ****

      ProcessAction(action);
    }
  }

  /**
   * Process an action in this game
   * @param action Action to process
   */
  private void ProcessAction(GameAction action)
  {
    // **** act depending on type ****

    switch (action.getActionType())
    {
      case GameAction.GameActionTypeShuffleDeck:
      {
        // **** shuffle the deck ****

        _gameDeck.ShuffleDeck();

        // **** check to see if we need to process ante payment ****

        if (_ante != 0)
        {
          // **** need to wait on ante ****

          RequestAnte();
        }
        else
        {
          // **** need to burn first card ****

          RequestBurnCard();
        }
      }
      break;

      case GameAction.GameActionTypeRequestAnte:
      {
        // **** process ante payment from computer players ****

        for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
        {
          int playerAmount = _players[playerIndex].getDollars();

          // **** make sure they can pay ****

          if (playerAmount < _ante)
          {
            // **** player will not be active this hand ****

            _playerActiveFlags[playerIndex] = false;
            _handActivePlayerCount--;
          }

          // **** take their ante payment ****

          playerAmount -= _ante;

          // **** update player amount ****

          _players[playerIndex].setDollars(playerAmount);

          // **** update the pot ****

          _potValue += _ante;
        }

        // **** request next state ****

        RequestBurnCard();
      }
      break;

      case GameAction.GameActionTypeBurnCard:
      {
        // **** remove next card from shuffled deck ****

        _gameDeck.CardAt(_numberOfCardsUsed++).setCardState(PlayingCard.CardStateOutOfPlay);

        // **** set state ****

        _currentRoundState = GameRoundStateDealing;

        // **** next state is dealing face down ****

        RequestDealFaceDown();
      }
      break;

      case GameAction.GameActionTypeDealFaceDown:
      {
        // **** deal a face down card to each player ****

        for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
        {
          // **** check for player being out already ****

          if (_playerActiveFlags[playerIndex] == false)
          {
            continue;
          }

          // **** flag that this card is being dealt face down ****

          _gameDeck.CardAt(_numberOfCardsUsed).setCardState(PlayingCard.CardStateFaceDown);

          // **** deal this player the next card ****

          _playerHands[playerIndex].AddCardToHand(_gameDeck.CardAt(_numberOfCardsUsed));

          // **** this card has been used ****

          _numberOfCardsUsed++;
        }

        // **** another card has been dealt to each player ****

        _cardsDealtPerPlayer++;

        // **** act depending on how many cards have been dealt ****

        RequestActionAfterDeal();
      }
      break;

      case GameAction.GameActionTypeDealFaceUp:
      {
        // **** deal a face up card to each player ****

        for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
        {
          // **** check for player being out already ****

          if (_playerActiveFlags[playerIndex] == false)
          {
            continue;
          }
          // **** flag that this card is being dealt face up ****

          _gameDeck.CardAt(_numberOfCardsUsed).setCardState(PlayingCard.CardStateFaceUp);

          // **** deal this player the next card ****

          _playerHands[playerIndex].AddCardToHand(_gameDeck.CardAt(_numberOfCardsUsed));

          // **** this card has been used ****

          _numberOfCardsUsed++;
        }

        // **** another card has been dealt to each player ****

        _cardsDealtPerPlayer++;

        // **** act depending on how many cards have been dealt ****

        RequestActionAfterDeal();
      }
      break;

      case GameAction.GameActionTypeRequestPlayerAction:
      {
        // **** determine which player's turn it is ****
        
        int playerNumber = (_firstPlayerThisRound + _playerTurnsThisRound++) % _playerCount;

        // **** check to see if this player is still in this hand ****

        while (_playerActiveFlags[playerNumber] == false)
        {
          // **** move to next player ****

          playerNumber = (_firstPlayerThisRound + _playerTurnsThisRound++) % _playerCount;
        }

        // **** ask the player for an action ****

        if (_players[playerNumber].getIsComputer() == true)
        {
          // **** set game state ****

          _currentRoundState = GameRoundStateComputerPlayer;

          // **** ask the player for an action ****

          _players[playerNumber].RequestActionForGame(_playerHands[playerNumber], this);
        }
        else
        {
          // **** wait for human player ****

          _currentRoundState = GameRoundStateWaitingForPlayer;

          // **** flag we are waiting for the player ****

          RequestWaitingForPlayer(_players[playerNumber].getPlayerId(), playerNumber);
        }
      }
      break;

      case GameAction.GameActionTypeRaise:
      {
        // **** grab the player index (for sanity) ****
        
        int playerIndex = action.getGamePlayerIndex();

        // **** make sure the player has enough money to raise ****

        int playerAmount = _players[playerIndex].getDollars();

        int raiseDifference = (_currentBetToStayIn - _playerBetTotalsThisRound[playerIndex]) + action.getAmount();

        // **** make sure they can pay ****

        if (playerAmount < raiseDifference)
        {
          // **** player will not be active this hand ****
    
          _playerActiveFlags[playerIndex] = false;
          _handActivePlayerCount--;

          // **** reset to empty hand ****

          _playerHands[playerIndex] = new PlayerHand(_players[playerIndex].getPlayerId(), playerIndex);
        }
        else
        {
          // **** take their payment ****

          playerAmount -= raiseDifference;

          // **** update player amount ****

          _players[playerIndex].setDollars(playerAmount);

          // **** update the player's bet state ****

          _playerBetTotalsThisRound[playerIndex] += raiseDifference;

          // **** update the pot ****

          _potValue += raiseDifference;

          if (raiseDifference < 0) {
            System.out.println("Should not be here!");
          }

        }

        // **** reset call count, raising counts as the first call ****

        _consecutiveCallCount = 1;

        // **** set the new bet to stay in ****

        _currentBetToStayIn = _playerBetTotalsThisRound[playerIndex];

        System.out.println(String.format(
          "Processed Raise, player: %d, stay in: %d total bet: %d, ",
          playerIndex,
          _currentBetToStayIn,
          _playerBetTotalsThisRound[playerIndex]
          ));


        // **** determine next action in the game ****

        RequestActionAfterPlayerAction();
      }
      break;

      case GameAction.GameActionTypeCall:
      {
        // **** grab the player index (for sanity) ****
        
        int playerIndex = action.getGamePlayerIndex();

        // **** make sure the player has enough money to call ****

        int playerAmount = _players[playerIndex].getDollars();

        int callDifference = _currentBetToStayIn - _playerBetTotalsThisRound[playerIndex];

        // **** make sure they can pay ****

        if (playerAmount < callDifference)
        {
          // **** player will not be active this hand ****
    
          _playerActiveFlags[playerIndex] = false;
          _handActivePlayerCount--;

          // **** reset to empty hand ****

          _playerHands[playerIndex] = new PlayerHand(_players[playerIndex].getPlayerId(), playerIndex);
        }
        else
        {
          // **** take their payment ****

          playerAmount -= callDifference;

          // **** update player amount ****

          _players[playerIndex].setDollars(playerAmount);

          // **** update the player's bet state ****

          _playerBetTotalsThisRound[playerIndex] += callDifference;

          // **** update the pot ****

          _potValue += callDifference;

          if (callDifference < 0) {
            System.out.println("Should not be here!");
          }
        }
        
        System.out.println(String.format(
          "Processed Call, player: %d, stay in: %d total bet: %d",
          playerIndex,
          _currentBetToStayIn,
          _playerBetTotalsThisRound[playerIndex]
          ));

        // **** flag that we called ****

        _consecutiveCallCount++;

        // **** determine next action in the game ****

        RequestActionAfterPlayerAction();
      }
      break;

      case GameAction.GameActionTypeFold:
      case GameAction.GameActionTypeMuck:
      {
        // **** grab the player index (for sanity) ****

        int playerIndex = action.getGamePlayerIndex();

        // **** player will not be active this hand ****
  
        _playerActiveFlags[playerIndex] = false;
        _handActivePlayerCount--;

        // **** reset to empty hand ****

        _playerHands[playerIndex] = new PlayerHand(_players[playerIndex].getPlayerId(), playerIndex);

        // **** determine next action in the game ****

        RequestActionAfterPlayerAction();
      }
      break;

      case GameAction.GameActionTypeShowCards:
      {
        // **** flag we are showing ****

        _playerShowingCount++;

        // **** determine next action in the game ****

        RequestActionAfterPlayerAction();
      }
      break;

      case GameAction.GameActionTypeEndGame:
      {
        int winnerIndex = 0;
        int winningScore = 0;
  
        // **** score the game ****
  
        for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
        {
          // **** check for new high score - ties go to closer to dealer ****
  
          if (_playerHands[playerIndex].getScoreTotal() > winningScore)
          {
            winnerIndex = playerIndex;
            winningScore = _playerHands[playerIndex].getScoreTotal();
          }
        }
  
        // **** notifiy our winners and losers ****
  
        for (int playerIndex = 0; playerIndex < _playerCount; playerIndex++)
        {
          // **** check for new high score - ties go to closer to dealer ****
  
          if (playerIndex == winnerIndex)
          {
            _players[playerIndex].incrementWins();
          }
          else
          {
            _players[playerIndex].incrementLosses();
          }
        }

  
        // **** flag winner in game ****

        _winningPlayerIndex = winnerIndex;

        // **** add the pot value to the player total ****
  
        int playerDollars = _players[winnerIndex].getDollars();
        playerDollars += _potValue;
        _players[winnerIndex].setDollars(playerDollars);

        // **** set state ****

        _currentRoundState = GameRoundStateGameOver;
      }
      break;
    }

    // **** keep track of actions ****
    
    _gameActions.add(action);
  }

  /**
   * Request the next action after dealing a card
   */
  private void RequestActionAfterPlayerAction()
  {
    // System.out.println(String.format(
    //   "Hand Players: %d, Players Showing: %d, Consecutive Calls: %d, Round: %d",
    //   _handActivePlayerCount,
    //   _playerShowingCount,
    //   _consecutiveCallCount,
    //   _currentRound
    //   ));

    // **** check for a single player left or all remaining players showing ****

    if ((_handActivePlayerCount == 1) || (_playerShowingCount == _handActivePlayerCount))
    {
      RequestEndGame();

      // **** done ****

      return;
    }

    // **** check our consecutive call count (round is over when all have called) ****

    if (_consecutiveCallCount == _handActivePlayerCount)
    {
      // **** act depending on round we are in ****

      if ((_currentRound == 1) ||
          (_currentRound == 2) ||
          (_currentRound == 3))
      {
        // **** next action is to deal face up ****

        RequestDealFaceUp();

        // **** done ****

        return;
      }

      if (_currentRound == 4)
      {
        // **** next action is to deal face down ****

        RequestDealFaceDown();

        // **** done ****

        return;
      }

      // **** final round ****

      AdvanceRound(false);

      // **** done ****

      return;
    }

    // **** need to get next player action ****

    RequestPlayerAction();
  }

  /**
   * Chose the first player for a round
   * @return Player Index of the first player for this round
   */
  private int ChooseFirstPlayer()
  {
    int playerNumber = 0;

    // **** act depending on round ****

    if (_currentRound == 1)
    {
      int lowScore = PokerScorer.InvalidHighScore;

      // **** traverse players looking for lowest score ****

      for (int handIndex = 0; handIndex < _playerCount; handIndex++)
      {
        int handScore = _playerHands[handIndex].getScoreFaceUp();

        if ((handScore < lowScore) && (_playerActiveFlags[handIndex] == true)) 
        {
          lowScore = handScore;
          playerNumber = handIndex;
        }
      }

      // **** done ****

      return playerNumber;
    }

    // **** use highest exposed hand ****

    int highScore = 0;

    // **** traverse players looking for highest score ****

    for (int handIndex = 0; handIndex < _playerCount; handIndex++)
    {
      int handScore = _playerHands[handIndex].getScoreFaceUp();

      if ((handScore > highScore) && (_playerActiveFlags[handIndex] == true))
      {
        highScore = handScore;
        playerNumber = handIndex;
      }
    }

    // **** return the index of the first player ****

    return playerNumber;
  }

  /**
   * Advance to the next round of the game internally
   * @param isFirstRound True if this is the first round
   */
  private void AdvanceRound(boolean isFirstRound)
  {
    // **** check for first round ****

    if (isFirstRound)
    {
      // **** force to round 1 ****

      _currentRound = 1;
    }
    else
    {
      // **** increment our round ****

      _currentRound++;
    }

    // **** check for advancing too far ****

    if (_currentRound > 7)
    {
      System.out.println("Should not be here!");
    }

    // **** figure out player turn info ****

    _firstPlayerThisRound = ChooseFirstPlayer();
    _playerTurnsThisRound = 0;
    _consecutiveCallCount = 0;

    // **** no bets have been made this round ****

    _playerBetTotalsThisRound = new int[_playerCount];

    // **** no actions have been taken this round ****

    _playerLastActions = new GameAction[_playerCount];

    // **** configure betting for this round ****

    _currentBetToStayIn = 0;
    _currentBetToStayIn = getClosestValidBet(0);

    System.out.println(String.format(
      "Advanced to round: %d, first player: %d, minimum bet: %d", 
      _currentRound,
      _firstPlayerThisRound,
      _currentBetToStayIn));

    // **** we need to start player actions ****

    RequestPlayerAction();
  }

  /**
   * Request the next action after dealing a card
   */
  private void RequestActionAfterDeal()
  {
    // **** act depending on number of cards dealt ****

    switch (_cardsDealtPerPlayer)
    {
      case 1:
        // **** next action is a face down deal ****

        RequestDealFaceDown();

      break;

      case 2:
        // **** next action is a face up deal ***

        RequestDealFaceUp();

      break;

      case 3:
        // **** we now move into round one ****

        AdvanceRound(true);

      break;

      case 4:
      case 5:
      case 6:
      case 7:

        // **** move into the next round ****

        AdvanceRound(false);

      break;
    }
  }

  /**
   * Enqueue a request for processing
   * @param action Action being requested
   */
  private void RequestAction(GameAction action)
  {
    // **** add this action to our internal list ****

    _requestedActions.add(action);

    // **** notify any listeners that an action was taken ****

    _propertyChangeSupport.firePropertyChange("GameAction", null, action);
  }

  /** Request that the game ends */
  private void RequestEndGame()
  {
    RequestAction(new GameAction(null, -1, _pokerGameId, GameAction.GameActionTypeEndGame, 0, _currentRound, _gameActionCount++));
  }

  /** Request that a player takes action */
  private void RequestPlayerAction()
  {
    RequestAction(new GameAction(null, -1, _pokerGameId, GameAction.GameActionTypeRequestPlayerAction, 0, _currentRound, _gameActionCount++));
  }

  /**
   * Add an action to the queue to show we are waiting on a player
   * @param playerId ID of the player we are waiting for
   * @param playerIndex Index of the player we are waiting for
   */
  private void RequestWaitingForPlayer(UUID playerId, int playerIndex)
  {
    RequestAction(new GameAction(playerId, playerIndex, _pokerGameId, GameAction.GameActionTypeWaitOnPlayerAction, 0, _currentRound, _gameActionCount++));
  }

  /** Request that the dealer deals a face-up card to each player */
  private void RequestDealFaceUp()
  {
    RequestAction(new GameAction(null, -1, _pokerGameId, GameAction.GameActionTypeDealFaceUp, 1, _currentRound, _gameActionCount++));
  }

  /** Request that the dealer deals a face-down card to each player */
  private void RequestDealFaceDown()
  {
    RequestAction(new GameAction(null, -1, _pokerGameId, GameAction.GameActionTypeDealFaceDown, 1, _currentRound, _gameActionCount++));
  }

  /** Request that the game shuffles the deck */
  private void RequestShuffleDeck()
  {
    RequestAction(new GameAction(null, -1, _pokerGameId, GameAction.GameActionTypeShuffleDeck, 0, _currentRound, _gameActionCount++));
  }

  /** Request that all players pay the ante */
  private void RequestAnte()
  {
    RequestAction(new GameAction(null, -1, _pokerGameId, GameAction.GameActionTypeRequestAnte, _ante, _currentRound, _gameActionCount++));
  }

  /** Request that the dealer burns a card from the deck */
  private void RequestBurnCard()
  {
    RequestAction(new GameAction(null, -1, _pokerGameId, GameAction.GameActionTypeBurnCard, 1, _currentRound, _gameActionCount++));
  }

  /**
   * Place a player request to fold (only valid in rounds 1 through 5)
   * @param playerIndex Game Player Index of the player requesting this action
   */
  public void PlayerActionFold(int playerIndex)
  {
    // **** create our action ***

    GameAction action = new GameAction(
      _players[playerIndex].getPlayerId(), 
      playerIndex, 
      _pokerGameId, 
      GameAction.GameActionTypeFold, 
      0, 
      _currentRound, 
      _gameActionCount++);

    // **** this is now the latest action for the player ****

    _playerLastActions[playerIndex] = action;
    
    // **** request this action ****

    RequestAction(action);
  }

  /**
   * Place a player request to muck their cards (only valid in round 6)
   * @param playerIndex Game Player Index of the player requesting this action
   */
  public void PlayerActionMuck(int playerIndex)
  {
    // **** create our action ***
    
    GameAction action = new GameAction(
      _players[playerIndex].getPlayerId(), 
      playerIndex, 
      _pokerGameId, 
      GameAction.GameActionTypeMuck, 
      0, 
      _currentRound, 
      _gameActionCount++);

    // **** this is now the latest action for the player ****

    _playerLastActions[playerIndex] = action;
    
    // **** request this action ****

    RequestAction(action);
  }

  /**
   * Place a player request to bet with a raise
   * @param playerIndex Game Player Index of the player requesting this action
   * @param amount Amount the player wishes to raise
   */
  public void PlayerActionRaise(int playerIndex, int amount)
  {
    // **** create our action ***
    
    GameAction action = new GameAction(
      _players[playerIndex].getPlayerId(), 
      playerIndex, 
      _pokerGameId, 
      GameAction.GameActionTypeRaise, 
      amount, 
      _currentRound, 
      _gameActionCount++);
      
    // **** this is now the latest action for the player ****

    _playerLastActions[playerIndex] = action;
    
    // **** request this action ****

    RequestAction(action);
  }

  /**
   * Place a player request to call
   * @param playerIndex Game Player Index of the player requesting this action
   * @param amount Amount the player must pay in order to call
   */
  public void PlayerActionCall(int playerIndex, int amount)
  {
    // **** create our action ***
    
    GameAction action = new GameAction(
      _players[playerIndex].getPlayerId(), 
      playerIndex, 
      _pokerGameId, 
      GameAction.GameActionTypeCall, 
      amount, 
      _currentRound, 
      _gameActionCount++);
      
    // **** this is now the latest action for the player ****

    _playerLastActions[playerIndex] = action;
    
    // **** request this action ****

    RequestAction(action);
  }

  /**
   * Place a player request to show cards
   * @param playerIndex Game Player Index of the player requesting this action
   */
  public void PlayerActionShow(int playerIndex)
  {
    // **** create our action ***
    
    GameAction action = new GameAction(
      _players[playerIndex].getPlayerId(), 
      playerIndex, 
      _pokerGameId, 
      GameAction.GameActionTypeShowCards, 
      0, 
      _currentRound, 
      _gameActionCount++);
      
    // **** this is now the latest action for the player ****

    _playerLastActions[playerIndex] = action;
    
    // **** request this action ****

    RequestAction(action);
  }

  /**
   * Get the list of valid actions for the player, given the current game state
   * @return List of Valid GameActionTypes
   */
  public List<GameActionTypes> getValidPlayerActions()
  {
    List<GameActionTypes> validActions = new ArrayList<GameActionTypes>();

    // **** act depending on round ****

    switch (_currentRound)
    {
      case 1:
        validActions.add(GameActionTypes.Call);
        validActions.add(GameActionTypes.Fold);
        validActions.add(GameActionTypes.Raise);
      break;

      case 2:
        validActions.add(GameActionTypes.Call);
        validActions.add(GameActionTypes.Fold);

        // **** check to see if raise is valid ****
        
        for (int handIndex = 0; handIndex < _playerCount; handIndex++)
        {
          // **** check for a face-up pair ****

          if (_playerHands[handIndex].getHandTypeFaceUp() == PokerScorer.ScoreTypeOnePair)
          {
            // **** raising is valid ****

            validActions.add(GameActionTypes.Raise);

            // **** stop looking ****

            break;
          }
        }

      break;

      case 3:
        validActions.add(GameActionTypes.Call);
        validActions.add(GameActionTypes.Fold);
      break;

      case 4:
        validActions.add(GameActionTypes.Call);
        validActions.add(GameActionTypes.Fold);
        validActions.add(GameActionTypes.Raise);
      break;

      case 5:
        validActions.add(GameActionTypes.Call);
        validActions.add(GameActionTypes.Fold);
        validActions.add(GameActionTypes.Raise);
      break;

      case 6:
        validActions.add(GameActionTypes.ShowCards);
        validActions.add(GameActionTypes.Muck);
      break;
    }

    return validActions;
  }

  /**
   * Check to see if this game can be started right now
   * @return True if game is in a state which can be started, false if not
   */
  public boolean CanGameStart()
  {
    // **** check for game over ****

    if (_currentRoundState == GameRoundStateGameOver)
    {
      return true;
    }

    // **** check for game already in progress ****

    if (_currentRound != 0)
    {
      return false;
    }

    // **** check for an invalid number of players ****

    if ((_playerCount < 2) || (_playerCount > 7))
    {
      return false;
    }

    // **** check for invalid low/high stakes ****
    
    if ((_lowStake < 1) ||
        (_lowStake >= _highStake) ||
        (_highStake < 2))
    {
      return false;
    }

    // **** everything passes ****

    return true;
  }

  // /**
  //  * Add a positive amount to the pot
  //  * @param value Amount to add to the pot
  //  * @return New pot total
  //  * @throws InvalidParameterException If the amount to add is negative
  //  */
  // private int AddToPot(int value) throws InvalidParameterException
  // {
  //   // **** make sure the value is positive ****

  //   if (value < 0)
  //   {
  //     throw new InvalidParameterException(String.format("Cannot take money from the pot!"));
  //   }

  //   // **** add this value to the pot ****

  //   _potValue += value;

  //   // **** return our new pot total ****

  //   return _potValue;
  // }
  

  //#endregion Public Interface . . .
}