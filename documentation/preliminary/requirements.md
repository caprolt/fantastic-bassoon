# CSC 478 Poker Requirements

------------
## 1. Introduction

### 1.1. Purpose of Document  
  This document will serve as the official requirements listing for this project.  

### 1.2. Scope of Product  
  The generality of the project is to produce a fully-functioning Poker Game program (including installers, user documentation, etc.) as well as all required artefacts of the development process as assigned (e.g., Org Chart, Gantt Chart, etc.).

### 1.3. Definitions, acronyms, and abbreviations  
  * **WSOP** - World Series of Poker

### 1.4. References
  * World Series of Poker  
    | Item    | URL  |
    | ------- | ---- |
    | Seven Card Stud Rules | http://www.wsop.com/poker-games/seven-card-stud/rules/ | 
    | Poker Hand Ranking    | http://www.wsop.com/poker-hands/ |  

------------
## 2. General Description

### 2.1. Product Perspective

This product is being built for a semester-long project for CSC478 at University of Illinois - Springfield.  The project topic was decided by the assigned group as a project which seemed viable for completion.  The game should be a useful way to spend idle time, but the primary focus is for each group member to experince team-based software development.

### 2.2. Product Functions

This product is a Seven Card Stud Poker game.  It will use the rules from the Official World Series of Poker to allow a human player to play poker against computer opponents.

### 2.3. User Characteristics

The target users of the product are the developers and the instructor for the course.  Tt is possible that the software may be made availble to additional students, but this is not the focus of the project.

### 2.4. General Constraints

The software will generally be limited to use on a single Windows-based computer.

### 2.5. Assumptions and Dependencies

The software will assume a current installation of a modern version of Windows (Windows 10 preferred).

------------

## 3. Requirements Listing

* **REQ010000**: Platform Requirements  
    General requirements about the product's envrionment

  * **REQ010100**: Must run on standard windows computer  
  * ~~**REQ010200**: Must allow easy communication with clients~~
  * **REQ010300**: Must allow for saving of data locally


* **REQ020000**: Human Player Requirements  
    Requirements relating to the operations of the human player.

  * ~~**REQ020100**: Must be able to store a list of human players.~~
  * ~~**REQ020200**: Must be able to create a human player~~
  * ~~**REQ020300**: Must be able to delete a human player~~
  * ~~**REQ020400**: Must be able to select a particular player from the list~~
  * ~~**REQ020500**: Must include facility to prevent double-use of the same player~~
  * **REQ020600**: Must be able to track player information
    * **REQ020601**: Name
    * **REQ020602**: Current money
    * **REQ020603**: Total debt
    * **REQ020604**: Number of Wins
    * **REQ020605**: Number of Losses
  * **REQ020700**: Human players must be able to borrow money when broke
  * **REQ020800**: Human players must not be allowed to borrow money while at a table
  * **REQ020900**: Human players must not be able to bet more than they have


* **REQ030000**: Computer Player Requirements  
  Requirements relating to the operation of computer players.
  
  * **REQ030100**: Must be able to track computer player information
    * **REQ030101**: Name
    * **REQ030102**: Current money
    * **REQ030103**: Total debt
    * **REQ030104**: Number of Wins
    * **REQ030105**: Number of Losses
    * **REQ030106**: Skill level
  * **REQ030200**: Computer players must be able to borrow money when broke
  * **REQ030300**: Computer players must not be allowed to borrow money while at a table
  * **REQ030400**: Computer players must not be allowed to bet more than they have


* **REQ040000**: Game Object Requirements  
  Requirements relating to game objects.

  * **REQ040100**: Must track Deck information
    * **REQ040101**: Correct 52 Cards
  * **REQ040200**: Must allow Deck to be shuffled
    * **REQ040201**: Must allow pseudorandom Shuffle
    * **REQ040103**: Must allow non-random shuffle (Sort)
  * **REQ040300**: Must track Card information
    * **REQ040301**: Suit
    * **REQ040302**: Rank
  * ~~**REQ040400**: Must track Table information~~ (see **REQ050500** to **REQ051000**)
    * ~~**REQ040401**: Ante (can be zero or monetary amount)~~
    * ~~**REQ040402**: Low stake limit (positive amount, non-zero, smaller than high stake limit)~~
    * ~~**REQ040403**: High stake limit (positive amount, larger than low stake limit)~~
    * ~~**REQ040404**: Number of players (2 - 7)~~
    * ~~**REQ040405**: Position of players ~~at table~~ in game~~
    * ~~**REQ040406**: Position of dealer ~~at table~~ in game~~
  * ~~**REQ040500**: Must track dealer information~~ (see _**REQ050000**_ for game state)
    * ~~**REQ040501**: Must track game play state~~
    * ~~**REQ040502**: Must track deck state of current game~~
    * ~~**REQ040503**: Dealer must know own location at table~~
  * **REQ040600**: Player hand
    * **REQ040601**: Each of up to seven cards
    * **REQ040602**: Face up/down state


* **REQ050000**: Game State Requirements  
  Requirements relating to game state.

  * **REQ050100**: Uniquely track games
  * **REQ050200**: Log game history
  * **REQ050300**: Keep track of players for each game
    * ~~**REQ050301**: Allow human players to replace computer players between hands~~
    * ~~**REQ050302**: Require approval from existing human players~~
  * **REQ050400**: Keep track of game objects for each game
  * **REQ050500**: Ante (can be zero or monetary amount)
  * **REQ050600**: Low stake limit (positive amount, non-zero, smaller than high stake limit)
  * **REQ050700**: High stake limit (positive amount, larger than low stake limit)
  * **REQ050800**: Number of players (2 - 7)
  * **REQ050900**: Position of players ~~at table~~ in game
  * **REQ051000**: Position of dealer ~~at table~~ in game


* **REQ060000**: Game Operation Requirements  
  Requirements relating to game play.

  * **REQ060100**: Shuffle deck before dealing
  * **REQ060200**: Ante payment (for nonzero ante)
  * **REQ060300**: Burn first card of hand
  * **REQ060400**: Deal face down
    * **REQ060401**: Card 1 (Round 1 Deal)
    * **REQ060402**: Card 2 (Round 1 Deal)
    * **REQ060403**: Card 7 (Round 5 Deal)
  * **REQ060500**: Deal face up
    * **REQ060501**: Card 3 (Round 1 Deal)
    * **REQ060502**: Card 4 (Round 2 Deal)
    * **REQ060503**: Card 5 (Round 3 Deal)
    * **REQ060504**: Card 6 (Round 4 Deal)
  * **REQ060600**: Determine first player to take action in a Round
    * **REQ060601**: Round 1 - Lowest up card
    * **REQ060602**: Round 2 - Highest exposed hand (up cards)
    * **REQ060603**: Round 3 - Highest exposed hand (up cards)
    * **REQ060604**: Round 4 - Highest exposed hand (up cards)
    * **REQ060605**: Round 5 - Highest exposed hand (up cards)
    * **REQ060606**: Showdown with no bets in Round 5: high showing hand
    * **REQ060607**: Showdown with bets in Round 5: Last to establish bet or raise
    * **REQ060608**: Ties settled by closest to dealer in deal order
  * **REQ060700**: Process player choices per player in Round 1
    * **REQ060701**: Forced minimum bet: low limit stake
    * **REQ060702**: Raise can be any amount
    * **REQ060703**: Player can call by matching current bet amount
    * **REQ060704**: Player can fold, removing themselves from the hand
  * **REQ060800**: Process player choices per player in Round 2
    * **REQ060801**: Bet is set at low limit stake
    * **REQ060802**: Raise is not allowed unless a player has a face up pair
    * **REQ060803**: If raise is allowed, it must be in multiples of low or high stake
    * **REQ060804**: If raises are used and a player has used a multiple of the high stake, remaining raises must be multiples of high stake as well.
    * **REQ060805**: Player can call by matching current bet amount
    * **REQ060806**: Player can fold, removing themselves from the hand
  * **REQ060900**: Process player choices per player in Round 3
    * **REQ060901**: Bet is set at high limit stake
    * **REQ060902**: Raise is not allowed
    * **REQ060903**: Player can call by matching current bet amount
    * **REQ060904**: Player can fold, removing themselves from the hand
  * **REQ061000**: Process player choices per player in Round 4
    * **REQ061001**: Raise can be any amount
    * **REQ061002**: Player can call by matching current bet amount
    * **REQ061003**: Player can fold, removing themselves from the hand
  * **REQ061100**: Process player choices per player in Round 5
    * **REQ061101**: Raise can be any amount
    * **REQ061102**: Player can call by matching current bet amount
    * **REQ061103**: Player can fold, removing themselves from the hand
  * **REQ061200**: Process player choices per player in "Showdown"
    * **REQ061201**: Show cards
    * **REQ061202**: Muck (toss cards into center pile)
  * **REQ061300**: Find best hand from 1-7 cards
    * **REQ061301**: Royal flush
      * Same suit Ace, King, Queen, Jack, and 10 (high 5 ranks)
      * Requires 5 cards
    * **REQ061302**: Straight flush
      * 5 same suit consecutive ranked cards
      * Requires 5 cards
    * **REQ061303**: 4 of a kind
      * 4 cards of matching rank
      * Sorted by matched card rank, then solo card rank
      * Requires 4 cards
    * **REQ061304**: Full house
      * 3 cards of matching rank and 2 cards of another matching rank
      * Sorted by large matched rank, then low matched rank
      * Requires 5 cards
    * **REQ061305**: Flush
      * 5 cards of same suit, not in sequence
      * Sorted by high card rank
      * Requires 5 cards
    * **REQ061306**: Straight
      * 5 consecutive ranked cards (not suit matched)
      * Sorted by high rank
      * Requires 5 cards
    * **REQ061307**: 3 of a kind
      * 3 cards of matching rank
      * Sorted by matched card rank, solo cards high rank
      * Requires 4 cards
    * **REQ061308**: 2 Pair
      * 2 cards of matching rank and 2 cards of another matching rank
      * Sorted by high matched rank, low matched rank, solo card rank
      * Requires 4 cards
    * **REQ061309**: 1 Pair
      * 2 cards of matching rank
      * Sorted by high matched rank, high solo card rank
      * Requires 2 cards
    * **REQ061310**: High card
      * Sorted by high solo rank
      * Requires 1 card
  * **REQ061400**: Compare player scores to find winner
    * **REQ061401**: Winning ties are resolved by splitting the pot
  * **REQ061500**: Process the pot
    * **REQ061501**: Add to correct player/s accounts


* **REQ070000**: User Interface Requirments  
  Requirements relating to the user interface.

  * **REQ070100**: Must include a game lobby
    * **REQ070101**: Must allow table configuration
    * **REQ070102**: Must allow human player to select the total number of players
    * **REQ070103**: Must allow computer player selection
    * **REQ070104**: Must allow players to borrow money from the bank if broke
    * **REQ070105**: Must allow a player to start the game
    * **REQ070106**: Must show current player information  
       See _**REQ020600**_ for required player information
  * **REQ070200**: Must include a game play screen (during play)
    * **REQ070201**: Should clearly display current game state
    * **REQ070202**: Must display game round information
    * **REQ070203**: Must show current hand information for all other players
    * **REQ070204**: Must show current player money amount
    * **REQ070205**: Must show valid player action choices
    * **REQ070206**: Must allow player to select allowed amounts during betting actions
    * **REQ070207**: Must show computer player actions
    * **REQ070208**: Must allow player to return to the game loby  
      * Forfeit current bets
  * **REQ070300**: Must include a hand over screen
    * **REQ070301**: Must show scoring
    * **REQ070302**: Must show game results
    * **REQ070303**: Must allow player to deal next hand
    * **REQ070304**: Must allow player to return to the game loby
