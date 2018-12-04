/*
 *  project: pokerGame
 *     file: src\test\java\org\csc478\pokerGame\TestCardDeck.java
 *  created: 2018-11-13 11:55:13
 *       by: Gino Canessa
 * modified: 2018-12-03
 *       by: Gino Canessa
 *
 *  summary: 
 */

package org.csc478.pokerGame;

import org.junit.Test;
import org.csc478.pokerGame.models.*;
//import java.security.InvalidParameterException;

import static org.junit.Assert.*;


public class TestCardDeck {
  @Test
  public void testCreateCardDeck01() {

    // **** create deck ****

    CardDeck deck = new CardDeck();

    // **** ****

    assertNotNull(deck);

    assertEquals(PlayingCard.CardSuitClubs, deck.CardAt(0).getCardSuit());
    assertEquals(PlayingCard.CardRankTwo, deck.CardAt(0).getCardRank());

    assertEquals(PlayingCard.CardSuitSpades, deck.CardAt(51).getCardSuit());
    assertEquals(PlayingCard.CardRankAce, deck.CardAt(51).getCardRank());
  }

  
  @Test
  public void testCreateCardDeck02() {

    // **** create deck ****

    CardDeck deck = new CardDeck();

    // **** ****

    assertNotNull(deck);

    int cardsClubs = 0;
    int cardsDiamonds = 0;
    int cardsHearts = 0;
    int cardsSpades = 0;

    // **** check the deck ****

    for (int cardIndex = 0; cardIndex < 52; cardIndex++)
    {
      switch (deck.CardAt(cardIndex).getCardSuit())
      {
        case PlayingCard.CardSuitClubs:
        {
          cardsClubs++;
        }
        break;
        case PlayingCard.CardSuitDiamonds:
        {
          cardsDiamonds++;
        }
        break;
        case PlayingCard.CardSuitHearts:
        {
          cardsHearts++;
        }
        break;
        case PlayingCard.CardSuitSpades:
        {
          cardsSpades++;
        }
        break;
      }
    }

    assertEquals(13, cardsClubs);
    assertEquals(13, cardsDiamonds);
    assertEquals(13, cardsHearts);
    assertEquals(13, cardsSpades);
  }

  

  @Test
  public void testShuffleDeck01() {

    // **** create deck ****

    CardDeck unshuffledDeck = new CardDeck();
    CardDeck shuffledDeck = new CardDeck();

    // **** ****

    assertNotNull(unshuffledDeck);
    assertNotNull(shuffledDeck);

    // **** shuffle the deck ****

    shuffledDeck.ShuffleDeck();

    assertNotNull(shuffledDeck);

    int matchingCardCount = 0;
    
    // **** traverse the deck looking for unshuffled cards ****

    for (int cardIndex = 0; cardIndex < 52; cardIndex++)
    {
      if ((shuffledDeck.CardAt(cardIndex).getCardRank() == unshuffledDeck.CardAt(cardIndex).getCardRank()) &&
          (shuffledDeck.CardAt(cardIndex).getCardSuit() == unshuffledDeck.CardAt(cardIndex).getCardSuit()))
      {
        matchingCardCount++;
      }
    }

    // **** do not allow more than 5 cards to stay in place ****

    assertTrue(String.format("Too many maching cards: %d",matchingCardCount), (matchingCardCount < 6));
  }

  
  @Test
  public void testShuffleDeck02() {

    // **** create deck ****

    CardDeck deck = new CardDeck();

    deck.ShuffleDeck();

    // **** ****

    assertNotNull(deck);

    int cardsClubs = 0;
    int cardsDiamonds = 0;
    int cardsHearts = 0;
    int cardsSpades = 0;

    // **** check the deck ****

    for (int cardIndex = 0; cardIndex < 52; cardIndex++)
    {
      switch (deck.CardAt(cardIndex).getCardSuit())
      {
        case PlayingCard.CardSuitClubs:
        {
          cardsClubs++;
        }
        break;
        case PlayingCard.CardSuitDiamonds:
        {
          cardsDiamonds++;
        }
        break;
        case PlayingCard.CardSuitHearts:
        {
          cardsHearts++;
        }
        break;
        case PlayingCard.CardSuitSpades:
        {
          cardsSpades++;
        }
        break;
      }
    }

    assertEquals(13, cardsClubs);
    assertEquals(13, cardsDiamonds);
    assertEquals(13, cardsHearts);
    assertEquals(13, cardsSpades);
  }

  @Test
  public void testBurnCard01() {
    // **** create deck ****

    CardDeck deck = new CardDeck();

    // **** burn the fist card ****

    deck.CardAt(0).setCardState(PlayingCard.CardStateOutOfPlay);

    // **** ****

    assertEquals(PlayingCard.CardStateOutOfPlay, deck.CardAt(0).getCardState());
  }

}