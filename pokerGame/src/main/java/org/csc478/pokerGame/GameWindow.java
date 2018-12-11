/*
 *  project: c:\Users\gino.canessa\Documents\UIS\2018_Fall\CSC478\fantastic-bassoon
 *     file: pokerGame\src\main\java\org\csc478\pokerGame\GameWindow.java
 *  created: 2018-12-09 14:52:11
 *       by: Gino Canessa
 * modified: 2018-12-10
 *       by: Gino Canessa
 *
 *  summary: Main UI Window for this game
 */

 
package org.csc478.pokerGame;

import javax.activity.InvalidActivityException;
import javax.swing.*;

import org.csc478.pokerGame.models.GameAction;
import org.csc478.pokerGame.models.GameAction.GameActionTypes;
import org.csc478.pokerGame.models.PokerGame;
import org.csc478.pokerGame.models.PokerPlayer;

import java.util.*;
import java.util.List;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Swing JFrame decendent which displays a window to play poker.
 * Listens for property change events from a PokerGame object.
 */
public class GameWindow extends JFrame implements PropertyChangeListener {

  //#region Constants

  private static final long serialVersionUID = 2;
  
  private static final int _defaultWidth = 1280;
  private static final int _defaultHeight = 1000;

  private static final int _defaultAnte = 0;
  private static final int _defaultLowStake = 10;
  private static final int _defaultHighStake = 50;

  private static final String _computerPlayerNames[] = {
    "Roger",
    "Mike",
    "Steve",
    "Greg",
    "John",
    "Dave",
    "Ken",
    "Frank",
    "Tanner",
    "Charles",
    "Gino"
  };

  //#endregion Constants

  //#region Object variables

  /** Reference to the running game for use by ourselves and the GamePanel */
  public PokerGame _pokerGame;

  /** Game panel showing the UI */
  private GamePanel _gamePanel;

  /** A random number generator for this deck */
  private Random _rand;

  //#endregion Object variables

  //#region Constructors

  /** Default constructor */
  public GameWindow() {

    // **** initialize our random number generator ****

    _rand = new Random();

    this.setSize(_defaultWidth, _defaultHeight);
		this.setTitle("Texas Holdem' Poker Game - 478");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // **** create our game ****

    _pokerGame = new PokerGame(_defaultAnte, _defaultLowStake, _defaultHighStake);

    // **** create our actual UI ****

    _gamePanel = new GamePanel(
      this
      );

    // **** set our content to our game UI ****

    this.setContentPane(_gamePanel);

    // **** do not use a layout manager ****

    this.setLayout(null);

    // **** display our window ****

    this.setVisible(true);

    // **** add the human player ****

    HandleButtonAddPlayerPress();
  }

  //#endregion Constructors

  //#region Action handlers
  
  /** Function to handle the Button Press of a Call action */
  public void HandleButtonCallPress() {
    _pokerGame.PlayerActionCall(0, _pokerGame.getCurrentMinumumBetForPlayerIndex(0));
  }

  /** Function to handle the Button Press of a Raise action */
  public void HandleButtonRaisePress() {

    int minBet = _pokerGame.getCurrentMinumumBetForPlayerIndex(0);
    int playerDollars = _pokerGame.getPlayerDollars(0);

    // **** ask the user for a raise amount ****

    String raiseString = JOptionPane.showInputDialog(
      this, 
      String.format("How much would you like to raise (%d to %d)?", minBet, playerDollars),
      "Raise Amount",
      JOptionPane.QUESTION_MESSAGE
      );
    
    int raiseAmount = -1;

    try {
      raiseAmount = Integer.parseInt(raiseString);
    } catch (NumberFormatException e) {
      // **** do nothing ****
    }
    
    // **** check for an invalid amount ****

    if ((raiseAmount < minBet) || (raiseAmount > playerDollars)) {
      // **** tell the user ****

      JOptionPane.showMessageDialog(
        this, 
        "Invalid bet!",
        "Raise Amount",
        JOptionPane.INFORMATION_MESSAGE
        );

      // **** nothing else to do ****

      return;
    }

    // **** perform the raise ****

    _pokerGame.PlayerActionRaise(0, raiseAmount);
  }

  /** Function to handle the Button Press of a Fold action */
  public void HandleButtonFoldPress() {
    _pokerGame.PlayerActionFold(0);
  }

  /** Function to handle the Button Press of a Muck action */
  public void HandleButtonMuckPress() {
    _pokerGame.PlayerActionMuck(0);
  }

  /** Function to handle the Button Press of a Show Cards action */
  public void HandleButtonShowPress() {
    _pokerGame.PlayerActionShow(0);
  }

  /** Function to handle the Button Press of a Ante action */
  public void HandleButtonAntePress() {
    // **** update the UI ****

    this.repaint();
  }

  /** Function to handle the Button Press of a Add Player action */
  public void HandleButtonAddPlayerPress() {

    // **** get the number of players so we know if human or computer ****

    int currentPlayerCount = _pokerGame.getNumberOfPlayers();

    // **** check for too many players (ignore) ****

    if (currentPlayerCount >= 6) {
      // **** disable the add player button ****

      _gamePanel.setEnableAddPlayer(false);

      System.out.println("Ignoring add player request - too many players");
      return;
    }

    String playerName;
    boolean isComputer = true;
    int skillLevel = 0;

    // **** first player is human ****

    if (currentPlayerCount == 0) {
      // **** ask the user for a player name ****

      playerName = JOptionPane.showInputDialog(
        this, 
        "Please enter your name",
        "Player Name",
        JOptionPane.QUESTION_MESSAGE
        );
      isComputer = false;
    } else {

        // **** get a random name from our list ****

        int playerNameIndex = _rand.nextInt(_computerPlayerNames.length);

        playerName = _computerPlayerNames[playerNameIndex];
        skillLevel = playerNameIndex % 10;
    }

    // **** check for no name (user cancel) ****

    if ((playerName == null) || (playerName == "")) {
      return;
    }

    // **** create our player ****

    PokerPlayer player = new PokerPlayer(playerName, isComputer, skillLevel);

    // **** add a player to the game ****

    try {
      _pokerGame.AddPlayer(player);
    } catch (InvalidActivityException e) {
      e.printStackTrace();
      assert(false);
    }

    // **** enable/disable the start game button ****

    _gamePanel.setEnableStartGame(_pokerGame.CanGameStart());

    // **** check if that was the last player we could add **** 

    if (currentPlayerCount >= 5) {
      // **** disable the add player button ****

      _gamePanel.setEnableAddPlayer(false);
    }

    // **** need to redraw ui ****
    
    this.repaint();
  }

  /** Function to handle the Button Press of a Start Game action */
  public void HandleButtonStartGamePress() {
    // **** check for stargin the game ****

    if (!_pokerGame.CanGameStart()) {

      System.out.println("Cannot start yet");
      return;
    }

    // **** disable the start game button ****

    _gamePanel.setEnableStartGame(false);

    // **** disable the add player button ****

    _gamePanel.setEnableAddPlayer(false);

    // **** set ourselves to receive game action notifications ****

    _pokerGame.AddGameEventListener(this);

    // **** start the game ****

    _pokerGame.StartGame();
  }

  /**
   * Property Change event listener to subscribe to GameAction notifications
   * @param event Event passed to this listener
   */
  public void propertyChange(PropertyChangeEvent event) {
    GameAction action = (GameAction)event.getNewValue();

    int gameActionType = action.getActionType();

    System.out.println(String.format("Recevied action number: %02d, type: %s (%d), for player: %d",
      action.getGameActionNumber(),
      GameAction.getActionName(gameActionType),
      action.getAmount(),
      action.getGamePlayerIndex(),
      action.getPlayerId()
      ));
    
    // **** check for game over action type ****

    if (gameActionType == GameAction.GameActionTypeEndGame) {
      // **** re-enable the start game button ****

      _gamePanel.setEnableStartGame(true);

      // **** disable in-game actions ****

      _gamePanel.enableValidActions(new ArrayList<GameActionTypes>());
    }

    // **** check for player action type ****

    if (gameActionType == GameAction.GameActionTypeWaitOnPlayerAction) {
      // **** get list of valid actions ****

      List<GameActionTypes> validActions = _pokerGame.getValidPlayerActions();

      // **** enable and disable UI elements ****

      _gamePanel.enableValidActions(validActions);
    }

    // **** update the UI ****

    this.repaint();
  }

  //#endregion Action handlers
}