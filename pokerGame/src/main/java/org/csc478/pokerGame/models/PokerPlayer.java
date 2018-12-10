/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PokerPlayer.java
 *  created: 2018-11-09 13:03:59
 *       by: Gino Canessa
 * modified: 2018-11-12
 *       by: Gino Canessa
 *
 *  summary: A poker player object
 */

package org.csc478.pokerGame.models;

import java.util.Random;
import java.util.UUID;

public class PokerPlayer {

  //#region Private Constants . . .

  /** The initial amount of money (in dollars) a player starts with */
  private static final int _initialPlayerDollars = 5000;

  /** The confidence threshold required to raise (-1.0 - 1.0) */
  private static final double _confidenceToRaise = 0.4;

  /** The confidence threshold for raising with high stake instead of low */
  private static final double _confidenceForHighStake = 0.75;

  /** The confidence threshold required to fold */
  private static final double _confidenceToFold = -0.5;

  // /** The confidence threshold required to call */
  // private static final double _confidenceToCall = 0.00;

  //#endregion Private Constants . . .

  //#region Object Variables . . .

  /** Unique player id */
  private final UUID _playerId;

  /** Player name */
  private String _name;

  /** Flag for if this is a computer player */
  private final boolean _isComputer;

  /** If this is a computer player, the skill level */
  private int _skillLevel;

  /** Total number of wins for this player */
  private int _wins;

  /** Total number of losses for this player */
  private int _losses;

  /** Current number of dollars the player has */
  private int _dollars;

  /** Current amount of debt (in dollars) this player has accrued */
  private int _debt;

  /** This player's index in its current game */
  private int _currentGamePlayerIndex;

  /** A random number generator for this player */
  private Random _rand;

  /** Action tolerance based on skill level */
  private double _actionTolerance;

  /** Chance to bluff, based on skill level */
  private double _bluffChance;

  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Gets the unique player identifier
   * @return The unique player identifier
   */
  public UUID getPlayerId() { return _playerId; }

  /**
   * Gets the player name
   * @return The player name
   */
  public String getPlayerName() { return _name; }
  /**
   * Sets the player name
   * @param value The player name
   */
  public void setPlayerName(final String value) { _name = value; }

  /**
   * Gets the flag for if this is a computer player
   * @return True if this is a computer player, false if it is a human player
   */
  public boolean getIsComputer() { return _isComputer; }

  /**
   * Gets the skill level of a computer player,
   * undefined for human players
   * @return Skill level (1-10) for computer players
   */
  public int getSkillLevel() { return _skillLevel; }
  /**
   * Sets the skill level for a computer player,
   * undefined for human players
   * @param value Skill level (1-10) for computer players
   */
  public void setSkillLevel(final int value) { _skillLevel = value; }

  /**
   * Gets the number of wins for this player
   * @return The number of wins for this player
   */
  public int getWins() { return _wins; }
  /**
   * Sets the number of wins for this player
   * @param value The number of losses for this player
   */
  public void setWins(final int value) { _wins = value; }

  /**
   * Gets the number of losses for this player
   * @return The number of losses for this player
   */
  public int getLosses() { return _losses; }
  /**
   * Sets the number of losses for this player
   * @param value The number of losses for this player
   */
  public void setLosses(final int value) {_losses = value;}

  /**
   * Gets the current amount of money (in dollars) ths player has
   * @return Current number of dollars this player has
   */
  public int getDollars() { return _dollars; }
  /**
   * Sets the current amount of money (in dollars) this player has
   * @param value Current number of dollars this player has
   */
  public void setDollars(final int value) { _dollars = value; }

  /**
   * Gets the total amount of debt this player has accrued
   * @return Number of dollars in debt this player is
   */
  public int getDebt() { return _debt; }
  /**
   * Sets the total amount of debt this player has accrued
   * @param value Number of dollars in debt this player is
   */
  public void setDebt(final int value) { _debt = value; }

  /**
   * Set the currrent game's player index for this player (for convenience)
   * @param value The player index for this player in its current game
   */
  public void setGamePlayerIndex(final int value) { _currentGamePlayerIndex = value;}

  //#endregion Accessors and Mutators . . .

  //#region Constructors . . .

  /**
   * Default Player constructor
   * @param playerName Name of the player
   * @param isComputer True if this is a computer player, false if this is a human player
   * @param skillLevel Skill level (1 - 10) for computer players, not required for human players
   */
  public PokerPlayer(
    final String playerName,
    final boolean isComputer,
    final int skillLevel
    )
  {
    // **** set reasonable values ****

    _playerId = UUID.randomUUID();
    _name = playerName;
    _isComputer = isComputer;
    _skillLevel = skillLevel;

    _wins = 0;
    _losses = 0;
    _dollars = _initialPlayerDollars;
    _debt = 0;

    // **** initialize our random number generator ****

    _rand = new Random();

    // **** configure tolerances based on skill level ****

    if (skillLevel != 0)
    {
      _actionTolerance = 0.01 * skillLevel;
      _bluffChance = 0.01 * skillLevel;
    }
  }

  /**
   * Factory-style function to create a human player object
   * @param playerName Name of the human player to create
   * @return Player object
   */
  public PokerPlayer CreateHumanPlayer(String playerName)
  {
    return new PokerPlayer(playerName, false, 0);
  }

  /**
   * Factory-style function to create a computer player object
   * @param playerName Name of the computer player to create
   * @param skillLevel Skill level (1-10) of this player
   * @return Player object
   */
  public PokerPlayer CreateComputerPlayer(String playerName, int skillLevel)
  {
    return new PokerPlayer(playerName, true, skillLevel);
  }

  //#endregion Constructors . . .


  //#region Public Interface . . .

  public void SetWinner(int handType, int potAmount)
  {
    //
  }

  /**
   * Perform a game action based on current knowable state
   * @param hand This player's current hand
   * @param game Game object
   */
  public void RequestActionForGame(
    final PlayerHand hand, 
    PokerGame game
    )
  {
    // **** get information we need from the game ****

    int roundNumber = game.getRoundNumber();

    // **** get the minimum bet to stay in ****

    int betMinimum = game.getCurrentMinumumBetForPlayerIndex(_currentGamePlayerIndex);

    // **** check to see if we can even stay in ****

    if (betMinimum > _dollars)
    {
      // **** have to fold/muck ****

      if (roundNumber < 6)
      {
        game.PlayerActionFold(_currentGamePlayerIndex);
      }
      else
      {
        game.PlayerActionMuck(_currentGamePlayerIndex);
      }

      // ****  nothing else to do ****

      return;
    }

    // **** grab our current scores ****

    int scoreTotal = hand.getScoreTotal();
    
    // **** grab our array of face up scores ****

    int faceUpScores[] = game.getOtherPlayerFaceUpScores(_currentGamePlayerIndex);

    // **** start with 0 confidence - stay in game but don't raise ****

    double confidence = 0.0;

    // **** loop over face up scores building our confidence level ****

    for (int scoreIndex = 0; scoreIndex < faceUpScores.length; scoreIndex++)
    {
      // **** act depending on which is higher - ties go to lower confidence since this includes ALL of my cards ****

      if (scoreTotal > faceUpScores[scoreIndex])
      {
        confidence += _rand.nextDouble();
      }
      else
      {
        confidence -= _rand.nextDouble();
      }
    }

    // **** adjust confidence based on number of active players ****

    confidence = confidence / (double)game.getNumberOfActivePlayers();

    // **** check for final round actions ****

    if (roundNumber >= 6)
    {
      // **** check for muck ****

      if (confidence < _confidenceToFold)
      {
        // **** muck ****

        game.PlayerActionMuck(_currentGamePlayerIndex);

        // **** done ****

        return;
      }

      // **** show ****

      game.PlayerActionShow(_currentGamePlayerIndex);

      // **** done ****

      return;
    }

    // **** check for bluff chance ****

    if (_rand.nextDouble() < _bluffChance)
    {
      // **** make high confidence ****

      confidence = _confidenceToRaise + _rand.nextDouble();
    }

    // **** start by checking for raising ****

    if ((confidence - _actionTolerance) > _confidenceToRaise)
    {
      // **** determine raise amount ****

      int amount = 0;

      // **** look at confidence for base stake ****

      if (confidence > _confidenceForHighStake)
      {
        // **** determine how much we'd like to bet ***

        amount = (int)(game.getHighStake() * (_rand.nextDouble() * 5));
      }
      else
      {
        // **** determine how much we'd like to bet ***

        amount = (int)(game.getLowStake() * (_rand.nextDouble() * 5));
      }

      // **** ask the game for the nearest allowed betting amount ****

      amount = game.getClosestValidBet(amount);

      // **** make sure our bet is at least the minimum bet ****

      if (amount <= betMinimum)
      {
        // **** just call, we aren't confident to raise more than this ****

        game.PlayerActionCall(_currentGamePlayerIndex, betMinimum);
      }
      else
      {
        // **** raise ****

        game.PlayerActionRaise(_currentGamePlayerIndex, amount);
      }

      // **** nothing else to do ****

      return;
    }

    // **** check for confidence to stay in ****

    if ((confidence - _actionTolerance) > _confidenceToFold)
    {
      // **** just call, we aren't confident to raise more than this ****

      game.PlayerActionCall(_currentGamePlayerIndex, betMinimum);

      // **** nothing else to do ****

      return;
    }
  
    // **** have to fold/muck ****

    if (roundNumber < 6)
    {
      game.PlayerActionFold(_currentGamePlayerIndex);
    }
    else
    {
      game.PlayerActionMuck(_currentGamePlayerIndex);
    }

    // ****  nothing else to do ****

    return;
  }

  //#endregion Public Interface . . .

}