package com.example.systemcapabilitiesactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSActivity extends AppCompatActivity {
final int SEND_SMS_CODE = 1;
    private EditText number;
    private EditText message;
    private FloatingActionButton send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_smsactivity);
        number = findViewById(R.id.PhoneNo);
        message = findViewById(R.id.textSMS);
        send = findViewById(R.id.sendbtn);

        send.setEnabled(false);
        // Checking if SEND_SMS permission is granted
        if(checkPermission(Manifest.permission.SEND_SMS)) send.setEnabled(true);
            // Enabling send button if permission is granted

        else  ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.SEND_SMS}, SEND_SMS_CODE);
    }
    // Method to handle the send button click
    public void onSend(View v){
     String phoneNumber=number.getText().toString();
     String Message=message.getText().toString();
        // Checking if the phone number and message fields are empty
        if(phoneNumber == null || phoneNumber.length()==0 || message==null ||message.length()==0) {
         Toast.makeText(this, "Please enter phone number and message",
                 Toast.LENGTH_SHORT).show();
         return;
     }
        // Checking if SEND_SMS permission is granted
        if(checkPermission(Manifest.permission.SEND_SMS)){
          try {
              SmsManager smsManager = SmsManager.getDefault();
              smsManager.sendTextMessage(phoneNumber, null, Message, null, null);
              Toast.makeText(this, "SMS sent successfully",
                      Toast.LENGTH_SHORT).show();
          } catch (Exception e) {
              Toast.makeText(this, "Failed to send SMS: " + e.getMessage(),
                      Toast.LENGTH_SHORT).show();
          }

      }
      else Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

    }
    // Method to check if the required permission is granted
    public boolean checkPermission(String permission){
     return (ContextCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_GRANTED);
    }
}