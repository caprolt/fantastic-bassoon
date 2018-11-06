


package pokerGame


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

//backend functionalities

public class MainBoard extends JFrame implements Client {
	
	private static final long
	private static final 
	private static final int
	private static final int
	private final Table table;
	private final Map<String, Player> players;
	private final Player humanPlayer;
	private String dealerName;
	private String actorName;
	
	
	public Main() {
		super ("Texas Hold'em Poker");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		con = new GridBagConstraints();
		
		controlPanel= new ControlPanel(TypeTable);
		pokerBoardPanel = new PokerBoardPanel(pokerControl);
		addComponent(pokerBoardPanel, 1, 1, 1, 1);
		
		players = new LinkedHashMap<String, Player>();
		humanPlayer = new PokerPlayer("Player", STARTING_CASH, this);
		players.put("Player", humanPlayer);
		players.put("Michael", new Player("Michael", STARTING_CASH, new Bot(0,75)));
		players.put("Crystal", new Player("Crystal", STARTING_CASH, new Bot(25,50)));
		players.put("Edward", new Player("Edward", STARTING_CASH, new Bot(50,25)));
		
		table = new Table(TypeTable, BIG_BLIND);
		for(Player player : players.values())  {
			table.addPlayer(player);
		}
		
		
		playerPanels = new HashMap<String, PlayerPanel>();
        int i = 0;
		
        for (Player player : players.values()) {
            PlayerPanel panel = new PlayerPanel();
            playerPanels.put(player.getName(), panel);
            switch (i++) {
                case 0:
                    // North
                    addComponent(panel, 1, 0, 1, 1);
                    break;
                case 1:
                    // East
                    addComponent(panel, 2, 1, 1, 1);
                    break;
                case 2:
                    // South
                    addComponent(panel, 1, 2, 1, 1);
                    break;
                case 3:
                    // West
                    addComponent(panel, 0, 1, 1, 1);
                    break;
                default:
                    //nothing.
            }
        }
		
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		table.run();
		
	}
	
	public static void main(String[] args) {
        new Main();
    }
	
    @Override
    public void joinedTable(TypeTable type, int bigBlind, List<Player> players) {
        for (Player player : players) {
            PlayerPanel playerPanel = playerPanels.get(player.getName());
            if (playerPanel != null) {
                playerPanel.update(player);
            }
        }
    }
	@Override
    public void messageReceived(String message) {
        boardPanel.setMessage(message);
        boardPanel.waitForUserInput();
    }
	
    @Override
    public void handStarted(Player dealer) {
        setDealer(false);
        dealerName = dealer.getName();
        setDealer(true);
    }
	
    @Override
    public void actorRotated(Player actor) {
        setActorInTurn(false);
        actorName = actor.getName();
        setActorInTurn(true);
    }
	
    @Override
    public void boardUpdated(List<Card> cards, int bet, int pot) {
        boardPanel.update(cards, bet, pot);
    }
	
    @Override
    public void playerUpdated(Player player) {
        PlayerPanel playerPanel = playerPanels.get(player.getName());
        if (playerPanel != null) {
            playerPanel.update(player);
        }
    }	
	
    @Override
    public void playerActed(Player player) {
        String name = player.getName();
        PlayerPanel playerPanel = playerPanels.get(name);
        if (playerPanel != null) {
            playerPanel.update(player);
            Action action = player.getAction();
            if (action != null) {
                boardPanel.setMessage(String.format("%s %s.", name, action.getVerb()));
                if (player.getClient() != this) {
                    boardPanel.waitForUserInput();
                }
            }
        } else {
            throw new IllegalStateException(
                    String.format("No Panel found for player '%s'", name));
        }
    }
	
    @Override
    public Action act(int minBet, int currentBet, Set<Action> allowedActions) {
        boardPanel.setMessage("Please select an action:");
        return controlPanel.getUserInput(minBet, humanPlayer.getCash(), allowedActions);
    }
	
	
	
    private void addComponent(Component component, int x, int y, int width, int height) {
        con.gridx = x;
        con.gridy = y;
        con.gridwidth = width;
        con.gridheight = height;
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.NONE;
        con.weightx = 0.0;
        con.weighty = 0.0;
        getContentPane().add(component, gc);
    }
	
	    private void setActorInTurn(boolean isInTurn) {
        if (actorName != null) {
            PlayerPanel playerPanel = playerPanels.get(actorName);
            if (playerPanel != null) {
                playerPanel.setInTurn(isInTurn);
            }
        }
    }
	
	
	    private void setDealer(boolean isDealer) {
        if (dealerName != null) {
            PlayerPanel playerPanel = playerPanels.get(dealerName);
            if (playerPanel != null) {
                playerPanel.setDealer(isDealer);
            }
        }
    }
	
	
	
	
	
	
	
	
}	
	