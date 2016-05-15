
package game.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import game.entities.Player;

public class DBConnector {
    private final String HOST     = "matadordist.crxqr17duncu.us-west-2.rds.amazonaws.com";
    private final int    PORT     = 3306;
    private final String USERNAME = "arbabjava";
    private final String PASSWORD = "fresh123";
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
    String createDB = "CREATE DATABASE " + timeStamp;
    public Connection connection;
    
    /****************************************************************
     * This method is used to create a connection to the specific	*
     * database that we are using.									*
     ***************************************************************/
    public DBConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + PORT;
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    
    public Connection getConnection() {
        return connection;
    }
    
    /****************************************************************************
     * Method to use making SQL queries for our later use of SQL scripts.		*
     * @param query The SQL command we want to complete is defined by the query	*
     * @return Returns the results we want.										*
     ***************************************************************************/
    public ResultSet doQuery(String query) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }
    
    /****************************************************************************
     * Method to use when we want to send the query we just created to the 		*
     * database.																*
     * @param query The SQL command we want to complete is defined by the query	*
     ***************************************************************************/
    public void doUpdate(String query) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }
    
    /****************************************************************************
     * Method that will create a string from the query we created so it can be	*
     * read in the database.													*
     * @param query The SQL command we want to complete is defined by the query	*
     ***************************************************************************/
    public String[] doQueryToString(String query) throws SQLException {
        ArrayList<String> list= new ArrayList<String>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            list.add(rs.getString(1));
            
        }
        
        String[] result = new String[list.size()];
        result = list.toArray(result);
        
        return result;
    }
    
    /****************************************************************************
     * Method that will create an arraylist of the players in the database		*
     * and will create the columns listed under in the Player schema.			*
     * @param query The SQL command we want to complete is defined by the query	*
     * @return The list of players that all have the same columns in the schema	*
     ***************************************************************************/
    public ArrayList<Player> loadPlayersToArray(String query) throws SQLException{
        ArrayList<Player> list= new ArrayList<Player>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next())
        {
            String PlayerName = rs.getString("playerName");
            int PlayerBalance = rs.getInt("playerBalance");
            int HousesOwned = rs.getInt("housesOwned");
            int HotelsOwned = rs.getInt("hotelsOwned");
            int PrisonCards = rs.getInt("prisonCards");
            int PlayerPosition = rs.getInt("playerPosition");
            Player player = new Player(PlayerName, PlayerBalance, PlayerPosition, false, false, 0, PrisonCards, HousesOwned, HotelsOwned);
            list.add(player);
        }
        return list;
    }
    
    /****************************************************************************
     * Method that will create an arraylist of the games in the database		*
     * and will create the columns listed under in the Game schema.				*
     * @param query The SQL command we want to complete is defined by the query	*
     * @return The list of games that all have the same columns in the schema	*
     ***************************************************************************/
    public ArrayList<String> loadGameToArray(String query) throws SQLException{
        ArrayList<String> list= new ArrayList<String>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next())
        {
            String PlayerName = rs.getString("player_turn");
            int TurnNumber = rs.getInt("turnNumber");
            list.add(PlayerName);
            list.add(String.valueOf(TurnNumber));
        }
        return list;
    }
    
    /****************************************************************************
     * Method that will create an arraylist of the fields in the database		*
     * and will create the columns listed under in the Field schema.			*
     * @param query The SQL command we want to complete is defined by the query	*
     * @return The list of fields that all have the same columns in the schema	*
     ***************************************************************************/
    public ArrayList<String> loadFieldsToArray(String query) throws SQLException{
        ArrayList<String> list= new ArrayList<String>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next())
        {
            int fieldNo = rs.getInt("fieldNo");
            String fieldOwner = rs.getString("fieldOwner");
            int housesOnField = rs.getInt("houseOnField");
            int hotelOnField = rs.getInt("hotelOnField");
            String fieldType= rs.getString("fieldType");
            list.add(fieldNo+","+fieldOwner+","+housesOnField+","+hotelOnField+","+fieldType);
            
        }
        return list;
    }
    
    /************************************************************************
     * SQL method to create a schema of games. It saves everything from the	*
     * GameController so that it is stored for when you come back.			*
     * @param gameName Name of the game that is being played. This will be	*
     * 			the time and date of the play initiation.					*
     ***********************************************************************/
    public void createGame(String gameName){
        String query = ("CREATE TABLE IF NOT EXISTS " + gameName+".game " +
                "(game_id varchar (255) NOT NULL, " +
                "game_State varchar(45) NOT NULL, " +
                "turnNumber int (11) NOT NULL, " +
                "player_turn varchar (45) DEFAULT NULL, " +
                "PRIMARY KEY (game_id));");
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /************************************************************************
     * SQL method to create a schema of players. It saves everything about 	*
     * the players so that it is stored for when you come back.				*
     * @param gameName Name of the game that is being played. This will be	*
     * 			the time and date of the play initiation.					*
     ***********************************************************************/
    public void createPlayersList(String gameName){
        String query = ("CREATE TABLE IF NOT EXISTS " + gameName+".player_list " +
                "(playerName varchar(45) NOT NULL, " +
                "playerIndex int (11) NOT NULL, " +
                "playerBalance int (11) DEFAULT NULL, " +
                "housesOwned int(2) DEFAULT NULL, " +
                "hotelsOwned tinyint (1) DEFAULT NULL, " +
                "prisonCards int (1) DEFAULT NULL, " +
                "playerPosition int (2) DEFAULT NULL, " +
                "PRIMARY KEY (playerIndex));");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /************************************************************************
     * SQL method to create a schema of fields. It saves everything about 	*
     * the fields so that it is stored for when you come back. Ex. how many *
     * houses/hotels are on the different fields.							*
     * @param gameName Name of the game that is being played. This will be	*
     * 			the time and date of the play initiation.					*
     ***********************************************************************/
    public void createField(String gameName){
        //Gemmer alt om felter.
        String query = ("CREATE TABLE IF NOT EXISTS " + gameName+".field " +
                "(fieldNo int (11) NOT NULL," +
                "fieldType varchar (45) NOT NULL," +
                "fieldOwner varchar (45) DEFAULT NULL, " +
                "houseOnField int(2) DEFAULT NULL, " +
                "hotelOnField tinyint(1) DEFAULT NULL, " +
                "PRIMARY KEY (fieldNo));");
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void createCards(String gameName){
        String query =("CREATE TABLE IF NOT EXISTS " + gameName+".cards " +
                "(CardNo int (11) NOT NULL," +
                "CardIndex int (11) DEFAULT NULL, " +
                "CardOwner varchar (45) DEFAULT NULL, " +
                "CardText varchar (4096) NOT NULL, " +
                "PRIMARY KEY (CardIndex));");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /************************************************************************
     * Method for when there a updates to the field table.					*
     * @param gameName Name of the game that is being played. 				*
     * @param playerName Name of player buying the field or some property.	*
     * @param fieldNo Identification number of the field in question.		*
     * @param housesOnField How many houses are on the field.				*
     * @param hotelsOnField If there are a hotel on the field.				*
     * @param fieldType What type of field it is.							*
     ***********************************************************************/
    public void addToFieldTable(String gameName, String playerName, int fieldNo, int housesOnField, int hotelsOnField, String fieldType) {
        String query = ("INSERT INTO " + gameName+".field(fieldNo, fieldType, fieldOwner, houseOnField, hotelOnField)" +
                "VALUES('"+fieldNo+"','"+fieldType+"', '"+playerName+"','"+housesOnField+"','"+hotelsOnField+"');");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /***
     * Adds player to table when gameBoard is initialized.
     * @param gameName Name of the game that is being played. 		*
     * @param playerIndex
     * @param playerName Name of the player being added.
     * @param balance The balance of the player being added.
     * @param housesOwned How many houses the player owns.
     * @param hotelsOwned How many hotels the player owns.
     * @param prisonCards If the player has any prisoncards.
     * @param playerPosition Where the player is on the gameBoard.	*
     ***************************************************************/
    public void addToPlayerTable(String gameName, int playerIndex,String playerName, int balance, int housesOwned, int hotelsOwned, int prisonCards, int playerPosition){
        String query = ("INSERT INTO " + gameName+".player_list(playerName, playerIndex,playerBalance, housesOwned, hotelsOwned, prisonCards, playerPosition)" +
                "VALUES('"+playerName+"','"+playerIndex+"','"+balance+"','"+housesOwned+"','"+hotelsOwned+"','"+prisonCards+"','"+playerPosition+"');");
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /********************************************************************
     * Updates the player table whenever there are changes to the table.*
     * @param gameName Name of the game that is being played.			*
     * @param playerName Name of the player being added.				*
     * @param balance The balance of the player being added.			*
     * @param housesOwned How many houses the player owns.				*
     * @param hotelsOwned How many hotels the player owns.				*
     * @param prisonCards If the player has any prisoncards.			*
     * @param playerPosition Where the player is on the gameBoard.		*
     *******************************************************************/
    public void updatePlayerTable(String gameName, String playerName, int balance, int housesOwned, int hotelsOwned, int prisonCards, int playerPosition){
        String query = ("UPDATE "+ gameName+".player_list " +
                "SET playerBalance='"+balance+"', housesOwned='"+housesOwned+"', hotelsOwned='"+hotelsOwned+"', prisonCards='"+prisonCards+"', playerPosition='"+playerPosition+"' " +
                "WHERE playerName='"+playerName+"';");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /************************************************************************
     * This method will drop the current database when there is a winner.	*
     * This makes it impossible to suddenly enter game that is already 		*
     * finished.															*
     * @param gameName Name of the game that is being dropped.				*
     ***********************************************************************/
    //Drops current Database , when a winner is found
    public void dropCurrentGameTable(String gameName){
        String query = ("DROP DATABASE "+ gameName);
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /****************************************************************************
     * Will update the field table when there are changes.						*
     * @param gameName Name of the game that is being played.					*
     * @param playerName Name of player that is involved in the update.			*
     * @param fieldNo The id of the field that has changes made to it.			*
     * @param housesOnField Updates the houses that are currently on the field.	*
     * @param hotelsOnField Updates the hotels that are currently on the field.	*
     ***************************************************************************/
    public void updateFieldTable(String gameName, String playerName, int fieldNo, int housesOnField, int hotelsOnField) {
        String query = ("UPDATE "+ gameName+".field " +
                "SET fieldOwner='"+playerName+"', houseOnField='"+housesOnField+"', hotelOnField='"+hotelsOnField+"'" +
                "WHERE fieldNO='"+fieldNo+"';");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /************************************************************
     * Adds a new game to the game table.						*
     * @param gameName Name of the game that is being created.	*
     * @param gameState Which state of the game we're at.		*
     * @param turnNumber Which turn it is.						*
     * @param playerTurn Whose turn it is to roll the dice.		*
     ***********************************************************/
    public void addToGameTable(String gameName, String gameState, int turnNumber, String playerTurn) {
        String query = ("INSERT INTO " + gameName+".game(game_id, game_State, turnNumber, player_turn)"+
                "VALUES('"+gameName+"', '"+gameState+"', '"+turnNumber+"', '"+playerTurn+"');");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /****************************************************************
     * Will update game table when there are changes to the table.	*
     * @param gameName Name of the game that is being updated.		*
     * @param gameState Which state the game is at.					*
     * @param turnNumber Which turn it is.							*
     * @param playerTurn Whose turn it is to roll the dice.			*
     ***************************************************************/
    public void updateGameTable(String gameName, String gameState, int turnNumber, String playerTurn) {
        String query = ("UPDATE " + gameName+".game " +
                "SET game_State='" + gameState + "', turnNumber='" + turnNumber + "', player_turn='" + playerTurn + "'" +
                "WHERE game_id='"+gameName+"';");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void addToCardsTable(String gameName, int cardIndex,int cardNo, String cardOwner, String cardText){
        String query = ("INSERT INTO " + gameName+".cards(CardNo,CardIndex, CardOwner, CardText)" +
                "VALUES('"+cardNo+"', '"+cardIndex+"', '"+cardOwner+"', '"+cardText+"');");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void updateCardsTable(String gameName, int cardIndex,int cardNo, String cardOwner, String cardText){
        String query = ("UPDATE "+ gameName+".cards " +
                "SET CardNo='"+cardNo+"', CardOwner='"+cardOwner+"', CardText='"+cardText+"'" +
                "WHERE CardIndex='"+cardIndex+"';");
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void createTables(String gameName){
        createGame(gameName);
        createField(gameName);
        createPlayersList(gameName);
        createCards(gameName);
    }
    public String createGameDB(String timeStamp){
        try {
            doUpdate(createDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }
    
}