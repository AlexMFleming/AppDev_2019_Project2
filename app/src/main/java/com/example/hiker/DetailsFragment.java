package com.example.hiker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailsFragment extends Fragment {

    private Trail mTrail;

    public static DetailsFragment newInstance(int trailId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("trailId", trailId);
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

       // mTrail = TrailDatabase.getInstance(getContext()).getTrails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.trailName);
        nameTextView.setText(mTrail.getTrail_name());

        TextView descriptionTextView = (TextView) view.findViewById(R.id.trailDescription);
            String features = "";
            if (mTrail.hasWildlife()==1){
                features+= "wildlife ";
            }
            if(mTrail.hasWaterfalls()==1){
                features+="waterfalls ";
            }
            if (mTrail.hasCreeks()==1){
                features+="creeks";
            }
        descriptionTextView.setText(features);

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
