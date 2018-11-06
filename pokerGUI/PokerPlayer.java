


package pokerGame

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


//backend imports for the class that gives cards their values 
import
//import for backend class that supports the individual players
import
//import for backend class that handles in game requests
import 

public class pokerPlayer extends JPanel {
	
	  //Filled D button image when player is dealer. //
    private static final Icon BUTTON_PRESENT_ICON =
            ResourceManager.getIcon("/images/dealer_icon.png");
    
    / No D button image when player is not dealer. //
    private static final Icon BUTTON_ABSENT_ICON =
            ResourceManager.getIcon("/images/nodeal_icon.png");
    
    private static final Icon CARD_PLACEHOLDER_ICON =
        ResourceManager.getIcon("/images/placeholder.png");

    private static final Icon CARD_BACK_ICON =
            ResourceManager.getIcon("/images/back.png");
    
	//border 
	private static final Border BORDER = new EmptyBorder(10, 10, 10, 10);
	//name border for player
	//dealer button image label//
	private JLabel dealerButton;
	private JLabel nameLabel;
	//cash label for player
	private JLabel actionLabel;
	//current bet label//
	private JLable cashLabel;
	//last action performed label//
	private JLabel betLabel;
	//first hold card label//
	private JLabel card1Label
	//second hold card label//
	private JLabel card2Label;
	

	//player Constructor
    public PlayerPanel() {
        setBorder(BORDER);
        setBackground(UIConstants.TABLE_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints playercon = new GridBagConstraints();
        
        nameLabel = new MyLabel();
        cashLabel = new MyLabel();
        actionLabel = new MyLabel();
        betLabel = new MyLabel();
        card1Label = new JLabel(CARD_PLACEHOLDER_ICON);
        card2Label = new JLabel(CARD_PLACEHOLDER_ICON);
        dealerButton = new JLabel(BUTTON_ABSENT_ICON);
        
        playercon.gridx = 0;
        playercon.gridy = 0;
        playercon.gridwidth = 2;
        playercon.gridheight = 1;
        playercon.weightx = 1.0;
        playercon.weighty = 1.0;
        playercon.anchor = GridBagConstraints.CENTER;
        playercon.fill = GridBagConstraints.NONE;
        add(dealerButton, playercon);
        playercon.gridx = 0;
        playercon.gridy = 1;
        playercon.gridwidth = 1;
        playercon.gridheight = 1;
        playercon.insets = new Insets(1, 1, 1, 1);
        playercon.anchor = GridBagConstraints.CENTER;
        playercon.fill = GridBagConstraints.HORIZONTAL;
        playercon.weightx = 1.0;
        playercon.weighty = 1.0;
        add(nameLabel, playercon);
        playercon.gridx = 1;
        playercon.gridy = 1;
        playercon.gridwidth = 1;
        playercon.gridheight = 1;
        playercon.weightx = 1.0;
        playercon.weighty = 1.0;
        playercon.anchor = GridBagConstraints.CENTER;
        playercon.fill = GridBagConstraints.HORIZONTAL;
        add(cashLabel, playercon);
        playercon.gridx = 0;
        playercon.gridy = 2;
        playercon.gridwidth = 1;
        playercon.gridheight = 1;
        playercon.weightx = 1.0;
        playercon.weighty = 1.0;
        playercon.anchor = GridBagConstraints.CENTER;
        playercon.fill = GridBagConstraints.HORIZONTAL;
        add(actionLabel, playercon);
        playercon.gridx = 1;
        playercon.gridy = 2;
        playercon.gridwidth = 1;
        playercon.gridheight = 1;
        playercon.weightx = 1.0;
        playercon.weighty = 1.0;
        playercon.anchor = GridBagConstraints.CENTER;
        playercon.fill = GridBagConstraints.HORIZONTAL;
        add(betLabel, playercon);
        playercon.gridx = 0;
        playercon.gridy = 3;
        playercon.gridwidth = 1;
        playercon.gridheight = 1;
        playercon.weightx = 1.0;
        playercon.weighty = 1.0;
        playercon.anchor = GridBagConstraints.CENTER;
        playercon.fill = GridBagConstraints.NONE;
        add(card1Label, playercon);
        playercon.gridx = 1;
        playercon.gridy = 3;
        playercon.gridwidth = 1;
        playercon.gridheight = 1;
        playercon.weightx = 1.0;
        playercon.weighty = 1.0;
        playercon.anchor = GridBagConstraints.CENTER;
        playercon.fill = GridBagConstraints.NONE;
        add(card2Label, playercon);

        setInTurn(false);
        setDealer(false);
    }
//extendable class for name customization
	private static class MyLabel extends JLabel {

        public PlayherLabel() {
            setBorder(UIConstants.LABEL_BORDER);
            setForeground(UIConstants.TEXT_COLOR);
            setHorizontalAlignment(JLabel.HORIZONTAL);
            setText(" ");
        }
        
    }
	//visual panel update uses the player class
    public void update(Player player) {
        nameLabel.setText(player.getName());
        cashLabel.setText("$ " + player.getCash());
        int bet = player.getBet();
        if (bet == 0) {
            betLabel.setText(" ");
        } else {
            betLabel.setText("$ " + bet);
        }
        Action action = player.getAction();
        if (action != null) {
            actionLabel.setText(action.getName());
        } else {
            actionLabel.setText(" ");
        }
        if (player.hasCards()) {
            Card[] cards = player.getCards();
            if (cards.length == 2) {
                // Visible cards.
                card1Label.setIcon(ResourceManager.getCardImage(cards[0]));
                card2Label.setIcon(ResourceManager.getCardImage(cards[1]));
            } else {
                // Hidden cards (face-down).
                card1Label.setIcon(CARD_BACK_ICON);
                card2Label.setIcon(CARD_BACK_ICON);
            }
        } else {
            // No cards.
            card1Label.setIcon(CARD_PLACEHOLDER_ICON);
            card2Label.setIcon(CARD_PLACEHOLDER_ICON);
        }
    }	
	//sets the visual for the player thats the dealer
	public void setDealer(boolean isDealer) {
        if (isDealer) {
            dealerButton.setIcon(BUTTON_PRESENT_ICON);
        } else {
            dealerButton.setIcon(BUTTON_ABSENT_ICON);
        }
    }
	
	
	//sets the order in which players play with the color
    public void setInTurn(boolean inTurn) {
        if (inTurn) {
            nameLabel.setForeground(Color.YELLOW);
        } else {
            nameLabel.setForeground(Color.GREEN);
        }
    }








}
