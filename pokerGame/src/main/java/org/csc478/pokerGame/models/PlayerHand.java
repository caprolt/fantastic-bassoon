/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PlayerHand.java
 *  created: 2018-11-12 12:36:29
 *       by: Gino Canessa
 * modified: 2018-11-12
 *       by: Gino Canessa
 *
 *  summary: Class to track a player's hand
 */

package org.csc478.pokerGame.models;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
  //#region Private Constants . . .

  /** Total number of cards in a hand */
  private static final int _handSize = 7;

  /** Total nubmer of cards in hand minus one (for boundary checking) */
  private static final int _handSizeMinusOne = _handSize - 1;

  /** Flags for if a card is dealt face up or down to this hand */
  private static final boolean _cardFaceUpArray[] = {
    false,          // Round 1, Card 1, Total Card 1
    false,          // Round 1, Card 2, Total Card 2
    true,           // Round 1, Card 3, Total Card 3
    true,           // Round 2, Card 1, Total Card 4
    true,           // Round 3, Card 1, Total Card 5
    true,           // Round 4, Card 1, Total Card 6
    false,          // Round 5, Card 1, Total Card 7
  };

  //#endregion Private Constants . . .

  //#region Object Variables . . .

  /** Number of cards currently in this hand */
  private int _numberOfCardsInHand;

  /** Cards in this hand */
  private PlayingCard _cardsInHand[];

  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Gets a list of the face-up cards in this player's hand
   * @return List of face-up cards in this hand
   */
  public List<PlayingCard> getFaceUpCards()
  {
    List<PlayingCard> cardsToReturn = new ArrayList<PlayingCard>();

    // **** traverse our array adding cards ****

    for (int cardIndex = 0; cardIndex < _numberOfCardsInHand; cardIndex++)
    {
      // **** check for face down ****

      if (!_cardFaceUpArray[cardIndex])
      {
        // **** skip ****

        continue;
      }

      // **** add to our list ****

      cardsToReturn.add(_cardsInHand[cardIndex]);
    }

    // **** return our list ***

    return cardsToReturn;
  }

  /**
   * Get a list of all cards in this hand
   * @return List of all cards in this hand
   */
  public List<PlayingCard> getCards()
  {
    List<PlayingCard> cardsToReturn = new ArrayList<PlayingCard>();

    // **** traverse our array adding cards ****

    for (int cardIndex = 0; cardIndex < _numberOfCardsInHand; cardIndex++)
    {
      // **** add to our list ****

      cardsToReturn.add(_cardsInHand[cardIndex]);
    }

    // **** return our list ***

    return cardsToReturn;
  }
  
  /**
   * Add a card to a hand
   * @param card Playing Card to add
   * @return Number of cards now in hand
   */
  public int AddCardToHand(PlayingCard card)
  {
    // **** check for maximum cards ****

    if (_numberOfCardsInHand == _handSizeMinusOne)
    {
      // **** cannot add ****

      return _numberOfCardsInHand;
    }

    // **** append this card to our array ****

    _cardsInHand[_numberOfCardsInHand++] = card;

    // **** return our new number of cards ****

    return _numberOfCardsInHand;
  }


  //#endregion Accessors and Mutators . . .

  //#region Constructors . . .

  /**
   * Default constructor - makes an empty hand
   */
  public PlayerHand()
  {
    // **** create our card array ****

    _cardsInHand = new PlayingCard[_handSize];

    // **** there are no cards in this hand ****

    _numberOfCardsInHand = 0;
  }
  //#endregion Constructors . . .

  
  /**
   * Clear a hand of any existing cards
   * @param returnCards True to return cards to deck, false to remove from play
   */
  public void ClearHand(final boolean returnCards)
  {
    int nextState = returnCards ? PlayingCard.CardStateInDeck : PlayingCard.CardStateOutOfPlay;

    // **** update the state of our cards ****

    for (int cardIndex = 0; cardIndex < _numberOfCardsInHand; cardIndex++)
    {
      // **** update this card ****

      _cardsInHand[cardIndex].setCardState(nextState);
    }

    // **** clear our array ****

    _cardsInHand = new PlayingCard[_handSize];

    // **** we have no cards ****

    _numberOfCardsInHand = 0;
  }

}