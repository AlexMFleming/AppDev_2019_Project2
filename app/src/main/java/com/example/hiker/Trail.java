package com.example.hiker;

public class Trail {
    private long trail_id;
    private String trail_name;
    private int features;
    private int elevation;
    private int trail_distance;

    public Trail() {}

    public Trail(String trail, int length, int elev, int feat) {
        trail_name = trail;
        trail_distance = length;
        elevation = elev;
        features = feat;

    }
    public Trail(long id, String trail, int length, int elev, int feat) {
        trail_id=id;
        trail_name = trail;
        trail_distance = length;
        elevation = elev;
        features = feat;

    }

    public void setTrail_id(long id){
        trail_id = id;
    }

    public String getTrail_name(){
        return trail_name;
    }

    public int getFeatures(){
        return features;
    }

    public int getElevation(){
        return elevation;
    }

    public int getTrail_distance() {
        return trail_distance;
    }
}
