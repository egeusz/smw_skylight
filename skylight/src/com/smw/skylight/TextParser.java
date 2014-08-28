package com.smw.skylight ;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import android.util.Log;


//This Class provides fucntions to parse Text 
public class TextParser {
	String moreList 		= "(more|m|higher|h|greater|bigger|larger)"; 
	String lessList 		= "(less|l|lower|smaller|fewer)";
	
//	String yesList 		= "(yes|yeah|y|yup|yep|yuppers|true|correct|sure|of course|I suppose|Damn straight|Definitely|positive|agreed|right on|righton|groovy|si|sí|oui)"; 
//	String noList 		= "(no|nope|n|nah|not|noo|negative|false|wrong|sorry|no|siento|pas|désolé)"; 
//	String maybeList 	= "(maybe|sometimes|not sure|can't tell|possibly|don't know|kinda|kind of|kindof|sorta|sortof|sort of|no idea)"; 
//	
//	private static final String PLAYERSTATE_ACTIVE 		= "active";
//	private static final String PLAYERSTATE_PAUSED 		= "paused";
	
	private static final String COMMAND_PREFIX 			= "r>";
	
	
	ResponseTreeBuilder rtBuilder; 
	DatabaseHandler db; 
	
	public TextParser(DatabaseHandler _db)
	{
		db = _db; 
		rtBuilder = new ResponseTreeBuilder(db);  
	}
	

	//Parses the message for more/less answer returns 1/-1
	public int ParseMoreLess(String _message)
	{
		if (ParseWord(moreList,_message))
		{
			return 1; //"more";
		}
		else if (ParseWord(lessList,_message))
		{
			return -1; //"less";
		}
		else 
		{
			return 0; //"unknown";
		}
	}
	
	
//	//Parses the message for yes or nos or maybes
//	public int ParseYesNo(String _message)
//	{
//		if (_message.equals(":;:none:;:"))
//		{
//			return 0; //"none"; 
//		}
//		else if (ParseWord(yesList,_message))
//		{
//			return 1; //"yes";
//		}
//		else if (ParseWord(noList,_message))
//		{
//			return 2; //"no";
//		}
//		else if (ParseWord(maybeList,_message))
//		{
//			return 3; //"maybe"; 
//		}
//		else 
//		{
//			return 4; //"any";
//		}
//	}
	
	//searches for a word or a list of words in the message. returns true or false if a word is found. Lists are formatted as (foobar|foobaz|...)
	public boolean ParseWord(String _word, String _message)
	{
		if (_message.matches("(?i).*\\b"+_word+"\\b.*"))	
		{
			return true;
		}
		return false; 
	}
	
	
	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //---- Question Functions --------------------------
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	
	
	
	
	// Takes a string message and attempts to figure out what number it is. 
	public String parseAnswerNumber(String _message)
	{
		return _message.replaceAll("\\D+","");
	}
	
	
	
	
	
	
	
	
	
	
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //---- ADMIN CONTROL FUNCTIONS --------------------------
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	public boolean isCommandFromAdmin(String _phonenumber , String _message)
	{
		if( _phonenumber.equals("+15055733247") || _phonenumber.equals("+15054591694"))
		{
			if (_message.toLowerCase(Locale.getDefault()).startsWith(COMMAND_PREFIX))
	  		{
	  			return true;
	  		}
		}
	
		return false;
	}
	
	
	//------ Checks the message for an admin command. If found it executes it and returns and returns a response
    public String ParseAdminCommand(String _phoneNumber, String _message)
    {
    	String[] args = FormatCommand(_message);//splits the message on - to get the args
    	
    	if(CheckCommand("delete all players", args[0]))
        {//--Clear All Players from the Player table
         	return AdminCMD_DeleteAllPlayers(_phoneNumber,args); 
        }
    	else if(CheckCommand("delete player", args[0]))
    	{//-- Deletes A Player
    		return AdminCMD_DeletePlayer(_phoneNumber,args); 
        }
    	else if(CheckCommand("show all players", args[0]))
        {//--Show All PLayers 
    		return AdminCMD_ShowAllPlayers(_phoneNumber, args); 
        }
    	else if(CheckCommand("show player", args[0]))
        {//--Show a PLayer 
    		return AdminCMD_ShowPLayer(_phoneNumber,args); 
        }
//    	else if(CheckCommand("move player", args[0]))
//        {//--Show a PLayer 
//    		AdminCMD_MovePLayer(_phoneNumber,args); 
//    		return true; 
//        }
    	else if(CheckCommand("build response table", args[0]))
        {//--Build All Responses 	
    		return AdminCMD_BuildResponseTable(_phoneNumber,args);
        }
    	else if(CheckCommand("show response table", args[0]))
        {//--Show All Responses 
    		return AdminCMD_ShowResponseTable(_phoneNumber,args); 
        }
//    	else if(CheckCommand("set all player state", args[0]))
//        {//--Show All Responses 
//    		AdminCMD_ToggleAllPlayerPause(_phoneNumber,args); 
//    		return true; 
//        }
    	else if(CheckCommand("udp", args[0]))
        {//--Show All PLayers 
    		
    		return AdminCMD_SendUDPPacket(_phoneNumber, args); 
        }
    	else if(CheckCommand("help", args[0]))
        {//--Show All PLayers 
    		
    		return AdminCMD_Help(); 
        }
    	else if (CheckCommand(" ", args[0]))
    	{//--if message was a command but could not be understood. 
    		return  "r> Bad Command. Try Again.";
    	}
    	//-- message was not a command. 
    	return "r> Message was not a Command. Something is seriously fucked here, Dude!";
    }   
	//------ Compares the message to the given command string and returns true if the message contains the command. ei "CMD some command" == "CMD some command -arg1 -arg2"
    public boolean CheckCommand(String _command, String _message)
  	{
  		String command = COMMAND_PREFIX + _command;
  		command = command.toLowerCase(Locale.getDefault()); 
  		if (_message.toLowerCase(Locale.getDefault()).startsWith(command))
  		{
  			return true;
  		}
  		return false; 
  	}
    //------ Splits a message formatted into commands into an array of arguments. "R> some command -arg1 -arg2" -> [CMD some command][arg1][arg2]"
    public String[] FormatCommand(String _message)
    {
      String[] args = _message.split(" -");
      return args;   	
    }
    
    
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //---- ADMIN COMMAND FUNCTIONS --------------------------
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public String AdminCMD_DeleteAllPlayers(String _phoneNumber,  String[] args)
    {
    	db.ClearPlayerTable(); 	
     	return "r> Player table cleared";
    }
    public String AdminCMD_DeletePlayer(String _phoneNumber,  String[] args)
    {
    	String phoneNumber =""; 
		Boolean DeletePlayer = false; 	
		if(args.length >= 2)
		{
			if(ParseWord("me", args[1]))
			{
				phoneNumber = FormatPhoneNumber(_phoneNumber); 
				DeletePlayer = true; 
			}
			else 
			{
				phoneNumber = FormatPhoneNumber(args[1]);
				DeletePlayer = true; 
			}
		}
		else 
		{
			DeletePlayer = false;
			return  "r> ERROR \r\n You must specify a phone number for player.";	
		}
		
		if(DeletePlayer)
		{
    		Player player = null;
    	    player = db.getPlayer(phoneNumber); //find the player
    	    if(player == null) //If the player does not exist
    	    {
    	    	return "r> Player ["+phoneNumber+"] not in the player list";
    	    }
    	    else  // otherwise remove the player. 
    	    {
    	        db.deletePlayer(player);
    	        return "r> Player ["+phoneNumber+"] removed from the player list.";
    	    }
		}
		return "r> ERROR Could not delete player.";
    }
    public String AdminCMD_ShowAllPlayers(String _phoneNumber,  String[] args)
    {
    	printAllPlayers();
    	return "r> PLAYER TABLE \r\n" + getAllPlayers();
    }
    public String AdminCMD_ShowPLayer(String _phoneNumber,  String[] args)
    {
    	String phoneNumber =""; 
		Boolean ShowPlayer = false; 
		
		if(args.length >= 2)
		{
			if(ParseWord("me", args[1])) //if "me" is used for player number it will use your number
			{
				phoneNumber = FormatPhoneNumber(_phoneNumber); 
				ShowPlayer = true; 
			}
			else 
			{
				phoneNumber =FormatPhoneNumber(args[1]);
				ShowPlayer = true; 
			}
		}
		else 
		{
			ShowPlayer = false;
			return "r> ERROR \r\n You must specify a phone number for player.";
		}
		
		if(ShowPlayer)
		{
    		Player player = null;
    	    player = db.getPlayer(phoneNumber); //find the player
    	    if(player == null) //If the player does not exist
    	    {
    	    	return "r> Player ["+phoneNumber+"] not in the player list.";
    	    }
    	    else  // otherwise print the player. 
    	    {
    	        return player.toShortString();
    	    }
		}
		return "r> ERROR Could not find player.";
    }
    public String AdminCMD_SendUDPPacket(String _phoneNumber,  String[] args)
    {
    	new UDPTask().execute(args[1]);
    	return "r> Packet Sent With\r\n  \"" + args[1]+"\"";
    }
    public String AdminCMD_Help()
    {
    	return "r> COMMANDS \r\n" +
    		   "> show all players\r\n" +
    		   "> show player -[phonenumber]\r\n" +
    		   "> delete all players\r\n" +
    		   "> delete player -[phonenumber]\r\n" +	   
    		   "> build response table -[name]\r\n" +
    		   "> show response table\r\n" +
    		   "> udp -[string]\r\n" +
    		   "> help\r\n" +
    		   "";    
    	
    }
    public String AdminCMD_BuildResponseTable(String _phoneNumber,  String[] args)
    {
    	db.ClearResponseTable();
    	if(args.length >= 2)
		{
	    	if(rtBuilder.PopulateResponses(args[1]))
			{
	    		
	    		printAllResponses();
	    		return "r> Response table Built"; 
			}
			else 
			{
				return "r> ERROR \r\n "+args[1]+"not correct id for a table. Try \"rain\" or \"test\"";	
			}
		}
    	else
    	{
    		return "r> ERROR \r\n Table id must be specified. Try \"rain\" or \"test\" ";	
    	}
    }
    public String AdminCMD_ShowResponseTable(String _phoneNumber,  String[] args)
    {
    	printAllResponses(); 
		return "r> RESPONSE TABLE \r\n" + getAllResponses();
    	//SendOutSMS(_phoneNumber, "BEB> PLAYER TABLE \r\n" + getAllResponses());
    }
    
    
    
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //---- Util DB calls -------------------------------------
    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public String getAllPlayers()
    {
    	List<Player> players = db.getAllPlayers();       
    	String playersString = "";
        for (Player p : players) 
        {
        	playersString += ">" + p.toShortString(); 
        	playersString += "\r\n"; 
        }
        return playersString;
    }
    public String getAllResponses()
    {
    	List<Response> responses = db.getAllResponses();    
    	String responseString = "";
    	for (Response r : responses) 
        {
    		responseString += ">" + r.toShortString(); 
    		responseString += "\r\n"; 
        }
        return responseString;
    } 
    public void printAllPlayers()
    {
    	Log.d(">> PLAYER_TABLE ", "Reading all players.."); 
    	List<Player> players = db.getAllPlayers();       
         for (Player p : players) 
         {// Writing Players to log
             String log = p.toShortString();
             Log.d("Player: ", log);
         }
    }
    public void printAllResponses()
    {
    	Log.d(">> RESPONSE_TABLE ", "Reading all responses..");  
    	List<Response> responses = db.getAllResponses();       
         for (Response r : responses) 
         {// Writing Responses to log
             String log = r.toShortString(); 
             Log.d("Response: ", log);
         }
    }
    

	//Takes a message to be stored as a name and rebuilds it with proper case. "garruS VAKarian" => "Garrus Vakarian"
//	public String BuildName(String _message)
//	{
//		
//		
//		String[] nameArgs = _message.split(" ");
//		String name = ""; 
//		for (String n : nameArgs) 
//		{
//			if (n.length() == 0)
//			{
//				//Empty do nothing
//			}
//			else if (n.length() == 1)
//			{
//				name += n.toUpperCase() + " "; //String of length 1
//			}
//			else 
//			{
//				name += n.substring(0,1).toUpperCase() + n.substring(1).toLowerCase() + " "; 
//			}
//			
//		}
//		Log.d(">> BUILD NAME"  , "Built new name " +_message+ " -> "+name);
//		return name.trim();
//	}
	
	public String FormatPhoneNumber( String _phonenumber )
	{
		String phonenumber = _phonenumber;//.split("+")[0]; //remove + 
		String[] phonenumberchunks = phonenumber.split("-");//ex: 505-573-3247
		phonenumber = "";
		for (String n : phonenumberchunks) 
		{
			 phonenumber += n; 
		}
		
		if(phonenumber.length() == 10)//ex: 15055733247
		{
			return "+1" + phonenumber; //ex: +15055733247
		}
		else if(phonenumber.length() == 11 && phonenumber.startsWith("1"))//ex: 15055733247
		{
			return "+" + phonenumber; //ex: +15055733247
		}
		else if (phonenumber.length() == 12 && phonenumber.startsWith("+"))//ex: +5055733247
		{
			return phonenumber;//ex: +5055733247
		}
		else
		{
			return "ERROR! "+phonenumber+" is not a phone number."; 
		}
		
		
	
	}
	
//	//removes :;: from mesage strings and replaces them with spaces. 
//	public String FormatMessage(String _message)
//	{
//		String[] _messageArgs = _message.split(":;:");
//		
//		String message = "";
//		for (String m : _messageArgs) {
//			message += m +" "; 
//		}
//		
//		return message; 
//	}
	
//	public String[] BuildDataArray(String _data)
//	{
//		return _data.split(":;:");
//	}

}
