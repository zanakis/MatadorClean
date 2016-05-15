package game.entities.cards;

import game.boundaries.Outputable;
import game.entities.Player;

public abstract class AbstractCard {

	public enum CardType{MOVE, PRISON, REFUGE, TAX}


	protected Outputable output;
	protected CardType cardType;
	protected int cardNo;
	protected String text;

	public AbstractCard( Outputable output, CardType cardType, String text){
		this.cardType = cardType;
		this.output = output;
		this.text = text;
	}
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return text;
	}

	public int getCardNo(){
		return cardNo;
	}

	public CardType getCardType(){
		return cardType;
	}

	// Can be called for a player drawing the card
	public abstract void drawCard(Player player);

	public String toString(){
		switch(cardType){
		case MOVE:
			return "cardType=Move";
		case PRISON:
			return "cardType=prison";
		case REFUGE:
			return "cardType=Refuge";
		case TAX:
			return "cardType=Tax";
		default:
			return "";
		}
	}
}
