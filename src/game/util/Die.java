package game.util;

/********************************
 * Die.java						*
 * Simulates a six sided die	*
 *******************************/
public class Die {

	final byte NUMBER_OF_SIDES = 6;
	private byte faceValue;

	// Constructors

	/********************************************
	 * set die value to an intial value of 1	*
	 *******************************************/
	public Die()
	{
		// Set die to an initial value of 1
		faceValue = 1;
	}


	// Mutators

	/********************************************
	 * @return current faceValue				*
	 *******************************************/
	public byte getFaceValue(){
		return faceValue;
	}

	// Service Methodes

	/********************************************************
	 * sets faceValue to a new random value between 1 and 6	*
	 * and returns the new value							*
	 * @return a new random faceValue 						*
	 *******************************************************/
	public byte roll()
	{
		faceValue = (byte)(Math.random()*6+1);
		return faceValue;
	}
}
