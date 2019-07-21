package com.example.hiker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private Trail mTrail;
    private TextView nameTextView, descriptionTextView, lengthTextView, elevationTextView, featuresTextView;

    public static DetailsFragment newInstance(long trailId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong("trailId", trailId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the ID from the intent that started DetailsActivity
        long trailId = 1;
        if (getArguments() != null) {
            trailId = getArguments().getLong("trailId");
        }

        mTrail = TrailDatabase.getInstance(getContext()).getTrailById(trailId);
        Log.i("Final trail name: ", mTrail.getTrail_name());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        nameTextView = view.findViewById(R.id.trailName);
        descriptionTextView = view.findViewById(R.id.trailDescription);
        lengthTextView = view.findViewById(R.id.trailLength);
        elevationTextView = view.findViewById(R.id.trailElevate);

        nameTextView.setText(mTrail.getTrail_name());
        descriptionTextView.setText(mTrail.getDescription());
        lengthTextView.setText(String.valueOf(mTrail.getTrail_distance()));
        elevationTextView.setText(String.valueOf(mTrail.getElevation()));

        featuresTextView = view.findViewById(R.id.trailFeatures);
            String features = "";
            if (mTrail.hasWildlife()==1){
                features += "Wildlife\n";
            }
            if(mTrail.hasWaterfalls()==1){
                features += "Waterfalls\n";
            }
            if (mTrail.hasCreeks()==1){
                features += "Creeks\n";
            }
        featuresTextView.setText(features);

        return view;
    }


    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
