/*
 *  project: c:\Users\gino.canessa\Documents\UIS\2018_Fall\CSC478\fantastic-bassoon
 *     file: pokerGame\src\main\java\org\csc478\pokerGame\GamePanel.java
 *  created: 2018-12-09 14:58:30
 *       by: Gino Canessa
 * modified: 2018-12-09
 *       by: Gino Canessa
 *
 *  summary: Actual UI used by the poker game
 */

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

public class GamePanel extends JPanel {

  //#region UI Colors

  private static final Color _backgroundColor = new Color(178, 34, 34);
  private static final Color _buttonColor = new Color(204, 204, 0);
  private static final Color _fontColor = Color.black;
  private static final Color _primaryColor = Color.black;

  //#endregion UI Colors

  //#region Object variables

  private GameWindow _gameWindow;

  //#endregion Object variables

  //#region Fonts

  private static final Font _buttonFont = new Font("Times New Roman", Font.PLAIN, 13);
	private static final Font _cardFont = new Font("Times New Roman", Font.BOLD, 19);
	private static final Font _primaryFont = new Font("Times New Roman", Font.BOLD, 30);
	private static final Font _secondaryFont = new Font("Times New Roman", Font.BOLD, 20);

  //#endregion Fonts

  //#region Size/Position Constants

  private static final Point _tableUiOffset = new Point(50, 50);
  private static final Dimension _tableUiDims = new Dimension(900, 400);

  private static final Dimension _primaryButtonDims = new Dimension(120, 40);
  // private static final Dimension _primaryButtonSpacingDims = new Dimension(4, 4);

  //#endregion Size/Position Constants

  //#region Misc Constants

  private static final int _maxPlayers = 6;

  //#endregion Misc Constants

  //#region Size/Position variables

  private int _panelWidth;
  private int _panelHeight;
  
  private static final Point _tablePos = new Point(335, 210);
  private static final Dimension _tableDims = new Dimension(480, 480);

  private Point _currentBetPos;
  private Point _totalPotPos;

  //#endregion Size/Position variables

  //#region Action Panel Related

  
  private static final String _actionPanelButtonText[] = {
    "CALL",
    "RAISE",
    "FOLD",
    "MUCK CARDS",
    "ANTE"
  };

  private static final int _actionPanelButtonCount = 5;
  
  private static final int _actionPanelButtonCall = 0;
  private static final int _actionPanelButtonRaise = 1;
  private static final int _actionPanelButtonFold = 2;
  private static final int _actionPanelButtonMuck = 3;
  private static final int _actionPanelButtonAnte = 4;

  private JButton _actionPanelButtons[];

  private static final Point _actionPanelOffset = new Point(1050, 50);
  private static final Dimension _actionPanelButtonDims = new Dimension(120, 40);
  private static final Dimension _actionPanelButtonSpacingDims = new Dimension(0, 44);

  //#endregion Action Panel Related

  //#region Game Panel Related

  private static final String _gamePanelButtonText[] = {
    "Add Player",
    "Start Game",
  };

  private static final int _gamePanelButtonCount = 2;

  private static final int _gamePanelButtonAddPlayer = 0;
  private static final int _gamePanelButtonStartGame = 1;

  private JButton _gamePanelButtons[];

  private static final Point _gamePanelOffset = new Point(1050, 500);
  private static final Dimension _gamePanelButtonDims = new Dimension(120, 40);
  private static final Dimension _gamePanelButtonSpacingDims = new Dimension(0, 44);


  //#endregion Game Panel Related

  //#region Player Related

  private static final Point _playerSeatPositions[] = {
    new Point(500, 110),
    new Point(810, 300),
    new Point(750, 600),
    new Point(500, 700),
    new Point(300, 600),
    new Point(240, 300)
  };

  private static final Dimension _playerSeatDims = new Dimension(90, 90);
  
  private static final Point _playerCardPositions[] = {
    new Point(345,   5),
    new Point(915, 150),
    new Point(810, 700),
    new Point(345, 850),
    new Point(  5, 700),
    new Point(125, 150)
  };

  private static final Dimension _playerCardDimsHoriz = new Dimension(450, 100);
  private static final Dimension _playerCardDimsVert = new Dimension(100, 450);

  private static final boolean _playerCardsAreHoriz[] = {
    true,
    false,
    true,
    true,
    true,
    false
  };

  private JButton _playerAddButtons[];

  private static final Point _playerAddButtonOffset = new Point(10, 10);
  private static final Dimension _playerAddButtonDims = new Dimension(70, 70);

  //#endregion Player Related

  //#region Card Related

  private static final Dimension _cardDims = new Dimension(64, 100);
  private static final Dimension _cardSpacingDims = new Dimension(2, 2);

  // int cardSpace=2;
	// int cardEdgeSoftener=11;
	// int cardTW= 64;
	// int cardTH= 100;
	// int cardAW=60;
	// int cardAH=96;	
	
  //#endregion Card Related

  //#region Constructors

  /**
   * Primary constructor for game panel UI
   * @param gameWindow GameWindow this frame belongs in - will receive action notifications
   * @param width Width to use for this frame
   * @param height Height ot use for this frame
   */
  public GamePanel(GameWindow gameWindow, int width, int height) {

    _gameWindow = gameWindow;

    _panelWidth = width;
    _panelHeight = height;

    _currentBetPos = new Point((int)(width * 0.5) - 150, (int)(height * 0.5) - 150);
    _totalPotPos = new Point((int)(width * 0.5) - 170, (int)(height * 0.5));

    // **** add UI buttons ****

    addButtons();
  }

  //#endregion Constructors

  //#region Public Interface



  //#endregion Public Interface

  
  //#region Internal Functions

  private void addButtons() {

    // **** create our action button array ****

    _actionPanelButtons = new JButton[_actionPanelButtonCount];

    // **** create our buttons ****

    for (int buttonIndex = 0; buttonIndex < _actionPanelButtonCount; buttonIndex++)
    {
      // **** create this button ****

      _actionPanelButtons[buttonIndex] = new JButton(_actionPanelButtonText[buttonIndex]);

      // **** these buttons are disabled by default (game needs to start first) ****

      // _actionPanelButtons[buttonIndex].setEnabled(false);

      // **** set location and size ****

      _actionPanelButtons[buttonIndex].setBounds(
        _actionPanelOffset.x,
        _actionPanelOffset.y + (buttonIndex * _actionPanelButtonSpacingDims.height), 
        _actionPanelButtonDims.width,
        _actionPanelButtonDims.height);

      // **** set color ****

      _actionPanelButtons[buttonIndex].setBackground(_buttonColor);
      
      // **** set font ****

      _actionPanelButtons[buttonIndex].setFont(_buttonFont);

      // **** setup the correct event handler ****

      switch (buttonIndex) {
        case _actionPanelButtonCall:
        {
          // **** call button ****

          _actionPanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonCallPress();
            }
          });
        }
        break;

        case _actionPanelButtonRaise:
        {
          // **** raise button ****

          _actionPanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonRaisePress();
            }
          });
        }
        break;

        case _actionPanelButtonFold:
        {
          // **** fold button ****

          _actionPanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonFoldPress();
            }
          });
        }
        break;

        case _actionPanelButtonMuck:
        {
          // **** muck button ****

          _actionPanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonMuckPress();
            }
          });
        }
        break;

        case _actionPanelButtonAnte:
        {
          // **** ante button ****

          _actionPanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonAntePress();
            }
          });
        }
        break;
      }

      // **** add to the UI ****

      this.add(_actionPanelButtons[buttonIndex]);
    }

    // **** create our game button array ****

    _gamePanelButtons = new JButton[_gamePanelButtonCount];

    // **** create our buttons ****

    for (int buttonIndex = 0; buttonIndex < _gamePanelButtonCount; buttonIndex++)
    {
      // **** create this button ****

      _gamePanelButtons[buttonIndex] = new JButton(_gamePanelButtonText[buttonIndex]);

      // **** set location and size ****

      _gamePanelButtons[buttonIndex].setBounds(
        _gamePanelOffset.x,
        _gamePanelOffset.y + (buttonIndex * _gamePanelButtonSpacingDims.height), 
        _gamePanelButtonDims.width,
        _gamePanelButtonDims.height);

      // **** set color ****

      _gamePanelButtons[buttonIndex].setBackground(_buttonColor);
      
      // **** set font ****

      _gamePanelButtons[buttonIndex].setFont(_buttonFont);

      // **** setup the correct event handler ****

      switch (buttonIndex) {
        case _gamePanelButtonAddPlayer:
        {
          // **** call button ****

          _gamePanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonAddPlayerPress();
            }
          });
        }
        break;

        case _gamePanelButtonStartGame:
        {
          // **** raise button ****

          _gamePanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonStartGamePress();
            }
          });
        }
        break;
      }

      // **** add to the UI ****

      this.add(_gamePanelButtons[buttonIndex]);
    }

    // // **** create our player button array ****

    // _playerAddButtons = new JButton[_maxPlayers];

    // // **** traverse players ****

    // for (int playerIndex = 0; playerIndex < _maxPlayers; playerIndex++)
    // {

    //   // **** create this button ****

    //   _playerAddButtons[playerIndex] = new JButton("Add");

    //   // **** set location and size ****

    //   _playerAddButtons[playerIndex].setBounds(
    //     _playerSeatPositions[playerIndex].x + _playerAddButtonOffset.x,
    //     _playerSeatPositions[playerIndex].y + _playerAddButtonOffset.y,
    //     _playerAddButtonDims.width,
    //     _playerAddButtonDims.height);

    //   // **** set color ****

    //   _playerAddButtons[playerIndex].setBackground(_buttonColor);
      
    //   // **** set font ****

    //   _playerAddButtons[playerIndex].setFont(_buttonFont);

    //   // **** grab the player index as a final so that Java can copy it

    //   final int arrayPlayerIndex = playerIndex;

    //   // **** setup the correct event handler ****

    //   _playerAddButtons[playerIndex].addActionListener(new ActionListener() {
    //     public void actionPerformed(ActionEvent ae) {
    //       _gameWindow.HandleButtonAddPlayerPress(arrayPlayerIndex);
    //     }
    //   });

    //   // **** add to the UI ****

    //   this.add(_playerAddButtons[playerIndex]);
    // }

  }

  /**
   * Override of paintComponent to draw our UI
   * @param graphics Graphics context to draw onto
   */
  public void paintComponent(Graphics graphics) {
    
    // **** always draw the base UI ****

    drawBaseUi(graphics);

    // **** always draw the table ****

    drawTable(graphics);

    // **** draw player UI ****

    drawPlayers(graphics);
  }

  /**
   * Draw the base UI
   * @param graphics Graphics context to draw onto
   */
  private void drawBaseUi(Graphics graphics) {
    // **** start with the background color ****

    graphics.setColor(_backgroundColor);
    
    // **** clear our ui ****

    graphics.fillRect(0, 0, _panelWidth, _panelHeight);

    // **** 
  }

  /**
   * Draw the table object into our ui
   * @param graphics Graphics context to draw onto
   */
  private void drawTable(Graphics graphics) {
    // **** set our draw color ****

    graphics.setColor(_primaryColor);

    // **** draw our table ****

    graphics.drawOval(_tablePos.x, _tablePos.y, _tableDims.width, _tableDims.height);

    // **** check for no game or round zero (we are done) ****

    if ((_gameWindow._pokerGame == null) || (_gameWindow._pokerGame.getRoundNumber() == 0)) {
      return;
    }

    // **** use the primary UI font ****

    graphics.setFont(_primaryFont);

    // **** display required values ****

    graphics.drawString(
      String.format("Total Pot: $ %d", _gameWindow._pokerGame.getCurrentPot()),
      _totalPotPos.x,
      _totalPotPos.y);
    
    graphics.drawString(
      String.format("Current Bet: $ %d", _gameWindow._pokerGame.getCurrentMinumumTotalBet()),
      _currentBetPos.x,
      _currentBetPos.y);
    
  }

  /**
   * Draw player objects into our UI
   * @param graphics Graphics context to draw onto
   */
  private void drawPlayers(Graphics graphics) {

    // **** draw in our correct color ****

    graphics.setColor(_primaryColor);

    // **** check for no cards to draw (done) ****

    if (_gameWindow._pokerGame == null) {
      return;
    }
    
    int currentPlayerCount = _gameWindow._pokerGame.getNumberOfPlayers();

    // **** traverse players ****

    for (int playerIndex = 0; playerIndex < currentPlayerCount; playerIndex++)
    {
      // **** draw this player's seat ****

      graphics.drawOval(
        _playerSeatPositions[playerIndex].x,
        _playerSeatPositions[playerIndex].y,
        _playerSeatDims.width,
        _playerSeatDims.height
      );
      
      // **** draw the player's name ****

      graphics.setFont(_secondaryFont);
      graphics.drawString(
        _gameWindow._pokerGame.getPlayerName(playerIndex),
        _playerSeatPositions[playerIndex].x + 4,
        _playerSeatPositions[playerIndex].y + (int)(_playerSeatDims.height * 0.5)
      );

      // **** draw this player's card area ****

      if (_playerCardsAreHoriz[playerIndex] == true) {
        // **** draw our horizontal rectangle ****

        graphics.drawRect(
          _playerCardPositions[playerIndex].x,
          _playerCardPositions[playerIndex].y,
          _playerCardDimsHoriz.width,
          _playerCardDimsHoriz.height
          );
      } else {
        // **** draw our horizontal rectangle ****

        graphics.drawRect(
          _playerCardPositions[playerIndex].x,
          _playerCardPositions[playerIndex].y,
          _playerCardDimsVert.width,
          _playerCardDimsVert.height
          );
      }
    }
  }

  private void drawCardsForPlayer(Graphics graphics, int playerIndex, boolean showAllCards) {
    // **** get this player's cards ****

    List<PlayingCard> cards = _gameWindow._pokerGame.getPlayerHand(playerIndex).getCards();

    // **** traverse the cards ****

    for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++)
    {

    }
  }

  /**
   * Draw the specified card at the specifed location with the specified orientation
   * @param graphics Graphics context to draw onto
   * @param position Position this card will be drawn at
   * @param vertical True to draw vertical, false to draw horizontal
   * @param card Card to draw
   */
  private void drawCard(Graphics graphics, Point position, boolean vertical, PlayingCard card) {

    // **** draw the background ****

    graphics.setColor(Color.white);

  //   graphics.fillRect(position.x, position.y+cardEdgeSoftener, cardAW, cardAH-2*cardEdgeSoftener);
  //   graphics.fillRect(position.x+cardEdgeSoftener, position.y, cardAW-2*cardEdgeSoftener, cardAH);
  //   graphics.fillOval(position.x, position.y, 2*cardEdgeSoftener, 2*cardEdgeSoftener);
  //   graphics.fillOval(position.x+cardAW-2*cardEdgeSoftener, position.y+cardSpace, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				 
  //   graphics.fillOval(position.x, position.y+cardSpace+cardAH-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				
  //   graphics.fillOval(position.x+cardAW-2*cardEdgeSoftener, position.y+cardAH+cardSpace-2*cardEdgeSoftener, 2*cardEdgeSoftener, 2*cardEdgeSoftener);				

    
  // private static final Dimension _cardDims = new Dimension(64, 100);
  // private static final Dimension _cardSpacingDims = new Dimension(2, 2);

  // int cardSpace=2;
	// int cardEdgeSoftener=11;
	// int cardTW= 64;
	// int cardTH= 100;
	// int cardAW=60;
	// int cardAH=96;	

  }

  //#endregion Internal Functions
}