package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.util.List;

public class Trail_List_Activity extends AppCompatActivity {

    private TrailDatabase TrailDb;

    List<Trail> trailList;
    String trailnames = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_list);

        TrailDb = TrailDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        trailList=TrailDb.getTrails(intent.getIntExtra("DISTANCE", -1), intent.getIntExtra("ELEVATION", -1), intent.getIntExtra("FEATURE", 0b000));


        //trailList = TrailDb.getTrails(distanceUpperBound, distanceLowerBound);

        TextView view = findViewById(R.id.textView3);
        for (int i = 0; i<trailList.size();i++){
            trailnames+=", " + (trailList.get(i)).getTrail_name();
        }
        view.setText(trailnames);
    }
}
