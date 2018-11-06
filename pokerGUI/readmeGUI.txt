This is a readme file for the texas holdem poker game!

This is the front end methods that will drive the look of the texas holdem game

A few things have been taken into consideration.
 - there will be a player backend class that will do all backend functionality
 - there will be a card class that compares cards and assigns them values
 - there will be a hand class that evaluates a players hand
 - a table class that will drive basic functions of the table


 code breakdown - this gui front end contains 7 front end classes that will drive the game, the other file configures the types of table that can be played ( fixed limit and no limit table types)
DisplayAmountBoard.java - sets the betting sliders for betting in game
MainBoard.java - this class drives the gui interface 
PokerBoardPanel.java - this sets the look of the poker board as well as updates the player hands
PokerControl.java - this class control the actions that each player selects by clicking
PokerManager.java - this class controls the images and load them in *(images need to be renamed to be read by backend and the imageicon function)*
PokerPlayer.java - this file control the look of each player, it also provides gui logic for the cards that each player has, it also sets the order that each player will play
	and which player is dealing, the file also lists each players name, current bet amount, avaliable cash amount and their last performed action
TypeTable.java - this file inits the types of tables that can be played: fixed limit and no limit games
PokerConstants.java - this small file set the basic layout colors for the gui
folder with game images

