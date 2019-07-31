package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateEmergencyContact extends AppCompatActivity {

    EditText emName;
    EditText emNumber;
    EmergencyContact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_emergency_contact);


        getSupportActionBar().setTitle("Update Emergency Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emName = findViewById(R.id.textView8);
        emNumber = findViewById(R.id.textView6);

    }

    public void registerEmergencyContact(View view){
        String phoneNumberPattern = "(?:\\d{3}-){2}\\d{4}";
        Log.d("UpdateEmergencyContact", "registerEmergencyContact: " + emNumber.getText().toString().matches(phoneNumberPattern) + " ==== " +emName.getText().toString());
        if (emNumber.getText().toString().matches(phoneNumberPattern) && emName.getText().toString().length()>0){
            contact = new EmergencyContact(emName.getText().toString(), emNumber.getText().toString());
            TrailDatabase.getInstance(getApplicationContext()).addEmergencyContact(contact);
            Intent intent = new Intent(this, Trip_Activity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "please fill out all fields", Toast.LENGTH_LONG).show();
        }
    }
}
