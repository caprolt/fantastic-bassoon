/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\GameAction.java
 *  created: 2018-11-09 13:15:02
 *       by: Gino Canessa
 * modified: 2018-11-12
 *       by: Gino Canessa
 *
 *  summary: A Poker Game Action
 */

package org.csc478.pokerGame.models;

import java.util.Date;
import java.util.UUID;

public class GameAction {
  //#region Public Constants . . .

  public static final char GameActionTypePayAnte = 'P';
  public static final char GameActionTypeCall = 'C';
  public static final char GameActionTypeRaise = 'R';
  public static final char GameActionTypeFold = 'F';
  public static final char GameActionTypeMuck = 'M';
  public static final char GameActionTypeRequestAnte = 'A';
  public static final char GameActionTypeBurnCard = 'B';
  public static final char GameActionTypeDealFaceUp = 'U';
  public static final char GameActionTypeDealFaceDown = 'D';
  public static final char GameActionTypeGetFirstPlayerForRound = 'G';
  public static final char GameActionTypeWaitOnPlayerAction = 'W';
  public static final char GameActionScoring = 'S';
  
  //#endregion Public Constants . . .

  //#region Object Variables . . .

  /** Unique identifier for this game action */
  private final UUID _gameActionId;

  /** Unique identifier for the player performing this action */
  private final UUID _playerId;

  /** Unique identifier for the game this action applies to */
  private final UUID _pokerGameId;

  /** Type of this action */
  private final char _actionType;

  /** Round number within the game this action is from */
  private final int _roundNumber;

  /** Action number within the game */
  private final int _gameActionNumber;

  /** Date and time this action was performed */
  private final Date _actionPerformedDateTime;

  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Gets the game action id for this action
   * @return UUID representing this game action
   */
  public UUID getGameActionId() { return _gameActionId; }

  /**
   * Gets the player id for the player which perfomed the action
   * @return UUID representing the player that performed this action
   */
  public UUID getPlayerId() { return _playerId; }
  
  /**
   * Gets the id of the game which this action was performed in
   * @return UUID representing the game this action was performed in
   */
  public UUID getPokerGameId() { return _pokerGameId; }

  /**
   * Gets the action type for this action
   * @return Action type of this action
   */
  public char getActionType() { return _actionType; }

  /**
   * Gets the round number this action was performed in
   * @return Round number this action was performed in
   */
  public int getRoundNumber() { return _roundNumber; }

  /**
   * Gets the game action number (sequential) for this action
   * @return Game action number (sequential within game) for this action
   */
  public int getGameActionNumber() { return _gameActionNumber; }

  /**
   * Gets the date and time this action was performed
   * @return Date (and time) this action was performed at
   */
  public Date getActionPerformedDateTime() { return _actionPerformedDateTime; }

  //#endregion Accessors and Mutators . . .

  //#region Class Variables . . .

  //#endregion Class Variables . . .

  //#region Constructors . . .

  public GameAction(
    final UUID playerId,
    final UUID pokerGameId,
    final char actionType,
    final int roundNumber,
    final int gameActionNumber
    )
  {
    _gameActionId = UUID.randomUUID();

    _playerId = playerId;
    _pokerGameId = pokerGameId;
    _actionType = actionType;
    _roundNumber = roundNumber;
    _gameActionNumber = gameActionNumber;
    _actionPerformedDateTime = new Date();
  }

  //#endregion Constructors . . .
}