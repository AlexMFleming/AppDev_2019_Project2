package com.example.hiker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Trip_Activity extends AppCompatActivity {
    String trailName;
    Trip trip;
    Trail trail;
    TextView trailNameView, departureDateView, returnDateView, returnTimeView, emergencyContactView;
    EmergencyContact emergencyContact;
    private TrailDatabase TrailDb;
    private final int EMERGENCY_CONTACT_REQUEST_CODE = 3;
    private DatePickerDialog.OnDateSetListener mDepartureDateSetListener;
    private DatePickerDialog.OnDateSetListener mReturnDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String departureDateforDb;
    String returnDateforDb;
    String returnTimeforDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_);
        departureDateView = findViewById(R.id.departureDateView);
        returnDateView = findViewById(R.id.returnDateView);
        returnTimeView = findViewById(R.id.returnTimeView);
        emergencyContactView = findViewById(R.id.emergencyContactView);
        TrailDb = TrailDatabase.getInstance(getApplicationContext());
        trail = TrailDb.getTrailById(getIntent().getLongExtra("TRAILID", -1));//will be null if called from Main_Activity
        Intent intent = getIntent();

        if (trail == null){
            findViewById(R.id.calledFromTrail).setVisibility(View.GONE);
            trailNameView=findViewById(R.id.calledFromMain);
        }else {
            findViewById(R.id.calledFromMain).setVisibility(View.GONE);
            trailNameView=findViewById(R.id.calledFromTrail);
            trailName = trail.getTrail_name();
            trailNameView.setText(trailName);
        }
        emergencyContact = TrailDb.getEmergencyContact();
        if (emergencyContact != null) {
            emergencyContactView.setText("Name: " + emergencyContact.getName() + " Number: " + emergencyContact.getPhoneNumber());
        }
        trip = TrailDb.getTrip(intent.getLongExtra("TRAILID", -1));//will be null after db query if there was no TRAILID sent through intent, this is the case when this activity is started from Main_Activity
        if (trip != null){
            returnDateView.setText(trip.getDeparture());
            departureDateView.setText(trip.getReturndate());
        }


        departureDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Trip_Activity.this, android.R.style.Theme_Material_Dialog_MinWidth, mDepartureDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });

        mDepartureDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if ((i1+1)<10 && i2<10){
                    departureDateforDb = i + "-0" + (i1+1) + "-0" + i2 + " 01:00:00.000";
                }else if((i1+1)<10){
                    departureDateforDb = i + "-0" + (i1+1) + "-" + i2 + " 01:00:00.000";
                }else if(i2<10) {
                    departureDateforDb = i + "-" + (i1 + 1) + "-0" + i2 + " 01:00:00.000";
                } else{
                    departureDateforDb = i + "-" + (i1 + 1) + "-" + i2 + " 01:00:00.000";
                }
                departureDateView.setText((i1+1) + "/"+i2+"/"+i);
            }
        };

        returnDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Trip_Activity.this, android.R.style.Theme_Material_Dialog_MinWidth, mReturnDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });

        mReturnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if ((i1+1)<10 && i2<10){
                    returnDateforDb = i + "-0" + (i1+1) + "-0" + i2 + " ";
                }else if((i1+1)<10){
                    returnDateforDb = i + "-0" + (i1+1) + "-" + i2 + " ";
                }else if(i2<10) {
                    returnDateforDb = i + "-" + (i1 + 1) + "-0" + i2 + " ";
                } else{
                    returnDateforDb = i + "-" + (i1 + 1) + "-" + i2 + " ";
                }
                returnDateView.setText((i1+1) + "/"+i2+"/"+i);
            }
        };

        returnTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timeDialog = new TimePickerDialog(Trip_Activity.this, mTimeSetListener, 12, 0, false);
                timeDialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                if(minute<10&&hour<10) {
                    returnTimeforDb = "0" + hour + ":0" + minute;

                }else if (minute<10) {
                    returnTimeforDb =  hour + ":0" + minute;

                }else if (hour<10){
                    returnTimeforDb ="0" + hour + ":" + minute;

                } else {
                    returnTimeforDb =hour + ":" + minute;

                }
                returnTimeView.setText(returnTimeforDb);
                returnTimeforDb=returnTimeforDb+"00.000";
            }
        };


    }

    public void addTrip(View button){
        if (trailNameView.getText()==null || departureDateforDb== null || returnDateforDb==null || returnTimeforDb==null){
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
        }else {
            if (trail != null) {
                TrailDb.addTrip(new Trip(trail.getTrail_id(), departureDateforDb, returnDateforDb + returnTimeforDb));
                sendResults();
            } else {
                trail = TrailDb.getTrailByName((trailNameView.getText()).toString());
                if (trail == null) {
                    Toast.makeText(this, "there are no trails by the name " + (trailNameView.getText()).toString(), Toast.LENGTH_LONG).show();
                } else {
                    TrailDb.addTrip(new Trip(trail.getTrail_id(), departureDateforDb, returnDateforDb + returnTimeforDb));
                    sendResults();
                }
            }
        }
    }

    public void sendResults() {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_TRAIL_ID, trail.getTrail_id());
        startActivity(intent);
    }
}
