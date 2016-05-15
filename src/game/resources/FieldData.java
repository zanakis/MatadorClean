package game.resources;

import java.awt.Color;

import game.entities.fields.AbstractField;
import game.entities.fields.AbstractField.FieldType;

public class FieldData {

	/********************************************************************
	 * All the fields are created in an array of fields and are given	*
	 * a unique fieldnumber for identification from 1 to 41.			*
	 *******************************************************************/
	public static final int[] FIELDNUMBER = {
			1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
			17,18,19,20,21,22,23,24,25,26,27,28,29,
			30,31,32,33,34,35,36,37,38,39,40,41
	};

	/************************************************************
	 * The fields all have specific names of danish roads and 	*
	 * corresponds to a specific fieldnumber.					*
	 ***********************************************************/
	public static final String[] FIELDNAME_DATA = {
			"Start",
			"Rodovrevej",
			"provLykken",
			"Hvidovrevej",
			"Indkomstskat",
			"LBFaergerne",
			"Roskildevej",
			"provLykken",
			"valbyLanggade",
			"Allaegade",
			"Faengsel1",
			"frederiksbergAlle",
			"Carlsberg",
			"Bylowsvej",
			"Kongevej",
			"Danmark",
			"Bernstorfvej",
			"provLykken",
			"Hellerupvej",
			"Strandvej",
			"Parkering",
			"Trianglen",
			"provLykken",
			"Osterbrogade",
			"Gronningen",
			"molsLinien",
			"Bredgade",
			"Nytorv",
			"cocaCola",
			"Ostergade",
			"Faengsel2",
			"Amagertorv",
			"Vimmelskaftet",
			"provLykken",
			"Nygade",
			"skandinaviskLinietrafik",
			"provLykken",
			"Frederiksberggade",
			"ekstraordinaerStatsskat",
			"Raadhuspladsen"
	};
	
	/********************************************************************
	 * All the fields have different amount of rent when there are no	*
	 * houses on them and in this array those are defined.				*
	 *******************************************************************/
	public static final int[] FIELDRENT1_DATA = {
			0,50,0,50,4000,0,100,0,100,
			150,0,200,0,200,250,0,300,
			0,300,350,200,350,0,350,400,
			0,450,450,0,500,0,550,550,
			0,600,0,0,700,2000,1000
	};
	
	/********************************************************************
	 * All the fields have different amount of rent when there are one	*
	 * house on them and in this array those are defined.				*
	 *******************************************************************/
	public static final int[] FIELDRENT2_DATA = {
			0,250,0,250,0,500,600,0,600
			,800,0,1000,0,1000,1250,0
			,1400,0,1400,1600,0,1800,0,
			1800,2000,0,2200,2200,0,2400,
			0,2600,2600,0,3000,0,0,3500,0,4000
	};
	
	/********************************************************************
	 * All the fields have different amount of rent when there are two	*
	 * houses on them and in this array those are defined.				*
	 *******************************************************************/
	public static final int[] FIELDRENT3_DATA = {
			0,750,0,750,0,0,1800,0,1800,
			2000,0,3000,0,3000,3750,0,4000,
			0,4000,4400,0,5000,0,5000,6000,
			0,6600,6600,0,7200,0,7800,7800,0,
			9000,0,0,10000,0,12000
	};
	
	/********************************************************************
	 * All the fields have different amount of rent when there are 		*
	 * three houses on them and in this array those are defined.		*
	 *******************************************************************/
	public static final int[] FIELDRENT4_DATA = {
			0,2250,0,2250,0,0,5400,0,5400,
			6000,0,9000,0,9000,10000,0,11000,
			0,11000,12000,0,14000,0,14000,15000,
			0,16000,16000,0,17000,0,18000,18000,0,
			20000,0,0,22000,0,28000
	};
	
	/********************************************************************
	 * All the fields have different amount of rent when there are four	*
	 * houses on them and in this array those are defined.				*
	 *******************************************************************/
	public static final int[] FIELDRENT5_DATA = {
			0,4000,0,4000,0,1000,8000,0,
			8000,9000,0,12500,0,12500,14000,
			0,15000,0,15000,16000,0,17500,0,
			17500,18500,0,19500,19500,0,20500,
			0,22000,22000,0,24000,0,0,26000,
			0,34000
	};
	
	/********************************************************************
	 * All the fields have different amount of rent when there are one	*
	 * hotel on them and in this array those are defined.				*
	 *******************************************************************/
	public static final int[] FIELDRENT6_DATA = {
			0,6000,0,6000,0,0,11000,0,11000,12000,
			0,15000,0,15000,18000,0,19000,0,19000,
			20000,0,21000,0,21000,22000,0,23000,23000,
			0,24000,0,25000,25000,0,28000,0,0,30000,
			0,40000
	};
	
	/********************************************************************
	 * In this array the amount of money you have to pay for a house 	*
	 * or hotel (if you already bought 4 houses) are defined. Notice	*
	 * that it is the same price to buy the first house or the fourth.	*
	 *******************************************************************/
	public static final int[] FIELDPROPERTYBUY_DATA = {
			0,1000,0,1000,0,2000,1000,0,
			1000,1000,0,2000,0,2000,2000,
			0,2000,0,2000,2000,0,3000,0,
			3000,3000,0,3000,3000,0,3000,
			0,4000,4000,0,4000,0,0,4000,
			0,4000
	};
	
	/********************************************************************
	 * This array defines the price which you have to pay to own the 	*
	 * field you're standing on.										*
	 *******************************************************************/
	public static final int[] FIELDBUYPRICE_DATA = {
			0,1200,0,1200,0,4000,2000,0,2000,2400,
			0,2800,3000,2800,3200,4000,3600,0,3600,
			4000,0,4400,0,4400,4800,4000,5200,5200,
			3000,5600,0,6000,6000,0,6400,4000,0,7000,0,8000	
	};
	
	/****************************************************************
	 * In this array the fields are defined as booleans that can	* 
	 * be owned or cannot be owned.									*
	 ***************************************************************/
	public static final boolean[] FIELDOWNABLE_DATA = {
			false,true,false,true,false,true,true,false,true,true,
			false,true,true,true,true,true,true,false,true,
			true,false,true,false,true,true,true,true,true,
			true,true,false,true,true,false,true,true,false,true,false,true
	};
	
	/************************************************************************
	 * This array will sort the fields into the different types of fields	*
	 * that are constructed in the game.entities.fields package. There are	*
	 * 8 different types and they all have different effect on the player.	*
	 ***********************************************************************/
	public static final FieldType[] FIELDTYPE_DATA = {
			AbstractField.FieldType.START,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.LUCKYCARD,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TAX,
			AbstractField.FieldType.FLEET,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.LUCKYCARD,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.PRISON,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.LABOR_CAMP,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.FLEET,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.LUCKYCARD,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.REFUGE,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.LUCKYCARD,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.FLEET,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.LABOR_CAMP,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.PRISON,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.LUCKYCARD,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.FLEET,
			AbstractField.FieldType.LUCKYCARD,
			AbstractField.FieldType.TERRITORY,
			AbstractField.FieldType.TAX,
			AbstractField.FieldType.TERRITORY
	};
	
	/****************************************************************
	 * This array will give the fields different colors depending	*
	 * on which fields they are "together with". It's the same as	*
	 * in the real boardgame										*
	 ***************************************************************/
	public static final Color[] fieldColor = {
			Color.red,
			Color.blue,
			Color.DARK_GRAY,
			Color.blue,
			null,
			null,
			Color.orange,
			Color.DARK_GRAY,
			Color.orange,
			Color.orange,
			null,
			Color.green,
			null,
			Color.green,
			Color.green,
			null,
			Color.gray,
			Color.DARK_GRAY,
			Color.gray,
			Color.gray,
			null,
			Color.red,
			Color.DARK_GRAY,
			Color.red,
			Color.red,
			null,
			Color.white,
			Color.white,
			null,
			Color.white,
			null,
			Color.yellow,
			Color.yellow,
			Color.DARK_GRAY,
			Color.yellow,
			null,
			Color.DARK_GRAY,
			Color.pink,
			null,
			Color.pink,
			
	};
}
