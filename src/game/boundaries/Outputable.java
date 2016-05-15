package game.boundaries;

import game.entities.Player;

/************************************************************************
 * 																		*
 * Interface defining methods for input output to screen from game		*
 * 																		*
 ************************************************************************/
public interface Outputable {
	
	public void update(int[] dice, int pos, int balance, String playerName);

	public void addPlayer(String playerName, int balance, int playerNo);

	public void setDice(int[] dice);

	public void removeAllOwners();

	public void removeOwner(int fieldNumber);

	public void showUpdateMessage(String playerName, int pos);
	
	public void showWelcome();
	
	public void showStartingPlayer(String playerName);
	
	public void showWinner(String playerName);
	
	public void showWithdrawMessage(String playerName, int amount);
	
	public void showDepositMessage(String playerName, int bonus);//Refuge metode
	
	public void showTransferMessage(String playerName, String ownerName, int amount);
	
	public void showNotEnoughBalanceMessage(String playerName);
	
	public void showBrokeMessage(String playerName);
	
	public void showNotBoughtMessage(String playerName);
	
	public void showFieldBoughtMessage(String playerName, int fieldNumber);
	
	public void showRollingDiceForRent(String playeName);
	
	public void showPlayerIsOwner(String playerName);
	
	public String promptPlayerName(int playerNumber, boolean error);
	
	public void promptRollDice(String playerName);
	
	public boolean promptTax(String playerName, int taxAmount, int percentAmount);
	
	public boolean promptBuy(String name, int price);
	
	public void initializeBoard();


	public int PromptPrison(String playerName);
	
	public void setHouse(int houseCount, int fieldNo);
	
	public void showNoCardMessage(String name);

	public void showGetPrisonCardMessage(String player);//AbstractCard

	public void showDoTimeMessage(String player);
    
	void showCardMessage(String player, int cardNo);//AbstractCard

	public boolean promptBuyProperty(String name, int i);

	public void showEnoughPropertys(String name);

	public void setHotel(boolean hasHotel, int fieldNo);

	public int promptAction(String name);

	public boolean promptSellProperty(String name, int i);

	public void showNoPropertys(String name);

	public int promptGameState();

	public void showImprisonedMessage(String name2);

	public void showCard(String text);

	public boolean promptSellFields(Player player);

	public void showHousesOnFieldMessage(Player player);

	public void showDontSell(Player player);

	public void showParkingMessage(Player player);

	public String promptLoadAction(String[] games);

	public void showPassStartMessage(String name);

	public void showNextPlayerTurn(String name);
	
	public void setFieldOwners(String playerName, int fieldNumber);

	public void showIsDoublesMessage(String name);
	
	






}