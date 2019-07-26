package com.example.hiker;

public class Trip {
    private long tr_id;
    private String departure;
    private String returndate;

    //this constructor called when adding a trip to a trail. create the trip object with the trail_id of the trail its associated with.
    public Trip (long tr_id){
        this.tr_id=tr_id;
    }

    //this constructor is called when building a List<Trip> for populating list of trips in Trail_activity
    public Trip(long tr_id, String departure, String returndate){
        this.tr_id=tr_id;
        this.departure = departure;
        this.returndate = returndate;
    }


    public void setDeparture(String departure){
        this.departure = departure;
    }
    public void setReturndate(String returndate){
        this.returndate = returndate;
    }

    public long getTr_id() {
        return tr_id;
    }

    public String getDeparture() {
        return departure;
    }

    public String getReturndate() {
        return returndate;
    }
}
