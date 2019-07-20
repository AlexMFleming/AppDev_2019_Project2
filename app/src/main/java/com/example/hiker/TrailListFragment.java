package com.example.hiker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class TrailListFragment extends Fragment {
    // For the activity to implement
    public interface OnTrailSelectedListener {
        void onTrailSelected(long trailId);
    }

    // Reference to the activity
    private OnTrailSelectedListener mListener;
    private TrailDatabase TrailDb;
    int distanceUpperBound;
    int distanceLowerBound;
    List<Trail> trailList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trail_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.trail_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // SY - may cause problems
        TrailDb = TrailDatabase.getInstance(getActivity());
        Intent intent = getActivity().getIntent();

        distanceLowerBound = intent.getIntExtra("DISTANCE_LOWER_BOUND", -1);
        distanceUpperBound = intent.getIntExtra("DISTANCE_UPPER_BOUND", -1);

        trailList = TrailDb.getTrails(intent.getIntExtra("DISTANCE", -1), intent.getIntExtra("ELEVATION", -1), intent.getIntExtra("FEATURE", 0b000));
        TrailAdapter adapter = new TrailAdapter(trailList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class TrailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Trail mTrail;
        private TextView mTrailName, mTrailHeight, mTrailElevation, mTrailDescription;

        public TrailHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trail, parent, false));
            itemView.setOnClickListener(this);
            mTrailName = itemView.findViewById(R.id.trailName);
        }

        public void bind(Trail trail) {
            mTrail = trail;
            mTrailName.setText(mTrail.getTrail_name());
        }

        @Override
        public void onClick(View view) {
            mListener.onTrailSelected(mTrail.getTrail_id());
        }
    }

    private class TrailAdapter extends RecyclerView.Adapter<TrailHolder> {
        private List<Trail> mTrails;
        public TrailAdapter(List<Trail> trails) {
            mTrails = trails;
        }

        @Override
        public TrailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TrailHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TrailHolder holder, int position) {
            Trail trail = mTrails.get(position);
            holder.bind(trail);
        }

        @Override
        public int getItemCount() {
            return mTrails.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTrailSelectedListener) {
            mListener = (OnTrailSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTrailSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}