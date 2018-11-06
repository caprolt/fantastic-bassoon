



package pokerGame
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//imports for backend action

//import actions


public class displayAmountBoard extends JPanel implements ChangeListener, ActionListener{

private static final int NO_OF_TICKS = 10;

private final JSlider amountSlider;

private final JLabel amountLabel;

private final JButton raiseBetButton;

private final JButton cancelBetButton;

private final HashMap<Integer, Integer> sliderAmounts;

private final Object monitor = new Object();

private Action defaultAction;

private Action selectedAction;

//constructor
public displayAmountBoard() {

	setBackground(UIConstants.TABLE_COLOR);
	
	sliderAmounts = new HashMap<Integer, Integer>();

	setLayout(new GridBagLayout());
	GridBagConstraints con = new GridBagConstraints();
	
	amountSlider = JSlider();
	amountSlider.setBackground(UIConstants.TABLE_COLOR);
	amountSlider.setMajorTickSpacing(1);
	amountSlider.setMinorTickSpacing(1);
	amountSlider.setPaintTicks(true);
	amountSlider.setSnapToTicks(true);
	amountSlider.addChangeListener(this);
	con.gridx=0;
	con.gridy=0;
	con.gridwidth=2;
	con.gridheight=1;
	con.weightx=0.0;
	con.weighty = 0.0;
	con.anchor= GridBagConstraints.CENTER;
	con.fill = GridBagConstraints.NONE;
	con.insets= new Insets(0,0,0,5);
	add(amountSlider, con);

	
	amountSlider = JLabel(" ");
	amountSlider.setForeground(UIConstants.TABLE_COLOR);
	con.gridx=0;
	con.gridy=1;
	con.gridwidth=2;
	con.gridheight=1;
	con.weightx=0.0;
	con.weighty = 0.0;
	con.anchor= GridBagConstraints.CENTER;
	con.fill = GridBagConstraints.NONE;
	con.insets= new Insets(5,0,5,0);
	add(amountLabel, con);
	
	raiseBetButton = JButton("BET");
	raiseBetButton.addActionListener(this);
	con.gridx=0;
	con.gridy=2;
	con.gridwidth=1;
	con.gridheight=1;
	con.weightx=0.0;
	con.weighty = 0.0;
	con.anchor= GridBagConstraints.CENTER;
	con.fill = GridBagConstraints.NONE;
	con.insets= new Insets(0,0,0,0);
	add(raiseBetButton, con);
	
	cancelBetButton= JButton("CANCEL");
	cancelBetButton.addActionListener(this);
	con.gridx=1;
	con.gridy=2;
	con.gridwidth=1;
	con.gridheight=1;
	con.weightx=0.0;
	con.weighty = 0.0;
	con.anchor= GridBagConstraints.CENTER;
	con.fill = GridBagConstraints.NONE;
	con.insets= new Insets(0,0,0,0);
	add(cancelBetButton, con);
}

//reset, show, default actions, min bet, max bet, and returns the selected action

public Action show(Action defaultAction, int minimumBet, int maximumBet){

	this.defaultAction = defaultAction;
	raiseBetButton.setText(defaultAction.getName());
	selectedAction = null;
	
	sliderAmounts.clear();
	int noOfValues= 0;
	int value = minBet;
	while (value < maxBet && noOfValues < (NO_OF_TICKS - 1)) 
	{
		sliderAmounts.put(noOfValues, value);
		noOfValues++;
		value *=2
		}
		
	sliderAmounts.put(noOfValues, maximumBet);
	amountSlider.setMinimum(0);
	amountSlider.setMaximum(noOfValues);
	amountSlider.setValue(0);
	
	//wait for amount to be seleceted by user
	synchronized (monitor) {
		try{
			monitor.wait();
			}
			catch (InterruptedException e) {
					//do nothing
					}
	}
	
	
	return selectedAction;
}

public int getAmount() {

	int index = amountSlider.getValue();
	return sliderAmounts.get(index);
}

@Override
public void stateChanged(ChangeEvent e) {
	
	int index - amountSlider.getValue();
	int amount = sliderAmounts.get(index);
	amountLabel.setText(String.format("$ %d", amount));
}

@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == raiseBetButton) {
			selectedAction = defaultAction;
		}
		else if (e.getSource() == cancelBetButton) {
			selectedAction = null;
		}
		
	synchronized (monitor) {
		monitor.notifyAll();
		}
	}
	

}