package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Search_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
    }

    public void searchTrails(View view){
        EditText text = (EditText)findViewById(R.id.distanceLowerBoundView);
        int distanceLowerBoundView = Integer.parseInt(text.getText().toString());

        text = (EditText)findViewById(R.id.distanceUpperBoundView);
        int distanceUpperBoundView = Integer.parseInt(text.getText().toString());

        Intent intent = new Intent(this, Trail_List_Activity.class);
        intent.putExtra("DISTANCE_UPPER_BOUND", distanceUpperBoundView);
        intent.putExtra("DISTANCE_LOWER_BOUND", distanceLowerBoundView);
        startActivity(intent);
    }
}
