/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package game.boundaries;

import desktop_codebehind.Car;
import desktop_fields.Brewery;
import desktop_fields.Chance;
import desktop_fields.Field;
import desktop_fields.Jail;
import desktop_fields.Refuge;
import desktop_fields.Shipping;
import desktop_fields.Start;
import desktop_fields.Street;
import desktop_fields.Tax;
import desktop_resources.GUI;
import java.awt.Color;

/**
 *
 * @author zanakis
 */
public final class GUIBoundary {
 
    int[] fieldType;
    Color[] fieldColor;
    Field[] fields;
    String[] fieldTitle;
    String[] fieldDescription;
    String[] fieldSubtext;
    String[] cards;
    
    public GUIBoundary(String[] fieldTitle, String[] fieldDescription,
            String[] fieldSubtext, int[] fieldType, Color[] fieldColor) {
        this.fieldType = fieldType;
        this.fieldColor = fieldColor;
        this.fields = new Field[fieldTitle.length];
        this.fieldTitle = fieldTitle;
        this.fieldDescription = fieldDescription;
        this.fieldSubtext = fieldSubtext;
        initializeBoard();
    }
    
    // Methods
    
    /***********************************************
     * Displays the dices in the GUI for the player*
     * @param dice
     ***********************************************/
    
    public void setDice(int[] dice){
        GUI.setDice(dice[0], dice[1]);
    }
    /************************************************************************
     * Sets a hotel on the GUI, if the player has enough houses on the field*
     * 																		*
     * @param hasHotel boolean to show if a hotel i present                 *
     * @param fieldNo fieldNo for the field to set a hotel                  *
     ************************************************************************/
    public void setHotel(boolean hasHotel, int fieldNo) {
        if(hasHotel==false){
            GUI.setHouses(fieldNo, 4);
            GUI.setHotel(fieldNo, false);
        }
        else if(hasHotel==true){
            GUI.setHouses(fieldNo, 0);
            GUI.setHotel(fieldNo, true);
        }
    }
    /**********************************************************************
     * Displays a specific amount of houses on a specific field on the GUI*
     * 																	  *
     * @param houseCount the amount of houses to display                  *
     * @param fieldNo the specific field to show the houses on            *
     **********************************************************************/
    public void setHouse(int houseCount, int fieldNo) {
        GUI.setHotel(fieldNo, false);
        GUI.setHouses(fieldNo, houseCount);
    }
    
    /****************************************************************
     * Updating active player balance and position and displays 	*
     * the dice rolled by the player								*
     * @param dice
     * @param pos
     * @param balance
     * @param playerName
     ***************************************************************/
    public void update(int[] dice, int pos, int balance, String playerName) {
        // Updating dice values
        GUI.setDice(dice[0], dice[1]);
        // Updating active player balance
        GUI.setBalance(playerName, balance);
        // Removing players current car
        GUI.removeAllCars(playerName);
        // Adding player car at updated position
        GUI.setCar(pos+1, playerName);
    }
    
    /************************************************************************
     * Adds a new player to the board. using balance, name, and player #	*
     * 																		*
     * @param playerName Name of player which should be added to the board	*
     * @param balance Starting balance of player							*
     * @param playerNumber 0-5, otherwise error will occur					*
     ***********************************************************************/
    public void addPlayer(String playerName, int balance, int playerNumber) {
        Color[] colors = {Color.BLUE, Color.WHITE, Color.MAGENTA, Color.YELLOW, Color.BLACK, Color.GREEN};
        Car car = new Car.Builder()
                .primaryColor(colors[playerNumber])
                .secondaryColor(colors[5-playerNumber]).build();
        GUI.addPlayer(playerName, balance, car);
        GUI.setCar(1, playerName);
    }
    
    /************************************************************************
     * Clears all owners from the interactive gameboard.					*
     ***********************************************************************/
    public void removeAllOwners(){
        for(int i = 0; i < fields.length;i++){
            fields[i].setTitle(String.valueOf(i+1));
            GUI.setHouses(i+1, 0);
            GUI.setHotel(i+1, false);
        }
    }
    
    public void removeAllCars(String name) {
        GUI.removeAllCars(name);
    }
    
    /************************************************************************
     * Removes the owner of a specific field so that the field is empty		*
     * and can be bought by another player.									*
     * 																		*
     * @param fieldNumber The number of the field 1-40						*
     ***********************************************************************/
    public void removeOwner(int fieldNumber){
        fields[fieldNumber].setTitle(String.valueOf(fieldNumber+1));
    }
    
    /************************************************************************
     * Shows a message in the interface which is the playername and the		*
     * position of that player												*
     * 																		*
     * @param msg
     ***********************************************************************/
    public void showMessage(String msg){
        GUI.showMessage(msg);
    }
    
    
    /************************************************************************
     * This method will change the status of a field if it changes owner	*
     * or is bought by a player.											*
     * 																		*
     * @param playerName Name of player getting ownership of the field.		*
     * @param fieldNumber Identification number of field changing ownership	*
     ***********************************************************************/
    public void setFieldOwners(String playerName, int fieldNumber){
        fields[fieldNumber].setTitle(String.valueOf(fieldNumber+1) + " (" + playerName + ")");
    }
    
    /************************************************************************
     * Shows a message that the player have to roll the dice to find out 	*
     * how much money he has to pay when he lands on another players 		*
     * LaborCamp field.														*
     * 																		*
     * @param msg
     * @param btnRoll
     * @return 
     ***********************************************************************/
    
    public String getUserButtonPressed(String msg, String btnRoll) {
        return GUI.getUserButtonPressed(msg, btnRoll);
    }
    
    /************************************************************************
     * Shows a message that the player have to roll the dice to find out 	*
     * how much money he has to pay when he lands on another players 		*
     * LaborCamp field.														*
     * 																		*
     * @param msg
     * @param c1
     * @param c2
     * @param c3
     * @param c4
     * @return 
     ***********************************************************************/
    
    public String getUserButtonPressed(String msg, String c1, String c2, String c3, String c4) {
        return GUI.getUserButtonPressed(msg, c1, c2, c3, c4);
    }
    
    /************************************************************************
     * Show a message with the 'Prøv Lykken' card and what it reads.		*
     * Also what effect it will have on the player.							*
     * 																		*
     * @param msg
     ***********************************************************************/
    
    public void showCardMessage(String msg) {
        GUI.displayChanceCard(msg);
    }
    /***********************************************************
     * The player has no properties, and can derfefore not sell*
     ***********************************************************/
    
    /**
     * The player has no properties, and can derfefore not sell*
     * @param msg
     */
    public void showNoPropertys(String msg) {
        GUI.getUserButtonPressed(msg, "Ok");
    }
    
    /************************************************************************
     * Shows the 'Prøv Lykken' card in the interface and will 				*
     * display the effect and text of the card.								*
     * 																		*
     * @param text What is actually written on the card						*
     ***********************************************************************/
    
    public void showCard(String text) {
        GUI.displayChanceCard(text);
        GUI.getUserButtonPressed("", "OK");
    }
    
    /************************************************************************
     * This method will prompt the option to insert a playerName. It is		*
     * created with an if-else statement where you have to insert a name.	*
     * 																		*
     * @param msg
     * @return 
     ***********************************************************************/
    
    public String promptPlayerName(String msg) {
        return GUI.getUserString(msg);
    }
    
    /************************************************************************
     * This method will prompt a button on the interface when you land on 	*
     * a field of the type tax. You will have the option to pay a certain 	*
     * amount or a percentage of your account balance.						*
     * 																		*
     * @param msg
     * @param btnPercent
     * @param btnTaxAmount
     * @return 
     ***********************************************************************/
    
    public String getUserButtonPressed(String msg, String btnPercent, String btnTaxAmount) {
        return GUI.getUserButtonPressed(msg, btnPercent, btnTaxAmount);
    }
    
    /************************************************************************
     * This method will prompt a button so that the active player can buy 	*
     * the field he has landed on, if it is not owned by another player.	*
     * 																		*
     * @param msg
     * @param yes
     * @param no
     * @return 
     ***********************************************************************/
    
    public boolean promptYesNo(String msg, String yes, String no) {
        return GUI.getUserLeftButtonPressed(msg, yes, no);
    }
    
    /********************************************************
     * Prompts the player to take a choice, when imprisoned.*
     * Either pay the fine                                  *
     * Roll the dice                                        *
     * Use a jailcard                                       *
     * or wait 3 rounds                                     *
     * @param playerName the player on the field            *
     ********************************************************/
    
    /**
     * Prompts the player to take a choice, when imprisoned.*
 Either pay the fine                                  *
 Roll the dice                                        *
 Use a jailcard                                       *
 or wait 3 rounds                                     *
     * @param msg
     * @param c1
     * @param c2
     * @param c3
     * @param c4
     * @return
     */
    public int PromptPrison(String msg, String c1, String c2, String c3, String c4) {
        String result = GUI.getUserButtonPressed(msg, c1,c2,c3,c4);
        
        if(result.equals(c1)){
            return 1;
        }
        else if(result.equals(c1)){
            return 2;
        }
        else if(result.equals(c3)){
            return 3;
        }
        else{
            return 4;
        }
    }
    
    /************************************************************************************************
     * Prompts the player to take a choice whether he wants to sell, buy or do nothing on his field.*
     * @param msg
     * @param c1
     * @param c2
     * @param c3
     * @return 
     ************************************************************************************************/
    
    public int promptAction(String msg, String c1, String c2, String c3) {
        String result = GUI.getUserButtonPressed(msg, c1,c2, c3);
        
        if(result.equals(c1)){
            return 1;
        }
        else if(result.equals(c2)){
            return 2;
        }
        else {
            return 3;
        }
    }
    
    /************************************************************************
     * This method will load a saved game if there is one. It is loaded		*
     * from an array of strings from the database.							*
     * 																		*
     * @param s1
     * @param games An array of strings from the database games				*
     * @return 
     ***********************************************************************/
    
    public String promptLoadAction(String s1, String[] games){
        return GUI.getUserSelection(s1, games);
    }
    
    /************************************************************************
     * This method will prompt an option to sell an active players owned	*
     * field and will deposit the amount into the active players account.	*
     * 																		*
     * @param msg
     * @param c1
     * @param c2
     * @return 
     ***********************************************************************/
    
    public boolean promptSellFields(String msg, String c1, String c2) {
        String result = GUI.getUserButtonPressed(msg, c1,c2);
        return result.equals(c1);
    }
    //Initialize text on GUI
    /************************************************************************
     * This method will initiate the gameboard in the interface. It will	*
     * create the fields so that they are visible for the user. This method	*
     * is essential for the entire visual experience of the game.			*
     ***********************************************************************/
    
    public void initializeBoard() {
        for(int i = 0; i < fields.length; i++){
            switch(fieldType[i]){
                case 0:
                    fields[i] = new Refuge.Builder().build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
                case 1:
                    fields[i] = new Tax.Builder().build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
                case 2:
                    fields[i] = new Street.Builder()
                            .setBgColor(fieldColor[i])
                            .build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
                case 3:
                    fields[i] = new Brewery.Builder().build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
                case 4:
                    fields[i] = new Shipping.Builder().build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
                case 5:
                    fields[i] = new Jail.Builder().build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
                case 6:
                    fields[i] = new Start.Builder().build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
                    
                case 7:
                    fields[i] = new Chance.Builder().build();
                    fields[i].setDescription(fieldDescription[i]);
                    break;
            }
            fields[i].setTitle(fieldTitle[i]);
            fields[i].setSubText(fieldSubtext[i]);
        }
        GUI.create(fields);
    }
}