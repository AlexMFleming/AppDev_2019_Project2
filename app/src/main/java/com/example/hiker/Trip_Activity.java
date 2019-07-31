package com.example.hiker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
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
    boolean departureFlag = false;

    private TrailDatabase TrailDb;
    private final int EMERGENCY_CONTACT_REQUEST_CODE = 3;
    private DatePickerDialog.OnDateSetListener mDepartureDateSetListener;
    private DatePickerDialog.OnDateSetListener mReturnDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String departureDateforDb;
    String returnDateforDb;
    String returnTimeforDb;
    boolean returnconfirmed = false;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Start a hike");

        departureDateView = findViewById(R.id.departureDateView);
        returnDateView = findViewById(R.id.returnDateView);
        returnTimeView = findViewById(R.id.returnTimeView);
        emergencyContactView = findViewById(R.id.emergencyContactView);
        TrailDb = TrailDatabase.getInstance(getApplicationContext());
        trail = TrailDb.getTrailById(getIntent().getLongExtra("TRAILID", -1));//will be null if called from Main_Activity
        Intent intent = getIntent();

        if (getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE).contains("TRAILNAME")){
            sharedPreferences = getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
            trailName = sharedPreferences.getString("TRAILNAME", "");
            returnTimeforDb = sharedPreferences.getString("RETURNTIME", "");
            returnDateforDb = sharedPreferences.getString("RETURNDATE", "");
            departureDateforDb = sharedPreferences.getString("DEPARTUREDATE", "");
            trail = TrailDb.getTrailById(sharedPreferences.getLong("TRAILID", -1));
            deleteSharedPreferences("MYPREFERENCES");

            if (trail == null) {
                findViewById(R.id.calledFromMain).setVisibility(View.GONE);
                trailNameView = findViewById(R.id.calledFromTrail);
                if (!trailName.matches("")){
                    trailNameView.setText(trailName);
                }
            }else{
                findViewById(R.id.calledFromMain).setVisibility(View.GONE);
                trailNameView = findViewById(R.id.calledFromTrail);
                trailNameView.setText(trail.getTrail_name());
            }

            if (!departureDateforDb.matches("")){
                departureDateView.setText(departureDateforDb.substring(5, 7) + "/" + departureDateforDb.substring(8, 10) + "/" + departureDateforDb.substring(0, 4));
            }
            if(!returnDateforDb.matches("")){
                returnDateView.setText(returnDateforDb.substring(5, 7) + "/" + returnDateforDb.substring(8, 10) + "/" + returnDateforDb.substring(0, 4));
            }
            if(!returnTimeforDb.matches("")){
                returnTimeView.setText(returnTimeforDb.substring(0, 5));
            }

        }else {


            if (trail == null) {
                findViewById(R.id.calledFromTrail).setVisibility(View.GONE);
                trailNameView = findViewById(R.id.calledFromMain);
            } else {
                findViewById(R.id.calledFromMain).setVisibility(View.GONE);
                trailNameView = findViewById(R.id.calledFromTrail);
                trailName = trail.getTrail_name();
                trailNameView.setText(trailName);
            }

            trip = TrailDb.getTrip(intent.getLongExtra("TRAILID", -1));//will be null after db query if there was no TRAILID sent through intent, this is the case when this activity is started from Main_Activity
            if (trip != null) {
                String tripdeparture = trip.getDeparture();
                String tripreturn = trip.getReturndate();
                departureDateView.setText(tripdeparture.substring(5, 7) + "/" + tripdeparture.substring(8, 10) + "/" + tripdeparture.substring(0, 4));
                returnDateView.setText(tripreturn.substring(5, 7) + "/" + tripreturn.substring(8, 10) + "/" + tripreturn.substring(0, 4));
                returnTimeforDb = tripreturn.substring(11);
                returnDateforDb = tripreturn.substring(0, 11);
                returnTimeView.setText(returnTimeforDb.substring(0, 5));
                departureDateforDb = tripdeparture;
            }
        }
        emergencyContact = TrailDb.getEmergencyContact();
        if (emergencyContact != null) {
            emergencyContactView.setText("Name: " + emergencyContact.getName() + " Number: " + emergencyContact.getPhoneNumber());
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
                departureFlag = true;
                Log.d("trip_activity", "onDateSet: departuredatefordb= " + departureDateforDb);
            }
        };

        returnDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (departureFlag) {
                    int year =Integer.parseInt(departureDateforDb.substring(0,4));
                    int month = Integer.parseInt(departureDateforDb.substring(5,7))-1;
                    int day = Integer.parseInt(departureDateforDb.substring(8,10));

                    DatePickerDialog dialog = new DatePickerDialog(Trip_Activity.this, android.R.style.Theme_Material_Dialog_MinWidth, mReturnDateSetListener, year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
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
                returnTimeforDb=returnTimeforDb+":00.000";
            }
        };





    }
    public void confirmReturnClick(View view){
        if (trail != null && trip !=null){
            trip.setTripCompleted(1);
            TrailDb.addTrip(trip);
            Toast.makeText(this, "Trip Completed! Emergency Notification disabled for this trip", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "You must first add this trip", Toast.LENGTH_LONG).show();
        }

    }

    public void updateEmergencyContact(View view){
        sharedPreferences = getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("TRAILNAME", trailName);
        editor.putString("DEPARTUREDATE", departureDateforDb);
        editor.putString("RETURNDATE", returnDateforDb);
        editor.putString("RETURNTIME", returnTimeforDb);
        editor.putLong("TRAILID", trail.getTrail_id());

        editor.commit();

        Intent intent = new Intent(this, UpdateEmergencyContact.class);
        startActivity(intent);
    }

    public void addTrip(View button){
        int tripCompleted = 0;
        if (trailNameView.getText()==null || departureDateforDb== null || returnDateforDb==null || returnTimeforDb==null){
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
        }else {
            long current = Calendar.getInstance().getTimeInMillis();
            Calendar returnTime = Calendar.getInstance();
            returnTime.set(Integer.parseInt(returnDateforDb.substring(0, 4)), Integer.parseInt(returnDateforDb.substring(5, 7)) - 1, Integer.parseInt(returnDateforDb.substring(8, 10)), Integer.parseInt(returnTimeforDb.substring(0, 2)), Integer.parseInt(returnTimeforDb.substring(3, 5)));
            if (Calendar.getInstance().getTimeInMillis() >= returnTime.getTimeInMillis()) {
                tripCompleted = 1;
            } else{
                tripCompleted = 0;
            }
            if (trail != null) {
                trip = new Trip(trail.getTrail_id(), departureDateforDb, returnDateforDb + returnTimeforDb, tripCompleted);
                TrailDb.addTrip(trip);
                setAlarm();
                sendResults();

            } else {
                trail = TrailDb.getTrailByName((trailNameView.getText()).toString());
                if (trail == null) {
                    Toast.makeText(this, "there are no trails by the name " + (trailNameView.getText()).toString(), Toast.LENGTH_LONG).show();
                } else {
                    trip = new Trip(trail.getTrail_id(), departureDateforDb, returnDateforDb + returnTimeforDb, tripCompleted);
                    TrailDb.addTrip(trip);
                    setAlarm();
                    sendResults();
                }
            }
        }
    }

    public void setAlarm() {
        Intent alarmIntent = new Intent(this, EmergencyNotificationIntentService.class);
        if (trip.getTripCompleted()==1) {
            returnconfirmed = true;

        }

        int minute = Integer.parseInt(returnTimeforDb.substring(3,5));
        int hour = Integer.parseInt(returnTimeforDb.substring(0,2));
        int year = Integer.parseInt(returnDateforDb.substring(0,4));
        int month = Integer.parseInt(returnDateforDb.substring(5,7))-1;
        int day = Integer.parseInt(returnDateforDb.substring(8,10));


        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 0);

        alarmIntent.putExtra("EXTRA_RETURN_MILLIS", calendar.getTimeInMillis());
        alarmIntent.putExtra("TRAILID", trip.getTr_id());
        alarmIntent.putExtra("RETURN_CONFIRMED", returnconfirmed);
        startService(alarmIntent);
    }

    public void sendResults() {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_TRAIL_ID, trail.getTrail_id());
        startActivity(intent);
    }

   @Override
   public void onDestroy(){
       super.onDestroy();
       try {
           if (trip.getTripCompleted()==1) {
               returnconfirmed = true;

           }else {
               Log.d("Trip_Activity", "onDestroy: ");
               Intent broadcastIntent = new Intent();
               broadcastIntent.setAction("restartservice");
               broadcastIntent.setClass(this, RestartEmergencyNotificationService.class);
               int minute = Integer.parseInt(returnTimeforDb.substring(3, 5));
               int hour = Integer.parseInt(returnTimeforDb.substring(0, 2));
               int year = Integer.parseInt(returnDateforDb.substring(0, 4));
               int month = Integer.parseInt(returnDateforDb.substring(5, 7)) - 1;
               int day = Integer.parseInt(returnDateforDb.substring(8, 10));


               Calendar calendar = Calendar.getInstance();
               calendar.set(year, month, day, hour, minute, 0);
               broadcastIntent.putExtra("EXTRA_RETURN_MILLIS", calendar.getTimeInMillis());
               broadcastIntent.putExtra("TRAILID", trip.getTr_id());
               broadcastIntent.putExtra("RETURN_CONFIRMED", returnconfirmed);
               this.sendBroadcast(broadcastIntent);
           }
       } catch (Exception e) {
           //Exception handling needed.
           Log.i("Crash caught", "Handle it later... :P");
       }
   }
}
