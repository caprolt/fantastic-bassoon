

package pokerGame


//IMPORTS
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import TypeTable;

//import for class that configures the different poker requests like posting to big blind, calling allin, folding, small blind, and other necessary functions, from backend
import 



//import for class that provides the method of betting and raising in game, from backend 
import 
import 

public class pokerControl extends JPanel implements ActionListener{

	//function declarations
    private final TypeTable TypeTable;
    private final JButton checkButton;
    private final JButton callButton;
    private final JButton betButton;
    private final JButton raiseButton;
    private final JButton foldButton;
    private final JButton continueButton;
    private final AmountPanel amountPanel;
    private final Object monitor = new Object();
    private Action selectedAction;
	
	//constructor
    public ControlPanel(TypeTable typeTable) {
        this.typeTable = typeTable;
        setBackground(UIConstants.TABLE_COLOR);
        displayAmountPanel = new DisplayAmountPanel();
		foldButton = createActionButton(Action.FOLD);
		checkButton = createActionButton(Action.CHECK);
        betButton = createActionButton(Action.BET);
		callButton = createActionButton(Action.CALL);
        raiseButton = createActionButton(Action.RAISE);
		continueButton = createActionButton(Action.CONTINUE);
        
        
    }

	
	//function waits for user to click continue
    public void waitForUserInput() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                removeAll();
                add(continueButton);
                repaint();
            }
        });
        Set<Action> allowedActions = new HashSet<Action>();
        allowedActions.add(Action.CONTINUE);
        getUserInput(0, 0, allowedActions);
    }
	

    public Action getUserInput(int minBet, int cash, final Set<Action> allowedActions) {
        selectedAction = null;
        while (selectedAction == null) {
            // Show game buttons
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    removeAll();
                    if (allowedActions.contains(Action.CONTINUE)) {
                        add(continueButton);
                    } else {
                        if (allowedActions.contains(Action.CHECK)) {
                            add(checkButton);
                        }
                        if (allowedActions.contains(Action.CALL)) {
                            add(callButton);
                        }
                        if (allowedActions.contains(Action.BET)) {
                            add(betButton);
                        }
                        if (allowedActions.contains(Action.RAISE)) {
                            add(raiseButton);
                        }
                        if (allowedActions.contains(Action.FOLD)) {
                            add(foldButton);
                        }
                    }
                    repaint();
                }
            });
            
            // Wait for the user to select an action.
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    // Ignore.
                }
            }
            
            // In case of a bet or raise, show panel to select amount.
            if (typeTable == TypeTable.NO_LIMIT && (selectedAction == Action.BET || selectedAction == Action.RAISE)) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        removeAll();
                        add(amountPanel);
                        repaint();
                    }
                });
                selectedAction = amountPanel.show(selectedAction, minBet, cash);
                if (selectedAction == Action.BET) {
                    selectedAction = new BetAction(amountPanel.getAmount());
                } else if (selectedAction == Action.RAISE) {
                    selectedAction = new RaiseAction(amountPanel.getAmount());
                } else {
                    // User cancelled.
                    selectedAction = null;
                }
            }
        }
        
        return selectedAction;
    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == continueButton) {
            selectedAction = Action.CONTINUE;
        } else if (source == callButton) {
            selectedAction = Action.CALL; 
		else if (source == checkButton) {
            selectedAction = Action.CHECK;
        }
        }else if (source == raiseButton) {
            selectedAction = Action.RAISE; 
		else if (source == betButton) {
            selectedAction = Action.BET;
        } 
        } else {
            selectedAction = Action.FOLD;
        }
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    private JButton createActionButton(Action action) {
        String label = action.getName();
        JButton button = new JButton(label);
        button.setMnemonic(label.charAt(0));
        button.setSize(100, 30);
        button.addActionListener(this);
        return button;
    }



}