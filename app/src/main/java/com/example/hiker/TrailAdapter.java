package com.example.hiker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.TrailHolder> {
    private List<Trail> mTrails;

    public TrailAdapter(List<Trail> trails) {
        this.mTrails = trails;
    }

    public class TrailHolder extends RecyclerView.ViewHolder {
        public TextView mTrailName, mTrailLength, mTrailElevation, mTrailDescription;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public TrailHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trail, parent, false));
            mTrailName = (TextView) itemView.findViewById(R.id.trailName);
            mTrailLength = (TextView) itemView.findViewById(R.id.trailLength);
            mTrailElevation = (TextView) itemView.findViewById(R.id.trailElev);
            mTrailDescription = (TextView) itemView.findViewById(R.id.trailDescription);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new TrailHolder(layoutInflater, parent);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TrailHolder holder, int position) {
        Trail trail = mTrails.get(position);
        holder.mTrailName.setText(trail.getTrail_name());
        //SY - This doesn't work if I include these (String resource doesn't exist
//        holder.mTrailLength.setText(trail.getTrail_distance());
//        holder.mTrailElevation.setText(trail.getElevation());
//        holder.mTrailDescription.setText(trail.getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTrails.size();
    }
}
