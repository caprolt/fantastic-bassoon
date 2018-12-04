/*
 *  project: c:\Users\gino.canessa\Documents\UIS\2018_Fall\CSC478\fantastic-bassoon
 *     file: pokerGame\src\main\java\org\csc478\pokerGame\models\PokerEventListener.java
 *  created: 2018-12-04 17:20:07
 *       by: Gino Canessa
 * modified: 2018-12-04
 *       by: Gino Canessa
 *
 *  summary: Example implementation of poker listener
 */


package org.csc478.pokerGame.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PokerEventListener implements PropertyChangeListener {
  
  private int _actionCount = 0;

  public void propertyChange(PropertyChangeEvent event) {
    _actionCount++;

    GameAction action = (GameAction)event.getNewValue();

    System.out.println(String.format("Recevied action number: %d, type: %d, for player: %d (%s)",
      action.getGameActionNumber(),
      action.getActionType(),
      action.getGamePlayerIndex(),
      action.getPlayerId()
      ));
  }

  public int getActionCount() { return _actionCount; }
}
