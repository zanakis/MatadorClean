package game.entities.cards;


import game.boundaries.Outputable;
import game.entities.Player;


public class MoveActivePlayer extends AbstractCard {
	private int fieldNo;
	private int cardNo;
	private static String text;



	public MoveActivePlayer(Outputable output, int cardNo) {
		super(output, CardType.MOVE, text);
		this.cardNo = cardNo;

	}

	public int getCardNo(){
		return cardNo;
	}

	/********************************************************************
	 * This method will pick a 'Prøv lykken' card from the stack and	* 
	 * make the effect of the card active for the active player.		*
	 * @param player The player who is drawing a card from the stack.	*
	 *******************************************************************/
	@Override
	public void drawCard(Player player) {

		int currentPosition = player.getPosition();
		int difference;
		switch (cardNo) {

		case (24)://Ryk tre felter tilbage
			player.setPosition(Math.abs((currentPosition-3)) % 40);
		break;

		case (3)://Gå til fængsel
			fieldNo = 11;
		player.setPosition(fieldNo);
		break;	

		case (4)://Gå til fængsel
			fieldNo = 11;
		player.setPosition(fieldNo);
		break;	

		case (2)://Gå til start
			fieldNo=1;
		difference = getCount(currentPosition, fieldNo);
		player.setPosition((currentPosition + difference-3) % 40);
		if(passStart(currentPosition,fieldNo)==true){player.deposit(4000);output.showPassStartMessage(player.getName());}
		break;	

		case(14)://Gå til Grønningen
			fieldNo = 25;
		difference = getCount(currentPosition, fieldNo);
		player.setPosition((currentPosition + difference-1) % 40);
		if(passStart(currentPosition,fieldNo)==true){player.deposit(4000);output.showPassStartMessage(player.getName());}
		break;	

		case(15)://Nærmeste redderi
			fieldNo = closestRefuge(currentPosition);
		difference = getCount(currentPosition, fieldNo);
		player.setPosition((currentPosition +difference-1) % 40);	
		break;

		case(16)://Nærmeste redderi
			fieldNo = closestRefuge(currentPosition);
		difference = getCount(currentPosition, fieldNo);
		player.setPosition((currentPosition + difference-1) % 40);
		break;	

		case(17)://LB færgerne
			fieldNo=6;
		difference = getCount(currentPosition, fieldNo);
		player.setPosition((currentPosition +difference-1) % 40);
		if(passStart(currentPosition,fieldNo)==true){ player.deposit(4000);}
		break;

		case(21)://Frederiksberg Alle
			fieldNo=12;
		difference = getCount(currentPosition, fieldNo);
		player.setPosition((currentPosition + difference-1) % 40);
		if(passStart(currentPosition,fieldNo)==true){ player.deposit(4000);}
		break;

		case(23)://Rådhuspladsen
			player.setPosition(39);
		break;		
		//Denne klasse er meget ucharmerende, men den virker	
		}	
	}	

	/****************************************************************************
	 * This method will set the position of the active player to 0 at			*
	 * the beginning of the game. When the player hits position 41 on			*
	 * the gameboard it will reset to position 0 which is Start.				*
	 * @param currentPosition The position on the board where active player is	*
	 * @param fieldNo The identification of each field on the board.			*
	 * @return Returns the position after the while loop.						*
	 ***************************************************************************/
	public int getCount(int currentPosition, int fieldNo){
		int count = 0;
		while(currentPosition != fieldNo){
			if(currentPosition<=40)
				currentPosition++;
			count++;

			if(currentPosition==41){
				currentPosition=0;
				count++;
			}
		}
		return count;
	}
	
	/****************************************************************************
	 * This method will determine if the player has passed Start.				*
	 * @param currentPosition The position on the board where active player is	*
	 * @param fieldNo The identification of each field on the board.			*
	 * @return Returns a boolean (t/f) if the player has passed Start			*
	 ***************************************************************************/
	public boolean passStart(int currentPosition, int fieldNo){
		if (currentPosition+getCount(currentPosition,fieldNo)<=40) {
			return false;
		}
		return true;
	}
	
	/************************************************************************
	 * This method is used to determine which is the closest refuge to the	*
	 * active player. It is used when the 'Prøv lykken' card says the player*
	 * has to move to the nearest refuge.									*
	 * @param currentPosition The position where active player is on board	*
	 * @return Returns one of three refuge depending on currentPosition		*
	 ***********************************************************************/
	public int closestRefuge(int currentPosition){
		int r1= 6;
		int r2 = 13;
		int r3 = 29;

		int difference1 = getCount(currentPosition, r1);
		int difference2 = getCount(currentPosition, r2);
		int difference3 = getCount(currentPosition, r3);

		if(difference1<difference2 && difference1<difference3){
			return r1;
		}else if(difference2<difference3 && difference2<difference1){
			return r2;
		}else{
			return r3;
		}
	}

	@Override
	public String toString(){
		return super.toString() + ", cardNo=" + cardNo ;
	}
}
