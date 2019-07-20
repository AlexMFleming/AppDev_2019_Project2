package com.example.hiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class Trail_List_Activity extends AppCompatActivity implements TrailListFragment.OnTrailSelectedListener {

    //Activity will hold and inflate TrailListFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);
        if (fragment == null) {
            fragment = new TrailListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onTrailSelected (long trailId) {
        // Send the trail ID of the clicked button to DetailsActivity
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_TRAIL_ID, trailId);
        startActivity(intent);
    }
}
