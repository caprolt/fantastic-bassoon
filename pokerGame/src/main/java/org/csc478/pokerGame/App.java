///-------------------------------------------------------------------------------------------------
// project:   org.csc478.pokerGame
// file:      src\main\java\org\csc478\pokerGame\App.java
// created:   2018-11-09 10:49:49
// modified:  2018-11-09
//
// summary:   Main entry point for game
///-------------------------------------------------------------------------------------------------

package org.csc478.pokerGame;

// import javax.swing.JFrame;


/**
 * Main entry point of application - spawns a window to play poker
 */
public class App implements Runnable {
	// GUI pokerGUI = new GUI();
	GameWindow window = new GameWindow();

    /**
     * Starts the program to player poker
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
    		new Thread(new App() ).start();
    	
    	
    }

	@Override
	public void run() {

		// window.repaint();

		//uncomment while loop to continuously paint to get updated board (may put in method to only update on variable changing to preserve resources
		//while(true) {
			// pokerGUI.refresher();
			// pokerGUI.repaint();
		//}
	}
}
