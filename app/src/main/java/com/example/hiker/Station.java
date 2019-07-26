package com.example.hiker;

public class Station {
    private long station_id;
    private long park_id;
    private String name;
    private String phone_number;

    public Station (long station_id, long park_id, String name, String phone_number) {
        this.station_id = station_id;
        this.park_id = park_id;
        this.name = name;
        this.phone_number = phone_number;
    }
    public Station (long park_id, String name, String phone_number) {
        this.park_id = park_id;
        this.name = name;
        this.phone_number = phone_number;
    }

    public long getStation_id(){return station_id;}

    public long getPark_id() {
        return park_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
