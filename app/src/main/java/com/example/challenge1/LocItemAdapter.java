package com.example.challenge1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LocItemAdapter extends RecyclerView.Adapter<LocItemView> implements  OnLocItemAction{

    private ArrayList<LocationItem> items;
    private ArrayList<LocationItem> shcItems;

    private OnLocItemAction obsever;

    public LocItemAdapter() {
        items = new ArrayList<>();
        shcItems = new ArrayList<>();
    }

    public void addItem(LocationItem item){
        items.add(item);
    }

    @NonNull
    @Override
    public LocItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.loc_item_row, parent,false);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        LocItemView locItemView = new LocItemView(rowroot);
        locItemView.setObserver(this);
        return locItemView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocItemView holder, int position) {
        LocationItem item = items.get(position);
        /*if(shcItems.size() == 0){
            item = shcItems.get(position);
        }*/
        holder.setItem(item);
        holder.getLocationName().setText(item.getName());
        holder.getLocationScore().setText(String.valueOf(item.getScore()));
        Bitmap image = BitmapFactory.decodeFile(item.getImageSrc());
        Bitmap thumbnail = Bitmap.createScaledBitmap(
                image,image.getWidth()/4, image.getHeight()/4,true
        );
        holder.getLocationImg().setImageBitmap(thumbnail);
        holder.getDistance().setText(String.format("%.2f",item.getUserDistance()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<LocationItem> items) {
        this.items = items;
        this.notifyDataSetChanged();
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

    public void setObsever(OnLocItemAction obsever) {
        this.obsever = obsever;
    }

    @Override
    public void onViewLocation(LocationItem item) {
        obsever.onViewLocation(item);
    }


    public void filter(String text, ArrayList<LocationItem> nItems){
        ArrayList<LocationItem> filteredList = new ArrayList<>();

        for (LocationItem item : nItems){
            if(item.getName().toLowerCase().contains(text)){
                filteredList.add(item);
            }
        }
        items = filteredList;
        notifyDataSetChanged();
    }
}
