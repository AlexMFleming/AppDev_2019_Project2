package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void addATrail(View view){
        Intent intent = new Intent(this, New_Trail_Activity.class);
        startActivity(intent);
    }
    public void findATrail(View view){
        Intent intent = new Intent(this, Search_Activity.class);
        startActivity(intent);
    }

}
