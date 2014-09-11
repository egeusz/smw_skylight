package com.smw.skylight ;

import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "playerManager";
 
    // Contacts table name
    private static final String TABLE_PLAYERS = "players";
    // Contacts Table Columns names
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_CURRENTQUESTION = "current_question";
    private static final String KEY_LASTANSWER = "last_answer";
    private static final String KEY_TRIES = "tries";
    
    //Response table name
    private static final String TABLE_RESPONSES = "responses";
    //Response table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";  
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_ANSWERTEXT = "answertext";
    
    // Contacts table name
    private static final String TABLE_UDPIP = "udpip";
    // Contacts Table Columns names
    private static final String KEY_IP = "ip";
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	//Player Table
    	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "("
    			+ KEY_PH_NO + 			" TEXT,"
                + KEY_CURRENTQUESTION + " INTEGER,"
    			+ KEY_LASTANSWER + 		" TEXT,"
    			+ KEY_TRIES  +			" INTEGER"
    			
    			+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
        //Response Table
        String CREATE_RESPONSE_TABLE = "CREATE TABLE " + TABLE_RESPONSES + "("
    			+ KEY_ID + 				" INTEGER,"
    			+ KEY_TEXT  +			" TEXT,"
                + KEY_ANSWER  +			" INTEGER,"
                + KEY_ANSWERTEXT +		" TEXT"
    			
    			+ ")";
        db.execSQL(CREATE_RESPONSE_TABLE);
        
        //UDP IP Table
        String CREATE_UDPIP_TABLE = "CREATE TABLE " + TABLE_UDPIP + "("
    			+ KEY_IP + 			" TEXT"
    			+ ")";
        db.execSQL(CREATE_UDPIP_TABLE);

    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSES);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_IP);
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new Player
    void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_PH_NO, player.getPhoneNumber()); // Player Phone
        values.put(KEY_CURRENTQUESTION, player.getCurrentQuestion());
        values.put(KEY_LASTANSWER, player.getLastAnswer());
        values.put(KEY_TRIES, player.getTries());
        // Inserting Row
        db.insert(TABLE_PLAYERS, null, values);
        db.close(); // Closing database connection
    }
    
    //Adding new RESPONSE
    void addResponse(Response response) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, response.getId()); 
        values.put(KEY_TEXT, response.getText()); 
        values.put(KEY_ANSWER, response.getAnswer()); 
        values.put(KEY_ANSWERTEXT, response.getAnswerText()); 
        // Inserting Row
        db.insert(TABLE_RESPONSES, null, values);
        db.close(); // Closing database connection
    }
    
    
    //Adding new UDP IP
    void addUDPIP(String _ip) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_IP, _ip); 
        // Inserting Row
        db.insert(TABLE_UDPIP, null, values);
        db.close(); // Closing database connection
    }
    
    
    // Getting single Player
    Player getPlayer(String _phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PLAYERS, new String[] {
        		KEY_PH_NO,  KEY_CURRENTQUESTION, KEY_LASTANSWER, KEY_TRIES,}, KEY_PH_NO + "=?",
                new String[] { _phoneNumber }, null, null, null, null);

        if (cursor.moveToFirst())
        {
        	Log.d(">> DATA HANDLER"  , "Found Player #" + _phoneNumber);
        	Player player = new Player();
        	player.setPhoneNumber(		cursor.getString(0));
            player.setCurrentQuestion(	cursor.getInt(1));
            player.setLastAnswer(		cursor.getString(2));
            player.setTries(		    cursor.getInt(3));
           
            cursor.close();
            db.close();
            return player;
        } 
        else
        {
        	Log.d(">> DATA HANDLER"  , "No Player with #" + _phoneNumber);
        	return null; 
        }
    }
    
    // Getting single Response
    Response getResponse(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_RESPONSES, new String[] {
        		KEY_ID, KEY_TEXT, KEY_ANSWER, KEY_ANSWERTEXT,}, KEY_ID + "=?",
        		new String[] { i+"" }, null, null, null, null);

        if (cursor.moveToFirst())
        {
        	Log.d(">> Data Handler"  , "Found Response id " + i);
        	Response response = new Response();
            response.setId(				cursor.getInt(0));
            response.setText(			cursor.getString(1));
            response.setAnswer(         cursor.getInt(2));
            response.setAnswerText(     cursor.getString(3));
            // return response
            cursor.close();
            db.close();
            return response;
        } 
        else
        {
        	Log.d(">> Data Handler"  , "No Response with id " + i);
        	return null; 
        }
    }
    
    
    // Getting All Players
    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setPhoneNumber(		cursor.getString(0));
                player.setCurrentQuestion(	cursor.getInt(1));
                player.setLastAnswer(		cursor.getString(2));
                player.setTries(		    cursor.getInt(3));
                // Adding contact to list
                playerList.add(player);
            } while (cursor.moveToNext());
        }
 
        db.close();
        
        // return contact list
        return playerList;
    }
 
    
    // Getting All Responses
    public List<Response> getAllResponses() {
        List<Response> responseList = new ArrayList<Response>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESPONSES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Response response = new Response();
            	 response.setId(				cursor.getInt(0));
                 response.setText(				cursor.getString(1));
                 response.setAnswer(         	cursor.getInt(2));
                 response.setAnswerText(     cursor.getString(3));
                 // adding response to list
                responseList.add(response);
            } while (cursor.moveToNext());
        }
 
        db.close();
        
        // return contact list
        return responseList;
    }
    
    // Getting All UDP IPS
    public List<String> getAllUDPIP() {
        List<String> ipList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_UDPIP;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do { 
                // adding ip to list
            	ipList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
 
        db.close();
        
        // return contact list
        return ipList;
    }
    
    // Updating single player
    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_PH_NO,           player.getPhoneNumber()); // Player Phone
        values.put(KEY_CURRENTQUESTION, player.getCurrentQuestion()); // Player Name
        values.put(KEY_LASTANSWER,      player.getLastAnswer());
        values.put(KEY_TRIES,           player.getTries());
        
        int dU = db.update(TABLE_PLAYERS, values, KEY_PH_NO + " = ?",
                new String[] { player.getPhoneNumber() });
        
        db.close();
        
        // updating row
        return dU;
       
    }
 
    // Updating UDP IP
    public int updateUDPIP(String _ip) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_IP,              _ip); // Player Phone
        
        int dU =  db.update(TABLE_UDPIP, values, null, null);
       
        db.close();
        
        // updating row
        return dU;
       
    }
    
    // Deleting single player
    public void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYERS, KEY_PH_NO + " = ?",
                new String[] { player.getPhoneNumber() });
        db.close();
    }
 
    // Getting contacts player
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }
    
    public void ClearPlayerTable()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Log.d(">> Data Handler"  , "Clearing Player Table");
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
    	
    	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "("
    			+ KEY_PH_NO + 			" TEXT,"
    			+ KEY_CURRENTQUESTION+	" INTEGER,"
                + KEY_LASTANSWER + 		" TEXT,"
                + KEY_TRIES + 		    " INTEGER"
                
    			+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.close();
    }
    
    public void ClearResponseTable()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Log.d(">> Data Handler"  , "Clearing Response Table");
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSES);
    	
    	String CREATE_RESPONSE_TABLE = "CREATE TABLE " + TABLE_RESPONSES + "("
    			+ KEY_ID + 				" INTEGER,"
    			+ KEY_TEXT  +			" TEXT,"
    			+ KEY_ANSWER  +			" INTEGER,"
                + KEY_ANSWERTEXT +		" TEXT"
    			
    			+ ")";
        db.execSQL(CREATE_RESPONSE_TABLE);
        db.close();
    }
    
    public void ClearUDPIPTable()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Log.d(">> Data Handler"  , "Clearing UDPIP Table");
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_UDPIP);
    	
    	String CREATE_UDPIP_TABLE = "CREATE TABLE " + TABLE_UDPIP + "("
     			+ KEY_IP + 			" TEXT"
     			+ ")";
        db.execSQL(CREATE_UDPIP_TABLE);
        db.close();
    }
}
