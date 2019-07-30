package com.example.hiker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ParkListFragment extends Fragment {
    // For the activity to implement
    public interface OnParkSelectedListener {
        void onParkSelected(long ParkId, String parkName);
    }

    // Reference to the activity
    private OnParkSelectedListener mListener;
    private TrailDatabase TrailDb;
    List<Park> parkList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_park_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.park_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TrailDb = TrailDatabase.getInstance(this.getContext());
        Intent intent = getActivity().getIntent();
        parkList = TrailDb.getParks();

        ParkAdapter adapter = new ParkAdapter(parkList);
        recyclerView.setAdapter(adapter);

        //Attaches recyclerview item remover
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    private class ParkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Park mPark;
        private long id;
        private TextView mParkName, mParkState, mParkCity;

        public ParkHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_park, parent, false));
            itemView.setOnClickListener(this);
            mParkName = itemView.findViewById(R.id.parkName);
            mParkState = itemView.findViewById(R.id.parkState);
            mParkCity = itemView.findViewById(R.id.parkCity);

        }

        public void bind(Park park) {
            mPark = park;
        }

        @Override
        public void onClick(View view) {
            mListener.onParkSelected(id, mPark.getName());
            Log.i("The id is... : ", String.valueOf(id));
        }
    }

    private class ParkAdapter extends RecyclerView.Adapter<ParkHolder> {
        private List<Park> mParks;
        private Park park;

        public ParkAdapter(List<Park> parks) {
            mParks = parks;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ParkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ParkHolder(layoutInflater, parent);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ParkHolder holder, int position) {
            final Park park = mParks.get(position);

            holder.id = park.getPark_id();
            holder.mParkName.setText(park.getName());

            holder.mParkState.setText(park.getState());
            holder.mParkCity.setText(park.getCity());
            holder.bind(park);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mParks.size();
        }

        // Deletes park from recyclerview and from database
        public void deleteItem(int position) {
            park = mParks.get(position);
            long parktoDelete = park.getPark_id();
            mParks.remove(position);
            notifyItemRemoved(position);

            //TODO: Create park delete method
            //TrailDb.deletePark(parktoDelete);

            Toast toast = new Toast(getContext());
            toast.makeText(getContext(), "Park deleted!", Toast.LENGTH_LONG).show();

        }
    }

    // Handles swipe gesture
    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private ParkAdapter mAdapter;

        public SwipeToDeleteCallback(ParkAdapter adapter) {
            super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;
        }

        @Override
        public boolean onMove(RecyclerView view, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) { return true; }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnParkSelectedListener) {
            mListener = (OnParkSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnParkSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}