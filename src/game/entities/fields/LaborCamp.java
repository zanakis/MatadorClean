package game.entities.fields;

import game.boundaries.Outputable;
import game.entities.FieldManager;
import game.entities.Player;
import game.util.DieCup;

public class LaborCamp extends AbstractOwnable {


	private DieCup dices;		// for rent calculation 
	private int fieldNo;

	public LaborCamp(FieldManager fieldManager, int price, int baseRent, Outputable output, int fieldNo ) {
		super(fieldManager, FieldType.LABOR_CAMP, price, baseRent, output, fieldNo);
		dices = new DieCup();
		this.fieldNo = fieldNo;
	}
	
	/************************************************************************
	 * Method that will recognize the amount of rent you have to pay		*
	 * if you land on another players labor camp.							*
	 ***********************************************************************/
	@Override
	public void landOnField(Player player){
		
		// checks if the field is owned
		if (this.owner == null) {
			super.landOnField(player);
		}
		
		// checks if the player is the owner if not. pay rent
		else if (this.owner != player) {
			// check how many LABOR CAMPS owner has
			int fieldsOwned = fieldManager.getFieldsOwned(owner, FieldType.LABOR_CAMP);
			output.showRollingDiceForRent(player.getName());
			
			int amountToPay = dices.roll() * rent * fieldsOwned; 
			output.setDice(dices.getDice());
			transferRent(amountToPay, player);
		}
		// checks if the player is the owner
		else if (this.owner == player) {
			output.showPlayerIsOwner(player.getName());

		}
	}
	@Override
	public int getFieldNo(){
		return fieldNo;
	}
	
	/************************************************************************
	 * Method to transfer money from the player to the owner of the field	*
	 * @param amountToPay The amount of money the player has to pay in rent	*
	 * @param player The player who has to pay the rent						*
	 ***********************************************************************/
	private void transferRent(int amountToPay, Player player){
		// withdraws the due rent or the rest of the players balance if he/she can't afford it 
		int withdrawAmount = player.withdraw(amountToPay);
		// deposits the withdrawed money
		owner.deposit(withdrawAmount);
		output.showTransferMessage(player.getName(), owner.getName(), withdrawAmount);
	}
	
	@Override
	public String toString(){
		return super.toString() + ", baserent=" + rent;
	}
}
