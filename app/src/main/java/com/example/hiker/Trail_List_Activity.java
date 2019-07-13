package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.util.List;

public class Trail_List_Activity extends AppCompatActivity {

    private TrailDatabase TrailDb;
    int distanceUpperBound;
    int distanceLowerBound;
    List<String> trailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_list);

        TrailDb = TrailDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        distanceLowerBound = intent.getIntExtra("DISTANCE_LOWER_BOUND", -1);
        distanceUpperBound = intent.getIntExtra("DISTANCE_UPPER_BOUND", -1);


        trailList = TrailDb.getTrails(distanceUpperBound, distanceLowerBound);

        TextView view = findViewById(R.id.textView3);

        view.setText(trailList.toString());
    }
}
