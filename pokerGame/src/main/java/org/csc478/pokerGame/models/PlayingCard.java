/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PlayingCard.java
 *  created: 2018-11-09 13:03:08
 *       by: Gino Canessa
 * modified: 2018-11-12
 *       by: Gino Canessa
 *
 *  summary: A standard playing card, with Suit and Rank.
 */

package org.csc478.pokerGame.models;

import java.security.InvalidParameterException;
import java.util.Comparator;

public class PlayingCard {
  //#region Private Constants . . .

  /** Invalid card suit (low invalid boundary) */
  private static final int _cardSuitInvalidLow = 0x00;

  /** The lowest valid card suit (low valid boundary) */
  private static final int _cardSuitFirst = 0x10;

  /** Invalid card suit (high invalid boundary) */
  private static final int _cardSuitInvalidHigh = 0x50;

  /** Invalid card rank (low invalid boundary) */
  private static final int _cardRankInvalidLow = 0x01;

  /** The lowest valid card rank (low valid boundary) */
  private static final int _cardRankFirst = 0x02;

  /** Invalid card rank (high invalid boundary) */
  private static final int _cardRankInvalidHigh = 0x0F;

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
  // public static final int CardRankAce = 0x01;
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
  /**   The card rank ace */
  public static final int CardRankAce = 0x0E;

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

  /**
   * Gets this card's rank
   * @return This card's rank
   */
  public int getCardRank() { return RankFromValue(_suitRank); }

  /**
   * Get this card's suit
   * @return This card's suit
   */
  public int getCardSuit() { return SuitFromValue(_suitRank); }


  /**
   * Get a string value name for a specified suit
   * @param cardSuit Suit we want the name for
   * @return Name of the suit (e.g., "Clubs")
   */
  public static String getSuitName(int cardSuit) {
    switch (cardSuit)
    {
      case CardSuitClubs:    return "Clubs";
      case CardSuitDiamonds: return "Diamonds";
      case CardSuitHearts:   return "Hearts";
      case CardSuitSpades:   return "Spades";
    }

    return "Unknown Suit";
  }

  /**
   * Get a string value name for a specified rank
   * @param cardRank Rank we want the name for
   * @return Name of the rank (e.g., "Two", "Ace")
   */
  public static String getRankName(int cardRank) {
    switch (cardRank)
    {
      case CardRankTwo:   return "2";
      case CardRankThree: return "3";
      case CardRankFour:  return "4";
      case CardRankFive:  return "5";
      case CardRankSix:   return "6";
      case CardRankSeven: return "7";
      case CardRankEight: return "8";
      case CardRankNine:  return "9";
      case CardRankTen:   return "10";
      case CardRankJack:  return "J";
      case CardRankQueen: return "Q";
      case CardRankKing:  return "K";
      case CardRankAce:   return "A";
    }

    return "Unknown Rank";
  }

  //#endregion Accessors and Mutators . . .

  //#region Constructors . . .

  /**
   * Create a playing card with a specified suit and rank
   * @param suit Suit of this card (e.g., CardSuitClubs)
   * @param rank Rank of this card (e.g., CardRankAce)
   */
  public PlayingCard(final int suit, final int rank) {
    // **** check our suit and rank to make sure they result in a valid card ****

    int suitRank = suit | rank;

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

    // **** 13 cards per suit, shit suit, add in 2 to offset first card so that values match ranks (Two is first) ****

    int suit = ((int)(cardIndexInDeck / 13) + (_cardSuitFirst >> 4)) << 4;
    int rank = (cardIndexInDeck % 13) + _cardRankFirst;

    _suitRank = suit | rank;

    // _suitRank = (cardIndexInDeck % 13) + ((int)(cardIndexInDeck / 13) << 4) + 2;

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
   * Create a RankSuit (for sorting) from a SuitRank
   * @param value SuitRank
   * @return RankSuit
   */
  private static final int RankSuitFromValue(final int value)
  {
    return ((value & 0x0F) << 4) | ((value & 0xF0) >> 4);
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

    //#region Internal Classes . . .

    static class CardSorter implements Comparator<PlayingCard>
    {
      public int compare(PlayingCard lhs, PlayingCard rhs)
      {
        // **** sort by rank, then suit ****

        return RankSuitFromValue(lhs._suitRank) - RankSuitFromValue(rhs._suitRank);
      }
    }
  
    //#endregion Internal Classes . . .
  
}

