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

public class StationListFragment extends Fragment {
    // For the activity to implement
    public interface OnStationSelectedListener {
        void onStationSelected(long StationId, String stationName);
    }

    // Reference to the activity
    private OnStationSelectedListener mListener;
    private TrailDatabase TrailDb;
    List<Station> stationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_station_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.station_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TrailDb = TrailDatabase.getInstance(this.getContext());
        Intent intent = getActivity().getIntent();
        stationList = TrailDb.getStations(intent.getLongExtra("PARKID", 0));

        StationAdapter adapter = new StationAdapter(stationList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class StationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Station mStation;
        private long id;
        private TextView mStationName, mStationNumber;

        public StationHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_station, parent, false));
            itemView.setOnClickListener(this);
            mStationName = itemView.findViewById(R.id.stationName);
            mStationNumber = itemView.findViewById(R.id.stationNumber);

        }

        public void bind(Station station) {
            mStation = station;
        }

        @Override
        public void onClick(View view) {
            mListener.onStationSelected(id, mStation.getName());
            Log.i("The id is... : ", String.valueOf(id));
        }
    }

    private class StationAdapter extends RecyclerView.Adapter<StationHolder> {
        private List<Station> mStations;

        public StationAdapter(List<Station> stations) {
            mStations = stations;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public StationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StationHolder(layoutInflater, parent);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(StationHolder holder, int position) {
            final Station station = mStations.get(position);

            holder.id = station.getStation_id();
            holder.mStationName.setText(station.getName());


            holder.mStationNumber.setText(station.getPhone_number());
            holder.bind(station);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mStations.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStationSelectedListener) {
            mListener = (OnStationSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStationSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}