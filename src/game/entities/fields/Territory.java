package game.entities.fields;

import game.boundaries.Outputable;
import game.entities.FieldManager;
import game.entities.Player;
import game.resources.FieldData;

public class Territory extends AbstractOwnable {

	public static int houseCount;
	public int hotelsOnField;
	public int fieldNo;


	public Territory(FieldManager fieldManager, int price, int rent, int fieldNo, Outputable output) {
		super(fieldManager, FieldType.TERRITORY, price, rent, output, fieldNo);
		this.fieldNo = fieldNo;
		Territory.houseCount = 0;
	}	

	/************************************************************************
	 * This method will be used whenever a player lands on a territory field*
	 * and will complete all purchases of territory fields. 				*
	 ***********************************************************************/
	@Override
	public void landOnField(Player player){
		getHotel();
		// If owner is null, call Ownable.landonfield for option to buy the field
		if (this.owner == null) {
			super.landOnField(player);
		}

		else if (this.owner != player) 
		{	// if player is not owner. player pay rent logic
			transferRent(player);
		}
		else if (this.owner==player) {// Player is owner and should not pay rent
			output.showPlayerIsOwner(player.getName());
			int choice = output.promptAction(player.getName());
			if(choice==1){
				buyProperties(player);
			}
			else if(choice==2){
				sellProperties(player);
			}
			else if(choice==3){
				output.showNextPlayerTurn(player.getName());
			}
		}
	}

	/************************************************************************
	 * This method will be used when you want to buy hotels for your fields	*
	 * and will put a hotel on the field if it is the 5th house bought.		*
	 ***********************************************************************/
	private void getHotel(){
		if(houseCount==5){
			hotelsOnField = 1;
		}
		else if(houseCount<5){
			hotelsOnField = 0;
		}
	}

	private  void setHouseCount(int amount, Player player){
		int oldValue = Territory.houseCount;
		Territory.houseCount = oldValue + amount;
		player.setHousesOwned(amount);	
	}

	/*******************************************************
	 * Asks the player to sell properties, if any is owned *
	 * @param player the player landing on the field       *
	 *******************************************************/
	private void sellProperties(Player player) {
		if(this.owner==player){
			if(houseCount==0){
				output.showNoPropertys(player.getName());
				this.landOnField(player);
			}


			if(houseCount<=4){
				boolean choice = output.promptSellProperty(player.getName(), FieldData.FIELDPROPERTYBUY_DATA[fieldNo-1]/2);
				if(choice==true){
					setHouseCount(-1, player);	
					output.setHouse(houseCount,fieldNo);
					this.landOnField(player);
				}
			}
			else if(houseCount==5){
				boolean choice = output.promptSellProperty(player.getName(), FieldData.FIELDPROPERTYBUY_DATA[fieldNo-1]/2);
				if(choice==true){
					setHouseCount(-1, player);	
					player.setHotelsOwned(1);
					output.setHotel(false,fieldNo);
					this.landOnField(player);
				}

			}
		}	
	}

	/******************************************************************
	 * Gives the player the ability to buy properties for owned fields*
	 * @param player the player landing on the field                         *
	 ******************************************************************/
	private void buyProperties(Player player){
		int price = FieldData.FIELDPROPERTYBUY_DATA[fieldNo-1];
		if(this.owner==player){
			if(houseCount<=3){
				boolean choice = output.promptBuyProperty(player.getName(), price);
				if(choice==true){
					player.withdraw(price);
					setHouseCount(1, player);
					output.setHouse(houseCount,fieldNo);
					this.landOnField(player);
				}
			}
			else if(houseCount==4){
				boolean choice = output.promptBuyProperty(player.getName(), FieldData.FIELDPROPERTYBUY_DATA[fieldNo-1]);
				if(choice==true){

					setHouseCount(1, player);	
					output.setHotel(true, fieldNo);
				}

			}
		}
		else{
			output.showEnoughPropertys(player.getName());
		}
	}

	/******************************************************
	 * Gives the player the ability to sell an owned field*
	 * @param player the player owning the field          *
	 ******************************************************/
	public void sellField(Player player){
		int price = FieldData.FIELDPROPERTYBUY_DATA[fieldNo-1];
		if(this.owner==player){
			if(houseCount!=0){
				output.showHousesOnFieldMessage(player);
			}
			else if(houseCount==0){
				boolean choice = output.promptSellFields(player);
				if(choice==true){
					this.owner=null;
					player.deposit(price/2);
				}
				else if(choice==false){
					output.showDontSell(player);
				}
			}
		}
	}
	@Override
	public int getFieldNo(){
		return fieldNo;
	}
	
	public int getHouseCount(){
		return houseCount;
	}

	/************************************************************************
	 * Transfer rent from player to owner									*
	 * @param player the player which should transfer the money to the owner*
	 ***********************************************************************/
	private void transferRent(Player player){
		switch (houseCount) {
		case 1:
			int withdrawAmount1 = FieldData.FIELDRENT2_DATA[fieldNo-1];
			player.withdraw(withdrawAmount1);
			owner.deposit(withdrawAmount1);
			output.showTransferMessage(player.getName(), owner.getName(), withdrawAmount1);
			break;
		case 2:
			int withdrawAmount2 = FieldData.FIELDRENT3_DATA[fieldNo-1];
			player.withdraw(withdrawAmount2);
			owner.deposit(withdrawAmount2);
			output.showTransferMessage(player.getName(), owner.getName(), withdrawAmount2);
			break;

		case 3:
			int withdrawAmount3 = FieldData.FIELDRENT4_DATA[fieldNo-1];
			player.withdraw(withdrawAmount3);
			owner.deposit(withdrawAmount3);
			output.showTransferMessage(player.getName(), owner.getName(), withdrawAmount3);
			break;

		case 4:
			int withdrawAmount4 = FieldData.FIELDRENT5_DATA[fieldNo-1];
			player.withdraw(withdrawAmount4);
			owner.deposit(withdrawAmount4);
			output.showTransferMessage(player.getName(), owner.getName(), withdrawAmount4);
			break;
		case 5:
			int withdrawAmount5 = FieldData.FIELDRENT6_DATA[fieldNo-1];
			player.withdraw(withdrawAmount5);
			owner.deposit(withdrawAmount5);
			output.showTransferMessage(player.getName(), owner.getName(), withdrawAmount5);

		default:
			int withdrawAmount = player.withdraw(rent);
			owner.deposit(withdrawAmount);
			output.showTransferMessage(player.getName(), owner.getName(), withdrawAmount);
			break;
		}
	}

	@Override
	public String toString(){
		if (houseCount==1){
			return super.toString() + ", rent =" + FieldData.FIELDRENT2_DATA[fieldNo-1];
		}
		else if(houseCount==2){
			return super.toString() + ", rent =" + FieldData.FIELDRENT3_DATA[fieldNo-1];
		}
		else if(houseCount==3){
			return super.toString() + ", rent =" + FieldData.FIELDRENT4_DATA[fieldNo-1];
		}
		else if(houseCount==4){
			return super.toString() + ", rent =" + FieldData.FIELDRENT5_DATA[fieldNo-1];
		}
		else if(houseCount==5){
			return super.toString() + ", rent =" + FieldData.FIELDRENT6_DATA[fieldNo-1];
		}
		return super.toString()+ ", rent=" + rent;
	}
}
