/*
 *  project: pokerGame
 *     file: src\main\java\org\csc478\pokerGame\models\PlayerHand.java
 *  created: 2018-11-12 12:36:29
 *       by: Gino Canessa
 * modified: 2018-12-10
 *       by: Gino Canessa
 *
 *  summary: Class to track a player's hand
 */

package org.csc478.pokerGame.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayerHand {
  //#region Private Constants . . .

  /** Total number of cards in a hand */
  private static final int _handSize = 7;

  /** Total nubmer of cards in hand minus one (for boundary checking) */
  // private static final int _handSizeMinusOne = _handSize - 1;

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

  /** Player ID this hand belongs to */
  private UUID _playerId;

  /** Index of this player within the game - for convenience */
  private int _gamePlayerIndex;

  /** Number of cards currently in this hand */
  private int _numberOfCardsInHand;

  /** Cards in this hand */
  private PlayingCard _cardsInHand[];

  private int _currentScoreTotal;
  private int _currentScoreFaceUp;
  private int _currentHandTotal;
  private int _currentHandFaceUp;
  
  //#endregion Object Variables . . .

  //#region Accessors and Mutators . . .

  /**
   * Gets the current best score from ALL cards in the hand
   * @return Score value
   */
  public int getScoreTotal() { return _currentScoreTotal; }

  /**
   * Gets the current best score from FACE UP cards in the hand
   * @return Score value
   */
  public int getScoreFaceUp() { return _currentScoreFaceUp; }

  /**
   * Gets the current best hand from ALL cards in the hand
   * @return Hand Type
   */
  public int getHandTypeTotal() { return _currentHandTotal; }

  /**
   * Gets the current best FACE UP hand showing in this hand
   * @return Hand Type
   */
  public int getHandTypeFaceUp() { return _currentHandFaceUp; }

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
   * Get a sorted list of cards.  Note that this invalidates position-based hand tracking
   * @param faceUpOnly True to return only face-up cards, false to return all cards
   * @return List of playing cards, sorted by Rank and Suit
   */
  public List<PlayingCard> getSortedCards(final boolean faceUpOnly)
  {
    List<PlayingCard> cards;

    // **** get the correct list ****

    if (faceUpOnly)
    {
      cards = getFaceUpCards();
    }
    else
    {
      cards = getCards();
    }

    // **** create a sorted list ****

    Collections.sort(cards, new PlayingCard.CardSorter());

    // **** return our list ****

    return cards;
  }
  
  /**
   * Add a card to a hand
   * @param card Playing Card to add
   * @return Number of cards now in hand
   */
  public int AddCardToHand(PlayingCard card)
  {
    // **** check for maximum cards ****

    if (_numberOfCardsInHand == _handSize)
    {
      // **** cannot add ****

      return _numberOfCardsInHand;
    }

    // **** append this card to our array ****

    _cardsInHand[_numberOfCardsInHand++] = card;

    // **** update current scores ****

    _currentScoreTotal = PokerScorer.getScoreForSortedCards(this.getSortedCards(false));

    // **** update hand type ****
    
    _currentHandFaceUp = PokerScorer.getHandTypeFromScore(_currentScoreTotal);

    // **** update face up info if this was a face-up card ****

    if (card.getCardState() == PlayingCard.CardStateFaceUp)
    {
      // **** update score ****

      _currentScoreFaceUp = PokerScorer.getScoreForSortedCards(this.getSortedCards(true));

      // **** update showing hand type ****

      _currentHandFaceUp = PokerScorer.getHandTypeFromScore(_currentScoreFaceUp);
    }

    // **** return our new number of cards ****

    return _numberOfCardsInHand;
  }

  /**
   * Set the cards for this hand
   * @param cards Cards to use in this hand 
   * @return Number of cards now in the hand
   */
  public int setCards(final List<PlayingCard> cards)
  {
    // **** check for too many cards ****

    if (cards.size() > _handSize)
    {
      return -1;
    }

    // **** set our cards ****

    _cardsInHand = (PlayingCard[])cards.toArray();
    _numberOfCardsInHand = _cardsInHand.length;

    // **** return our number of cards ****

    return _numberOfCardsInHand;
  }

  /**
   * Get the Player ID this hand belongs to
   * @return UUID of the player this hand belongs to
   */
  public UUID getPlayerId() { return _playerId; }

  /**
   * Get the Game PlayerIndex this hand belongs to
   * @return Index of the player within the game this hand belongs to
   */
  public int getGamePlayerIndex() { return _gamePlayerIndex; }

  //#endregion Accessors and Mutators . . .

  //#region Constructors . . .

  /**
   * Create an empty hand for a player within a game
   * @param playerId UUID for the player this hand relates to
   * @param gamePlayerIndex Index of the player within the game, for convenience
   */
  public PlayerHand(UUID playerId, int gamePlayerIndex)
  {
    // **** set player information ****

    _playerId = playerId;
    _gamePlayerIndex = gamePlayerIndex;

    // **** create our card array ****

    _cardsInHand = new PlayingCard[_handSize];

    // **** there are no cards in this hand ****

    _numberOfCardsInHand = 0;
    _currentScoreTotal = 0;
    _currentScoreFaceUp = 0;
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