package app;


import game.controllers.GameController;

/********************************************************************
 * the App function starts the application and will run it in a		*
 * separate window. Connects to the GameController to execute the	*
 * command.															*
 * @author adamleth													*
 *																	*
 *******************************************************************/

public class App {
	public static void main(String[] args) {
		
		// instantiate GameController and starts game.
		GameController controller = new GameController();
		controller.run();
		
		
	}
}
