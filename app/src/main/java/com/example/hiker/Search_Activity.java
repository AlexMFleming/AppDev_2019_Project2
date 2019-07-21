package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Search_Activity extends AppCompatActivity {

//    Sets up the view from top down
    RadioGroup hikeLengthGroup;
    RadioButton fivem;
    RadioButton fivetenm;
    RadioButton fifteenm;

    RadioGroup elevationGroup;
    RadioButton sevhft;
    RadioButton sevfifthfeet;
    RadioButton fifteenhft;

    RadioGroup featuresGroup;
    RadioButton waterfalls;
    RadioButton creeks;
    RadioButton wildlife;

    RadioButton returnButton1;
    RadioButton returnButton2;
    RadioButton returnButton3;


    Button submit;

    int distanceselect;
    int elevationselect;
    int waterfallfeature;
    int creekfeature;
    int wildlifefeature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        hikeLengthGroup = findViewById(R.id.radioGroup);
        elevationGroup = findViewById(R.id.radioGroup2);
        featuresGroup =  findViewById(R.id.featuresGroup);

        fivem =  findViewById(R.id.radioButton);
        fivetenm =  findViewById(R.id.radioButton2);
        fifteenm =  findViewById(R.id.radioButton3);

        sevhft =  findViewById(R.id.radioButton4);
        sevfifthfeet =  findViewById(R.id.radioButton5);
        fifteenhft =  findViewById(R.id.radioButton6);

        waterfalls =  findViewById(R.id.radioButton7);
        wildlife = findViewById(R.id.radioButton8);
        creeks =  findViewById(R.id.radioButton9);

        submit =  findViewById(R.id.button4);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fivem.isChecked()){
                    distanceselect=0;
                }else if (fivetenm.isChecked()){
                    distanceselect=1;
                }else if (fifteenm.isChecked()){
                    distanceselect=2;
                }else{
                    distanceselect=-1;
                }
                if (sevhft.isChecked()){
                    elevationselect=0;
                }else if(sevfifthfeet.isChecked()){
                    elevationselect=1;
                }else if (fifteenhft.isChecked()){
                    elevationselect=2;
                }else{
                    elevationselect=-1;
                }
                waterfallfeature=0;//resets the features to zero for a new search
                creekfeature = 0;
                wildlifefeature = 0;
                if (waterfalls.isChecked()){
                    waterfallfeature=1;
                }
                if (creeks.isChecked()) {
                    creekfeature=1;
                }
                if (wildlife.isChecked()){
                    wildlifefeature=1;
                }
                toTrailList();
//                int rbuttonId1 = hikeLengthGroup.getCheckedRadioButtonId();
//                Log.d("rbuttonId1 ", ""+rbuttonId1);
//                int rbuttonId2 = elevationGroup.getCheckedRadioButtonId();
//                int rbuttonId3 = featuresGroup.getCheckedRadioButtonId();

//                This would get the value selected and save it as a variable, which you can use in the next activity.
//                returnButton1 = findViewById(rbuttonId1);
//                String rb1val = returnButton1.getText().toString();
//                Log.d("rb1val ", rb1val);
//                returnButton2 = findViewById(rbuttonId2);
//                String rb1va2 = returnButton2.getText().toString();
//                returnButton3 = findViewById(rbuttonId3);
//                String rb1va3 = returnButton3.getText().toString();
//                toTrailList();
            }
        });




    }

        public void toTrailList(){
            Intent intent = new Intent(this,Trail_List_Activity.class);
            intent.putExtra("DISTANCE", distanceselect);
            intent.putExtra("ELEVATION", elevationselect);
            intent.putExtra("WATERFALL", waterfallfeature);
            intent.putExtra("CREEK", creekfeature);
            intent.putExtra("WILDLIFE", wildlifefeature);
            startActivity(intent);
        }

//    public void searchTrails(View view){
//        EditText text = (EditText)findViewById(R.id.distanceLowerBoundView);
//        int distanceLowerBoundView = Integer.parseInt(text.getText().toString());
//
//        text = (EditText)findViewById(R.id.distanceUpperBoundView);
//        int distanceUpperBoundView = Integer.parseInt(text.getText().toString());
//
//        Intent intent = new Intent(this, Trail_List_Activity.class);
//        intent.putExtra("DISTANCE_UPPER_BOUND", distanceUpperBoundView);
//        intent.putExtra("DISTANCE_LOWER_BOUND", distanceLowerBoundView);
//        startActivity(intent);
//    }


}
