package game.entities;

import java.util.ArrayList;

import game.boundaries.Outputable;

public class GameBoard {

	public ArrayList<Player> players;
	public FieldManager fieldManager;
	private int playerTurn;
	

	/************************************************************
	 * Constructor, takes names starting balance and a gui      *
	 ************************************************************/
	public GameBoard(ArrayList<String> names, int startingBalance, int startingPosition,int startingHouses,int startingHotels,int startingPrisonCards, int startingPrisonTime, Outputable gui){
		
		players =  new ArrayList<Player>();
	
		fieldManager = new FieldManager(gui);
		// Makes a new ArrayList of the players
		for (int i = 0; i < names.size(); i++) {
			players.add(new Player(names.get(i), startingBalance, startingPosition,false, false,startingPrisonTime,startingPrisonCards,startingHouses,startingHotels));
		}
	
		fieldManager = new FieldManager(gui);
	}		
	
	public int getActivePlayerPosition(){
		return players.get(playerTurn).getPosition();
	
	}

	public Player getActivePlayer(){
		return players.get(playerTurn);
	}
	public int getActivePlayerBalance(){
		return players.get(playerTurn).getBalance();
	}

	public String getActivePlayerName(){
		return players.get(playerTurn).getName();
	}
	public int getActivePlayerHouses(){
		return players.get(playerTurn).getHousesOwned();
	}
	public int getActivePlayerHotels(){
		return players.get(playerTurn).getHotelsOwned();
	}
	public int getActivePlayerPrisonCards(){
		return players.get(playerTurn).getOutOfJailCard();
	}

	/**************************************
	 * Returns true if there is a winner  *
	 **************************************/
	public boolean getWinner(){
		
		// If arraylist is only one player long we have a winner
		if (players.size() == 1){
			return true;
		}
		return false;
	}
	
	/******************************************************
	 * Moves the player an int amount from where he is    *
	 ******************************************************/
	public void moveActivePlayer(int amount ){
		players.get(playerTurn).setPosition((players.get(playerTurn).getPosition() + amount) % fieldManager.getNumberOfFields());
		fieldManager.newPosAmount=amount;
		fieldManager.oldPos=players.get(playerTurn).getPosition()-amount;
		if(players.get(playerTurn).getPosition()+amount>40){
			players.get(playerTurn).deposit(4000);
		}
		
	}
	/********************************************
	 * Registers player movement in fieldManager*
	 ********************************************/
	public void activePlayerFieldAction(){
		fieldManager.landOnFieldByNumber(players.get(playerTurn), players.get(playerTurn).getPosition());
	}

	/*****************************
	 * Resolves starting player  *
	 *****************************/
	public void findStartingPlayer(){
		playerTurn = (int)(Math.random() * players.size());
	}
	
	/********************************************************
	 * Checks to see if activePlayer is broke. 				*
	 * If the player is broke all his fields are released.	*
	 * The player is removed from the players array.		*
	 * And the players name is returned						*
	 ********************************************************/
	public String isActivePlayerBroke(){
		String playerName = null;
		if (players.get(playerTurn).getBroke()){
			playerName = players.get(playerTurn).getName();
			fieldManager.freeFields(players.get(playerTurn));
			players.remove(playerTurn);
			playerTurn = playerTurn == 0? players.size()-1 : playerTurn-1;
			
		}

		return playerName;
	}
	/********************************************************
	 * Checks to see if activePlayer is imprisoned. 		*
	 * If player is imprisoned, the game shifts turn		*
	 ********************************************************/
	public String isActivePlayerImprisoned(){
		String playerName = null;
		if(players.get(playerTurn).getImprisoned()==true){
			playerName = players.get(playerTurn).getName();
			players.get(playerTurn).setPosition(11);
			players.get(playerTurn).setTimeInPrison(1);
		}
		return playerName;
	}
	/*******************************************
	 * Changes playersTurn to the next in line *
	 *******************************************/
	public void nextTurn(){
		playerTurn++;
		if (playerTurn >= players.size()){
			playerTurn = 0;
		}	
		
	}
	}

