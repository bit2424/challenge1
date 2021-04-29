package com.example.challenge1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private LocItemAdapter adpater;
    private GoogleMap nMap;
    private LocationManager manager;
    private OnChangeFragment observer;
    private AppModel model;
    private MarkerOptions currentPosition;
    private ConstraintLayout closestLocationLayout;
    private TextView closestName;
    private TextView closestAddress;
    private ImageView closestImage;

    GoogleMap.OnMapClickListener clickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng pos) {
            if(model.getState() == model.STATE_CREATING){
                //Toast toast = Toast.makeText(getContext(), "Seleccionaste: "+String.valueOf(pos.latitude)+" , "+String.valueOf(pos.longitude), Toast.LENGTH_SHORT);
                Toast toast = Toast.makeText(getContext(), "Haz seleccionado una nueva ubicación!", Toast.LENGTH_SHORT);
                toast.show();
                nMap.clear();
                nMap.addMarker(new MarkerOptions().position(pos).title("Nueva Locación"));
                model.getNewItem().setMyLocation(pos);
            }else if(model.getState() == model.STATE_E_LOOKING){
                model.setState(model.STATE_G_LOOKING);
                closestLocationLayout.setVisibility(View.GONE);
                model.updateDistance();
                setInitialPos();
            }

        }
    };

    GoogleMap.OnCameraMoveListener moveListener = new GoogleMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {

        }
    };

    LocationListener myLocationListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng myPos = new LatLng(location.getLatitude(),location.getLongitude());
            //Here is where I ask my distance between stored locations
            if(!model.getItems().isEmpty()) {
                updateDistances(myPos);
            }
            updateLocation(myPos,12);
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

    //when this method is called it has to have elelments in the arrayList of items
    private void updateDistances(LatLng myPos) {

        ArrayList<LocationItem> items = model.getItems();
        LocationItem closest = items.get(0);
        int n = items.size();
        for (int i = 0; i < n; i++) {
            if(closest.getUserDistance()>items.get(i).getUserDistance()) closest = items.get(i);
            items.get(i).setUserDistance(getDistance(myPos.latitude,myPos.longitude,items.get(i).getMyLocation().latitude,items.get(i).getMyLocation().longitude));
        }

        if(closest.getUserDistance()<= 0.1){
            showBottomLayout(closest);
        }
        model.setUserLocation(myPos);
    }

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
            nMap.setOnCameraMoveListener(moveListener);
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
        closestLocationLayout = getActivity().findViewById(R.id.closestLocationLayout);
        closestName = ((TextView)getActivity().findViewById(R.id.closestLocationName));
        closestAddress = ((TextView)getActivity().findViewById(R.id.closestLocationAddress));
        closestImage = ((ImageView)getActivity().findViewById(R.id.closestLocationIMG));
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
        LatLng myPos;

        if(model.getState() == model.STATE_E_LOOKING){
            showBottomLayout(model.getShwItem());
            myPos = model.getShwItem().getMyLocation();
            updateLocation(myPos,17);
            showToast("El marcador rojo es el lugar selecionado!",Toast.LENGTH_SHORT);
            nMap.addMarker(new MarkerOptions().position(myPos).title(model.getShwItem().getName()).icon(BitmapDescriptorFactory.defaultMarker(0)));
            if(location != null) {
                model.setUserLocation( new LatLng(location.getLatitude(), location.getLongitude()));
            }else{
                showToast("Por favor enciende tu GPS!",Toast.LENGTH_SHORT);
            }
        }else{
            if(location != null) {
                showToast("El marcador amarillo es su posición actual y los azules son lugares agregados!",Toast.LENGTH_LONG);
                myPos = new LatLng(location.getLatitude(), location.getLongitude());
                updateLocation(myPos,12);
                model.setUserLocation(myPos);
                model.getNewItem().setMyLocation(myPos);
                nMap.clear();
                nMap.addMarker(new MarkerOptions().position(myPos).title("Ubicación Actual").icon(BitmapDescriptorFactory.defaultMarker(50)));


                //Updates all the places
                ArrayList<LocationItem> items = model.getItems();
                if(!items.isEmpty()){
                    int n = items.size();
                    for (int i = 0; i < n; i++) {
                        nMap.addMarker(new MarkerOptions().position(items.get(i).getMyLocation()).title(items.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(75)));
                    }
                    updateDistances(model.getUserLocation());
                }


            }else{
                showToast("Por favor enciende tu GPS!",Toast.LENGTH_SHORT);

            }
        }



    }

    private void showBottomLayout(LocationItem item) {
        closestLocationLayout.setVisibility(View.VISIBLE);
        closestName.setText(item.getName());
        closestAddress.setText(item.getAddress());
        Bitmap image = BitmapFactory.decodeFile(item.getImageSrc());
        Bitmap thumbnail = Bitmap.createScaledBitmap(
                image,image.getWidth()/4, image.getHeight()/4,true
        );
        closestImage.setImageBitmap(thumbnail);

    }

    public void setObserver(OnChangeFragment observer) {
        this.observer = observer;
    }

    private  void updateLocation(LatLng pos, int zoom){
        //nMap.addMarker(new MarkerOptions().position(pos).title("Yo"));
        nMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,zoom));
    }

    public void setModel(AppModel model) {
        this.model = model;
    }

    private void showToast(String message, int length){
        Toast toast = Toast.makeText(getContext(), message, length);
        toast.show();
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    private double deg2rad( double deg) {
        return deg * (Math.PI/180);
    }
}