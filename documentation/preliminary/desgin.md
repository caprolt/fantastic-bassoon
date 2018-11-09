# CSC 478 Poker Design

------------
## 1. Classes

See Spec Document... Need to figure out what the format should be for turn-in.

### 1.1 Playing Card

  #### 1.1.1 Constructors

  | Constructor | Requirement | Purpose |
  | ----------- | ----------- | ------- |
  | | REQ040101 | Prevent invalid cards from being created |
  
  #### 1.1.2 Properties
  
  | Property | Requirement | Purpose |
  | -------- | ----------- | ------- |
  | Suit | REQ040301 | Keep track of card suit
  | Rank | REQ040302 | Keep track of card rank


### 1.2 Deck

  #### 1.2.1 Constructors

  | Constructor | Requirement | Purpose |
  | ----------- | ----------- | ------- |
  | | REQ040101 | Only allow proper decks to be created |
  
  #### 1.2.2 Properties
  
  | Property | Requirement | Purpose |
  | -------- | ----------- | ------- |
  | Cards | REQ040100 | Keep track of cards in deck |

  #### 1.2.3 Functions
  
  | Function | Requirement | Purpose |
  | -------- | ----------- | ------- |
  | Shuffle | REQ040200 | Randomly shuffle the deck |


### 1.3 Player

  #### 1.3.1 Constructors

  | Constructor | Requirement | Purpose |
  | ----------- | ----------- | ------- |
  | | REQ020000 | Create a human player object |
  | | REQ030000 | Create a computer player object |

  #### 1.3.2 Properties
  
  | Property | Requirement | Purpose |
  | -------- | ----------- | ------- |
  | PlayerId | REQ020600 | Uniquely identify a human player |
  |          | REQ030100 | Uniquely identify a computer player |
  | Name | REQ020601 | Track human player name |
  |      | REQ030101 | Track computer player name |
  | Dollars | REQ020602 | Track human player amount of money |
  |         | REQ030102 | Track computer player amount of money |
  | Debt | REQ020603 | Track human player amount of debt |
  |      | REQ030103 | Track computer player amount of debt |
  | Wins | REQ020604 | Track human player number of wins |
  |      | REQ030104 | Track computer player number of wins |
  | Losses | REQ020605 | Track human player number of losses |
  |        | REQ030105 | Track computer player number of losses |
  | IsComputer | REQ020000 | Track if player object represents human or computer |
  | SkillLevel | REQ030106 | Set skill level of computer player |

  #### 1.3.3 Functions
  
  | Function | Requirement | Purpose |
  | -------- | ----------- | ------- |

### 1.4 Poker Game

  #### 1.4.1 Constructors

  | Constructor | Requirement | Purpose |
  | ----------- | ----------- | ------- |
  | | ~~REQ040400~~ | Create a poker table |
  | | REQ050000 | Create a poker Game |
  
  #### 1.4.2 Properties
  
  | Property | Requirement | Purpose |
  | -------- | ----------- | ------- |
  | PokerGameId | REQ050100 | Uniquely identify game |
  | Ante | REQ050500 | Track ante amount (allowed to be zero) |
  | LowStake | REQ050600 | Track low stake (positive non-zero, smaller than high stake) |
  | HighStake | REQ050700 | Track high stake (positive non-zero, larger than low stake) |
  | NumberOfPlayers | REQ050800 | Set number of players allowed at the table |

  #### 1.4.3 Functions
  
  | Function | Requirement | Purpose |
  | -------- | ----------- | ------- |

### 1.5 Player Hand

  #### 1.5.1 Constructors

  | Constructor | Requirement | Purpose |
  | ----------- | ----------- | ------- |
  | | REQ040600 | Create an empty player hand for a player |
  
  #### 1.5.2 Properties
  
  | Property | Requirement | Purpose |
  | -------- | ----------- | ------- |
  | CardsInHand | REQ040600 | Track the cards in a player hand |


  #### 1.5.3 Functions
  
  | Function | Requirement | Purpose |
  | -------- | ----------- | ------- |
  | ClearHand | | Start each game with empty hands for players |
  | AddToHand | | Allow dealer to add a card to a player's hand |
  | FaceUpCards | | Allow players and dealer to see face up cards at any time |
  | Cards | | Allow dealer to check all cards in hand during scoring |

  #### 1.5.4 Constants
  
  | Name | Requirement | Purpose |
  | ---- | ----------- | ------- |
  | HandSize | REQ040601 | Fixed number of cards per hand |


------------
## 2. Game Flow


------------
## 3. User Interface


