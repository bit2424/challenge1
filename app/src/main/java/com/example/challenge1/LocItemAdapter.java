package com.example.challenge1;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LocItemAdapter extends RecyclerView.Adapter<LocItemView> {

    private ArrayList<LocationItem> items;
    private ArrayList<LocationItem> shcItems;

    public LocItemAdapter() {
        items = new ArrayList<>();
        shcItems = new ArrayList<>();
        items.add(new LocationItem("LOL","asdf asdfa","No dir",0));
    }

    public void addItem(LocationItem item){
        items.add(item);
    }

    @NonNull
    @Override
    public LocItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.loc_item_row, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        LocItemView locItemView = new LocItemView(rowroot);
        return locItemView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocItemView holder, int position) {
        if(shcItems.size() == 0){
            LocationItem item = items.get(position);
            holder.getLocationName().setText(item.getName());
            holder.getLocationScore().setText(String.valueOf(item.getScore()));
            //holder.getDistance().setText(String.valueOf(item.getUserDistance()));
            //Set the other parameters of the view
        }else{
            LocationItem item = shcItems.get(position);
            holder.getLocationName().setText(item.getName());
            holder.getLocationScore().setText(String.valueOf(item.getScore()));
            //holder.getDistance().setText(String.valueOf(item.getUserDistance()));
            //Set the other parameters of the view
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<LocationItem> items) {
        this.items = items;
    }

    public void searchPlace(String searching){
        //Buscar los lugares que empicen con searching
        for (int i = 0; i < items.size(); i++) {
            //Tirar otro for por cada titulo de cada lugar hasta el tamaño de searching
            for (int j = 0; j < searching.length(); j++) {

            }
            //Si cumple que son iguales lo metes en shcItems
        }


    }
}
