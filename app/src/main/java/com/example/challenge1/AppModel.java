package com.example.challenge1;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class AppModel {

    private ArrayList<LocationItem> items;
    private LocationItem newItem;
    private LocationItem shwItem;
    private LatLng userLocation;
    public static final int STATE_CREATING = 1;
    public static final int STATE_E_LOOKING = 2;
    public static final int STATE_G_LOOKING = 3;
    private int state;
    public int imgIdentifier;

    public AppModel() {
        newItem = new LocationItem(null,"No seleccionada","No dir",0);
        newItem = new LocationItem(null,"No seleccionada","No dir",0);
        items = new ArrayList<>();
        state = STATE_G_LOOKING;
        imgIdentifier = 0;
        userLocation = new LatLng(0,0);
    }

    public ArrayList<LocationItem> getItems() {
        return items;
    }

    public LocationItem getNewItem() {
        return newItem;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LatLng userLocation) {
        this.userLocation = userLocation;
    }

    public LocationItem getShwItem() {
        return shwItem;
    }

    public void setShwItem(LocationItem shwItem) {
        this.shwItem = shwItem;
    }

    public void addNewItem() {
        state = STATE_G_LOOKING;
        items.add(newItem);
        newItem = new LocationItem(null,"No seleccionada","No dir",0);
    }

    public void updateDistance() {
    }
}
