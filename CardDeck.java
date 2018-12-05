/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\CardDeck.java
 *  created: 2018-11-09 13:03:35
 *       by: Gino Canessa
 * modified: 2018-11-12
 *       by: Gino Canessa
 *
 *  summary: A deck of playing cards.
 */
package org.csc478.pokerGame.models;

import java.security.InvalidParameterException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CardDeck {
  //#region Private Constants . . .

  /** Number of cards in the deck  */
  private static final int _cardsPerDeck = 52;

  /** Number of cards in the deck minus one (for boundary checking) */
  private static final int _cardsPerDeckMinusOne = _cardsPerDeck - 1;

  //#endregion Private Constants . . .

  //#region Object Variables . . .

  /** The cards in this deck */
  private static PlayingCard[] _cards;

  /** A random number generator for this deck */
  private Random _rand;

  //#endregion Object Variables . . .

  //#region Constructors . . .

  /**
   * Default constructor makes a standard deck of cards
   */
  public CardDeck()
  {
    // **** initialize our random number generator ****

    _rand = new Random();

    // **** create our array of cards ****

    _cards = new PlayingCard[_cardsPerDeck];

    // **** initialize our deck ****

    IniializeDeck();
  }

  //#endregion Constructors . . .

  //#region Public Interface . . .

  /**
   * Get the card within the deck at the specified index
   * @param cardIndex 0-based index of the card in the deck
   * @return The playing card
   */
  public static PlayingCard CardAt(final int cardIndex)
  {
    // **** sanity check ****

    if ((cardIndex < 0) || (cardIndex > _cardsPerDeckMinusOne))
    {
      throw new InvalidParameterException(String.format("Invalid card request, card index: %d", cardIndex));
    }

    // **** return the card ****
    //System.out.println("Card at:"+_cards[cardIndex].getCardSuit());
    
    //System.out.println("Card at:"+_cards[cardIndex].getCardRank());
    return _cards[cardIndex];
    
  }
//adding getters to the card deck to retrieve card rank and suit to GUI
  
  public static int getCardSuit(final int cardIndex) {
	  
	  int cardSuit = _cards[cardIndex].getCardSuit();
	  return cardSuit;
	  
  }
  
  public static int getCardRank(final int cardIndex){
	  int cardRank = _cards[cardIndex].getCardRank();
	  return cardRank;
	  
  }
  
  /**
   * Shuffle the deck of cards
   */
  public void ShuffleDeck()
  {
    // **** build a list of indicies ****

    List<Integer> cardIndices = new ArrayList<Integer>(_cardsPerDeck);

    // **** push our indicies onto the list ****

    for (int index = 0; index < _cardsPerDeck; index++)
    {
      cardIndices.add(index, index);
    }

    // **** grab random indicies until we have used all cards ****

    for (int deckCardIndex = 0; deckCardIndex < _cardsPerDeck; deckCardIndex++)
    {
    	
    	
    	
      // **** get a random card from what is left ****

      int nextCardIndex = _rand.nextInt(cardIndices.size());

      // **** set our card ****

      _cards[deckCardIndex] = new PlayingCard(cardIndices.get(nextCardIndex));

      // **** remove our index - this card has been used ****
      //System.out.print(deckCardIndex + ". " + _cards[deckCardIndex].getCardRank());
      //System.out.println(", " + _cards[deckCardIndex].getCardSuit());
      cardIndices.remove(nextCardIndex);
    }
  }


  //#endregion Public Interface . . .

  //#region Internal Functions . . .

  /**
   * Initialize a deck of cards
   */
  private void IniializeDeck()
  {
    // **** initialize a deck ****

    for (int cardIndex = 0; cardIndex < _cardsPerDeck; cardIndex++)
    {
      // **** create the card for this index ****

      _cards[cardIndex] = new PlayingCard(cardIndex);
    }
  }

  //#endregion Internal Functions . . .
}