package game.entities.fields;

import game.boundaries.Outputable;
import game.entities.FieldManager;
import game.entities.Player;

public abstract class AbstractField {

	public enum FieldType{TERRITORY, LABOR_CAMP, FLEET, TAX, REFUGE, PRISON, LUCKYCARD, START}

	protected FieldManager fieldManager;
	protected AbstractOwnable abstractOwnable;
	protected Outputable output;


	protected FieldType fieldType;


	public AbstractField(FieldManager fieldManager, FieldType fieldType, Outputable output, int fieldNo) {
		this.fieldManager = fieldManager;
		this.output = output;
		this.fieldType = fieldType;
		

	} 

	public FieldType getFieldType(){
		return fieldType;
	}
	public String getOwned(){
		return abstractOwnable.owner.getName();
	}
	
	
	

	// Can be called for a player landing on the field
	public abstract void landOnField(Player player);
	
	public abstract int getFieldNo();

	@Override
	public String toString(){
		switch(fieldType){
		case TERRITORY:
			return "fieldType=Territory";
		case LABOR_CAMP:
			return "fieldType=Labor Camp";
		case FLEET:
			return "fieldType=Fleet";
		case TAX:
			return "fieldType=Tax";
		case REFUGE:
			return "fieldType=Refuge";
		default:
			return "";
		}
	}

}
