package com.example.hiker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    List<Trail> trailList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trail_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.trail_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TrailDb = TrailDatabase.getInstance(this.getContext());
        Intent intent = getActivity().getIntent();
        trailList = TrailDb.getTrails(intent.getIntExtra("DISTANCE", -1), intent.getIntExtra("ELEVATION", -1), intent.getIntExtra("WATERFALL", 0), intent.getIntExtra("CREEK", 0), intent.getIntExtra("WILDLIFE", 0));

        TrailAdapter adapter = new TrailAdapter(trailList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class TrailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Trail mTrail;
        private TextView mTrailName, mTrailLength, mTrailElevation, mTrailDescription;

        public TrailHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trail, parent, false));
            itemView.setOnClickListener(this);
            mTrailName = itemView.findViewById(R.id.trailName);
            mTrailLength = itemView.findViewById(R.id.trailLength);
            mTrailElevation = itemView.findViewById(R.id.trailElev);
            mTrailDescription = itemView.findViewById(R.id.trailDescription);
        }

        public void bind(Trail trail) {
            mTrail = trail;
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

        // Create new views (invoked by the layout manager)
        @Override
        public TrailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TrailHolder(layoutInflater, parent);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(TrailHolder holder, int position) {
            final Trail trail = mTrails.get(position);
            holder.mTrailName.setText(trail.getTrail_name());
            //Need to wrap ints to String
            holder.mTrailLength.setText(String.valueOf(trail.getTrail_distance()));
            holder.mTrailElevation.setText(String.valueOf(trail.getElevation()));
            holder.mTrailDescription.setText(String.valueOf(trail.getDescription()));
            holder.bind(trail);
        }

        // Return the size of your dataset (invoked by the layout manager)
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