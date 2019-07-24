package com.example.hiker;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private Trail mTrail;
    private TextView nameTextView, descriptionTextView, lengthTextView, elevationTextView, featuresTextView;
    private ImageView imageView;
    private final int REQUEST_WRITE_CODE = 0;
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
        imageView = view.findViewById(R.id.imageView);

        nameTextView.setText(mTrail.getTrail_name());
        descriptionTextView.setText(mTrail.getDescription());
        lengthTextView.setText(String.valueOf(mTrail.getTrail_distance()));
        elevationTextView.setText(String.valueOf(mTrail.getElevation()));

        List<Image> images = new ArrayList<Image>();
        images = TrailDatabase.getInstance(getContext()).getImageById(mTrail.getTrail_id());
        Log.d("images array", "onCreateView: " + images.toString());
        if (images.size()>0){
            try {
                imageView.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/" + images.get(0).getFilename()));

            } catch (Exception e) {
               e.printStackTrace();
            }

        }

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
