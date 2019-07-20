package com.example.hiker;

public class Trip {
    private long tr_id;
    private String date;
    private String description;

    //this constructor called when adding a trip to a trail. create the trip object with the trail_id of the trail its associated with.
    public Trip (long tr_id){
        this.tr_id=tr_id;
    }

    //this constructor is called when building a List<Trip> for populating list of trips in Trail_activity
    public Trip(long tr_id, String date, String description){
        this.tr_id=tr_id;
        this.date=date;
        this.description=description;
    }


    public void setDate(String date){
        this.date = date;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public long getTr_id() {
        return tr_id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
