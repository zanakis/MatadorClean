package game.entities.fields;

import javax.management.modelmbean.DescriptorSupport;

import game.boundaries.Outputable;
import game.entities.FieldManager;
import game.entities.Player;

public class Start extends AbstractField {
	private int bonus = 4000;
	private int cardNo;
	private int fieldNo;

	public Start(FieldManager fieldManager, int bonus, int cardNo, Outputable output, int fieldNo) {
		super(fieldManager, FieldType.START, output, fieldNo);
		this.bonus = bonus;
		this.cardNo = cardNo;
		this.fieldNo = fieldNo;
		
	}
	@Override
	public int getFieldNo(){
		return fieldNo;
	}

	/********************************************************************
	 * This method will deposit a bonus to the players account when he	*
	 * moves across the 'start' field.									*
	 *******************************************************************/
	public void landOnField(Player player) {
		player.deposit(bonus);
		output.showPassStartMessage(player.getName());
		
	}

}

