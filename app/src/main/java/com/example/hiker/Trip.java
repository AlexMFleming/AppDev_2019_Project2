package com.example.hiker;

public class Trip {
    private int tr_id;
    private String date;
    private String description;

    public Trip(int tr_id){
        this.tr_id = tr_id;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public int getTr_id() {
        return tr_id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
