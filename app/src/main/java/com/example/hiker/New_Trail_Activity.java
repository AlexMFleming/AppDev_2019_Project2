package com.example.hiker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class New_Trail_Activity extends AppCompatActivity {

    private TrailDatabase TrailDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__trail_);

        TrailDb = TrailDatabase.getInstance(getApplicationContext());//get database instance for every new activity, we dont have to pass it through intent
    }

    public void createTrailClick(View view){
        EditText text = (EditText)findViewById(R.id.trail_name_view);
        String trailName = text.getText().toString();

        text = (EditText)findViewById(R.id.elevation_view);
        int elevation = Integer.parseInt(text.getText().toString());

        text = (EditText)findViewById(R.id.length_view);
        int distance = Integer.parseInt(text.getText().toString());


        //store the 3 feature elements in one 3 digit binary number. I thought this would be an efficient way to store it but it might end up being
        //more of a hastle than its worth.
        //orientation: (waterfalls, creeks, wildlife)
        CheckBox checkBox = findViewById(R.id.checkBox);
        CheckBox checkBox1 = findViewById(R.id.checkBox2);
        CheckBox checkBox2 = findViewById(R.id.checkBox3);
        int features = 0b000;
        if (checkBox.isChecked()){
            features += 0b001;
        }
        if (checkBox1.isChecked()){
            features += 0b010;
        }
        if (checkBox2.isChecked()){
            features += 0b100;
        }


        Trail trail = new Trail(trailName, distance, elevation, features);
        TrailDb.addTrail(trail);
        Toast.makeText(this, " " + trail.getTrail_name() +" added", Toast.LENGTH_LONG).show();
    }
}
