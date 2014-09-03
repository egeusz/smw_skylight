package com.smw.skylight ;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;


public class SendSMSActivity extends Activity {

	String phoneNumber, message;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        phoneNumber = getIntent().getExtras().getString("phoneNumber");
        message = getIntent().getExtras().getString("message");

        sendSMS(phoneNumber,message);
        
        finish();
    }
    
    

    
    //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {        
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, RestActivity.class), 0);                
        SmsManager sms = SmsManager.getDefault();
       
        String[] messageParts = message.split("><");
        if(messageParts.length > 1)
        {
        	for (int i = 0; i < messageParts.length; i++)
    		{
            	
            	sms.sendTextMessage(phoneNumber, null, messageParts[i]+"\n("+(i+1)+"/"+messageParts.length+")", pi, null);    
            	
    		}
        }
        else
        {
        	sms.sendTextMessage(phoneNumber, null, messageParts[0], pi, null);    
        }
        
    } 
    
//    //---sends an SMS message to another device---
//    private void sendSMS(String phoneNumber, String message)
//    {        
//        PendingIntent pi = PendingIntent.getActivity(this, 0,
//        new Intent(this, MainActivity.class), 0);                
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, pi, null);        
//    } 
	
}
