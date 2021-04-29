package com.example.challenge1;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class LocationItem {
    private String name;
    private String address;
    //private image locationImage;
    //private GmapsObject location;
    private LatLng myLocation;
    private double score;
    private double userDistance;
    private String imageSrc;

    public LocationItem(String name, String address, String imageSrc, double score) {
        this.name = name;
        this.address = address;
        this.score = score;
        this.imageSrc = imageSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getUserDistance() {
        return userDistance;
    }

    public void setUserDistance(double userDistance) {
        this.userDistance = userDistance;
    }

    public LatLng getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(LatLng myLocation) {
        this.myLocation = myLocation;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
