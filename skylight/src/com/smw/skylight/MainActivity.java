package com.smw.skylight ;
/*
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class MainActivity extends Activity
{
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
          
        
 
        
        
        //finish();
    	Log.d(">> MAIN"  , "I AM ALIVE");
        setContentView(R.layout.activity_main);              
        
        
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
 
        
        
        btnSendSMS.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {                
                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();                 
                if (phoneNo.length()>0 && message.length()>0) {                
                    //sendSMS(phoneNo, message);               
                	
                	Intent intent = new Intent();
                	intent.putExtra("phoneNumber", phoneNo);
                	intent.putExtra("message", message);
                	intent.setClass(getApplicationContext(), SendSMSActivity.class);
                	startActivity(intent);
                	
                }
                else
                    Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
            	}
        	}); 
       
        
        	
    }
    
    
    
}






