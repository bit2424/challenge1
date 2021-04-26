package com.example.challenge1;

import java.util.ArrayList;

public class AppModel {

    private ArrayList<LocationItem> items;
    private LocationItem newItem;
    private boolean creating;

    public AppModel() {
        newItem = new LocationItem(null,null,0);
        items = new ArrayList<>();
        creating = false;
    }

    public ArrayList<LocationItem> getItems() {
        return items;
    }

    public LocationItem getNewItem() {
        return newItem;
    }

    public boolean isCreating() {
        return creating;
    }

    public void setCreating(boolean creating) {
        this.creating = creating;
    }
}
