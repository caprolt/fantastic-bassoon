/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PlayingCard.java
 *  created: 2018-11-09 13:03:08
 *       by: Gino Canessa
 * modified: 2018-11-09
 *       by: Gino Canessa
 *
 *  summary: A standard playing card, with Suit and Rank.
 */

package org.csc478.pokerGame.models;

import java.security.InvalidParameterException;

public class PlayingCard {
  //#region Private Constants . . .

  /** Invalid card suit (low invalid boundary) */
  private static final int _cardSuitInvalidLow = 0x00;

  /** The lowest valid card suit (low valid boundary) */
  private static final int _cardSuitFirst = 0x10;

  /** Invalid card suit (high invalid boundary) */
  private static final int _cardSuitInvalidHigh = 0x50;

  /** Invalid card rank (low invalid boundary) */
  private static final int _cardRankInvalidLow = 0x00;

  /** The lowest valid card rank (low valid boundary) */
  private static final int _cardRankFirst = 0x01;

  /** Invalid card rank (high invalid boundary) */
  private static final int _cardRankInvalidHigh = 0x0E;

  //#endregion Private Constants . . .


  //#region Public Constants . . .

  /**   Card is in the deck (unused). */
  public static final int CardStateInDeck = 0;
  /**   Card has been dealt face down. */
  public static final int CardStateFaceDown = 1;
  /**   Card has been dealt face up. */
  public static final int CardStateFaceUp = 2;
  /**   Card is no longer in play (e.g., burned). */
  public static final int CardStateOutOfPlay = 3;


  /**   The card suit clubs. */
  public static final int CardSuitClubs = 0x10;
  /**   The card suit diamonds. */
  public static final int CardSuitDiamonds = 0x20;
  /**   The card suit hearts. */
  public static final int CardSuitHearts = 0x30;
  /**   The card suit spades. */
  public static final int CardSuitSpades = 0x40;


  /**   The card rank ace. */
  public static final int CardRankAce = 0x01;
  /**   The card rank two. */
  public static final int CardRankTwo = 0x02;
  /**   The card rank three. */
  public static final int CardRankThree = 0x03;
  /**   The card rank four. */
  public static final int CardRankFour = 0x04;
  /**   The card rank five. */
  public static final int CardRankFive = 0x05;
  /**   The card rank six. */
  public static final int CardRankSix = 0x06;
  /**   The card rank seven. */
  public static final int CardRankSeven = 0x07;
  /**   The card rank eight. */
  public static final int CardRankEight = 0x08;
  /**   The card rank nine. */
  public static final int CardRankNine = 0x09;
  /**   The card rank ten. */
  public static final int CardRankTen = 0x0A;
  /**   The card rank jack. */
  public static final int CardRankJack = 0x0B;
  /**   The card rank queen. */
  public static final int CardRankQueen = 0x0C;
  /**   The card rank king. */
  public static final int CardRankKing = 0x0D;

  //#endregion Public Constants . . .

  //#region Object Variables . . .

  private final int _suitRank;
  private int _cardState;

  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Gets the current card state.
   * @return Card State
   */
  public int getCardState() { return _cardState; }

  /**
   * Sets the current card state
   * @param cardState Card state of this card
   */
  public void setCardState(final int cardState) { _cardState = cardState;}


  //#endregion Accessors and Mutators . . .

  //#region Constructors . . .

  /**
   * Create a playing card with a specified suit and rank
   * @param suit Suit of this card (e.g., CardSuitClubs)
   * @param rank Rank of this card (e.g., CardRankAce)
   */
  public PlayingCard(final int suit, final int rank) {
    // **** check our suit and rank to make sure they result in a valid card ****

    int suitRank = suit & rank;

    // **** check to see if this suitRank is invalid ****

    if (!IsValidCard(suitRank))
    {
      // **** cannot create ****

      throw new InvalidParameterException(String.format("Invalid card request, suit: %h, rank: %h", suit, rank));
    }

    // **** set our suit and rank ***

    _suitRank = suitRank;

    // **** new cards are always in deck ****

    _cardState = CardStateInDeck;
  }
  
  /**
   * Create a playing card based on the location within a sorted deck
   * @param cardIndexInDeck 0-based index for card location in deck
   */
  public PlayingCard(final int cardIndexInDeck)
  {
    if ((cardIndexInDeck < 0) || (cardIndexInDeck > 51))
    {
      // **** cannot create ****

      throw new InvalidParameterException(String.format("Invalid card request, deck index: %d", cardIndexInDeck));
    }

    // **** 13 cards per suit, shit suit, add in 1 to offset first card so that values match ranks (Ace is first) ****

    _suitRank = (cardIndexInDeck % 13) + ((int)(cardIndexInDeck / 13) << 4) + 1;

    // **** start with card in deck ****

    _cardState = CardStateInDeck;
  }

  //#endregion Constructors . . .

  //#region Internal Functions . . .

  /**
   * Get a card's suit from a SuitRank value
   * @param value Combined SuitRank value
   * @return Card Suit
   */
  private static final int SuitFromValue(final int value)
  {
    return value & 0xF0;
  }

  /**
   * Get a card's rank from a SuitRank value
   * @param value Combined SuitRank value
   * @return Card Rank
   */
  private static final int RankFromValue(final int value)
  {
    return value & 0x0F;
  }

  /**
   * Check to see if 'value' results in a valid card SuitRank
   * @param value Combined SuitRank value
   * @return True if valid, false if not.
   */
  private static final boolean IsValidCard(final int value)
  {
    int rank = RankFromValue(value);
    int suit = SuitFromValue(value);

    return ((rank > _cardRankInvalidLow) && 
            (rank < _cardRankInvalidHigh) &&
            (suit > _cardSuitInvalidLow) &&
            (suit < _cardSuitInvalidHigh));
  }

  //#endregion Internal Functions . . .
}

