package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Station_List_Activity extends AppCompatActivity implements StationListFragment.OnStationSelectedListener {
    private final int REQUEST_STATIONCODE = 5;
    private String stationName;
    //Activity will hold and inflate StationListFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search for a trail");

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);
        if (fragment == null) {
            fragment = new StationListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onStationSelected(long stationId, String stationName) {
        // Send the trail ID of the clicked button to DetailsActivity
        Intent intent = new Intent();
        Log.d("station_list_activity", "onStationSelected: stationName= " + stationName);
        intent.putExtra("STATIONID", stationId);
        intent.putExtra("STATIONNAME", stationName);
        setResult( REQUEST_STATIONCODE, intent);
        finish();
    }

}