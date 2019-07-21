//package com.example.hiker;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.io.Serializable;
//import java.util.List;
//
//public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.TrailHolder> {
//
//    private List<Trail> mTrails;
//
//    public TrailAdapter(List<Trail> trails) {
//        this.mTrails = trails;
//    }
//
//    public class TrailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public TextView mTrailName, mTrailLength, mTrailElevation, mTrailDescription;
//
//        // Provide a reference to the views for each data item
//        // Complex data items may need more than one view per item, and
//        // you provide access to all the views for a data item in a view holder
//        public TrailHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.list_item_trail, parent, false));
//            itemView.setOnClickListener(this);
//            mTrailName = (TextView) itemView.findViewById(R.id.trailName);
//            mTrailLength = (TextView) itemView.findViewById(R.id.trailLength);
//            mTrailElevation = (TextView) itemView.findViewById(R.id.trailElev);
//            mTrailDescription = (TextView) itemView.findViewById(R.id.trailDescription);
//        }
//
//        @Override
//        public void onClick(View v) {
//
//        }
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public TrailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        return new TrailHolder(layoutInflater, parent);
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(TrailHolder holder, int position) {
//        final Trail trail = mTrails.get(position);
////        holder.mViewGroup.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Context context = v.getContext();
////                Intent intent = new Intent(context, DetailsActivity.class);
////                intent.putExtra("trail", mTrails.get(position).getTrail_id());
////                context.startActivity(intent);
////            }
////        });
//        holder.mTrailName.setText(trail.getTrail_name());
//        //Need to wrap ints to String
//        holder.mTrailLength.setText(String.valueOf(trail.getTrail_distance()));
//        holder.mTrailElevation.setText(String.valueOf(trail.getElevation()));
//        holder.mTrailDescription.setText(String.valueOf(trail.getDescription()));
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mTrails.size();
//    }
//}
