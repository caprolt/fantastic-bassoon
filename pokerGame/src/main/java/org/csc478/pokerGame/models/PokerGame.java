/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PokerGame.java
 *  created: 2018-11-09 13:09:08
 *       by: Gino Canessa
 * modified: 2018-11-09
 *       by: Gino Canessa
 *
 *  summary: Internal representation of a single game of poker
 */

package org.csc478.pokerGame.models;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activity.InvalidActivityException;

public class PokerGame {

  //#region Public Constants . . .

  public static final int GameRoundStateInvalid = 0;
  public static final int GameRoundStateWaitingForPlayer = 1;
  public static final int GameRoundStateDealing = 2;
  public static final int GameRoundStateBetting = 3;
  public static final int GameRoundStateGameOver = 4;

  //#endregion Public Constants . . .

  //#region Public Variables . . .

  //#endregion Public Variables . . .

  //#region Object Variables . . .

  /** Unique identifier for this game */
  private final UUID _pokerGameId;

  /** Number of actions take in this game so far */
  private int _gameActionCount;

  /** List of actions performed in this game */
  private List<GameAction> _gameActions;

  /** List of players in this game */
  private List<PokerPlayer> _players;

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

  /** Index of the player which needs to take next action */
  private int _playerTurnIndex;

  /** Number of players which have had a turn this round */
  private int _playerTurnsThisRound;

  /** Amount (in dollars) of the current pot */
  private int _potValue;

  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Gets the Ante (in dollars) for this game
   * @return
   */
  public int getAnte() { return _ante; }

  /**
   * Gets the Low Stake Limit (in dollars) for this game
   * @return
   */
  public int getLowStake() { return _lowStake; }

  /**
   * Gets the High Stake Limit (in dollars) for this game
   * @return
   */
  public int getHighStake() { return _highStake; }

  /**
   * Gets the Number of Players in this game
   * @return
   */
  public int getNumberOfPlayers() { return _players.size(); }

  /**
   * Get the current round number of the game
   * @return
   */
  public int getRoundNumber() { return _currentRound; }

  /**
   * Get the state of the current round of play
   * @return
   */
  public int getRoundState() { return _currentRoundState; }

  //#endregion Accessors and Mutators . . .

  //#region Constructors . . .

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

    if (lowStake <= highStake)
    {
      throw new InvalidParameterException(
        String.format("Invalid table stakes, low: %d must be smaller than high: %d", lowStake, highStake));
    }

    // **** generate a UUID ****

    _pokerGameId = UUID.randomUUID();

    _gameActionCount = 0;
    _gameActions = new ArrayList<GameAction>();

    // **** set basic game information ****

    _ante = ante;
    _lowStake = lowStake;
    _highStake = highStake;
    
    // **** no players have been added yet ****

    _players = new ArrayList<PokerPlayer>(7);
    _playerTurnIndex = 0;
    _playerTurnsThisRound = 0;

    _currentRound = 0;

    // **** set up our cards ****

    _gameDeck = new CardDeck();
    _numberOfCardsUsed = 0;

    _potValue = 0;

  }
  //#endregion Constructors . . .

  //#region Public Interface . . .

  /**
   * Add a player to the game
   * @param player
   * @return Number of players in the game
   * @throws InvalidActivityException Attempt to exceed the allowed number of players
   */
  public int AddPlayer(PokerPlayer player) throws InvalidActivityException
  {
    // **** sanity checks ****

    if (_players.size() > 6)
    {
      throw new InvalidActivityException(String.format("Cannot add another player to the game."));
    }

    // **** append this player ****

    _players.add(player);

    // **** return the number of players ****

    return _players.size();
  }

  // Start Game

  /**
   * Check to see if this game can start ****
   * @return
   */
  public boolean CanGameStart()
  {
    // **** check for game already in progress ****

    if (_currentRound != 0)
    {
      return false;
    }

    // **** check for an invalid number of players ****

    if ((_players.size() < 2) || (_players.size() > 7))
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

  /**
   * Add a positive amount to the pot
   * @param value Amount to add to the pot
   * @return New pot total
   * @throws InvalidParameterException If the amount to add is negative
   */
  public int AddToPot(int value) throws InvalidParameterException
  {
    // **** make sure the value is positive ****

    if (value < 0)
    {
      throw new InvalidParameterException(String.format("Cannot take money from the pot!"));
    }

    // **** add this value to the pot ****

    _potValue += value;

    // **** return our new pot total ****

    return _potValue;
  }
  

  //#endregion Public Interface . . .
}