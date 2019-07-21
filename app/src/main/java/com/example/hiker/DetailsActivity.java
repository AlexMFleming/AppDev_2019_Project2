package com.example.hiker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    public static String EXTRA_TRAIL_ID = "trailId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);

        if (fragment == null) {
            fragment = new com.example.hiker.DetailsFragment();
            long trailId = getIntent().getLongExtra(EXTRA_TRAIL_ID, 1);
            fragment = com.example.hiker.DetailsFragment.newInstance(trailId);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }
}
