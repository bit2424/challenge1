package com.example.challenge1;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class LocItemView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;
    private TextView locationName;
    private TextView locationScore;
    private TextView distance;
    private ImageView locationImg;
    private ImageButton locationViewBT;

    private LocationItem item;

    private LocItemAdapter observer;




    public LocItemView(ConstraintLayout root) {
        super(root);
        this.root = root;
        locationName = root.findViewById(R.id.locationName);
        locationScore = root.findViewById(R.id.locationScore);
        locationImg = root.findViewById(R.id.locationImg);
        distance = root.findViewById(R.id.distance);
        locationViewBT = root.findViewById(R.id.locationViewBT);
        locationViewBT.setOnClickListener(
                (View v)->{
                    observer.onViewLocation(this.item);
                }
        );
        //locationViewBT = root.findViewById(R.id.locationViewBT);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getLocationName() {
        return locationName;
    }

    public TextView getLocationScore() {
        return locationScore;
    }

    public TextView getDistance() {
        return distance;
    }

    public ImageView getLocationImg() {
        return locationImg;
    }

    public ImageButton getLocationViewBT() {return locationViewBT;}

    public void setItem(LocationItem item) { this.item = item; }

    public LocationItem getItem() { return item; }

    public void setObserver(LocItemAdapter locItemAdapter) {
        observer = locItemAdapter;
    }
}
