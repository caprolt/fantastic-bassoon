/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PokerPlayer.java
 *  created: 2018-11-09 13:03:59
 *       by: Gino Canessa
 * modified: 2018-11-09
 *       by: Gino Canessa
 *
 *  summary: A poker player object
 */

package org.csc478.pokerGame.models;

import java.util.UUID;

public class PokerPlayer {

  //#region Private Constants . . .

  /** The initial amount of money (in dollars) a player starts with */
  private static final int _initialPlayerDollars = 5000;

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

}