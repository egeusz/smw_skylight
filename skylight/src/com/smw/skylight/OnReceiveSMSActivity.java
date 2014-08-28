package com.smw.skylight ;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

 
public class OnReceiveSMSActivity extends Activity {
	
	String phoneNumber, message;
	DatabaseHandler db; 
	TextParser textParser;
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        phoneNumber = getIntent().getExtras().getString("phoneNumber");
        message = getIntent().getExtras().getString("message");
        
        db = new DatabaseHandler(this);
        textParser = new TextParser(db);
        
        Log.d(">> NEW MESSAGE"  , "From " + phoneNumber + " : \"" + message +"\"");
        
        
        
        
        if(textParser.isCommandFromAdmin(phoneNumber, message))
		{
        	String outmessage = textParser.ParseAdminCommand(phoneNumber, message);
        	SendOutSMS(phoneNumber, outmessage);
		}
        else
        {
	        Player player =  db.getPlayer(phoneNumber); //get the player. returns null if player was not found. 
	    	if(player == null)// if player was not found 
	    	{
	    		if(textParser.ParseWord("RAIN", message))//and the incoming message it BEB  
	    		{
	    			AddNewPlayer(phoneNumber); //add the player. 
	    			Response nextResponse = db.getResponse(1);
	    			
	    			SendOutSMS(phoneNumber, nextResponse.getText());
	    			SendOutSMS(phoneNumber, 	"Welcome to Rain.");
	    			
	    			
	    		}
	    	}
	    	else 
	    	{
	    			ParseAnswer(phoneNumber, message, player);
	    			
	    			//new UDPTask().execute("UDP test. You said "+message);
	    	}
        }
        
        
       
        
        //SendOutSMS(phoneNumber,  "You Said "+ message);

        finish();
    }
    
	//------- Magic send SMS function 
    public void SendOutSMS(String _phoneNumber, String _outMessage)
    {
    	 // Next Activity call
        Intent sendIntent = new Intent();
    	sendIntent.putExtra("phoneNumber", _phoneNumber);
    	sendIntent.putExtra("message", _outMessage);
        
        sendIntent.setClassName("com.sms.rain_sms", "com.sms.rain_sms.SendSMSActivity");
    	sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(sendIntent);
    	Log.d(">> MESSAGE OUT"  , "To " + _phoneNumber + " : \"" +_outMessage +"\"");
    }
    
    
    
    //------ Adds a new player to the database
    public void AddNewPlayer(String _phoneNumber)
    {
    	Player player = new Player();
        player.setPhoneNumber(_phoneNumber);
        player.setCurrentQuestion(1);
        player.setLastAnswer("");
        player.setTries(0);
        //Log.d(">> NEW PLAYER "  , player.toShortString());
        Log.d(">> NEW PLAYER ", _phoneNumber); 
        db.addPlayer(player); 
    }
    
    //------ Stores the message in the player's last message position. 
    public void ParseAnswer(String _phoneNumber, String _message, Player _player)
    {
    		Response currentResponse = db.getResponse(_player.getCurrentQuestion());
    		String playerguess = textParser.parseAnswerNumber(_message); 
    		
    		String outMessage = "";
    		String UDPtag = "n";
    		boolean correct = false; 
			if(playerguess == "")
			{
				outMessage = "You did not enter a number."; 
				UDPtag = "n";
				
			}
			else
			{
    			
				int guess = Integer.parseInt(playerguess);
				int correctAns = currentResponse.getAnswer();
				int range = currentResponse.getRange();
				int range_nonzero = 0;// to mitigate 0 causing all values outside correct from receiving "much too"
				if(range == 0)
				{
					range_nonzero = 1; 
				}
				else
				{
					range_nonzero = range; 
				}
				
				String suffix = currentResponse.getSuffix();
				
				
				
				
				if (guess < (correctAns - 10 * range_nonzero)) {
					outMessage = guess + suffix + " is much too low.";
					UDPtag = "l";
				} else if (guess < (correctAns - 2 * range_nonzero)) {
					outMessage = guess + suffix + " is too low.";
					UDPtag = "l";
				} else if (guess < (correctAns - range)) {
					UDPtag = "l";
					outMessage = "Close, but " + guess + suffix + " is too low.";
				} else if ((correctAns + 10 * range_nonzero) < guess) {
					outMessage = guess + suffix + " is much too high.";
					UDPtag = "h";
				} else if ((correctAns + 2 * range_nonzero) < guess) {
					outMessage = guess + suffix + " is too high.";
					UDPtag = "h";
				} else if ((correctAns + range) < guess) {
					outMessage = "Close, but " + guess + suffix + " is too high.";
					UDPtag = "h";
				} else if ((correctAns - range) <= guess
						&& guess <= (correctAns + range)) {
					outMessage = "Correct!";
					UDPtag = "c";
					correct = true;
				} else {
					outMessage = "You did not enter a number...";
					UDPtag = "n";
				}
			}
    		
			
			
			if(correct)
			{
				AskNextQuestion(_phoneNumber,_message,_player);
				SendOutSMS(_phoneNumber, outMessage);
				new UDPTask().execute("c,"+currentResponse.getAnswer()+","+currentResponse.getRange()+","+playerguess);
			}
			else
			{
				_player.setTries(_player.getTries()+1);
				if(_player.getTries() < 3)//The player has more tries left
				{
					outMessage = outMessage+" Try again.";
					SendOutSMS(_phoneNumber, outMessage);
					db.updatePlayer(_player);
	    			new UDPTask().execute(UDPtag+","+currentResponse.getAnswer()+","+currentResponse.getRange()+","+playerguess);
				}
				else
				{
					AskNextQuestion(_phoneNumber,_message,_player);
					outMessage += " The correct answer is "+currentResponse.getAnswer()+currentResponse.getSuffix()+".";
					SendOutSMS(_phoneNumber, outMessage);
					new UDPTask().execute(UDPtag+","+currentResponse.getAnswer()+","+currentResponse.getRange()+","+playerguess);
				}
				
			}
    
    
    
    
//    			AskNextQuestion(_phoneNumber,_message,_player);
//    			SendOutSMS(_phoneNumber, "The correct answer is "+currentResponse.getAnswer()+currentResponse.getSuffix()+".");
//    			new UDPTask().execute("n,"+currentResponse.getAnswer()+","+playerguess);
//    			
//    		
//    		if(playerguess == ""  && _player.getTries() < 2)
//    		{
//    			SendOutSMS(_phoneNumber, "You did not enter a number. Try again.");
//    			_player.setTries(_player.getTries()+1);
//    			db.updatePlayer(_player);
//    			new UDPTask().execute("n,"+currentResponse.getAnswer()+","+playerguess);
//    		}
//    		else if(playerguess == "")
//    		{
//    			
//    			SendOutSMS(_phoneNumber, "You did not enter a number. The correct answer is "+currentResponse.getAnswer()+currentResponse.getSuffix()+".");
//    			
//    			
//    		}
//    		else if(Integer.parseInt(playerguess) == currentResponse.getAnswer() && _player.getTries() < 2)
//    		{
//    			
//    			SendOutSMS(_phoneNumber, "Correct!");
//    			
//    			 
//    		}
//    		else if(Integer.parseInt(playerguess) < currentResponse.getAnswer() && _player.getTries() < 2)
//    		{
//    			SendOutSMS(_phoneNumber, playerguess+currentResponse.getSuffix()+" is too low. Try again.");
//    			_player.setTries(_player.getTries()+1);
//    			db.updatePlayer(_player);
//    			new UDPTask().execute("l,"+currentResponse.getAnswer()+","+playerguess);
//    		}
//    		else if(Integer.parseInt(playerguess) > currentResponse.getAnswer() && _player.getTries() < 2)
//    		{
//    			SendOutSMS(_phoneNumber,  playerguess+currentResponse.getSuffix()+" is too high. Try again.");
//    			_player.setTries(_player.getTries()+1);
//    			db.updatePlayer(_player);
//    			new UDPTask().execute("h,"+currentResponse.getAnswer()+","+playerguess);
//    		}
//    		else
//    		{
//    			
//    			
//    		}
    }
    
    
    
	public void AskNextQuestion(String _phoneNumber, String _message, Player _player)
	{
		
		int nextQuestion =  _player.getCurrentQuestion()+1;
		Response nextResponse = db.getResponse(nextQuestion);
		if(nextResponse == null)//At the end. 
		{
			SendOutSMS(_phoneNumber, "Thank you for playing Rain. To play again text \"Rain\"");
			db.deletePlayer(_player);
		}
		else
		{
			SendOutSMS(_phoneNumber, nextResponse.getText());
			_player.setCurrentQuestion(nextQuestion);
			_player.setTries(0);
			db.updatePlayer(_player);
		}
	}
    
    
}