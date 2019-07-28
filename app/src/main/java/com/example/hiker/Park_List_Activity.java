package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Park_List_Activity extends AppCompatActivity implements ParkListFragment.OnParkSelectedListener {
    private final int REQUEST_PARKCODE = 5;
    private String parkName;
    //Activity will hold and inflate ParkListFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);
        if (fragment == null) {
            fragment = new ParkListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onParkSelected(long parkId, String parkName) {
        this.parkName = parkName;
        // Send the trail ID of the clicked button to DetailsActivity
        Intent intent = new Intent(this, Station_List_Activity.class);
        intent.putExtra("PARKID", parkId);
        startActivityForResult(intent, REQUEST_PARKCODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("codes", "onActivityResult: request=" + requestCode + " result=" + resultCode);
        if (requestCode == REQUEST_PARKCODE){
            if (resultCode == REQUEST_PARKCODE){
                long returnedStation = data.getLongExtra("STATIONID", -1);
                String stationName = data.getStringExtra("STATIONNAME");
                Log.d("Park_list_activity", "onActivityResult: stationname= " + stationName);
                Intent returnData = new Intent();

                returnData.putExtra("STATIONID", returnedStation);
                returnData.putExtra("STATIONNAME", stationName);
                returnData.putExtra("PARKNAME", parkName);
                setResult(REQUEST_PARKCODE, returnData);
                finish();
            }
        }
    }

}