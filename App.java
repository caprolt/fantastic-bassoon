///-------------------------------------------------------------------------------------------------
// project:   org.csc478.pokerGame
// file:      src\main\java\org\csc478\pokerGame\App.java
// created:   2018-11-09 10:49:49
// modified:  2018-11-09
//
// summary:   Main entry point for game
///-------------------------------------------------------------------------------------------------

package org.csc478.pokerGame;

import javax.swing.JFrame;


/**
 * Hello world!
 */
public class App implements Runnable {
	GUI pokerGUI = new GUI();

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
    		new Thread(new App() ).start();
    	
    	
    }

	@Override
	public void run() {

		// TODO Auto-generated method stub
		
	}
}
