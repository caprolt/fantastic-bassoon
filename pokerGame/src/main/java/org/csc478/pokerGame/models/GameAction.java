/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\GameAction.java
 *  created: 2018-11-09 13:15:02
 *       by: Gino Canessa
 * modified: 2018-11-13
 *       by: Gino Canessa
 *
 *  summary: A Poker Game Action
 */

package org.csc478.pokerGame.models;

import java.util.Date;
import java.util.UUID;

public class GameAction {
  //#region Public Constants . . .

  public static final int GameActionTypeInvalid             = 0;
  public static final int GameActionTypeShuffleDeck         = 1;
  public static final int GameActionTypeCall                = 2;
  public static final int GameActionTypeRaise               = 3;
  public static final int GameActionTypeFold                = 4;
  public static final int GameActionTypeMuck                = 5;
  public static final int GameActionTypeRequestAnte         = 6;
  public static final int GameActionTypeBurnCard            = 7;
  public static final int GameActionTypeDealFaceUp          = 8;
  public static final int GameActionTypeDealFaceDown        = 9;
  public static final int GameActionTypeWaitOnPlayerAction  = 10;
  public static final int GameActionTypeEndGame             = 11;
  public static final int GameActionTypeRequestPlayerAction = 12;
  public static final int GameActionTypeShowCards           = 13;
  
  /**
   * Enum type for game actions, must be in same order as constants 
   * so that ordinals match values
   */
  public enum GameActionTypes {
    Invalid,
    ShuffleDeck,
    Call,
    Raise,
    Fold,
    Muck,
    RequestAnte,
    BurnCard,
    DealFaceUp,
    DealFaceDown,
    WaitOnPlayerAction,
    EndGame,
    RequestPlayerAction,
    ShowCards
  }

  //#endregion Public Constants . . .

  //#region Object Variables . . .

  /** Unique identifier for this game action */
  private final UUID _gameActionId;

  /** Unique identifier for the player performing this action */
  private final UUID _playerId;

  /** Index of this player in its current game */
  private final int _gamePlayerIndex;

  /** Unique identifier for the game this action applies to */
  private final UUID _pokerGameId;

  /** Type of this action */
  private final int _actionType;

  /** Round number within the game this action is from */
  private final int _roundNumber;

  /** Action number within the game */
  private final int _gameActionNumber;

  /** Date and time this action was performed */
  private final Date _actionPerformedDateTime;

  /** Amount value for this action, if applicable */
  private final int _amount;

  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Get the action name for the specified action type
   * @param actionType Action type to name
   * @return Name of the specified action (e.g., "Shuffle Deck")
   */
  public static String getActionName(GameActionTypes actionType) {
    switch (actionType)
    {
      case Invalid: return "Invalid";
      case ShuffleDeck: return "Shuffle Deck";
      case Call: return "Call";
      case Raise: return "Raise";
      case Fold: return "Fold";
      case Muck: return "Muck";
      case RequestAnte: return "Request Ante";
      case BurnCard: return "Burn Card";
      case DealFaceUp: return "Deal Face Up";
      case DealFaceDown: return "Deal Face Down";
      case WaitOnPlayerAction: return "Wait on Player Action";
      case EndGame: return "End Game";
      case RequestPlayerAction: return "Request Player Action";
      case ShowCards: return "Show Cards";
    }
    return "Unknown Action";
  }

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
   * Gets the player index for the player which performed this action in its current game
   * @return Index of the player for this action in its current game
   */
  public int getGamePlayerIndex() { return _gamePlayerIndex; }
  
  /**
   * Gets the id of the game which this action was performed in
   * @return UUID representing the game this action was performed in
   */
  public UUID getPokerGameId() { return _pokerGameId; }

  /**
   * Gets the action type for this action
   * @return Action type of this action
   */
  public int getActionType() { return _actionType; }

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

  /**
   * Gets the amount relevant to this action (if applicable)
   * @return The amount relevant to this action (if applicable)
   */
  public int getAmount() { return _amount; }

  //#endregion Accessors and Mutators . . .

  //#region Class Variables . . .

  //#endregion Class Variables . . .

  //#region Constructors . . .

  public GameAction(
    final UUID playerId,
    final int gamePlayerIndex,
    final UUID pokerGameId,
    final int actionType,
    final int amount,
    final int roundNumber,
    final int gameActionNumber
    )
  {
    _gameActionId = UUID.randomUUID();

    _playerId = playerId;
    _gamePlayerIndex = gamePlayerIndex;
    _pokerGameId = pokerGameId;
    _actionType = actionType;
    _amount = amount;
    _roundNumber = roundNumber;
    _gameActionNumber = gameActionNumber;
    _actionPerformedDateTime = new Date();
  }

  //#endregion Constructors . . .
}