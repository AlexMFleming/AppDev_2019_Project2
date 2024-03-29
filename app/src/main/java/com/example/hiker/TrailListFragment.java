package com.example.hiker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        //Attaches recyclerview item remover
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    private class TrailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Trail mTrail;
        private long id;
        private TextView mTrailName, mTrailLength, mTrailElevation, mTrailDescription;
        private ImageView mTrailImage;

        public TrailHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trail, parent, false));
            itemView.setOnClickListener(this);
            mTrailImage = itemView.findViewById(R.id.trail_image);
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
            mListener.onTrailSelected(id);
            Log.i("The id is... : ", String.valueOf(id));
        }
    }

    private class TrailAdapter extends RecyclerView.Adapter<TrailHolder> {
        private List<Trail> mTrails;
        private Trail trail;

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
            trail = mTrails.get(position);
            if (TrailDb.getImageById(trail.getTrail_id()).size()>0) {
                holder.mTrailImage.setImageURI(Uri.parse(TrailDb.getImageById(trail.getTrail_id()).get(0).getFilename()));
            }

            //Hardcoded...
            switch (trail.getTrail_name()) {
                case "Art Loeb":
                    holder.mTrailImage.setImageResource(R.drawable.angels);
                    break;
                case "Blood Mountain":
                    holder.mTrailImage.setImageResource(R.drawable.angels);
                    break;
                case "Bear Creek":
                    holder.mTrailImage.setImageResource(R.drawable.banner4);
                    break;
                case "Iceberg Lake Trail":
                    holder.mTrailImage.setImageResource(R.drawable.iceberg);
                    break;
                case "Tower Arch Trail":
                    holder.mTrailImage.setImageResource(R.drawable.towerarch);
                    break;
                case "The Boardwalk Loop":
                    holder.mTrailImage.setImageResource(R.drawable.boardwalk);
                    break;
                case "The Hoh River Trail":
                    holder.mTrailImage.setImageResource(R.drawable.hohriver);
                    break;
                case "The Wonderland Trail":
                    holder.mTrailImage.setImageResource(R.drawable.wonderland);
                    break;
                case "Slaughter Canyon Cave":
                    holder.mTrailImage.setImageResource(R.drawable.slaughtercanyon);
                    break;
                case "Angel Landing":
                    holder.mTrailImage.setImageResource(R.drawable.angels);
                    break;
                case "Tall Trees Trail":
                    holder.mTrailImage.setImageResource(R.drawable.talltrees);
                    break;
                case "Cadillac Mountain":
                    holder.mTrailImage.setImageResource(R.drawable.cadillac);
                    break;
                case "Lost Mine Trail":
                    holder.mTrailImage.setImageResource(R.drawable.lostmine);
                    break;
                case "Sierra Point":
                    holder.mTrailImage.setImageResource(R.drawable.sierrapoint);
                    break;
                case "Mt. Katmai Caldera":
                    holder.mTrailImage.setImageResource(R.drawable.katmai);
                    break;
                case "Kalapana Lava Viewing":
                    holder.mTrailImage.setImageResource(R.drawable.kalapana);
                    break;
                case "The Narrows":
                    holder.mTrailImage.setImageResource(R.drawable.narrows);
                    break;
                case "Fairyland Loop":
                    holder.mTrailImage.setImageResource(R.drawable.fairyland);
                    break;
                case "North Kaibab Trail":
                    holder.mTrailImage.setImageResource(R.drawable.northkaibab);
                    break;
                case "Exit Glacier":
                    holder.mTrailImage.setImageResource(R.drawable.exitglacier);
                    break;
                case "Moraine Lake":
                    holder.mTrailImage.setImageResource(R.drawable.moraine);
                    break;
                case "Lower Bear Gulch":
                    holder.mTrailImage.setImageResource(R.drawable.lowerbear);
                    break;
                case "Cascades Pass Trail":
                    holder.mTrailImage.setImageResource(R.drawable.cascades);
                    break;
                case "Greenstone Ridge Trail":
                    holder.mTrailImage.setImageResource(R.drawable.greenstone);
                    break;
            }


            holder.id = trail.getTrail_id();
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

        // Deletes trail from recyclerview and from database
        public void deleteItem(int position) {
            trail = mTrails.get(position);
            long trailtoDelete = trail.getTrail_id();
            mTrails.remove(position);
            notifyItemRemoved(position);

            TrailDb.deleteTrail(trailtoDelete);

            Toast toast = new Toast(getContext());
            toast.makeText(getContext(), "Trail deleted!", Toast.LENGTH_LONG).show();

        }
    }

    // Handles swipe gesture
    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private TrailAdapter mAdapter;

        public SwipeToDeleteCallback(TrailAdapter adapter) {
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