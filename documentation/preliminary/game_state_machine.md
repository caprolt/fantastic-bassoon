# CSC 478 Poker Game State Machine

------------

* RequestShuffleDeck
  * Shuffle Deck
  * Branch: 
    * RequestAnte
    * RequestBurnCard
* RequestAnte
  * Subtract Ante from players
  * Remove players who cannot stay in
  * Go:
    * RequestBurnCard
* RequestBurnCard
  * Burn card from deck
  * Go:
    * RequestDealFaceDown
* RequestDealFaceDown
  * Give Face Down card to each player
  * Set state Dealing
  * Branch:
    * RequestDealFaceDown
    * RequestDealFaceUp
* RequestDealFaceUp
  * Give Face Up card to each player
  * Go:
    * AdvanceRound
* AdvanceRound
  * Update round number
  * Determine player that gets first turn
  * Figure out minimum bet for this round
  * Go:
    * RequestPlayerAction
* RequestPlayerAction
  * Determine player whose turn it is
  * Update game state
  * Branch:
    * Player.RequestActionForGame (computer player)
    * Wait for Player Action
* Player.RequestActionForGame
  * Check for enough money to stay in game
    * Fold
    * Muck
  * Determine desired action
    * Call
    * Raise
    * Fold
    * Muck
    * Show
* PlayerActionFold
  * Flag player as out of this hand
  * Reset cards so they are not factored into scoring
  * Go:
    * RequestActionAfterPlayer
* PlayerActionMuck
  * Flag player as out of this hand
  * Reset cards so they are not factored into scoring
  * Go:
    * RequestActionAfterPlayer
* PlayerActionCall
  * Make sure player has enough money to call
    * Remove player from hand
  * Add to call count
  * Move money into pot
  * Go:
    * RequestActionAfterPlayer
* PlayerActionRaise
  * Make sure player has enough money to raise
    * Remove player from hand
  * Reset call count
  * Move money into pot
  * Go:
    * RequestActionAfterPlayer
* PlayerActionShow
  * Increment show counter
  * Go:
    * RequestActionAfterPlayer
* RequestActionAfterPlayer
  * Check if enough players are showing cards that game is over
    * RequestEndGame
  * Check if there is only one player left 
    * RequestEndGame
  * Check to see if we have enough calls to move to next round
    * RequestDealFaceUp
    * RequestDealFaceDown
    * AdvanceRound
* RequestEndGame
  * Determine winner
  * Notify winning player
  * Add money to player's total
			
