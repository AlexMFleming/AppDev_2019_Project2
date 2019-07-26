package com.example.hiker;

public class EmergencyContact {
    private String name;
    private String phone_number;

    public EmergencyContact(String name, String phone_number){
        this.name = name;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }
}
