package com.example.kafka.spring;

import org.springframework.stereotype.Component;

@Component
public class Person {
    private String FirstName;
    private String LastName;
    private String TrackingID;

    public Person(String firstName, String lastName, String trackingID, String statue) {
        FirstName = firstName;
        LastName = lastName;
        TrackingID = trackingID;
        Statue = statue;
    }

    public String getTrackingID() {
        return TrackingID;
    }

    public void setTrackingID(String trackingID) {
        TrackingID = trackingID;
    }

    public String getStatue() {
        return Statue;
    }

    public void setStatue(String statue) {
        Statue = statue;
    }

    private String Statue;
    public Person() {
    }

    public Person(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
