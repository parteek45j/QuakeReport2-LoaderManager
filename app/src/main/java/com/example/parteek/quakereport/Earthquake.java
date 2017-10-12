package com.example.parteek.quakereport;

/**
 * Created by Parteek on 9/25/2017.
 */

class Earthquake {
    double Magnitude;
    String Location;
    Long time;
    String url;

    public Earthquake() {
    }

    public Earthquake(double magnitude, String location, Long time,String url) {
        this.Magnitude = magnitude;
        this.Location = location;
        this.time = time;
        this.url=url;
    }


    public double getMagnitude() {
        return Magnitude;
    }

    public String getLocation() {
        return Location;
    }

    public Long getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }
}
