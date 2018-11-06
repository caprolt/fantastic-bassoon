

package pokerGame

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class PokerBoardPanel extends JPanel {
    

//FUNCTION DECLARATIONS
// The maximum number of community cards. //
//control panel init
//bet label
//pot label
//community card label
//message label
    private static final int NO_OF_CARDS = 5;    
    private final PokerControl pokerControl;
    private final JLabel betLabel;
    private final JLabel potLabel;
    private final JLabel[] cardLabels;
    private final JLabel messageLabel;
    
//constrctor 

    public pokerBoardPanel(PokerControl pokerControl) {
        this.pokerControl = pokerControl;
        
        setBorder(UIConstants.PANEL_BORDER);
        setBackground(UIConstants.TABLE_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        
        JLabel label = new JLabel("BET");
        label.setForeground(Color.GREEN);
        con.gridx = 1;
        con.gridy = 0;
        con.gridwidth = 1;
        con.gridheight = 1;
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.NONE;
        con.weightx = 1.0;
        con.weighty = 0.0;
        con.insets = new Insets(0, 5, 0, 5);
        add(label, gc);
        
        label = new JLabel("POT");
        label.setForeground(Color.GREEN);
        con.gridx = 3;
        con.gridy = 0;
        con.gridwidth = 1;
        con.gridheight = 1;
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.NONE;
        con.weightx = 1.0;
        con.weighty = 0.0;
        con.insets = new Insets(0, 5, 0, 5);
        add(label, gc);
        
        betLabel = new JLabel(" ");
        betLabel.setBorder(UIConstants.LABEL_BORDER);
        betLabel.setForeground(Color.GREEN);
        betLabel.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 1;
        con.gridy = 1;
        con.gridwidth = 1;
        con.gridheight = 1;
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.weightx = 1.0;
        con.weighty = 0.0;
        con.insets = new Insets(5, 5, 5, 5);
        add(betLabel, gc);

        potLabel = new JLabel(" ");
        potLabel.setBorder(UIConstants.LABEL_BORDER);
        potLabel.setForeground(Color.GREEN);
        potLabel.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 3;
        con.gridy = 1;
        con.gridwidth = 1;
        con.gridheight = 1;
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.weightx = 1.0;
        con.weighty = 0.0;
        con.insets = new Insets(5, 5, 5, 5);
        add(potLabel, gc);

        // The five card positions.
        cardLabels = new JLabel[NO_OF_CARDS];
        for (int i = 0; i < 5; i++) {
            cardLabels[i] = new JLabel(ResourceManager.getIcon("/images/card_placeholder.png"));
            con.gridx = i;
            con.gridy = 2;
            con.gridwidth = 1;
            con.gridheight = 1;
            con.anchor = GridBagConstraints.CENTER;
            con.fill = GridBagConstraints.NONE;
            con.weightx = 0.0;
            con.weighty = 0.0;
            con.insets = new Insets(5, 1, 5, 1);
            add(cardLabels[i], gc);
        }
        
        // Message label.
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 0;
        con.gridy = 3;
        con.gridwidth = 5;
        con.gridheight = 1;
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.weightx = 1.0;
        con.weighty = 1.0;
        con.insets = new Insets(0, 0, 0, 0);
        add(messageLabel, gc);
        
        // Control panel.
        con.gridx = 0;
        con.gridy = 4;
        con.gridwidth = 5;
        con.gridheight = 1;
        con.insets = new Insets(0, 0, 0, 0);
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.BOTH;
        con.weightx = 1.0;
        con.weighty = 1.0;
        add(controlPanel, gc);
        
        setPreferredSize(new Dimension(400, 270));
        
        update(null, 0, 0);
    }
    
//updates for the player hand
    public void update(List<Card> cards, int bet, int pot) {
        if (bet == 0) {
            betLabel.setText(" ");
        } else {
            betLabel.setText("$ " + bet);
        }
        if (pot == 0) {
            potLabel.setText(" ");
        } else {
            potLabel.setText("$ " + pot);
        }
        int noOfCards = (cards == null) ? 0 : cards.size();
        for (int i = 0; i < NO_OF_CARDS; i++) {
            if (i < noOfCards) {
                cardLabels[i].setIcon(ResourceManager.getCardImage(cards.get(i)));
            } else {
                cardLabels[i].setIcon(ResourceManager.getIcon("/images/card_placeholder.png"));
            }
        }
    }
    
//sets message
    public void setMessage(String message) {
        if (message.length() == 0) {
            messageLabel.setText(" ");
        } else {
            messageLabel.setText(message);
        }
    }
    
//user input function

    public void waitForUserInput() {
        pokerControl.waitForUserInput();
    }
    
}