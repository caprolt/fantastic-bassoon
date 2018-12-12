/*
 *  project: c:\Users\gino.canessa\Documents\UIS\2018_Fall\CSC478\fantastic-bassoon
 *     file: pokerGame\src\main\java\org\csc478\pokerGame\GamePanel.java
 *  created: 2018-12-09 14:58:30
 *       by: Gino Canessa
 * modified: 2018-12-11
 *       by: Gino Canessa
 *
 *  summary: Actual UI used by the poker game
 */

package org.csc478.pokerGame;

import javax.swing.*;

import org.csc478.pokerGame.models.GameAction;
import org.csc478.pokerGame.models.GameAction.GameActionTypes;
import org.csc478.pokerGame.models.PlayingCard;
import org.csc478.pokerGame.models.PokerGame;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Poker user interface
 * @csc478.req REQ070102 - Number of players
 * @csc478.req REQ070103 - Human player
 * @csc478.req REQ070104 - Humans borrow money
 * @csc478.req REQ070105 - Start game
 * @csc478.req REQ070106 - Current player info
 * @csc478.req REQ070200 - Game Play Screen
 * @csc478.req REQ070201 - Display Game State
 * @csc478.req REQ070202 - Round information
 * @csc478.req REQ070203 - Current hands
 * @csc478.req REQ070204 - Current player money
 * @csc478.req REQ070205 - Only allow valid actions
 * @csc478.req REQ070206 - Allow raises
 * @csc478.req REQ070207 - Show computer actions
 * @csc478.req REQ070301 - Show scoring
 * @csc478.req REQ070302 - Show game results
 * @csc478.req REQ070303 - Start next hand
 */
public class GamePanel extends JPanel {

  //#region UI Colors

  private static final Color _backgroundColor = new Color(178, 34, 34);
  private static final Color _buttonColor = new Color(204, 204, 0);
  private static final Color _primaryColor = Color.black;

  private static final Color _cardBackPrimaryColor = Color.blue;
  private static final Color _cardBackSecondaryColor = Color.white;

  //#endregion UI Colors

  //#region Object variables

  private GameWindow _gameWindow;

  //#endregion Object variables

  //#region Fonts

  private static final Font _buttonFont = new Font("Times New Roman", Font.PLAIN, 12);
	private static final Font _cardFont = new Font("Times New Roman", Font.BOLD, 18);
	private static final Font _primaryFont = new Font("Times New Roman", Font.BOLD, 28);
	private static final Font _secondaryFont = new Font("Times New Roman", Font.BOLD, 24);
	private static final Font _captionFont = new Font("Times New Roman", Font.PLAIN, 18);

  //#endregion Fonts

  //#region Misc Constants

  private static final long serialVersionUID = 2;

  private static final int _panelWidth = 1280;
  private static final int _panelHeight = 1000;
  //#endregion Misc Constants

  //#region Table Related
  
  private static final Point _tablePos = new Point(335, 210);
  private static final Dimension _tableDims = new Dimension(480, 480);

  private static final int _tableTextOffsetX = 100;
  private static final int _tableTextOffsetY = 120;

  private static final Point _tableRoundNumberPos = new Point(_tablePos.x + _tableTextOffsetX, _tablePos.y + _tableTextOffsetY);
  private static final Point _tableBetPos = new Point(_tablePos.x + _tableTextOffsetX, _tablePos.y + _tableTextOffsetY + 50);
  private static final Point _tablePotPos = new Point(_tablePos.x + _tableTextOffsetX, _tablePos.y + _tableTextOffsetY + 100);
  private static final Point _tableStatePos = new Point(_tablePos.x + _tableTextOffsetX, _tablePos.y + _tableTextOffsetY + 150);
  private static final Point _tableWinnerPos = new Point(_tablePos.x + _tableTextOffsetX, _tablePos.y + _tableTextOffsetY + 200);

  //#endregion Table Related

  //#region Action Panel Related
  
  private static final String _actionPanelButtonText[] = {
    "CALL",
    "RAISE",
    "FOLD",
    "MUCK CARDS",
    "SHOW CARDS",
    "ANTE"
  };

  private static final int _actionPanelButtonCount = 5;
  
  private static final int _actionPanelButtonCall = 0;
  private static final int _actionPanelButtonRaise = 1;
  private static final int _actionPanelButtonFold = 2;
  private static final int _actionPanelButtonMuck = 3;
  private static final int _actionPanelButtonShow = 4;
  private static final int _actionPanelButtonAnte = 5;

  private JButton _actionPanelButtons[];

  private static final Point _actionPanelOffset = new Point(1050, 50);
  private static final Dimension _actionPanelButtonDims = new Dimension(120, 40);
  private static final Dimension _actionPanelButtonSpacingDims = new Dimension(0, 44);

  //#endregion Action Panel Related

  //#region Game Panel Related

  private static final String _gamePanelButtonText[] = {
    "Add Player",
    "Start Game",
    "Borrow"
  };

  private static final int _gamePanelButtonCount = 3;

  private static final int _gamePanelButtonAddPlayer = 0;
  private static final int _gamePanelButtonStartGame = 1;
  private static final int _gamePanelButtonBorrow = 2;

  private JButton _gamePanelButtons[];

  private static final Point _gamePanelOffset = new Point(1050, 500);
  private static final Dimension _gamePanelButtonDims = new Dimension(120, 40);
  private static final Dimension _gamePanelButtonSpacingDims = new Dimension(0, 44);

  //#endregion Game Panel Related

  //#region Player Money Related

  private static final Point _playerMoneyPositions[] = {
    new Point(10, 20),
    new Point(10, 40),
    new Point(10, 60),
    new Point(10, 80),
    new Point(10, 100),
    new Point(10, 120),
    new Point(10, 140),
    new Point(10, 160)
  };

  //#endregion Player Money Related

  //#region Player Related

  private static final Point _playerSeatPositions[] = {
    new Point(500, 110),
    new Point(810, 300),
    new Point(750, 600),
    new Point(500, 700),
    new Point(300, 600),
    new Point(240, 300)
  };

  private static final Dimension _playerSeatDims = new Dimension(94, 94);
  
  private static final Dimension _playerNameTextOffset = new Dimension(10, (int)(_playerSeatDims.height * 0.5) - 5);
  private static final Dimension _playerActionTextOffset = new Dimension(10, (int)(_playerSeatDims.height * 0.5) + 20);
  
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

  //#endregion Player Related

  //#region Card Related

  private static final Dimension _cardDimsHoriz = new Dimension(64, 100);
  private static final Dimension _cardDimsVert = new Dimension(100, 64);
  private static final Dimension _cardSpacingDims = new Dimension(2, 2);
  private static final int _cardCornerRadius = 20;
  private static final int _cardAccentOffset = 10;
  private static final int _cardAccentOffset2x = _cardAccentOffset * 2;

  private static final Dimension _cardTextOffset = new Dimension(6, 6);
  private static final Dimension _cardTextDims = new Dimension(15, 15);

  //#endregion Card Related

  //#region Constructors

  /**
   * Primary constructor for game panel UI
   * @param gameWindow GameWindow this frame belongs in - will receive action notifications
   */
  public GamePanel(GameWindow gameWindow) {

    _gameWindow = gameWindow;

    // _panelWidth = width;
    // _panelHeight = height;

    // **** add UI buttons ****

    addButtons();

    // **** cannot start game until players have been added ****

    setEnableStartGame(false);
  }

  //#endregion Constructors

  //#region Public Interface

  /**
   * Enable and Disable UI elements for the current list of valid actions
   * @param validActions List of GameActionTypes which are currently valid
   */
  public void enableValidActions(List<GameActionTypes> validActions) {

    // **** start with all buttons assumed to be disabled ****

    boolean actionButtonEnabled[] = new boolean[_actionPanelButtonCount];

    // **** traverse our actions enabling buttons ****

    for (int actionIndex = 0; actionIndex < validActions.size(); actionIndex++)
    {
      switch (validActions.get(actionIndex))
      {
        case RequestAnte:
        {
          actionButtonEnabled[_actionPanelButtonAnte] = true;
        }
        break;
        case Call:
        {
          actionButtonEnabled[_actionPanelButtonCall] = true;
        }
        break;
        case Raise:
        {
          actionButtonEnabled[_actionPanelButtonRaise] = true;
        }
        break;
        case Fold:
        {
          actionButtonEnabled[_actionPanelButtonFold] = true;
        }
        break;
        case Muck:
        {
          actionButtonEnabled[_actionPanelButtonMuck] = true;
        }
        break;
        case ShowCards:
        {
          actionButtonEnabled[_actionPanelButtonShow] = true;
        }
        break;
        default:
        {
          // **** do nothing ****
        }
        break;
      }
    }

    // **** enable/disable our buttons ****

    for (int buttonIndex = 0; buttonIndex < _actionPanelButtonCount; buttonIndex++)
    {
      // **** state comes from our array ****

      _actionPanelButtons[buttonIndex].setEnabled(actionButtonEnabled[buttonIndex]);
    }
  }

  /**
   * Enable or disable the Start Game button
   * @param canStart True if the button should be enabled, false if it should be disabled
   */
  public void setEnableStartGame(boolean canStart) {
    _gamePanelButtons[_gamePanelButtonStartGame].setEnabled(canStart);
  }

    /**
   * Enable or disable the Add Player button
   * @param canAddPlayer True if the button should be enabled, false if it should be disabled
   */
  public void setEnableAddPlayer(boolean canAddPlayer) {
    _gamePanelButtons[_gamePanelButtonAddPlayer].setEnabled(canAddPlayer);
  }

  /**
   * Enable or disable the Borrow Money button
   * @param canBorrowMoney True if the button should be enabled, false if it should be disabled
   */
  public void setEnableBorrow(boolean canBorrowMoney) {
    _gamePanelButtons[_gamePanelButtonBorrow].setEnabled(canBorrowMoney);
  }

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

      _actionPanelButtons[buttonIndex].setEnabled(false);

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

        case _actionPanelButtonShow:
        {
          // **** show cards button ****

          _actionPanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonShowPress();
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
          // **** add player button ****

          _gamePanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonAddPlayerPress();
            }
          });
        }
        break;

        case _gamePanelButtonStartGame:
        {
          // **** start game button ****

          _gamePanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonStartGamePress();
            }
          });
        }
        break;

        case _gamePanelButtonBorrow:
        {
          // **** start game button ****

          _gamePanelButtons[buttonIndex].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              _gameWindow.HandleButtonBorrowPress();
            }
          });
        }
        break;

      }

      // **** add to the UI ****

      this.add(_gamePanelButtons[buttonIndex]);
    }
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
      _tablePotPos.x,
      _tablePotPos.y
      );
    
    graphics.drawString(
      String.format("Current Bet: $ %d", _gameWindow._pokerGame.getCurrentMinumumTotalBet()),
      _tableBetPos.x,
      _tableBetPos.y
      );
    
    graphics.drawString(
      String.format(
        "Round: #%d",
        _gameWindow._pokerGame.getRoundNumber()
        ),
      _tableRoundNumberPos.x,
      _tableRoundNumberPos.y
      );

    graphics.drawString(
      _gameWindow._pokerGame.getRoundStateName(),
      _tableStatePos.x,
      _tableStatePos.y
      );
  
    // **** check for a winner ****

    if (_gameWindow._pokerGame.getWinnerIndex() != -1) {
      graphics.drawString(
        String.format(
          "%s Wins $ %d!",
          _gameWindow._pokerGame.getPlayerName(_gameWindow._pokerGame.getWinnerIndex()),
          _gameWindow._pokerGame.getCurrentPot()
        ),
        _tableWinnerPos.x,
        _tableWinnerPos.y
        );
    }
  }

  /**
   * Draw player objects into our UI
   * @param graphics Graphics context to draw onto
   */
  private void drawPlayers(Graphics graphics) {

    // **** check for no cards to draw (done) ****

    if (_gameWindow._pokerGame == null) {
      return;
    }
    
    int currentPlayerCount = _gameWindow._pokerGame.getNumberOfPlayers();

    // **** traverse players ****

    for (int playerIndex = 0; playerIndex < currentPlayerCount; playerIndex++)
    {
      // **** draw in our correct color ****

      graphics.setColor(_primaryColor);

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
        _playerSeatPositions[playerIndex].x + _playerNameTextOffset.width,
        _playerSeatPositions[playerIndex].y + _playerNameTextOffset.height
      );

      // **** check to see if this player has a 'last action' ****

      GameAction lastAction = _gameWindow._pokerGame.getPlayerLastAction(playerIndex);

      // **** set font to the caption font for player totals and other content ****

      graphics.setFont(_captionFont);

      // **** draw the player's total amount ****

      graphics.drawString(
        String.format(
          "$ %d: %s (%d-%d)",
          _gameWindow._pokerGame.getPlayerDollars(playerIndex),
          _gameWindow._pokerGame.getPlayerName(playerIndex),
          _gameWindow._pokerGame.getPlayerWins(playerIndex),
          _gameWindow._pokerGame.getPlayerLosses(playerIndex)
          ),
        _playerMoneyPositions[playerIndex].x,
        _playerMoneyPositions[playerIndex].y
      );

      // **** check for displaying hand type ****

      if (_gameWindow._pokerGame.getRoundState() == PokerGame.GameRoundStateGameOver) {
        graphics.drawString(
          _gameWindow._pokerGame.getScoreName(playerIndex),
          _playerSeatPositions[playerIndex].x + _playerActionTextOffset.width,
          _playerSeatPositions[playerIndex].y + _playerActionTextOffset.height
        );
      }
      // **** check for displaying last action ****
      else if ((playerIndex != 0) && (lastAction != null)) {
        int lastActionType = lastAction.getActionType();
        int lastActionAmount =lastAction.getAmount();

        if (lastActionAmount != 0) {
          graphics.drawString(
            String.format("%s: $%d", GameAction.getActionName(lastActionType), lastAction.getAmount()),
            _playerSeatPositions[playerIndex].x + _playerActionTextOffset.width,
            _playerSeatPositions[playerIndex].y + _playerActionTextOffset.height
          );
        } else {
          graphics.drawString(
            GameAction.getActionName(lastActionType),
            _playerSeatPositions[playerIndex].x + _playerActionTextOffset.width,
            _playerSeatPositions[playerIndex].y + _playerActionTextOffset.height
          );
        }
      }

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

      // **** determine if we should draw all cards face-up ****

      boolean showCards = ((playerIndex == 0) || (_gameWindow._pokerGame.getRoundState() == PokerGame.GameRoundStateGameOver));

      // **** draw this player's cards ****

      drawCardsForPlayer(graphics, playerIndex, showCards);
    }
  }

  /**
   * Draw cards for a player
   * @param graphics Graphics context to draw onto
   * @param playerIndex Array index of the player
   * @param showAllCards True to show all cards, false to show only face up cards
   */
  private void drawCardsForPlayer(Graphics graphics, int playerIndex, boolean showAllCards) {
    // **** get this player's cards ****

    List<PlayingCard> cards = _gameWindow._pokerGame.getPlayerHand(playerIndex).getCards();

    // **** traverse the cards ****

    for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++)
    {
      // **** draw this card ****

      drawCard(graphics, playerIndex, cardIndex, cards.get(cardIndex), showAllCards);
    }
  }

  /**
   * Draw the specified card at the specifed location with the specified orientation
   * @param graphics Graphics context to draw onto
   * @param playerIndex Index of the player this card belongs to
   * @param cardNumber Card number to draw
   * @param card Card to draw
   * @param forceFaceUp True to force draw this card face up
   */
  private void drawCard(
    Graphics graphics, 
    int playerIndex,
    int cardNumber, 
    PlayingCard card,
    boolean forceFaceUp) {

    // **** determine if we are horizontal or vertical ****
    
    boolean horizontal = _playerCardsAreHoriz[playerIndex];
  
    // **** start with the player card area locaiton ****

    Point cardPosition = new Point(_playerCardPositions[playerIndex]);
    Dimension cardDims;

    // **** handle horizontal vs vertical ****

    if (horizontal == true) {
      // **** cards use horizontal dimensions ****

      cardDims = _cardDimsHoriz;

      // **** offset cards on the x-axis ****

      cardPosition.x += (cardDims.width + _cardSpacingDims.width) * cardNumber;

    } else {
      // **** cards use vertical dimensions ****

      cardDims = _cardDimsVert;

      // **** offset cards on the y-axis ****

      cardPosition.y += (cardDims.height + _cardSpacingDims.height) * cardNumber;
    }

    // **** card outline is black ****

    graphics.setColor(_primaryColor);

    // **** draw the ouline ****

    graphics.drawRect(cardPosition.x, cardPosition.y, cardDims.width, cardDims.height);

    // **** check for drawing face-down ****

    if ((forceFaceUp == false) && (card.getCardState() == PlayingCard.CardStateFaceDown)) {

      // **** draw the base of the card ****

      graphics.setColor(_cardBackPrimaryColor);

      // **** draw the card base ****

      graphics.fillRoundRect(
        cardPosition.x,
        cardPosition.y,
        cardDims.width,
        cardDims.height,
        _cardCornerRadius,
        _cardCornerRadius
        );

      // **** change to accent color ****

      graphics.setColor(_cardBackSecondaryColor);

      // **** draw the card accent ****

      graphics.drawRect(
        cardPosition.x + _cardAccentOffset,
        cardPosition.y + _cardAccentOffset,
        cardDims.width - _cardAccentOffset2x,
        cardDims.height - _cardAccentOffset2x
        );
      
      // **** done drawing ****

      return;
    }

    // **** card front should be white for legibility ****

    graphics.setColor(Color.white);

    // **** draw the base of the card ****

    graphics.fillRoundRect(
      cardPosition.x,
      cardPosition.y,
      cardDims.width,
      cardDims.height,
      _cardCornerRadius,
      _cardCornerRadius
      );
    
    // **** get card info ****

    int suit = card.getCardSuit();
    int rank = card.getCardRank();
    String rankName = PlayingCard.getRankName(rank);

    // **** set our card drawing font ****

    graphics.setFont(_cardFont);

    // **** set the color based on the card type ****

    if ((suit == PlayingCard.CardSuitDiamonds) || (suit == PlayingCard.CardSuitHearts)) {
      graphics.setColor(Color.red);
    } else {
      graphics.setColor(Color.black);
    }
    
    // **** draw the card rank ****

    graphics.drawString(
      rankName,
      cardPosition.x + _cardTextOffset.width, 
      cardPosition.y + _cardTextOffset.height + _cardTextDims.height
      );

    // **** bottom draw string needs special handling for '10' ****

    if (rank == 10) {
      graphics.drawString(
        rankName, 
        cardPosition.x + cardDims.width - _cardTextOffset.width - (int)(_cardTextDims.width * 1.5), 
        cardPosition.y + cardDims.height - _cardTextOffset.height
        );
    } else {
      graphics.drawString(
        rankName, 
        cardPosition.x + cardDims.width - _cardTextOffset.width - _cardTextDims.width, 
        cardPosition.y + cardDims.height - _cardTextOffset.height
        );
    }
    
    // **** draw the correct symbol ****

    switch (suit) {
      case PlayingCard.CardSuitClubs:
      {
        int centerX = cardPosition.x + (int)(cardDims.width * 0.5);
        int centerY = cardPosition.y + (int)(cardDims.height * 0.5);

        graphics.fillOval(centerX-20, centerY-10, 21, 21);
        graphics.fillOval(centerX+00, centerY-10, 21, 21);
        graphics.fillOval(centerX-10, centerY-22, 20, 20);
        graphics.fillRect(centerX-5,  centerY-10, 10, 30);
      }
      break;
      case PlayingCard.CardSuitDiamonds:
      {
        int centerX = cardPosition.x + (int)(cardDims.width * 0.5);
        int centerY = cardPosition.y + (int)(cardDims.height * 0.5);

        int[] xPoly= {
          centerX,
          centerX - 15,
          centerX,
          centerX + 15
        };
        int[] yPoly= {
          centerY - 20,
          centerY,
          centerY + 20,
          centerY
        };
        graphics.fillPolygon(xPoly, yPoly, 4);
      }
      break;
      case PlayingCard.CardSuitHearts:
      {
        int centerX = cardPosition.x + (int)(cardDims.width * 0.5);
        int centerY = cardPosition.y + (int)(cardDims.height * 0.5);

        graphics.fillOval(centerX - 14, centerY - 15, 21, 21);
        graphics.fillOval(centerX, centerY - 15, 21, 21);
        graphics.fillArc(centerX - 17, centerY, 40, 30, 49, 80);
      }
      break;
      case PlayingCard.CardSuitSpades:
      {
        int centerX = cardPosition.x + (int)(cardDims.width * 0.5);
        int centerY = cardPosition.y + (int)(cardDims.height * 0.5);

        graphics.fillOval(centerX-18, centerY - 10,  20, 20);
        graphics.fillOval(centerX-3,  centerY - 10,  20, 20);
        graphics.fillArc( centerX-20, centerY-35, 40, 30, 230, 80);
        graphics.fillRect(centerX-5,  centerY, 10, 20);
      }
      break;
    }

  }

  //#endregion Internal Functions
}