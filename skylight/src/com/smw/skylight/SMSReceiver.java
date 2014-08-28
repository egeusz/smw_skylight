package com.smw.skylight ;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
 
public class SMSReceiver extends BroadcastReceiver
{
	
	
	
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "SMS from " + msgs[i].getOriginatingAddress();  
                str += " :";
                str += msgs[i].getMessageBody().toString();
                str += "\n";
                
                
                Intent sendIntent = new Intent();
            	sendIntent.putExtra("phoneNumber", msgs[i].getOriginatingAddress());
            	sendIntent.putExtra("message", msgs[i].getMessageBody().toString());
            	//sendIntent.setClass(context, SendSMSActivity.class);
            	sendIntent.setClassName("com.sms.rain_sms", "com.sms.rain_sms.OnReceiveSMSActivity");
            	//sendIntent.setClassName("com.example.sm_bebapp", "com.example.sm_bebapp.SendSMSActivity");
            	sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	context.startActivity(sendIntent);
                
                
            }
            
            
            
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
           
        }                         
    }
     
    
}/**/