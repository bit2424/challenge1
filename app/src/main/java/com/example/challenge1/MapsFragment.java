package com.example.challenge1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private LocItemAdapter adpater;
    private GoogleMap nMap;
    private LocationManager manager;
    private OnChangeFragment observer;
    private AppModel model;
    private MarkerOptions currentPosition;

    GoogleMap.OnMapClickListener clickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng pos) {

            if(model.isCreating()){
                //Toast toast = Toast.makeText(getContext(), "Seleccionaste: "+String.valueOf(pos.latitude)+" , "+String.valueOf(pos.longitude), Toast.LENGTH_SHORT);
                Toast toast = Toast.makeText(getContext(), "Haz seleccionado una nueva ubicación!", Toast.LENGTH_SHORT);
                toast.show();
                nMap.clear();
                nMap.addMarker(new MarkerOptions().position(pos).title("Nueva Locación"));
                model.getNewItem().setMyLocation(pos);
            }

        }
    };

    LocationListener myLocationListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng myPos = new LatLng(location.getLatitude(),location.getLongitude());
            //Here is where I ask my distance between stored locations
            updateLocation(myPos);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            nMap = googleMap;

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000,
                    2,
                    myLocationListener);

            nMap.setOnMapClickListener(clickListener);

            setInitialPos();
        }
    };

    public MapsFragment(LocItemAdapter adapter) {
         this.adpater = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @SuppressLint("MissingPermission")
    public void setInitialPos(){
        Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location != null) {
            LatLng myPos = new LatLng(location.getLatitude(), location.getLongitude());
            updateLocation(myPos);
            model.getNewItem().setMyLocation(myPos);
            nMap.addMarker(new MarkerOptions().position(myPos).title("Ubicación Actual").icon(BitmapDescriptorFactory.defaultMarker(50)));
        }
    }

    public void setObserver(OnChangeFragment observer) {
        this.observer = observer;
    }

    private  void updateLocation(LatLng pos){
        //nMap.addMarker(new MarkerOptions().position(pos).title("Yo"));
        nMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,12));
    }

    public void setModel(AppModel model) {
        this.model = model;
    }
}