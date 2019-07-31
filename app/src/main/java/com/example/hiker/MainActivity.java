package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import sns.SMS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button planATripButton, addTrailButton, searchTrailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        planATripButton = findViewById(R.id.addTripButton);
        addTrailButton = findViewById(R.id.trailForm_Button);
        searchTrailButton = findViewById(R.id.searchTrail_Button);

        planATripButton.setOnClickListener(this);
        addTrailButton.setOnClickListener(this);
        searchTrailButton.setOnClickListener(this);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) { }
    }

    //Direct to New_Trail_Activity
    public void addATrail(View view){
        Intent intent = new Intent(MainActivity.this, New_Trail_Activity.class);
        startActivity(intent);
    }
    //Direct to Search_Activity
    public void findATrail(View view){
        Intent intent = new Intent(MainActivity.this, Search_Activity.class);
        startActivity(intent);
    }
    //Direct to Trip_Activity
    public void planATrip(View view) {
        Intent i = new Intent (MainActivity.this, Trip_Activity.class);
        startActivity(i);
    }
    //Virtual test - Don't spam!
    public void SMSaction(View view) {
        SMS sms = new SMS("+16788357651", "Test");
        sms.sendSMSMessage();
    }

    @Override
    public void onClick(View v) {
        if (v == addTrailButton) {
            addATrail(v);
        }
        else if (v == searchTrailButton) {
            findATrail(v);
        } else if (v== planATripButton) {
            planATrip(v);
        } else {
            return;
        }
    }
}
