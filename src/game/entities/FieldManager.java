package game.entities;

import game.boundaries.Outputable;
import game.entities.fields.AbstractField;
import game.entities.fields.Fleet;
import game.entities.fields.LaborCamp;
import game.entities.fields.LuckyCard;
import game.entities.fields.Prison;
import game.entities.fields.AbstractOwnable;
import game.entities.fields.Refuge;
import game.entities.fields.Start;
import game.entities.fields.Tax;
import game.entities.fields.Territory;
import game.entities.fields.AbstractField.FieldType;
import game.resources.CardEffect;
import game.resources.FieldData;

public class FieldManager {

	public final int NUMBER_OF_FIELDS = 40;
	public AbstractField[] fields;
	public int newPosAmount;
	public int oldPos;


	/**************************************************
	 * Constructor, takes a GUI to pass to the fields *
	 **************************************************/
	public FieldManager(Outputable gui){
		initializeFields(gui);

	}

	public int getNumberOfFields(){
		return NUMBER_OF_FIELDS;
	}

	/***************************************************************************
	 * Returns an int with the number of fields owned of a type by a player    *
	 ***************************************************************************/
	public int getFieldsOwned(Player player, FieldType fieldtype){
		int count = 0;
		// Counts fields owned of a specific type by a player
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof AbstractOwnable){
				if (((AbstractOwnable)fields[i]).getOwner() == player && fields[i].getFieldType() == fieldtype){
					count ++;		
				}
			}
		}
		return count;
	}

	/****************************************************************************
	 * This method will find out who owns a specific field.						*
	 * 																			*
	 * @param fieldNumber Identification of the field that has been landed on	*
	 * @param player Player who has landed on a specific field					*
	 * @return Returns the name of another player who owns the field			*
	 ***************************************************************************/
	public String getFieldOwner(int fieldNumber, Player player){
		if(fields[fieldNumber] instanceof AbstractOwnable){
			if (((AbstractOwnable)fields[fieldNumber]).getOwner()==player);{
				return player.getName();
			}
		}
		else{
			return null;
		}
	}

	/****************************************************************************
	 * This method finds out how many houses are on specific field. Used later	*
	 * for figuring out how much the active player must pay the owner.			*
	 * 																			*
	 * @param fieldNumber The identification of the field in question.			*
	 * @param player Player who owns the field and therefore the houses.		*
	 * @return Returns the amount of houses on a specific field.				*
	 ***************************************************************************/
	public int getHouseCount(int fieldNumber, Player player){
		if(fields[fieldNumber] instanceof Territory){
			if (((AbstractOwnable)fields[fieldNumber]).getOwner()==player);{
				return Territory.houseCount;
			}
		}
		else{
			return 0;
		}
	}

	/****************************************************************************
	 * This method finds out how many hotels are on specific field. Used later	*
	 * for figuring out how much the active player must pay the owner.			*
	 * 																			*
	 * @param fieldNumber The identification of the field in question.			*
	 * @param player Player who owns the field and therefore the hotels.		*
	 * @return Returns the amount of hotels on a specific field.				*
	 ***************************************************************************/	
	public int getHotelCount(int fieldNumber, Player player){
		if(fields[fieldNumber] instanceof Territory){
			if (((AbstractOwnable)fields[fieldNumber]).getOwner()==player);{
				if (Territory.houseCount==5) {
					return 1;
				}
			}
		}
		return 0;
	}


	/*********************************************
	 * Uses landOnField on a field with a player *
	 *********************************************/
	public void landOnFieldByNumber(Player player, int fieldNumber){
		fields[fieldNumber].landOnField(player);
	}

	/****************************************
	 * Returnes the field number of a field *
	 ****************************************/
	public int getFieldNumber(AbstractField field){
		int number  = 0;
		for(int i = 0; i < NUMBER_OF_FIELDS; i++){
			if(field == fields[i])
				number = i;
		}
		return number;

	}

	/*********************************************************
	 * Returnes the value of all fields owned by a player    *
	 *********************************************************/
	public int getFieldsValue(Player player){
		int value = 0;
		for (int i = 0; i < fields.length; i++) {
			// Checks whether fields[i] is of class Ownable
			if(fields[i] instanceof AbstractOwnable){
				//If it is, cast and use ownable methods
				if (((AbstractOwnable)fields[i]).getOwner() == player){
					value += ((AbstractOwnable)fields[i]).getPrice();
				}
			}
		}
		return value;
	}

	/*****************************************************************
	 * Frees up all fields owned by a player so others can buy them  *
	 ****************************************************************/
	public void freeFields(Player player){
		for(AbstractField f: fields){
			if(f instanceof AbstractOwnable){
				if(((AbstractOwnable)f).getOwner() == player)
					((AbstractOwnable)f).clearOwner();
			}
		}
	}



	/*************************************************************
	 * Creates the array of fields used from the FieldData class *
	 ************************************************************/
	private void initializeFields(Outputable gui){
		fields = new AbstractField[NUMBER_OF_FIELDS];

		for (int i = 0; i < fields.length; i++) {
			switch(FieldData.FIELDTYPE_DATA[i]){
			case TERRITORY: 
				fields[i] = new Territory(this, FieldData.FIELDBUYPRICE_DATA[i],FieldData.FIELDRENT1_DATA[i], FieldData.FIELDNUMBER[i], gui);
				break;
			case LABOR_CAMP: 
				fields[i] = new LaborCamp(this, FieldData.FIELDBUYPRICE_DATA[i], FieldData.FIELDRENT1_DATA[i], gui,FieldData.FIELDNUMBER[i]);
				break;
			case FLEET: 
				fields[i] = new Fleet(this, FieldData.FIELDBUYPRICE_DATA[i], gui,FieldData.FIELDNUMBER[i]);
				break;
			case TAX: 
				fields[i] = new Tax(this, FieldData.FIELDRENT1_DATA[i], gui,FieldData.FIELDNUMBER[i]);
				break;
			case REFUGE: 
				fields[i] = new Refuge(this, FieldData.FIELDRENT1_DATA[i], gui,FieldData.FIELDNUMBER[i]);
				break;
			case PRISON:
				fields[i] = new Prison(this,gui,FieldData.FIELDNUMBER[i]);
				break;
			case LUCKYCARD:
				fields[i] = new LuckyCard(this, gui,FieldData.FIELDNUMBER[i]);
				break;
			case START:
				fields[i] = new Start(this, 4000,CardEffect.CardNo_DATA[i], gui,FieldData.FIELDNUMBER[i]);
				break;

			}	
		}	
	}
}
