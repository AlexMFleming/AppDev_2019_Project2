package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hikerchat.ChatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button addTrailButton, searchTrailButton, chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addTrailButton = findViewById(R.id.trailForm_Button);
        searchTrailButton = findViewById(R.id.searchTrail_Button);
        chatButton = findViewById(R.id.chatButton);

        addTrailButton.setOnClickListener(this);
        searchTrailButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);
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
    //Direct to ChatActivity
    public void startChat(View view) {
        Intent i = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if (v == addTrailButton) {
            addATrail(v);
        }
        else if (v == searchTrailButton) {
            findATrail(v);
        } else if (v == chatButton) {
            startChat(v);
        }
    }
}
