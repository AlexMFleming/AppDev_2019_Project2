package com.example.hiker;

public class Park {
    private long park_id;
    private String name;
    private String state;
    private String city;

    public Park(String name, String state, String city) {
        this.name = name;
        this.state = state;
        this.city = city;
    }

    public Park(long park_id, String name, String state, String city) {
        this.park_id = park_id;
        this.name = name;
        this.state = state;
        this.city = city;
    }

    public void setPark_id(long park_id) {
        this.park_id = park_id;
    }

    public long getPark_id() {
        return park_id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
