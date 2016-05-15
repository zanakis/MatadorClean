package game.entities.fields;

import game.boundaries.Outputable;
import game.entities.FieldManager;
import game.entities.Player;

public class Refuge extends AbstractField {

	private int bonus; 
	private int fieldNo;

	public Refuge(FieldManager fieldManager, int bonus, Outputable output, int fieldNo) {
		super(fieldManager, FieldType.REFUGE, output, fieldNo);
		this.bonus = bonus;
		this.fieldNo = fieldNo;
	}

	/************************************************************************
	 * This method will transfer money to the player who lands on a refuge	*
	 * field.																*
	 ***********************************************************************/
	@Override
	public void landOnField(Player player) {
		// deposits amount specified in bonus the player landed on this field
		player.deposit(bonus);		
		
		// outputs result of deposit
		output.showDepositMessage(player.getName(), bonus);
	}
	
	/*******************************************
	 * @return the specific bonus for the field*
	 *******************************************/
	public int getBonus(){
		return bonus;
	}
	@Override
	public int getFieldNo(){
		return fieldNo;
	}
	@Override
	public String toString(){
		return super.toString() + ", bonus=" + bonus;
	}
	
	
}
