package game.entities.fields;

import game.boundaries.Outputable;
import game.controllers.GameController;
import game.entities.FieldManager;
import game.entities.GameBoard;
import game.entities.Player;
import game.util.DieCup;

public class Prison extends AbstractField{

	private int fine;
	private DieCup dices;
	private int fieldNo;
	GameBoard board;

	public Prison(FieldManager fieldManager, Outputable output, int fieldNo) {
		super(fieldManager,FieldType.PRISON, output, fieldNo);
		dices = new DieCup();
		fine = 1000; 
		this.fieldNo = fieldNo;

	}
	/***********************************************************************
	 * @param isImprisoned setting the boolean imprisoned for the specific	*
	 * @param player the player to manipulate                              *
	 **********************************************************************/
	public void setImprisoned(boolean isImprisoned, Player player){
		player.setImprisoned(isImprisoned);
	}
	@Override
	public int getFieldNo(){
		return fieldNo;
	}

	/*******************************************************
	 * Lets the player pay the fine, for getting imprisoned*
	 * @param player the imprisoned player                 *
	 *******************************************************/
	public void payFine(Player player){
		player.withdraw(fine);
		output.showWithdrawMessage(player.getName(), fine);
	}

	/************************************************************************
	 * Method that will lock the player in prison for rounds to come.		*
	 * @param timeInPrison How many rounds the player has to be imprisoned	*
	 * @param player Player that is set to be imprisoned					*
	 ***********************************************************************/
	public void setTimeInPrison(int timeInPrison, Player player) {
		player.setTimeInPrison(timeInPrison);
	}

	public void setOutOfJailCard(int outOfJailCard, Player player){
		player.setOutOfJailCard(outOfJailCard);
	}

	/**************************************************************
	 * Initializes the player choice for landing on a prison field*
	 * @param player the player, landing on the prison field      *
	 **************************************************************/
	public void initializeChoice(Player player){
		int choice = output.PromptPrison(player.getName());
		switch(choice){
		case 1:
			payFine(player);
			setTimeInPrison(0, player);
			setImprisoned(false, player);
			break;
		case 2:
			output.showRollingDiceForRent(player.getName());
			output.setDice(dices.getDice());

			if (dices.getDie1()==dices.getDie2()) {
				setImprisoned(false, player);
				setTimeInPrison(0, player);


			}
			else setTimeInPrison(1, player);


			break; 
		case 3:
			if (player.getOutOfJailCard()==0)	{
				output.showNoCardMessage(player.getName());
				initializeChoice(player);
			}
			else if(player.getOutOfJailCard()>0) {
				setImprisoned(false, player);
				setTimeInPrison(0, player);
				setOutOfJailCard(-1, player);
			}
			break;
		case 4:
			setTimeInPrison(1, player);
			setImprisoned(true, player);
			output.showDoTimeMessage(player.getName());
			break;
		}
	}


	/********************************************************************
	 * This method will imprison the active player if he lands on field	*
	 * number 11 which is the prison field.								*
	 *******************************************************************/
	@Override
	public void landOnField(Player player) {
		if(fieldNo==11){
			player.setTimeInPrison(player.getTimeInPrison()+1);
			setImprisoned(true, player);
			initializeChoice(player);
		}
		else {
			setImprisoned(false, player);
			setTimeInPrison(0, player);

		}
	}	
}
