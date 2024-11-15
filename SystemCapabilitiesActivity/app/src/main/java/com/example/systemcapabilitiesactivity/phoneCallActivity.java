package com.example.systemcapabilitiesactivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View; import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class phoneCallActivity extends AppCompatActivity {
    EditText phoneNO;
    FloatingActionButton callbtn;
    static int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        phoneNO = findViewById(R.id.editTextPhone);
        callbtn = findViewById(R.id.calbtn);

        // Check for phone call permission
        if (ContextCompat.checkSelfPermission(phoneCallActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(phoneCallActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
        }

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the phone number and validate it
                String phoneNumber = phoneNO.getText() != null ? phoneNO.getText().toString().trim() : "";

                if (phoneNumber.isEmpty()) {
                    // Show error if phone number is empty
                    phoneNO.setError("Please enter a phone number");
                    return;
                }

                // Check permission again before making the call
                if (ContextCompat.checkSelfPermission(phoneCallActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // Make the call
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(i);
                } else {
                    Toast.makeText(phoneCallActivity.this,
                            "Phone permission is required to make calls",
                            Toast.LENGTH_SHORT).show();
                    // Request permission again
                    ActivityCompat.requestPermissions(phoneCallActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
                }
            }
        });
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Phone permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Phone permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}