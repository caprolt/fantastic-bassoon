
package org.csc478.pokerGame;

import javax.activity.InvalidActivityException;
import javax.swing.*;

import org.csc478.pokerGame.models.CardDeck;
import org.csc478.pokerGame.models.GameAction.GameActionTypes;
import org.csc478.pokerGame.models.PlayerHand;
import org.csc478.pokerGame.models.PlayingCard;
import org.csc478.pokerGame.models.PokerGame;
import org.csc478.pokerGame.models.PokerPlayer;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//screen size
	int gameW = 1280;
	int gameH = 1000;
	//variables for pokerGame
	int pokerGameAnte=10;
	int pokerGameLowStake=100;
	int pokerGameHighStake=500;
	
	boolean player1Active = false;
	boolean player2Active = false;
	boolean player3Active = false;
	boolean player4Active = false;
	boolean player5Active = false;
	boolean player6Active = false;
	boolean callButtonHit = false;
	boolean endGameButtonHit = false;
	boolean continueButtonHit = false;
	boolean leaveTableButtonHit = false;
	boolean newGameButtonHit = false;
	boolean startNextHandButtonHit = false;
	boolean raiseButtonHit = false;
	boolean foldButtonHit = false;
	boolean muckCardsButtonHit = false;
	boolean showCardsButtonHit = false;

	
	PokerGame game = new PokerGame(pokerGameAnte, pokerGameLowStake, pokerGameHighStake);
	//background color
	Color colorBackground = new Color(178,34,34);
	Color colorButton = new Color(204,204,0);
	//button
	
	//font
	Font fontButton = new Font("Times New Roman", Font.PLAIN, 13);
	Font fontCard = new Font("Times New Roman", Font.BOLD, 19);
	Font fontScreen = new Font("Times New Roman", Font.BOLD, 30);
	Font fontSide = new Font("Times New Roman", Font.BOLD, 20);

	//BUTTONS
	JButton continueB = new JButton();
	JButton raise = new JButton();
	JButton call = new JButton();
	JButton fold = new JButton();
	JButton newGame = new JButton();
	JButton addPlayer1 = new JButton();
	JButton addPlayer2 = new JButton();
	JButton addPlayer3 = new JButton();
	JButton addPlayer4 = new JButton();
	JButton addPlayer5 = new JButton();
	JButton addPlayer6 = new JButton();
	JButton muck = new JButton();
	JButton anteUp = new JButton();
	JButton endGame = new JButton();
	JButton nextHand = new JButton();
	JButton leave = new JButton();
	JButton showCards = new JButton();
	
	//player Visual hand
	String[] playerNumber1 = new String[7];
	String[] playerNumber2 = new String[7];
	String[] playerNumber3 = new String[7];
	String[] playerNumber4 = new String[7];
	String[] playerNumber5 = new String[7];
	String[] playerNumber6 = new String[7];	
	//CARD GRID POSITIONS FOR SIX POSSIBLE PLAYERS
	
	int gridX =50;
	int gridY =50;
	int gridW =900;
	int gridH =400;
	
	//bottom menu grid positions and dimensions
	int mgpX =gridX+gridW+50;
	int mgpY =gridY;
	int mgpW =230;
	int mgpH =400; 

	//questions ingame position and dimensions
	int ingqX=mgpX; 
	int ingqY=mgpY+mgpH;
	int ingqW=mgpW;
	int ingqH=200;
	
	//player seat positions
	int pspX=500;
	int pspY=100;
	int pspW=100;
	int pspH=100;
	
	//player card layout position
	int pclpX=605;
	int pclpY=100;
	int pclpW=450;
	int pclpH=100;
	//card positions for drawing
	int dps1X=pspX; 
	int dps1Y=pspY+10;
	int dpsW=pspW;
	int dpsH=pspH;
	int dps2X=pspX+310;
	int dps2Y=pspY+200;
	int dps3X=pspX+250;
	int dps3Y=pspY+500; 
	int dps4X=pspX;
	int dps4Y=pspY+600;
	int dps5X=pspX-200;
	int dps5Y=pspY+500;
	int dps6X=pspX-260;
	int dps6Y=pspY+200;
	
	int dpc1X=pclpX-260;
	int dpc1Y=pclpY-95;
	int dpc2X=pclpX+310;
	int dpc2Y=pclpY+50;
	int dpc3X=pclpX+205;
	int dpc3Y=pclpY+600;
	int dpc4X=pclpX-260;
	int dpc4Y=pclpY+750;
	int dpc5X=pclpX-600;
	int dpc5Y=pclpY+600;
	int dpc6X=pclpX-480;
	int dpc6Y=pclpY+50;
	int dpcW=pclpW;
	int dpcH=pclpH;
	
	
	//card dimensions and spacing
	int cardSpace=2;
	int cardEdgeSoftener=11;
	int cardTW= pclpW/7;
	int cardTH= pclpH;
	int cardAW=cardTW-2*cardSpace;
	int cardAH=cardTH-2*cardSpace;	
	
	
	//arraylists for cards
	CardDeck cards = new CardDeck();

	
	@SuppressWarnings("null")
	public GUI(){
		/**
		   * builds shuffled Deck for printing cards
		   * 
		   */
		//setting JFrame super details
		this.setSize(gameW+6, gameH+29);
		this.setTitle("Texas Holdem' Poker Game - 478");
		this.setVisible(true);
		this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//set board for painting
		Board board= new Board();
		this.setContentPane(board);
		this.setLayout(null);
		
		//Game button actions/phases
		ActCall actCall = new ActCall();
		call.addActionListener(actCall);
		call.setBounds(mgpX+51, mgpY+1,120,40);
		call.setBackground(colorButton);
		call.setFont(fontButton);
		call.setText("CALL");
		board.add(call);
		
		ActRaise actRaise = new ActRaise();
		raise.addActionListener(actRaise);
		raise.setBounds(mgpX+51, mgpY+45,120,40);
		raise.setBackground(colorButton);
		raise.setFont(fontButton);
		raise.setText("RAISE");
		board.add(raise);
		
		ActFold actFold = new ActFold();
		fold.addActionListener(actFold);
		fold.setBounds(mgpX+51, mgpY+90,120,40);
		fold.setBackground(colorButton);
		fold.setFont(fontButton);
		fold.setText("FOLD");
		board.add(fold);
		
		ActMuck actMuck = new ActMuck();
		muck.addActionListener(actMuck);
		muck.setBounds(mgpX+51, mgpY+135,120,40);
		muck.setBackground(colorButton);
		muck.setFont(fontButton);
		muck.setText("MUCK CARDS");
		board.add(muck);
		
		ActAnte actAnte = new ActAnte();
		anteUp.addActionListener(actAnte);
		anteUp.setBounds(mgpX+51, mgpY+180,120,40);
		anteUp.setBackground(colorButton);
		anteUp.setFont(fontButton);
		anteUp.setText("ANTE");
		board.add(anteUp);
		
		ActEndGame actEndGame = new ActEndGame();
		endGame.addActionListener(actEndGame);
		endGame.setBounds(mgpX+51, 515,209,40);
		endGame.setBackground(colorButton);
		endGame.setFont(fontButton);
		endGame.setText("END GAME");
		board.add(endGame);
		
		ActNextHand actNextHand = new ActNextHand();
		nextHand.addActionListener(actNextHand);
		nextHand.setBounds(mgpX+51, 470,209,40);
		nextHand.setBackground(colorButton);
		nextHand.setFont(fontButton);
		nextHand.setText("Start Next Hand");
		board.add(nextHand);
		
		ActLeaveGame actLeaveGame = new ActLeaveGame();
		leave.addActionListener(actLeaveGame);
		leave.setBounds(mgpX+51, 515+89,209,40);
		leave.setBackground(colorButton);
		leave.setFont(fontButton);
		leave.setText("Leave Table");
		board.add(leave);		
		
		ActContinueB actContinueB = new ActContinueB();
		continueB.addActionListener(actContinueB);
		continueB.setBounds(ingqX+51, ingqY+110,100,40);
		continueB.setBackground(colorButton);
		continueB.setFont(fontButton);
		continueB.setText("CONTINUE");
		board.add(continueB);
		
		ActNewGame actNewGame = new ActNewGame();
		newGame.addActionListener(actNewGame);
		newGame.setBounds(ingqX+155, ingqY+110, 105, 40);
		newGame.setBackground(colorButton);
		newGame.setFont(fontButton);
		newGame.setText("NEW GAME");
		board.add(newGame);
		
		ActAddPlayer1 actAddPlayer1 = new ActAddPlayer1();
		addPlayer1.addActionListener(actAddPlayer1);
		addPlayer1.setBounds(pspX+10, pspY+20,pspW-30,pspH-30);
		addPlayer1.setBackground(colorButton);
		addPlayer1.setFont(fontButton);
		addPlayer1.setText("Add");
		board.add(addPlayer1);
		
		ActAddPlayer2 actAddPlayer2 = new ActAddPlayer2();
		addPlayer2.addActionListener(actAddPlayer2);
		addPlayer2.setBounds(pspX+324, pspY+213,pspW-25,pspH-25);
		addPlayer2.setBackground(colorButton);
		addPlayer2.setFont(fontButton);
		addPlayer2.setText("Add");
		board.add(addPlayer2);
		
		ActAddPlayer3 actAddPlayer3 = new ActAddPlayer3();
		addPlayer3.addActionListener(actAddPlayer3);
		addPlayer3.setBounds(pspX+263, pspY+513,pspW-25,pspH-25);
		addPlayer3.setBackground(colorButton);
		addPlayer3.setFont(fontButton);
		addPlayer3.setText("Add");
		board.add(addPlayer3);
		
		ActAddPlayer4 actAddPlayer4 = new ActAddPlayer4();
		addPlayer4.addActionListener(actAddPlayer4);
		addPlayer4.setBounds(pspX+13, pspY+615,pspW-25,pspH-25);
		addPlayer4.setBackground(colorButton);
		addPlayer4.setFont(fontButton);
		addPlayer4.setText("Add");
		board.add(addPlayer4);
		
		ActAddPlayer5 actAddPlayer5 = new ActAddPlayer5();
		addPlayer5.addActionListener(actAddPlayer5);
		addPlayer5.setBounds(pspX-187, pspY+514,pspW-25,pspH-25);
		addPlayer5.setBackground(colorButton);
		addPlayer5.setFont(fontButton);
		addPlayer5.setText("Add");
		board.add(addPlayer5);
		
		ActAddPlayer6 actAddPlayer6 = new ActAddPlayer6();
		addPlayer6.addActionListener(actAddPlayer6);
		addPlayer6.setBounds(pspX-247, pspY+212,pspW-25,pspH-25);
		addPlayer6.setBackground(colorButton);
		addPlayer6.setFont(fontButton);
		addPlayer6.setText("Add");
		board.add(addPlayer6);
		
		ActShowCards actShowCards = new ActShowCards();
		showCards.addActionListener(actShowCards);
		showCards.setBounds(gameW-400, gameH-100,pspW+100,pspH-25);
		showCards.setBackground(colorButton);
		showCards.setFont(fontButton);
		showCards.setText("Show Human Player Cards");
		board.add(showCards);
		
	}
	 
	public void refresher() {
		/**
		set buttons true or false 
		showing correct buttons
		else another condition
		set other buttons 
		check boolean values
		set boolean for show card method
		
		this updates the panel and writes the player actions to the JFrame
		this also runs the cover cards method for all seven card spaces to show that player has folded their cards
		**** This gets the valid player actions for human player to push ***
		actions[] = game.getValidPlayerActions().size());
		
		for(int i=0; i<actions.size(); i++) {
			//if(actions.get(i)== Muck) {
			 
			  		muck.setVisible(true);
			  
			  }
			  //if(actions.get(i)== Fold) {
			 
				fold.setVisible(true);
			  
			  }

			//if(actions.get(i)== Call) {
			 
				call.setVisible(true);
			  
			  }
			//if(actions.get(i)== Raise) {
			 
				raise.setVisible(true);
			  
			  }
			}
			if(endGameButtonHit==true){
				exit;
				}
			if(leaveTableButtonHit==true{
				//remove human player
				}
			((if startNewGameButtonHit ==true) && (game.getRoundNumber==0 || game.getRoundNumber==7))
			{
				//
				 game.startGame();
			}		
			if(boolean showCardsButtonHit = true)
			{
				//need a get human player number[] (int) method from game 
				playerNumber[]
				if(int i =0; i<playerNumber.size; i++){
				showCards(mb, boolean[] playersActive, playerNumber[i]) 
			}
			
			//need method to get player action string to print
			//prints computer actions to game board
			mb.setFont(fontScreen);
			mb.drawString("Player" + i "has chosen to" + game.getPlayerAction(String), gameW/2, gameH/2); 
			
			if(game.getRoundNumber ==1)
			{
				startDealing(mb, playersActive);
			}else if(game.getRoundNumber ==2)
			{
				startDealing(mb, playersActive);
			}else if(game.getRoundNumber ==3)
			{
				startDealing(mb, playersActive);
			}else if(game.getRoundNumber ==4)
			{
				startDealing(mb, playersActive);
			}		
					
			}
			if(game.getPlayerAction == Fold)
			{
				{
				for(int i = 0; i<7;i++) {
					coverCards(mb, i, game.getCurrentPlayerID() );
					)

			}
		}**/
	
	}
	
	public class Board extends JPanel{ 
		/**
		 * 
		 */
		private static final long serialVersionUID = 478;

		public void paintComponent(Graphics mb)
		{
			//setting initial JFrame size and color
			mb.setColor(colorBackground);
			mb.fillRect(0,0,gameW, gameH);
			mb.setColor(Color.black);
			game.getValidPlayerActions();
			//game.
			//mainBoardCircle
			mb.drawOval(325, 200, 500, 500);
			
			//drawing string  variables to JFrame for game info
			//pot size for game
			int potSizeStr=0;
			mb.setFont(fontScreen);
			mb.drawString("Pot Size is: " + potSizeStr, gameW/2-150, gameH/2); 
			mb.drawString("Current Bet is: " + potSizeStr, gameW/2-170, gameH/2-150); 
			//current Low Stake
			String lowStakeStr=Integer.toString(game.getLowStake());
			//current High Stake
			mb.setFont(fontSide);
			String highStakeStr=Integer.toString(game.getHighStake());
			mb.drawString("Low Stake: " + lowStakeStr + "/High Stake: " + highStakeStr, 10, 30);		
				

			//get game variables
			int hS=game.getHighStake();
			int lS=game.getLowStake();
			int ante=game.getAnte();
			System.out.println("hi" + hS + lS+ ante);
			//current minimum total bet for player to stay in the game
			String minimumStayIn=Integer.toString(game.getCurrentMinumumTotalBet());
			boolean[] playersActive = game.getActivePlayers();
			System.out.println(game.getNumberOfPlayers());

			//if (playersActive[y] == true) 
			//playersActive=game.getActivePlayers();			
			
			//function to draw player seats to condense code	
			drawPlayerSeats(mb);
			//function to draw cardSpaces
			drawPlayerCardSpace(mb);
			
			//draw in hand menu grid
			mb.drawRect(mgpX+50, mgpY, 121, mgpH-179);
			
			//draw game questions grid next hand, end game, continue, new game, leave table
			mb.drawRect(ingqX+50, ingqY+18, ingqW-20, ingqH-22);
			game.getNumberOfPlayers();
			int pokerRound = game.getRoundNumber();
			System.out.println(pokerRound);
			//can the games start?
			if(game.CanGameStart()) 
			{		
				//error checking; printing out variables to the console
				System.out.println(hS);
				System.out.println(lS);			
				System.out.println(ante);
				game.StartGame();
				startDealing(mb, playersActive);
				
			
			}
			else {System.out.println("NO");}
			
			

			//showCards(mb,playersActive, 0);
			//else {System.out.println("Player: "+ y +" is inactive");}
				
			
			
		}
		/**
		 * 
		 * @param mb
		 * @param playersActive
		 */
		private void startDealing(Graphics mb, boolean[] playersActive) 
		{
			//cardImageIndex this variable is the card index for drawing the card in the right player cardSpace. 0-7; left to right; up to down  			
			int cardImageIndex = 1;
			//looking at the gameDeck and returning the suit at and rank at index; index will tell which card is being drawn
			int index = 0;
			
			//number of cards to print			
			//if game round is equal to first round we will deal and print three cards to each player
			int gameRound=0;
			//System.out.println("active players are:" + game.getActivePlayers());
			//testing purposes

			int y=0;
			if( gameRound == 0)
			{
				for(int i = 0; i<3;i++) {
				for(y = 0; y<playersActive.length; y++) 
				{
					//for(cardImageIndex = 0; cardImageIndex<3; cardImageIndex++) {
					//drawing cards to JFrame; three cards; game round hard set to 0 to get first three cards; need to turn first two over
					drawCard(mb, index,i, y);index++;
					//System.out.println("cardindex: " + cardImageIndex);
					
						//game round is not zero so we only print one card to JFrame
					if(i==0 || i ==1) 
					{
						coverCards(mb, i, y );
					}
				}
				
				}
			}
				else 
					{
						drawCard(mb, index, cardImageIndex,y);
						index++;
						System.out.println("Player: "+ y +" is active");
					}

		}

		public void showCards(Graphics mb, boolean[] playersActive, int playerToShow) 		{
			//cardImageIndex this variable is the card index for drawing the card in the right player cardSpace. 0-7; left to right; up to down  			
			int cardImageIndex = 1;
			//looking at the gameDeck and returning the suit at and rank at index; index will tell which card is being drawn
			int index = 0;
			
			//number of cards to print			
			//if game round is equal to first round we will deal and print three cards to each player
			int gameRound=0;
			//System.out.println("active players are:" + game.getActivePlayers());
			//testing purposes

			int y=0;
			if( gameRound == 0)
			{
				for(int i = 0; i<3;i++) {
				for(y = 0; y<playersActive.length; y++) 
				{
					if(playerToShow==0) {
						//for(cardImageIndex = 0; cardImageIndex<3; cardImageIndex++) {
						//drawing cards to JFrame; three cards; game round hard set to 0 to get first three cards; need to turn first two over
						drawCard(mb, index,i, y);index++;
						//System.out.println("cardindex: " + cardImageIndex);
						break;
						//game round is not zero so we only print one card to JFrame
					}else if(playerToShow==1) {
						//for(cardImageIndex = 0; cardImageIndex<3; cardImageIndex++) {
						//drawing cards to JFrame; three cards; game round hard set to 0 to get first three cards; need to turn first two over
						drawCard(mb, index,i, y);index++;
						//System.out.println("cardindex: " + cardImageIndex);
						break;
						//game round is not zero so we only print one card to JFrame
					}else if(playerToShow==2) {
						//for(cardImageIndex = 0; cardImageIndex<3; cardImageIndex++) {
						//drawing cards to JFrame; three cards; game round hard set to 0 to get first three cards; need to turn first two over
						drawCard(mb, index,i, y);index++;
						//System.out.println("cardindex: " + cardImageIndex);
						break;
						//game round is not zero so we only print one card to JFrame
					}else if(playerToShow==3) {
						//for(cardImageIndex = 0; cardImageIndex<3; cardImageIndex++) {
						//drawing cards to JFrame; three cards; game round hard set to 0 to get first three cards; need to turn first two over
						drawCard(mb, index,i, y);index++;
						//System.out.println("cardindex: " + cardImageIndex);
						break;
						//game round is not zero so we only print one card to JFrame
					}else if(playerToShow==4) {
						//for(cardImageIndex = 0; cardImageIndex<3; cardImageIndex++) {
						//drawing cards to JFrame; three cards; game round hard set to 0 to get first three cards; need to turn first two over
						drawCard(mb, index,i, y);index++;
						//System.out.println("cardindex: " + cardImageIndex);
						break;
						//game round is not zero so we only print one card to JFrame
					}else if(playerToShow==5) {
						//for(cardImageIndex = 0; cardImageIndex<3; cardImageIndex++) {
						//drawing cards to JFrame; three cards; game round hard set to 0 to get first three cards; need to turn first two over
						drawCard(mb, index,i, y);index++;
						//System.out.println("cardindex: " + cardImageIndex);
						break;
						//game round is not zero so we only print one card to JFrame
					} 
				}
				
				}
			}
				else 
					{
						drawCard(mb, index, cardImageIndex,y);
						index++;
						System.out.println("Player: "+ y +" is active");
					}

		}


			
		}

		private void coverCards(Graphics mb, int cardImageIndex, int playerID) {
			// TODO Auto-generated method stub
			//drawing rectangles over cards to "Cover the cards"
			if(playerID==0) {
				mb.setColor(Color.white);
				mb.fillRect(dpc1X+cardImageIndex*cardTW+cardSpace, dpc1Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc1X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc1Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.red);
				mb.fillRect(dpc1X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc1Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.white);
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace, dpc1Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc1Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace, dpc1Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc1Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				

				
				cardImageIndex++;
			}
			else if(playerID==1) {//top right, need same method for player 6
				mb.setColor(Color.white);
				mb.fillRect(dpc2X+cardSpace, dpc2Y+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, cardAH,  cardAW-2*cardEdgeSoftener);
				mb.fillRect(dpc2X+cardSpace+cardEdgeSoftener, dpc2Y+cardImageIndex*cardTW+cardSpace, cardAH-2*cardEdgeSoftener, cardAW);
				mb.setColor(Color.red);
				mb.fillRect(dpc2X+cardSpace+cardEdgeSoftener, dpc2Y+cardImageIndex*cardTW+cardSpace, cardAH-2*cardEdgeSoftener, cardAW);
				mb.setColor(Color.white);
				mb.fillOval(dpc2X+cardSpace, dpc2Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc2X+cardSpace, dpc2Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc2X+cardSpace+cardAH-2*cardEdgeSoftener, dpc2Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc2X+cardAH+cardSpace-2*cardEdgeSoftener, dpc2Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				
				
				cardImageIndex++;
			}
			else if(playerID==2) {
				mb.setColor(Color.white);
				mb.fillRect(dpc3X+cardImageIndex*cardTW+cardSpace, dpc3Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc3X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc3Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.red);
				mb.fillRect(dpc3X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc3Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.white);
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace, dpc3Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc3Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace, dpc3Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc3Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				
				
				cardImageIndex++;
			}
			else if(playerID==3) {
				mb.setColor(Color.white);
				mb.fillRect(dpc4X+cardImageIndex*cardTW+cardSpace, dpc4Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc4X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc4Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.red);
				mb.fillRect(dpc4X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc4Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.white);
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace, dpc4Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc4Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace, dpc4Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc4Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				

				
				cardImageIndex++;
			}
			else if(playerID==4) {
				mb.setColor(Color.white);
				mb.fillRect(dpc5X+cardImageIndex*cardTW+cardSpace, dpc5Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc5X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc5Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.red);
				mb.fillRect(dpc5X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc5Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.setColor(Color.white);
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace, dpc5Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc5Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace, dpc5Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc5Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				
				
				cardImageIndex++;
			}
			else if(playerID==5) {
				mb.setColor(Color.white);
				mb.fillRect(dpc6X+cardSpace, dpc6Y+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, cardAH,  cardAW-2*cardEdgeSoftener);
				mb.fillRect(dpc6X+cardSpace+cardEdgeSoftener, dpc6Y+cardImageIndex*cardTW+cardSpace, cardAH-2*cardEdgeSoftener, cardAW);
				mb.setColor(Color.red);
				mb.fillRect(dpc6X+cardSpace+cardEdgeSoftener, dpc6Y+cardImageIndex*cardTW+cardSpace, cardAH-2*cardEdgeSoftener, cardAW);
				mb.setColor(Color.white);
				mb.fillOval(dpc6X+cardSpace, dpc6Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc6X+cardSpace, dpc6Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc6X+cardSpace+cardAH-2*cardEdgeSoftener, dpc6Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc6X+cardAH+cardSpace-2*cardEdgeSoftener, dpc6Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				
				
				cardImageIndex++;
			}
			
			
			
			
		}

		private void drawPlayerCardSpace(Graphics mb) {
			// TODO Auto-generated method stub
			//drawing player card space cards			
			for(int i = 0; i < 7; i++) {
				mb.drawRect(pclpX-260+i*cardTW+cardSpace, pclpY-95+cardSpace, cardAW, cardAH);
				mb.drawRect(pclpX+310+cardSpace, pclpY+50+i*cardTW+cardSpace, cardAH ,cardAW );
				mb.drawRect(pclpX+205+i*cardTW+cardSpace, pclpY+600+cardSpace, cardAW, cardAH);
				mb.drawRect(pclpX-260+i*cardTW+cardSpace, pclpY+750+cardSpace, cardAW, cardAH);
				mb.drawRect(pclpX-600+i*cardTW+cardSpace, pclpY+600+cardSpace, cardAW, cardAH);
				mb.drawRect(pclpY+25+cardSpace ,pclpX-455+i*cardTW+cardSpace, cardAH,cardAW );

			}
			
		}
		private void drawPlayerSeats(Graphics mb) {
		
		//playerSeats(6)(circle)
		
		mb.drawOval(dps1X, dps1Y, dpsW-10, dpsH-10);
		//playerCardSpace(rectangle)
			mb.drawRect(dpc1X, dpc1Y, dpcW, dpcH);
			
		//top right
			
		mb.drawOval(dps2X, dps2Y, dpsW, dpsH);
			//playerCardSpace(rectangle)
			mb.drawRect(dpc2X, dpc2Y, dpcH, dpcW);		

		//bottom right
		mb.drawOval(dps3X, dps3Y, dpsW, dpsH);
			//playerCardSpace(rectangle)
			mb.drawRect(dpc3X, dpc3Y, dpcW, dpcH);
			
		//Bottom
		mb.drawOval(dps4X, dps4Y, dpsW, dpsH);
			//playerCardSpace(rectangle)
			mb.drawRect(dpc4X, dpc4Y, dpcW, dpcH);
		
		//bottom left
		mb.drawOval(dps5X, dps5Y, dpsW, dpsH);
			//playerCardSpace(rectangle)
			mb.drawRect(dpc5X, dpc5Y, dpcW, dpcH);
		
		//top left
		mb.drawOval(dps6X, dps6Y, dpsW, dpsH);
		//add player button
		
			//playerCardSpace(rectangle)
			mb.drawRect(dpc6X, dpc6Y, dpcH, dpcW);
			
		//draw player names
			/**
				if(playerNames[0] = true)
				{mb.drawString("Player1", dps1X+10, dps1Y+50);}
				if (playerNames[1] = true) 
				{mb.drawString("Player2", dps2X+10, dps2Y+50);}
				if (playerNames[2] = true) 
				{mb.drawString("Player3", dps3X+10, dps3Y+50);}
				if (playerNames[3] = true) 
				{mb.drawString("Player4", dps4X+10, dps4Y+50);}
				if (playerNames[4] = true) 
				{mb.drawString("Player5", dps5X+10, dps5Y+50);}
				if (playerNames[5] = true) 
				{mb.drawString("Player6", dps6X+10, dps6Y+50);}
				**/

		}		
	
		public void drawCard(Graphics mb, int index, int cardImageIndex, int playerID) 
		{
			//card is  position in deck
			//cardimage is index of player hand for card painting
			//draw cards complete; have to draw cards to the correct player
			//drawing shapes using fill rect and fill oval to paint card space and card suit shape
			//using draw string to write card rank onto card
			//moving cards around JFrame by x,y coordinates.
      
      int currentSuit = game.getSuitForCard(index);
      int currentRank = game.getRankForCard(index);
      String currentRankName = PlayingCard.getRankName(currentRank);

			if(playerID==0) {
				mb.setColor(Color.white);
				mb.fillRect(dpc1X+cardImageIndex*cardTW+cardSpace, dpc1Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc1X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc1Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace, dpc1Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc1Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace, dpc1Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc1X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc1Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				//System.out.println("Card Suit: " + game.getSuitForCard(index));
				if(currentSuit==PlayingCard.CardSuitHearts || currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
				}
				mb.setFont(fontCard);
				mb.drawString(currentRankName, dpc1X+cardImageIndex*cardTW+cardSpace*2+2, dpc1Y+cardAH);

				if(currentSuit==PlayingCard.CardSuitSpades)
				{
					mb.setColor(Color.black);
					mb.fillOval(dpc1X+cardImageIndex*cardTW+13, pclpY/2, 20, 20);
					mb.fillOval(dpc1X+cardImageIndex*cardTW+28, pclpY/2, 20, 20);
					mb.fillArc(dpc1X+cardImageIndex*cardTW+11, pclpY/2-25, 40, 30, 230, 80);
					mb.fillRect(dpc1X+cardImageIndex*cardTW+27, pclpY/2, 8, 25);
				}else if(currentSuit==PlayingCard.CardSuitHearts) 
				{
					mb.setColor(Color.red);
					mb.fillOval(dpc1X+cardImageIndex*cardTW+13, pclpY/2-10, 21, 21);
					mb.fillOval(dpc1X+cardImageIndex*cardTW+29, pclpY/2-10, 21, 21);
					mb.fillArc(dpc1X+cardImageIndex*cardTW+11, pclpY/2+7, 40, 30, 49, 80);
				}
				else if(currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
					int x1,x2,x3,x4,y1,y2,y3,y4;
					x1=dpc1X+30+cardImageIndex*cardTW;
					y1=dpc1Y+19;
					x2=dpc1X+7+cardImageIndex*cardTW;
					y2=dpc1Y+50;
					x3=dpc1X+30+cardImageIndex*cardTW;
					y3=dpc1Y+80;
					x4=dpc1X+53+cardImageIndex*cardTW;
					y4=dpc1Y+50;
					int[] xPoly= {x1,x2,x3,x4};
					int[] yPoly= {y1,y2,y3,y4};
					mb.fillPolygon(xPoly, yPoly, 4);
				}
				else if(currentSuit==PlayingCard.CardSuitClubs) {
					mb.setColor(Color.black);
					mb.fillOval(dpc1X+cardImageIndex*cardTW+13, pclpY/2, 21, 21);
					mb.fillOval(dpc1X+cardImageIndex*cardTW+28, pclpY/2, 21, 21);
					mb.fillOval(dpc1X+cardImageIndex*cardTW+21, pclpY/2-16, 20, 20);
					mb.fillRect(dpc1X+cardImageIndex*cardTW+27, pclpY/2+4, 8, 25);				
					}

				
				cardImageIndex++;
				index++;
			}
			else if(playerID==1) {//top right, need same method for player 6
				mb.setColor(Color.white);
				mb.fillRect(dpc2X+cardSpace, dpc2Y+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, cardAH,  cardAW-2*cardEdgeSoftener);
				mb.fillRect(dpc2X+cardSpace+cardEdgeSoftener, dpc2Y+cardImageIndex*cardTW+cardSpace, cardAH-2*cardEdgeSoftener, cardAW);
				mb.fillOval(dpc2X+cardSpace, dpc2Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc2X+cardSpace, dpc2Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc2X+cardSpace+cardAH-2*cardEdgeSoftener, dpc2Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc2X+cardAH+cardSpace-2*cardEdgeSoftener, dpc2Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				//System.out.println("Card Suit: " + currentSuit);
				if(currentSuit==PlayingCard.CardSuitHearts || currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
				}
				mb.setFont(fontCard);
				mb.drawString(currentRankName, dpc2X+cardSpace*2+2, dpc2Y+cardImageIndex*cardTW+cardSpace*2+12);

				if(currentSuit==PlayingCard.CardSuitSpades)
				{

					mb.setColor(Color.black);
					mb.fillOval(dpc2X+30, dpc2Y+cardImageIndex*cardTW+30,  20, 20);
					mb.fillOval(dpc2X+45, dpc2Y+cardImageIndex*cardTW+30,  20, 20);
					mb.fillArc(dpc2X+28, dpc2Y+cardImageIndex*cardTW+4, 40, 30, 230, 80);
					mb.fillRect(dpc2X+44, dpc2Y+cardImageIndex*cardTW+30, 8, 25);
				}else if(currentSuit==PlayingCard.CardSuitHearts) 
				{
					mb.setColor(Color.red);
					mb.fillOval(dpc2X+32, dpc2Y+cardImageIndex*cardTW+20, 21, 21);
					mb.fillOval(dpc2X+48,dpc2Y+cardImageIndex*cardTW+20, 21, 21);
					mb.fillArc(dpc2X+30, dpc2Y+cardImageIndex*cardTW+37, 40, 30, 49, 80);
				}
				else if(currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
					int x1,x2,x3,x4,y1,y2,y3,y4;
					x1=dpc2X+30+20;
					y1=dpc2Y+19+cardImageIndex*cardTW;
					x2=dpc2X+7+30;
					y2=dpc2Y+50+cardImageIndex*cardTW-10;
					x3=dpc2X+30+20;
					y3=dpc2Y+80+cardImageIndex*cardTW-20;
					x4=dpc2X+53+10;
					y4=dpc2Y+50+cardImageIndex*cardTW-10;
					int[] xPoly= {x1,x2,x3,x4};
					int[] yPoly= {y1,y2,y3,y4};
					mb.fillPolygon(xPoly, yPoly, 4);
				}
				else if(currentSuit==PlayingCard.CardSuitClubs) {
					mb.setColor(Color.black);
					mb.fillOval(dpc2X+33, dpc2Y+cardImageIndex*cardTW+31, 21, 21);
					mb.fillOval(dpc2X+48, dpc2Y+cardImageIndex*cardTW+31, 21, 21);
					mb.fillOval(dpc2X+41, dpc2Y+cardImageIndex*cardTW+16, 20, 20);
					mb.fillRect(dpc2X+47, dpc2Y+cardImageIndex*cardTW+35, 8, 25);				
					}

				
				cardImageIndex++;
				index++;
			}
			else if(playerID==2) {
				mb.setColor(Color.white);
				mb.fillRect(dpc3X+cardImageIndex*cardTW+cardSpace, dpc3Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc3X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc3Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace, dpc3Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc3Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace, dpc3Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc3X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc3Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				//System.out.println("Card Suit: " + currentSuit);
				if(currentSuit==PlayingCard.CardSuitHearts || currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
				}
				mb.setFont(fontCard);
				mb.drawString(currentRankName, dpc3X+cardImageIndex*cardTW+cardSpace*2+2, dpc3Y+cardAH);

				if(currentSuit==PlayingCard.CardSuitSpades)
				{
					mb.setColor(Color.black);
					mb.fillOval(dpc3X+cardImageIndex*cardTW+13, dpc3Y+45, 20, 20);
					mb.fillOval(dpc3X+cardImageIndex*cardTW+28, dpc3Y+45, 20, 20);
					mb.fillArc(dpc3X+cardImageIndex*cardTW+11, dpc3Y+20, 40, 30, 230, 80);
					mb.fillRect(dpc3X+cardImageIndex*cardTW+27, dpc3Y+45, 8, 25);
				}else if(currentSuit==PlayingCard.CardSuitHearts) 
				{
					mb.setColor(Color.red);
					mb.fillOval(dpc3X+cardImageIndex*cardTW+13, dpc3Y+35, 21, 21);
					mb.fillOval(dpc3X+cardImageIndex*cardTW+29, dpc3Y+35, 21, 21);
					mb.fillArc(dpc3X+cardImageIndex*cardTW+11, dpc3Y+52, 40, 30, 49, 80);
				}
				else if(currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
					int x1,x2,x3,x4,y1,y2,y3,y4;
					x1=dpc3X+30+cardImageIndex*cardTW;
					y1=dpc3Y+19;
					x2=dpc3X+7+cardImageIndex*cardTW;
					y2=dpc3Y+50;
					x3=dpc3X+30+cardImageIndex*cardTW;
					y3=dpc3Y+80;
					x4=dpc3X+53+cardImageIndex*cardTW;
					y4=dpc3Y+50;
					int[] xPoly= {x1,x2,x3,x4};
					int[] yPoly= {y1,y2,y3,y4};
					mb.fillPolygon(xPoly, yPoly, 4);
				}
				else if(currentSuit==PlayingCard.CardSuitClubs) {
					mb.setColor(Color.black);
					mb.fillOval(dpc3X+cardImageIndex*cardTW+13, dpc3Y+45, 21, 21);
					mb.fillOval(dpc3X+cardImageIndex*cardTW+28, dpc3Y+45, 21, 21);
					mb.fillOval(dpc3X+cardImageIndex*cardTW+21, dpc3Y+29, 20, 20);
					mb.fillRect(dpc3X+cardImageIndex*cardTW+27, dpc3Y+49, 8, 25);				
					}

				
				cardImageIndex++;
				index++;
			}
			else if(playerID==3) {
				mb.setColor(Color.white);
				mb.fillRect(dpc4X+cardImageIndex*cardTW+cardSpace, dpc4Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc4X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc4Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace, dpc4Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc4Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace, dpc4Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc4X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc4Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				//System.out.println("Card Suit: " + currentSuit);
				if(currentSuit==PlayingCard.CardSuitHearts || currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
				}
				mb.setFont(fontCard);
				mb.drawString(currentRankName, dpc4X+cardImageIndex*cardTW+cardSpace*2+2, dpc4Y+cardAH);

				if(currentSuit==PlayingCard.CardSuitSpades)
				{
					mb.setColor(Color.black);
					mb.fillOval(dpc4X+cardImageIndex*cardTW+13, dpc4Y+45, 20, 20);
					mb.fillOval(dpc4X+cardImageIndex*cardTW+28, dpc4Y+45, 20, 20);
					mb.fillArc(dpc4X+cardImageIndex*cardTW+11, dpc4Y+20, 40, 30, 230, 80);
					mb.fillRect(dpc4X+cardImageIndex*cardTW+27, dpc4Y+45, 8, 25);
				}else if(currentSuit==PlayingCard.CardSuitHearts) 
				{
					mb.setColor(Color.red);
					mb.fillOval(dpc4X+cardImageIndex*cardTW+13, dpc4Y+35, 21, 21);
					mb.fillOval(dpc4X+cardImageIndex*cardTW+29, dpc4Y+35, 21, 21);
					mb.fillArc(dpc4X+cardImageIndex*cardTW+11, dpc4Y+52, 40, 30, 49, 80);
				}
				else if(currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
					int x1,x2,x3,x4,y1,y2,y3,y4;
					x1=dpc4X+30+cardImageIndex*cardTW;
					y1=dpc4Y+19;
					x2=dpc4X+7+cardImageIndex*cardTW;
					y2=dpc4Y+50;
					x3=dpc4X+30+cardImageIndex*cardTW;
					y3=dpc4Y+80;
					x4=dpc4X+53+cardImageIndex*cardTW;
					y4=dpc4Y+50;
					int[] xPoly= {x1,x2,x3,x4};
					int[] yPoly= {y1,y2,y3,y4};
					mb.fillPolygon(xPoly, yPoly, 4);
				}
				else if(currentSuit==PlayingCard.CardSuitClubs) {
					mb.setColor(Color.black);
					mb.fillOval(dpc4X+cardImageIndex*cardTW+13, dpc4Y+45, 21, 21);
					mb.fillOval(dpc4X+cardImageIndex*cardTW+28, dpc4Y+45, 21, 21);
					mb.fillOval(dpc4X+cardImageIndex*cardTW+21, dpc4Y+29, 20, 20);
					mb.fillRect(dpc4X+cardImageIndex*cardTW+27, dpc4Y+49, 8, 25);				
					}

				
				cardImageIndex++;
				index++;
			}
			else if(playerID==4) {
				mb.setColor(Color.white);
				mb.fillRect(dpc5X+cardImageIndex*cardTW+cardSpace, dpc5Y+cardSpace+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
				mb.fillRect(dpc5X+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, dpc5Y+cardSpace, cardAW-2*cardEdgeSoftener, cardAH);
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace, dpc5Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc5Y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace, dpc5Y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc5X+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, dpc5Y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				//System.out.println("Card Suit: " + currentSuit);
				if(currentSuit==PlayingCard.CardSuitHearts || currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
				}
				mb.setFont(fontCard);
				mb.drawString(currentRankName, dpc5X+cardImageIndex*cardTW+cardSpace*2+2, dpc5Y+cardAH);

				if(currentSuit==PlayingCard.CardSuitSpades)
				{
					mb.setColor(Color.black);
					mb.fillOval(dpc5X+cardImageIndex*cardTW+13, dpc5Y+45, 20, 20);
					mb.fillOval(dpc5X+cardImageIndex*cardTW+28, dpc5Y+45, 20, 20);
					mb.fillArc(dpc5X+cardImageIndex*cardTW+11, dpc5Y+20, 40, 30, 230, 80);
					mb.fillRect(dpc5X+cardImageIndex*cardTW+27, dpc5Y+45, 8, 25);
				}else if(currentSuit==PlayingCard.CardSuitHearts) 
				{
					mb.setColor(Color.red);
					mb.fillOval(dpc5X+cardImageIndex*cardTW+13, dpc5Y+35, 21, 21);
					mb.fillOval(dpc5X+cardImageIndex*cardTW+29, dpc5Y+35, 21, 21);
					mb.fillArc(dpc5X+cardImageIndex*cardTW+11, dpc5Y+52, 40, 30, 49, 80);
				}
				else if(currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
					int x1,x2,x3,x4,y1,y2,y3,y4;
					x1=dpc5X+30+cardImageIndex*cardTW;
					y1=dpc5Y+19;
					x2=dpc5X+7+cardImageIndex*cardTW;
					y2=dpc5Y+50;
					x3=dpc5X+30+cardImageIndex*cardTW;
					y3=dpc5Y+80;
					x4=dpc5X+53+cardImageIndex*cardTW;
					y4=dpc5Y+50;
					int[] xPoly= {x1,x2,x3,x4};
					int[] yPoly= {y1,y2,y3,y4};
					mb.fillPolygon(xPoly, yPoly, 4);
				}
				else if(currentSuit==PlayingCard.CardSuitClubs) {
					mb.setColor(Color.black);
					mb.fillOval(dpc5X+cardImageIndex*cardTW+13, dpc5Y+45, 21, 21);
					mb.fillOval(dpc5X+cardImageIndex*cardTW+28, dpc5Y+45, 21, 21);
					mb.fillOval(dpc5X+cardImageIndex*cardTW+21, dpc5Y+29, 20, 20);
					mb.fillRect(dpc5X+cardImageIndex*cardTW+27, dpc5Y+49, 8, 25);				
					}

				
				cardImageIndex++;
				index++;
			}
			else if(playerID==5) {
				mb.setColor(Color.white);
				mb.fillRect(dpc6X+cardSpace, dpc6Y+cardImageIndex*cardTW+cardSpace+cardEdgeSoftener, cardAH,  cardAW-2*cardEdgeSoftener);
				mb.fillRect(dpc6X+cardSpace+cardEdgeSoftener, dpc6Y+cardImageIndex*cardTW+cardSpace, cardAH-2*cardEdgeSoftener, cardAW);
				mb.fillOval(dpc6X+cardSpace, dpc6Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
				mb.fillOval(dpc6X+cardSpace, dpc6Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
				mb.fillOval(dpc6X+cardSpace+cardAH-2*cardEdgeSoftener, dpc6Y+cardImageIndex*cardTW+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.fillOval(dpc6X+cardAH+cardSpace-2*cardEdgeSoftener, dpc6Y+cardImageIndex*cardTW+cardSpace+cardAW-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
				mb.setColor(Color.black);

				//System.out.println("Card Suit: " + currentSuit);
				if(currentSuit==PlayingCard.CardSuitHearts || currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
				}
				mb.setFont(fontCard);
				mb.drawString(currentRankName, dpc6X+cardSpace*2+2, dpc6Y+cardImageIndex*+cardTW+cardSpace*2+12);

				if(currentSuit==PlayingCard.CardSuitSpades)
				{

					mb.setColor(Color.black);
					mb.fillOval(dpc6X+30, dpc6Y+cardImageIndex*cardTW+30,  20, 20);
					mb.fillOval(dpc6X+45, dpc6Y+cardImageIndex*cardTW+30,  20, 20);
					mb.fillArc(dpc6X+28, dpc6Y+cardImageIndex*cardTW+4, 40, 30, 230, 80);
					mb.fillRect(dpc6X+44, dpc6Y+cardImageIndex*cardTW+30, 8, 25);
				}else if(currentSuit==PlayingCard.CardSuitHearts) 
				{

					mb.fillOval(dpc6X+32, dpc6Y+cardImageIndex*cardTW+20, 21, 21);
					mb.fillOval(dpc6X+48,dpc6Y+cardImageIndex*cardTW+20, 21, 21);
					mb.fillArc(dpc6X+30, dpc6Y+cardImageIndex*cardTW+37, 40, 30, 49, 80);
				}
				else if(currentSuit==PlayingCard.CardSuitDiamonds) {
					mb.setColor(Color.red);
					int x1,x2,x3,x4,y1,y2,y3,y4;
					x1=dpc6X+30+20;
					y1=dpc6Y+19+cardImageIndex*cardTW;
					x2=dpc6X+7+30;
					y2=dpc6Y+50+cardImageIndex*cardTW-10;
					x3=dpc6X+30+20;
					y3=dpc6Y+80+cardImageIndex*cardTW-20;
					x4=dpc6X+53+10;
					y4=dpc6Y+50+cardImageIndex*cardTW-10;
					int[] xPoly= {x1,x2,x3,x4};
					int[] yPoly= {y1,y2,y3,y4};
					mb.fillPolygon(xPoly, yPoly, 4);
				}
				else if(currentSuit==PlayingCard.CardSuitClubs) {

					mb.setColor(Color.black);
					mb.fillOval(dpc6X+33, dpc6Y+cardImageIndex*cardTW+31, 21, 21);
					mb.fillOval(dpc6X+48, dpc6Y+cardImageIndex*cardTW+31, 21, 21);
					mb.fillOval(dpc6X+41, dpc6Y+cardImageIndex*cardTW+16, 20, 20);
					mb.fillRect(dpc6X+47, dpc6Y+cardImageIndex*cardTW+35, 8, 25);				
					}

				
				cardImageIndex++;
				index++;
			}
			
		}	
		

	public void dealCards(String deck[], int round) {
		//deal 1 round of cards to each player in the game
		//System.out.println("Dealing");
		int currentRound = round-1;
		int players = game.getNumberOfPlayers();
		if(players ==0) {
			//System.out.println("NO players to deal to");
		}
		else //we got players
		{
			int cardIndex=0;
			int i= 0;
			while( i < players) {
			//deal 1 card to each player

				if(deck[cardIndex]== "Card in Play")
					{
					//System.out.println("nextCard");
					//System.out.println(cardIndex);

					cardIndex++;
					
					}
				else{
					//playergetcard
					if(i==0) {
						//System.out.println(cardIndex);

						playerNumber1[currentRound]=deck[cardIndex];
						System.out.println(deck[cardIndex] +": card dealt to player " + 1);
						deck[cardIndex]="Card in Play";
						cardIndex++;
						i++;
					}
					else if(i==1) {
						//System.out.println(cardIndex);
						playerNumber2[currentRound]=deck[cardIndex];
						System.out.println(deck[cardIndex] +": card dealt to player " + 2);
						deck[cardIndex]="Card in Play";
						cardIndex++;
						i++;	
					}
					else if(i==2) {
						playerNumber3[currentRound]=deck[cardIndex];
						//System.out.println(deck[cardIndex] + ": card dealt to player " + 3);
						deck[cardIndex]="Card in Play";
						cardIndex++;
						i++;
					}
					else if(i==3) {
						playerNumber4[currentRound]=deck[cardIndex];
						
						//System.out.println(deck[cardIndex] + ": card dealt to player " + 4);
						deck[cardIndex]="Card in Play";
						cardIndex++;
						i++;
					}
					else if(i==4) {
						playerNumber5[currentRound]=deck[cardIndex];
						
						//System.out.println(deck[cardIndex] + ": card dealt to player " + 5);
						deck[cardIndex]="Card in Play";
						cardIndex++;
						i++;
					}
					else if(i==5) {
						playerNumber6[currentRound]=deck[cardIndex];
						
						//System.out.println(deck[cardIndex] +": card dealt to player " + 6);
						deck[cardIndex]="Card in Play";
						cardIndex++;
						i++;
					}
					else{
						//System.out.println("Ineligble player");
						}
					}	
				}			
			}
		}
	

	public class ActCall implements ActionListener {
	
	@Override
		public void actionPerformed(ActionEvent e) {
			callButtonHit = true;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Call Button");
		
		}
	}
	
	public class ActRaise implements ActionListener{

	@Override
		public void actionPerformed(ActionEvent e) {
				raiseButtonHit = false;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Raise Button");		
		}
	}

	
	public class ActFold implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
			foldButtonHit = false;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Fold Button");		
		}
	}
	
	public class ActMuck implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
		
			muckCardsButtonHit = false;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Muck Button");
		
		}
	}	
	
	public class ActAnte implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
			
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Ante Button");
		
		}
	}
	

	public class ActNextHand implements ActionListener {
		

	@Override
		public void actionPerformed(ActionEvent e) {
			startNextHandButtonHit = false;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Next Hand Button");
		
		}
	}	
	
	public class ActEndGame implements ActionListener {
		
	@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			System.out.println("You just clicked the End Game Button");
		
		}
	}
	
	public class ActContinueB implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
			continueButtonHit = false;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Continue Button");		
		}
	}	
	
	public class ActNewGame implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
			newGameButtonHit = false;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the New Game Button");		
		}
	}
	
	public class ActLeaveGame implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
			leaveTableButtonHit = false;
			// TODO Auto-generated method stub
			System.out.println("You just clicked the Leave Game Button");
		
		}
	}	

	public class ActAddPlayer1 implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		player1Active = true;
		addPlayer1.setVisible(false);
		PokerPlayer player1 = new PokerPlayer("player1", false,5);
		try {
			game.AddPlayer(player1);
		} catch (InvalidActivityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("You just Added Player 1");
		}
	}
	public class ActAddPlayer2 implements ActionListener {	

	@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		player2Active = true;
		addPlayer2.setVisible(false);
		PokerPlayer player2 = new PokerPlayer("player2", false,5);
		try {
			game.AddPlayer(player2);
		} catch (InvalidActivityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		System.out.println("You just added Player 2");		
		}
	}

	public class ActAddPlayer3 implements ActionListener {		

	@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		player3Active = true;
		addPlayer3.setVisible(false);
		PokerPlayer player3 = new PokerPlayer("player3", false,5);
		try {
			game.AddPlayer(player3);
		} catch (InvalidActivityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("You just added Player 3");		
		}
	}
	public class ActAddPlayer4 implements ActionListener {		

	@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		player4Active = true;
		addPlayer4.setVisible(false);
		PokerPlayer player4 = new PokerPlayer("player4", false,5);
		try {
			game.AddPlayer(player4);
		} catch (InvalidActivityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("You just added Player 4");		
		}
	}
	public class ActAddPlayer5 implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
		player5Active = true;
		addPlayer5.setVisible(false);
		PokerPlayer player5 = new PokerPlayer("player5", false,5);
		try {
			game.AddPlayer(player5);
		} catch (InvalidActivityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub
		System.out.println("You just added Player 5");		
		}
	}
	public class ActAddPlayer6 implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
		player6Active = true;
		addPlayer6.setVisible(false);
		PokerPlayer player6 = new PokerPlayer("player6", false,5);
		try {
			game.AddPlayer(player6);
		} catch (InvalidActivityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub
		System.out.println("You just added Player 6");		
		}
	}	


	public class ActShowCards implements ActionListener {

	@Override
		public void actionPerformed(ActionEvent e) {
		showCardsButtonHit = false;
		// TODO Auto-generated method stub
		System.out.println("Showing Human Player Cards");		
		}

	
	}

	public void drawNames() {
		// TODO Auto-generated method stub
		//if time permits this will draw player names to the seat that they have added instead of showing player1,  2, 3, etc.
		
	}	
	
}
