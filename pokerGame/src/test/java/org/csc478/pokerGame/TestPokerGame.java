/*
 *  project: c:\Users\gino.canessa\Documents\UIS\2018_Fall\CSC478\fantastic-bassoon
 *     file: pokerGame\src\test\java\org\csc478\pokerGame\TestPokerGame.java
 *  created: 2018-12-04 17:14:55
 *       by: Gino Canessa
 * modified: 2018-12-04
 *       by: Gino Canessa
 *
 *  summary: Unit tests for Poker Game
 */

package org.csc478.pokerGame;

import org.junit.Test;
import org.csc478.pokerGame.models.*;


import javax.activity.InvalidActivityException;

import static org.junit.Assert.*;

public class TestPokerGame {
  @Test
  public void testCreateGame01() {

    PokerGame game = new PokerGame(0, 5, 10);

    assertNotNull(game);
  }

  @Test
  public void testCreateGame02() {

    PokerGame game = new PokerGame(0, 5, 10);

    assertNotNull(game);

    PokerPlayer player1 = new PokerPlayer("Player 1", true, 0);
    PokerPlayer player2 = new PokerPlayer("Player 2", true, 1);
    PokerPlayer player3 = new PokerPlayer("Human Player", false, 0);

    try {
      game.AddPlayer(player1);
      game.AddPlayer(player2);
      game.AddPlayer(player3);
    } catch (InvalidActivityException e) {
      e.printStackTrace();
      assert(false);
    }
    
    PokerEventListener listener = new PokerEventListener();

    game.AddGameEventListener(listener);

    game.StartGame();

    assertNotEquals(0, listener.getActionCount());
  }

}
