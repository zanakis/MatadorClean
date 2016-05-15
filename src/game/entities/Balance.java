package game.entities;

public class Balance {

	// attributes

	private int balance; 

	// Constructors

	public Balance(int startingBalance){
		this.balance = startingBalance;
	}

	// Mutators
	
	public int getBalance(){
		return balance;
	}
	public void setBalance(int amount){
		this.balance = amount;
	}

	public void deposit(int amount){
		this.balance += amount;
	}

	/****************************************************************
	 * return as much as possible of the asked amount.				*
	 * If balance is less than amount, we return all of the  balance*
	 * 																*
	 * @param amount to be withdrawen								*
	 * @return return amount withdrawen from balance				*
	 ***************************************************************/
	public int withdraw(int amount){
		if (amount <= balance){
			balance -= amount;
		}
		else{
			
			amount = balance;
			balance = 0;
		}
		return amount;
	}

	@Override
	public String toString() {
		return "balance=" + balance;
	}

}
