/*
 *  project: pokerGame
 *     file: src\test\java\org\csc478\pokerGame\TestPlayingCard.java
 *  created: 2018-11-13 11:20:05
 *       by: Gino Canessa
 * modified: 2018-11-13
 *       by: Gino Canessa
 *
 *  summary: Tests for org.cyc478.pokerGame.models.PlayingCard
 */

package org.csc478.pokerGame;

import org.junit.Test;
import org.csc478.pokerGame.models.*;
import java.security.InvalidParameterException;

import static org.junit.Assert.*;


public class TestPlayingCard {
  @Test
  public void testCreateCardSuitRank01() {
    // **** create lowest valid card ****

    PlayingCard card = new PlayingCard(PlayingCard.CardSuitClubs, PlayingCard.CardRankTwo);

    assertNotNull(card);

    assertEquals(PlayingCard.CardSuitClubs, card.getCardSuit());
    assertEquals(PlayingCard.CardRankTwo, card.getCardRank());
  }

  @Test
  public void testCreateCardSuitRank02() {
    // **** create highest valid card ****
    
    PlayingCard card = new PlayingCard(PlayingCard.CardSuitSpades, PlayingCard.CardRankAce);

    assertNotNull(card);

    assertEquals(PlayingCard.CardSuitSpades, card.getCardSuit());
    assertEquals(PlayingCard.CardRankAce, card.getCardRank());
  }

  @Test(expected = InvalidParameterException.class)
  public void testCreateCardSuitRank03() {
    // **** create low suit invalid card ****
    
    PlayingCard card = new PlayingCard(0x00, PlayingCard.CardRankTwo);

    assertNull(card);
  }

  @Test(expected = InvalidParameterException.class)
  public void testCreateCardSuitRank04() {
    // **** create high suit invalid card ****
    
    PlayingCard card = new PlayingCard(0x50, PlayingCard.CardRankTwo);

    assertNull(card);
  }

  @Test(expected = InvalidParameterException.class)
  public void testCreateCardSuitRank05() {
    // **** create low rank invalid card ****
    
    PlayingCard card = new PlayingCard(PlayingCard.CardSuitClubs, 0x00);

    assertNull(card);
  }

  @Test(expected = InvalidParameterException.class)
  public void testCreateCardSuitRank06() {
    // **** create high rank invalid card ****
    
    PlayingCard card = new PlayingCard(PlayingCard.CardSuitClubs, 0x0F);

    assertNull(card);
  }

  @Test
  public void testCreateCardIndex01() {
    // **** create lowest valid card ****

    PlayingCard card = new PlayingCard(0);

    assertNotNull(card);

    assertEquals(PlayingCard.CardSuitClubs, card.getCardSuit());
    assertEquals(PlayingCard.CardRankTwo, card.getCardRank());
  }

  @Test
  public void testCreateCardIndex02() {
    // **** create highest valid card ****

    PlayingCard card = new PlayingCard(51);

    assertNotNull(card);

    assertEquals(PlayingCard.CardSuitSpades, card.getCardSuit());
    assertEquals(PlayingCard.CardRankAce, card.getCardRank());
  }

  @Test(expected = InvalidParameterException.class)
  public void testCreateCardIndex03() {
    // **** create low index invalid card ****
    
    PlayingCard card = new PlayingCard(-1);

    assertNull(card);
  }

  @Test(expected = InvalidParameterException.class)
  public void testCreateCardIndex04() {
    // **** create high index invalid card ****
    
    PlayingCard card = new PlayingCard(52);

    assertNull(card);
  }

}
