package game.entities.cards;


import game.boundaries.Outputable;
import game.entities.Player;


public class Prison extends AbstractOwnable {

	
	public Prison(Outputable output, int cardNo) {
		super(output, CardType.PRISON);
		this.cardNo=cardNo;
	}

	
	public int getCardNo(){
		return cardNo;
	}
	
	/********************************************************************
	 * This method will make the active player who has drawn a get-out-	*
	 * of-jail-card the owner of the card.								*
	 * @param player The player who has drawn the card from the stack.	*
	 *******************************************************************/
	@Override
	public void drawCard(Player player) {
		this.setOwner(player);
		player.setOutOfJailCard(player.getOutOfJailCard()+1);
		output.showGetPrisonCardMessage(player.getName());
	}
	

}
