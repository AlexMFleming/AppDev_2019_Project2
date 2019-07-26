package com.example.hiker;

public class Trail {
    private long trail_id;
    private String trail_name;
    private int waterfalls;
    private int creeks;
    private int wildlife;
    private int elevation;
    private int trail_distance;
    private String description;
    private long stationId;

    public Trail() {
    }

    public Trail(String trail, int length, int elev, int waterfalls, int creeks, int wildlife, String description, long stationId) {
        trail_name = trail;
        trail_distance = length;
        elevation = elev;
        this.waterfalls = waterfalls;
        this.creeks = creeks;
        this.wildlife = wildlife;
        this.description = description;
        this.stationId = stationId;

    }

    public Trail(long id, String trail, int length, int elev, int waterfalls, int creeks, int wildlife, String description, long stationId) {
        trail_id = id;
        trail_name = trail;
        trail_distance = length;
        elevation = elev;
        this.waterfalls = waterfalls;
        this.creeks = creeks;
        this.wildlife = wildlife;
        this.description = description;
        this.stationId = stationId;
    }

    public Trail(String trail, int length, int elev, int waterfalls, int creeks, int wildlife){
        trail_name =trail;
        trail_distance =length;
        elevation =elev;
        this.waterfalls=waterfalls;
        this.creeks=creeks;
        this.wildlife=wildlife;
    }

    public long getTrail_id(){
        return trail_id;
    }
    public void setTrail_id(long id){
        trail_id = id;
    }

    public String getTrail_name(){
        return trail_name;
    }

    public int hasWaterfalls(){
        return waterfalls;
    }

    public int hasCreeks() {
        return creeks;
    }

    public int hasWildlife() {
        return wildlife;
    }

    public int getElevation(){
        return elevation;
    }

    public int getTrail_distance() {
        return trail_distance;
    }

    public void setDescription(String description) {
        this.description=description;

    }
    public String getDescription() {
        return description;
    }
    public long getStationId() {return stationId;}
}
