package game.entities;

public class Player {


	// Attributes

	private int position, timeInPrison, outOfJailCard, housesOwned, hotelsOwned;
	private String name;
	private Balance balance;
	private boolean isBroke, isImprisoned; 

	// Constructors

	public Player(String name, int startingBalance, int position, boolean isBroke, boolean isImprisoned, int timeInPrison, int outOfJailCard, int housesOwned, int hotelsOwned){
		this.name = name;
		this.balance = new Balance(startingBalance);
		this.isBroke = false; 
		this.position = position;
		this.isImprisoned = false;
		this.timeInPrison = 0;
		this.outOfJailCard = 0;
		this.housesOwned = housesOwned;
		this.hotelsOwned = hotelsOwned;
	}

	// Mutators

	public String getName(){
		return name;
	}

	public int getPosition(){
		return position;
	}

	public void setPosition(int position){
		this.position = position;		
	}

	public boolean getBroke(){
		return isBroke; 
	}

	private void setBroke(boolean isBroke){
		this.isBroke = isBroke;
	}
	public boolean getImprisoned(){
		return isImprisoned;
	}
	public void setImprisoned(boolean isImprisoned){
		this.isImprisoned = isImprisoned;
	}

	public int getBalance(){
		
		return balance.getBalance();
	}

	public void deposit(int amount){
		balance.deposit(amount);
	}
	public void setBalance(int amount){
		balance.setBalance(amount);
	}
	public int getTimeInPrison() {
		return timeInPrison;
	}
	
	/************************************************************************
	 * This method will set the time in prison which a player will sentence	*
	 * If the player has been imprisoned for 3 rounds he is free but has to	* 
	 * pay 1000 from his account.											*
	 * @param timeInPrison Rounds in which the player cannot participate	*
	 ***********************************************************************/
	public void setTimeInPrison(int timeInPrison) {
		this.timeInPrison=getTimeInPrison()+timeInPrison;
		if(getTimeInPrison()==3){
			this.timeInPrison=0;
			this.withdraw(1000);
			this.setImprisoned(false);
		}
	}
	public int getOutOfJailCard (){
		return outOfJailCard;
	}
	public void setOutOfJailCard(int outOfJailCard)	{
		this.outOfJailCard=getOutOfJailCard()+outOfJailCard;
	}
	public void setHousesOwned(int amount){
		this.housesOwned=getHousesOwned()+amount;
	}
	public int getHousesOwned(){
		return housesOwned;
	}
	public void setHotelsOwned(int amount){
		this.hotelsOwned=getHotelsOwned()+amount;
	}
	public int getHotelsOwned(){
		return hotelsOwned;
	}
	

	/************************************************************
	 * Removes the amount from balance set as a parameter.		*
	 * If this is not possible it returns the amount which was	*
	 * able to be withdrawn										*
	 ***********************************************************/
	public int withdraw(int amount){
		int withdrawen = balance.withdraw(amount);

		// if withdrawen amount is less than asked for. player must be broke
		if(withdrawen < amount)
			this.setBroke(true);

		return withdrawen;
	}

	@Override
	public String toString() {
		return "Player [position=" + position + ", name=" + name + ", " + balance + ", isBroke=" + isBroke
				+ "]";
	}

}