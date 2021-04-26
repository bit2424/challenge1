package com.example.challenge1;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocItemAdapter extends RecyclerView.Adapter<LocItemView> {

    private ArrayList<LocationItem> items;

    public LocItemAdapter() {
        items = new ArrayList<>();
        items.add(new LocationItem("LOL","asdf asdfa",0));
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
        LocationItem item = items.get(position);
        holder.getLocationName().setText(item.getName());
        holder.getLocationScore().setText(String.valueOf(item.getScore()));
        //holder.getDistance().setText(String.valueOf(item.getUserDistance()));
        //Set the other parameters of the view
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
