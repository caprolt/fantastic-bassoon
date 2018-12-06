/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PokerScorer.java
 *  created: 2018-11-12 13:42:59
 *       by: Gino Canessa
 * modified: 2018-11-12
 *       by: Gino Canessa
 *
 *  summary: 
 */

package org.csc478.pokerGame.models;

import java.util.List;
import java.security.InvalidParameterException;
import java.util.UUID;


public abstract class PokerScorer {

  //#region Public Constants . . .

  public static final int ScoreTypeUnknown       = 0x00;
  public static final int ScoreTypeHighCard      = 0x01;
  public static final int ScoreTypeOnePair       = 0x02;
  public static final int ScoreTypeTwoPairs      = 0x03;
  public static final int ScoreTypeThreeOfAKind  = 0x04;
  public static final int ScoreTypeStraight      = 0x05;
  public static final int ScoreTypeFlush         = 0x06;
  public static final int ScoreTypeFullHouse     = 0x07;
  public static final int ScoreTypeFourOfAKind   = 0x08;
  public static final int ScoreTypeStraightFlush = 0x09;
  public static final int ScoreTypeRoyalFlush    = 0x0A;

  public static final int InvalidHighScore = 0x0B << 8;
  //#endregion Public Constants . . .

  //#region Private Constants . . .

  //#endregion Private Constants . . .

  //#region Class Variables . . .

  //#endregion Class Variables . . .

  //#region Public Interface . . .

  /**
   * Get the current Winning player ID
   * @param hands List of hands to score
   * @param scoreOnlyFaceUp True to score only face-up cards, false to score all cards
   * @return Unique player ID for the player with the current highest score
   */
  public static UUID getWinningPlayerId(final List<PlayerHand> hands, final boolean scoreOnlyFaceUp)
  {
    // **** sanity check ****

    if ((hands == null) || (hands.size() == 0))
    {
      throw new InvalidParameterException("Cannot score empty hands list.");
    }

    // **** get the winning score hand index ****

    int winningIndex = getHighScoreHandIndex(hands, scoreOnlyFaceUp);

    // **** return the player id ****

    return hands.get(winningIndex).getPlayerId();
  }

  /**
   * Gets the current winning game player index
   * @param hands List of hands to score
   * @param scoreOnlyFaceUp True to score only face-up cards, false to score all cards
   * @return Game Player Index of the player with the current highest score
   */
  public static int getWinningGamePlayerIndex(final List<PlayerHand> hands, final boolean scoreOnlyFaceUp)
  {
    // **** sanity check ****

    if ((hands == null) || (hands.size() == 0))
    {
      throw new InvalidParameterException("Cannot score empty hands list.");
    }

    // **** get the winning score hand index ****

    int winningIndex = getHighScoreHandIndex(hands, scoreOnlyFaceUp);

    // **** return the player id ****

    return hands.get(winningIndex).getGamePlayerIndex();
  }

  //#endregion Public Interface . . .

  //#region Internal Functions . . .

  /**
   * Get the index of the highest scoring hand, using either all cards or only face-up cards
   * @param hands List of hands to score
   * @param scoreOnlyFaceUp True to score only face-up cards, false to score all cards in hand
   * @return Index of the highest scoring hand
   */
  private static int getHighScoreHandIndex(final List<PlayerHand> hands, final boolean scoreOnlyFaceUp)
  {
    int highestHandIndex = -1;
    int highScore = -1;

    // **** traverse our hands scoring them ****

    for (int handIndex = 0; handIndex < hands.size(); handIndex++)
    {
      // **** get the correct list of cards for this hand ****

      List<PlayingCard> handCards = hands.get(handIndex).getSortedCards(scoreOnlyFaceUp);

      // **** get the score for this hand ****

      int currentScore = getScoreForSortedCards(handCards);

      // **** check for new high score ****

      if (currentScore > highScore)
      {
        // **** set high score ****

        highScore = currentScore;
        highestHandIndex = handIndex;
      }
    }

    // **** return winning hand index ****

    return highestHandIndex;
  }

  /**
   * Get a score for a set of sorted cards
   * @param sortedCards List of sorted cards to score
   * @return Highest score value possible of up to any 5 cards from this hand
   */
  public static int getScoreForSortedCards(final List<PlayingCard> sortedCards)
  {
    // **** sanity check ****

    if ((sortedCards == null) || (sortedCards.size() == 0))
    {
      return ScoreTypeUnknown;
    }

    PlayingCard cards[] = sortedCards.toArray(new PlayingCard[0]);
    int numberOfCards = sortedCards.size();

    int highestSoloRank = 0;
    int secondSoloRank = 0;

    int lastRank = 0;
    int rankIndex = -1;

    int cardRanks[] = new int[numberOfCards];
    int cardCounts[] = new int[numberOfCards];

    int rank4Kind = 0;
    int rank3Kind = 0;
    int rank2Kind = 0;
    int rank2KindAgain = 0;

    int cardSuits[] = { 0, 0, 0, 0 };

    boolean hasStraight = false;
    int straightHighRank = 0;
    int straightCounter = 0;

    // **** scan for consecutive cards with same rank ****

    for (int cardIndex = 0; cardIndex < numberOfCards; cardIndex++)
    {
      int rank = cards[cardIndex].getCardRank();
      int suit = cards[cardIndex].getCardSuit();

      // **** add to correct card suit ****

      switch (suit)
      {
        case PlayingCard.CardSuitClubs:
          cardSuits[0]++;
        break;

        case PlayingCard.CardSuitDiamonds:
          cardSuits[1]++;
        break;

        case PlayingCard.CardSuitHearts:
          cardSuits[2]++;
        break;

        case PlayingCard.CardSuitSpades:
          cardSuits[3]++;
        break;
      }

      // **** only continue straight checking if we don't have one ****

      if (!hasStraight)
      {
        // **** check for no straight rank ****

        if (straightCounter == 0)
        {
          straightHighRank = rank;
          straightCounter = 1;
        }
        else
        {
          // **** check for continuing ****

          if (rank == lastRank - 1)
          {
            // **** straight continues ****

            straightCounter++;
          }
          else
          {
            // **** reset ****

            straightHighRank = rank;
            straightCounter = 1;
          }
        }

        // **** check for qualifying as a straight for scoring ****

        if (straightCounter >= 5)
        {
          hasStraight = true;
        }
      }

      // **** check if this card is same rank as last card ****

      if (rank == lastRank)
      {
        // **** increment our number at the last rank ****

        cardCounts[rankIndex]++;

        // **** do not continue processing this card! ****

        continue;
      }

      // **** new last rank ****

      lastRank = rank;
      rankIndex++;
      cardRanks[rankIndex] = rank;
      cardCounts[rankIndex] = 1;
      
      // **** check for info on the closed rank before moving on ****

      if (rankIndex != 0)
      {
        int lastRankCount = cardCounts[rankIndex-1];
        int lastRankValue = cardRanks[rankIndex-1];

        // **** check for setting solo rank ****

        if ((highestSoloRank == 0) && (lastRankCount == 1))
        {
          highestSoloRank = lastRankValue;
        }

        if ((secondSoloRank == 0) && (lastRankValue != highestSoloRank) && (lastRankCount == 1))
        {
          secondSoloRank = lastRankValue;
        }

        // **** check for number of last rank to set - make sure not to overwrite higher ranking matches ****

        if (lastRankCount == 4)
        {
          if (rank4Kind == 0) rank4Kind = lastRankValue;
        }
        else if (lastRankCount == 3)
        {
          if (rank3Kind == 0) rank3Kind = lastRankValue;
        }
        else if (lastRankCount == 2)
        {
          if (rank2Kind != 0)
          {
            if (rank2KindAgain == 0) rank2KindAgain = lastRankValue;
          }
          else
          {
            if (rank2Kind == 0) rank2Kind = lastRankValue;
          }
        }
      }
    }

    boolean hasFlush = ((cardSuits[0] >= 5) ||
                        (cardSuits[1] >= 5) ||
                        (cardSuits[2] >= 5) ||
                        (cardSuits[3] >= 5));

    // **** royal flush ****

    if ((hasStraight) && (hasFlush) && (straightHighRank == PlayingCard.CardRankAce))
    {
      // **** no additional cards because we only score 5 and royal flush is always ace high ****

      return getScore(ScoreTypeRoyalFlush, 0, 0);
    }

    // **** straight flush ****

    if ((hasStraight) && (hasFlush))
    {
      // **** no second card because we only score 5 and stright flush is 5 cards ****

      return getScore(ScoreTypeStraightFlush, straightHighRank, 0);
    }

    // **** check for four of a kind ****

    if (rank4Kind != 0)
    {
      return getScore(ScoreTypeFourOfAKind, rank4Kind, highestSoloRank);
    }

    // **** check for full house ****

    if ((rank3Kind != 0) && (rank2Kind != 0))
    {
      return getScore(ScoreTypeFullHouse, rank3Kind, rank2Kind);
    }

    // **** flush ****
    
    if (hasFlush)
    {
      return getScore(ScoreTypeFlush, highestSoloRank, secondSoloRank);
    }

    // **** straight ****

    if (hasStraight)
    {
      // **** no second card because we only score 5 and stright is 5 cards ****

      return getScore(ScoreTypeStraight, straightHighRank, 0);
    }

    // **** check for three of a kind ****

    if (rank3Kind != 0)
    {
      return getScore(ScoreTypeThreeOfAKind, rank3Kind, highestSoloRank);
    }

    // **** check for two pair ****

    if ((rank2Kind != 0) && (rank2KindAgain != 0))
    {
      return getScore(ScoreTypeTwoPairs, rank2Kind, rank2KindAgain);
    }

    // **** check for one pair ****

    if (rank2Kind != 0)
    {
      return getScore(ScoreTypeOnePair, rank2Kind, highestSoloRank);
    }

    // **** at least one card is high card ****

    if (numberOfCards > 0)
    {
      return getScore(ScoreTypeHighCard, highestSoloRank, secondSoloRank);
    }

    // **** return this hand's ranking ****

    return ScoreTypeUnknown;
  }

  /**
   * Get a raking value for a particular rank and set of card values
   * @param ranking 
   * @param cardRank1
   * @param cardRank2
   * @return
   */
  private static final int getScore(int ranking, int cardRank1, int cardRank2)
  {
    return (ranking << 8) + (cardRank1 << 4) + cardRank2; 
  }

  /**
   * Gets a hand type from a score
   * @param score Score to get hand type from
   * @return Hand type
   */
  public static final int getHandTypeFromScore(int score)
  {
    return ((score & 0xF00) >> 8);
  }

  //#endregion Internal Functions . . .


}