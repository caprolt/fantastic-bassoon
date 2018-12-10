/*
 *  project: c:\Users\gino.canessa\Documents\UIS\2018_Fall\CSC478\fantastic-bassoon
 *     file: pokerGame\src\main\java\org\csc478\pokerGame\GameWindow.java
 *  created: 2018-12-09 14:52:11
 *       by: Gino Canessa
 * modified: 2018-12-09
 *       by: Gino Canessa
 *
 *  summary: Main UI Window for this game
 */

 
package org.csc478.pokerGame;

import javax.activity.InvalidActivityException;
import javax.swing.*;

import org.csc478.pokerGame.models.CardDeck;
import org.csc478.pokerGame.models.GameAction;
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameWindow extends JFrame implements PropertyChangeListener {

  //#region Constants

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

  public PokerGame _pokerGame;

  /** A random number generator for this deck */
  private Random _rand;

  //#endregion Object variables

  //#region Constructors

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

    GamePanel gamePanel = new GamePanel(
      this,
      _defaultWidth, 
      _defaultHeight
      );

    // **** set our content to our game UI ****

    this.setContentPane(gamePanel);

    // **** do not use a layout manager ****

    this.setLayout(null);

    // **** display our window ****

    this.setVisible(true);
  }

  //#endregion Constructors



  //#region Action handlers

  public void HandleButtonCallPress() {
    _pokerGame.PlayerActionCall(0, _pokerGame.getCurrentMinumumBetForPlayerIndex(0));
  }

  public void HandleButtonRaisePress() {
    System.out.println("Pressed Raise");
  }

  public void HandleButtonFoldPress() {
    _pokerGame.PlayerActionFold(0);
  }

  public void HandleButtonMuckPress() {
    System.out.println("Pressed Muck");
  }

  public void HandleButtonAntePress() {
    System.out.println("Pressed Ante");
  }

  public void HandleButtonAddPlayerPress() {

    // **** get the number of players so we know if human or computer ****

    int currentPlayerCount = _pokerGame.getNumberOfPlayers();

    // **** check for too many players (ignore) ****

    if (currentPlayerCount >= 6) {
      System.out.println("Ignoring add player request - too many players");
      return;
    }

    String playerName;
    boolean isComputer = true;
    int skillLevel = 0;

    // **** first player is human ****

    if (currentPlayerCount == 0) {
      int initialType = JOptionPane.QUESTION_MESSAGE;				
      playerName = JOptionPane.showInputDialog(
        null, 
        "Please enter your name",
        "Player Name",
        initialType
        );
      isComputer = false;
    } else {

        // **** get a random name from our list ****

        int playerNameIndex = _rand.nextInt(_computerPlayerNames.length);

        playerName = _computerPlayerNames[playerNameIndex];
        skillLevel = playerNameIndex % 10;
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

    // **** need to redraw ui ****
    
    this.repaint();
  }

  public void HandleButtonStartGamePress() {
    
    // **** check for stargin the game ****

    if (!_pokerGame.CanGameStart()) {
      System.out.println("Cannot start yet");
      return;
    }

    // **** set ourselves to receive game action notifications ****

    _pokerGame.AddGameEventListener(this);

    // **** start the game ****

    _pokerGame.StartGame();
  }

  public void propertyChange(PropertyChangeEvent event) {
    GameAction action = (GameAction)event.getNewValue();

    int gameActionType = action.getActionType();

    System.out.println(String.format("Recevied action number: %d, type: %s (%d), for player: %d (%s)",
      action.getGameActionNumber(),
      GameAction.getActionName(gameActionType),
      gameActionType,
      action.getGamePlayerIndex(),
      action.getPlayerId()
      ));
    

    // **** check for player action type ****

    if (gameActionType == GameAction.GameActionTypeWaitOnPlayerAction) {

      // **** traverse players and output cards ****

      int playerCount = _pokerGame.getNumberOfPlayers();

      for (int playerIndex = 0; playerIndex < playerCount; playerIndex++)
      {
        // **** get cards ****

        List<PlayingCard> cards = _pokerGame.getPlayerHand(playerIndex).getCards();

        System.out.print(String.format("Player: %d cards: ", playerIndex));

        // **** traverse cards ****

        for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++)
        {
          PlayingCard card = cards.get(cardIndex);

          // **** output this card ****

          System.out.print(String.format("%s of %s ",
            PlayingCard.getRankName(card.getCardRank()),
            PlayingCard.getSuitName(card.getCardSuit())
            ));
          
          if (card.getCardState() == PlayingCard.CardStateFaceUp) {
            System.out.print(" (U), ");
          } else {
            System.out.print(" (D), ");
          }
        }

        System.out.println();
      }

      // **** get list of valid actions ****

      List<GameActionTypes> validActions = _pokerGame.getValidPlayerActions();

      // **** get the round number ****

      int roundNumber = _pokerGame.getRoundNumber();

      // **** 

      System.out.println(String.format("Round: %d valid actions: ", roundNumber));

      for (int actionIndex = 0; actionIndex < validActions.size(); actionIndex++)
      {
        System.out.println(String.format("\t%s", GameAction.getActionName(validActions.get(actionIndex))));
      }
    }

    // **** update the UI ****

    this.repaint();
  }

  //#endregion Action handlers
}