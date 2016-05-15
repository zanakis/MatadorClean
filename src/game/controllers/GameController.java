package game.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import game.boundaries.*;
import game.entities.FieldManager;
import game.entities.GameBoard;
import game.entities.Player;
import game.entities.fields.AbstractOwnable;
import game.entities.fields.LuckyCard;
import game.util.DBConnector;
import game.util.DieCup;
import game.util.Rollable;

public class GameController {

	private final int STARTING_BALANCE = 30000;
	private final int STARTING_POSITION = 0;
	private final int STARTING_PRISONTIME = 0;
	private final int STARTING_PRISONCARDS = 0;
	private final int STARTING_HOUSES = 0;
	private final int STARTING_HOTELS = 0;

	public enum GameState {LOAD_STATE, NAME_STATE , PLAY_STATE, WIN_STATE};

	public GameBoard board;
	public String time = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
	public String timeStamp = String.valueOf(time);
	public String gameName;
	private FieldManager fieldManager;
	private int turnNumber = 0;
	private Outputable output;			// Gui controller to change the gui
	private Rollable dieCup;		// dieCup through the Rollable interface for easy change of dice
	public ArrayList<String> names;
	public ArrayList<Player> players;
	public Player tempPlayer;
	public ArrayList<String> gameTable;
	public ArrayList<String> fieldTable;
	private DBConnector con = new DBConnector();

	private GameState state = GameState.LOAD_STATE;

	public GameController(){
		output = new GUIController("resources/language2.xml");
		this.gameName = null;
		dieCup = new DieCup();	
		names = new ArrayList<String>();
		fieldManager = new FieldManager(output);
	}

	/*********************************************
	 * Loop running as long as you play the game *
	 *********************************************/
	public void run(){
		while (true){
			switch (state){
			case LOAD_STATE: loadState();
			break;
			case NAME_STATE: nameState();
			break;
			case PLAY_STATE: playState();
			break;
			case WIN_STATE: winState();
			break;

			}
		}
	}

	/****************************************************************
	 * This method will load a saved game from a database or create	*
	 * a new database for a new game depending on the users choice.	*
	 ***************************************************************/
	private void loadState(){
		int choice = output.promptGameState();
		//Make new game, and create DB.
		if (choice==1){
			try {
				this.gameName = con.createGameDB(timeStamp);
				con.createTables(gameName);


			} catch (Exception e) {
				e.printStackTrace();
			}
			state = GameState.NAME_STATE;
		}
		//load old game.
		else if(choice==2){
			String game = null;
			players = new ArrayList<Player>();
			gameTable = new ArrayList<String>();
			try {
				game = output.promptLoadAction(con.doQueryToString("SHOW DATABASES LIKE '20%'"));
				this.timeStamp = game;
				System.out.println("Database loaded: "+timeStamp);
				con.doQuery("USE "+game);
				this.players = con.loadPlayersToArray("SELECT * FROM player_list ");
				this.gameTable = con.loadGameToArray("SELECT * FROM game ");
				this.fieldTable = con.loadFieldsToArray("SELECT * FROM field");
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.turnNumber = Integer.parseInt(gameTable.get(1));
			
			loadBoard();
			
			// Set state back to play state
			state = GameState.PLAY_STATE;
		}
		
	}

	/*********************************************
	 * Method used to get the players names 	 *
	 ********************************************/
	private void nameState(){
		// Shows a welcome message in the GUI
		output.showWelcome();

		// Adds names to string array
		for (int i = 0; i < 6; i++){
			boolean error = false;
			// Checks if names are long enough and saves them in array
			while (true){

				String name = output.promptPlayerName(i+1, error);
				if (name.length() == 0){
					if(i>=3){
						// break loop (no more players)
						i = 6;
						break;
					}
					else
						error = true;
				}
				else{
					names.add(name);
					// Add player to gui
					output.addPlayer(name, STARTING_BALANCE, i);

					break;
				}				
			}	
		}

		// Creates the gameboard
		initBoard();

		// Set state back to play state
		state = GameState.PLAY_STATE;
	}


	/*******************************************************************
	 * Method used as the main playstate running until winner is found *
	 *******************************************************************/
	private void playState(){
		while (true){

			// If it is the first turn find starting player and show starting player
			if (turnNumber == 0){
				board.findStartingPlayer();
				output.showStartingPlayer(board.getActivePlayerName());
			}
			String name = board.isActivePlayerImprisoned();
			if(name != null){
				output.showImprisonedMessage(name);
				board.nextTurn();
			}

			// Prompts the GUI to show active player and get him to roll
			output.promptRollDice(board.getActivePlayerName());

			// Moves the active player on the board
			board.moveActivePlayer(dieCup.roll());


			// Updates the GUI
			output.update(dieCup.getDice(), board.getActivePlayerPosition(), board.getActivePlayerBalance(), board.getActivePlayerName());
			output.showUpdateMessage(board.getActivePlayerName(),  board.getActivePlayerPosition());
			board.activePlayerFieldAction();

			// Update GUI again.
			output.update(dieCup.getDice(), board.getActivePlayerPosition(), board.getActivePlayerBalance(), board.getActivePlayerName());

			// Check if active player is broke
			String name1 =board.isActivePlayerBroke();
			if(name1 != null){
				output.showBrokeMessage(name1);				
			}


			turnNumber++;

			// Changes turn
			con.updatePlayerTable(timeStamp, board.getActivePlayerName(), board.getActivePlayerBalance(), board.getActivePlayerHouses(), board.getActivePlayerHotels(), board.getActivePlayerPrisonCards(),board.getActivePlayerPosition());
			con.updateFieldTable(timeStamp, fieldManager.getFieldOwner(board.getActivePlayerPosition(),board.getActivePlayer()),board.getActivePlayerPosition(), fieldManager.getHouseCount(board.getActivePlayerPosition(),board.getActivePlayer()), fieldManager.getHotelCount(board.getActivePlayerPosition(), board.getActivePlayer()));
			con.updateGameTable(timeStamp, state.name(), this.turnNumber, board.getActivePlayerName());
			if(board.fieldManager.fields[board.getActivePlayerPosition()] instanceof LuckyCard){
				for (int i = 0; i < 32; i++) {
					con.updateCardsTable(timeStamp, i, LuckyCard.cards[i].getCardNo(), null, LuckyCard.cards[i].getText());
				}
			}
			else{
				board.nextTurn();

				// Check to see if we have a winner
				if (board.getWinner()){
					state = GameState.WIN_STATE;
					return;
				}		
			}
			
		}

	}
	/************************************
	 * Method used when winner is found *
	 ************************************/
	private void winState(){
		// Shows the winner
		output.showWinner(board.getActivePlayerName());
		//Drops the current game table, as the game is no longer active.
		con.dropCurrentGameTable(gameName);
                //Resets game
                state = GameState.LOAD_STATE;
	}

	/************************************************************
	 * Method that will initialize the interactive gameboard.	*
	 ***********************************************************/
	private void initBoard(){
		board = new GameBoard(names,STARTING_BALANCE,STARTING_POSITION,STARTING_HOUSES,STARTING_HOTELS,STARTING_PRISONTIME, STARTING_PRISONCARDS, output);
		con.addToGameTable(timeStamp, state.name(), this.turnNumber, null);
		for (int i = 0; i < names.size(); i++) {
			con.addToPlayerTable(timeStamp, i,names.get(i), STARTING_BALANCE, STARTING_HOUSES, STARTING_HOTELS, STARTING_PRISONCARDS,STARTING_POSITION);
		}
		for (int i = 0; i < fieldManager.NUMBER_OF_FIELDS; i++) {
			con.addToFieldTable(timeStamp, null, fieldManager.fields[i].getFieldNo()-1, 0, 0, fieldManager.fields[i].getFieldType().toString());
		}

		for (int i = 0; i < 32; i++) {
			con.addToCardsTable(gameName, i,LuckyCard.cards[i].getCardNo(), null, LuckyCard.cards[i].getText());
		}
		output.removeAllOwners();


	}
	public void loadBoard(){
		for (int i = 0; i < players.size(); i++) {
		names.add(players.get(i).getName());
		}
		board = new GameBoard(names,STARTING_BALANCE,STARTING_POSITION,STARTING_HOUSES,STARTING_HOTELS,STARTING_PRISONTIME, STARTING_PRISONCARDS, output);
		for (int j = 0; j < players.size(); j++) {
			board.players.get(j).setBalance(players.get(j).getBalance());
			board.players.get(j).setPosition(players.get(j).getPosition());
			board.players.get(j).setOutOfJailCard(players.get(j).getOutOfJailCard());
			board.players.get(j).setHousesOwned(players.get(j).getHousesOwned());
			board.players.get(j).setHotelsOwned(players.get(j).getHotelsOwned());
			output.addPlayer(board.players.get(j).getName(), board.players.get(j).getBalance(), j);
			output.update(dieCup.getDice(), board.players.get(j).getPosition(), board.players.get(j).getBalance(), board.players.get(j).getName());
			
		}
		System.out.println("Players loaded");
		for (int x = 0; x < fieldTable.size(); x++) {
			String[] fieldData = this.fieldTable.get(x).split(",");
			int fieldNumber = Integer.valueOf(fieldData[0]);
			String fieldOwner = fieldData[1];
			int houseOnField = Integer.valueOf(fieldData[2]);
			int hotelOnField = Integer.valueOf(fieldData[3]);
			String fieldType =  fieldData[4];//skal fjernes fra DB
			for (int i = 0; i < players.size(); i++) {
				if (fieldOwner.startsWith(players.get(i).getName())) {
				this.tempPlayer = players.get(i);
				}
			}
			if(board.fieldManager.fields[x] instanceof AbstractOwnable){
					((AbstractOwnable)board.fieldManager.fields[x]).setOwner(tempPlayer);
					this.tempPlayer=null;
					}
				
			if(!fieldOwner.startsWith("null")){
			output.setFieldOwners(fieldOwner, fieldNumber);
			output.setHouse(houseOnField, fieldNumber);
			if (hotelOnField == 1){
				output.setHotel(true, fieldNumber);
			}
			else{
				output.setHotel(false, fieldNumber);
			}
			
			}
		
		}
		System.out.println("Fields loaded");
		
	}
}