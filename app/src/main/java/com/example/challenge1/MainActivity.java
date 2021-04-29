package com.example.challenge1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnChangeFragment{

    private NewLocationFragment newLocationFragment;
    private ListItemFragment listItemFragment;
    public Button addMapAddress;
    private MapsFragment mapsFragment;
    private LocItemAdapter adapter;
    private BottomNavigationView navigator;
    private ConstraintLayout closestLocationLayout;
    private AppModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);

         model = new AppModel();

        newLocationFragment = NewLocationFragment.newInstance();
        newLocationFragment.setObserver2(this);
        newLocationFragment.setModel(model);
        listItemFragment = ListItemFragment.newInstance();
        listItemFragment.setObserver(this);
        listItemFragment.setModel(model);
        mapsFragment  = new MapsFragment(adapter);
        mapsFragment.setObserver(this);
        mapsFragment.setModel(model);
        addMapAddress = findViewById(R.id.addMapAddress);
        addMapAddress.setVisibility(View.GONE);
        addMapAddress.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.getNewItem().getMyLocation() != null){
                            changeFragment(R.id.newLocatrion);
                            addMapAddress.setVisibility(View.GONE);
                            model.setState(model.STATE_G_LOOKING);
                        }else{
                            Toast toast = Toast.makeText(v.getContext(), "Selecciona una ubicación en el mapa", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                }
        );

        closestLocationLayout = findViewById(R.id.closestLocationLayout);
        closestLocationLayout.setVisibility(View.GONE);
        //newMapFragment.setAdapter(adapter);

        navigator = findViewById(R.id.navigator);
        showFragment(newLocationFragment);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem) ->{
                    changeFragment(menuItem.getItemId());
                    return true;
                }

        );
    }

    private void changeFragment(int itemId) {
        switch (itemId){
            case R.id.newLocatrion:
                if(model.getState() == model.STATE_E_LOOKING || model.getState() == model.STATE_G_LOOKING){
                    closestLocationLayout.setVisibility(View.GONE);
                    model.setState(model.STATE_G_LOOKING);
                }
                if(model.getState() == model.STATE_CREATING){
                    closestLocationLayout.setVisibility(View.GONE);
                    addMapAddress.setVisibility(View.GONE);
                    model.setState(model.STATE_G_LOOKING);
                }
                showFragment(newLocationFragment);
                break;

            case R.id.listLocation:
                if(model.getState() == model.STATE_E_LOOKING || model.getState() == model.STATE_G_LOOKING){
                    closestLocationLayout.setVisibility(View.GONE);
                    model.setState(model.STATE_G_LOOKING);
                }
                if(model.getState() == model.STATE_CREATING){
                    closestLocationLayout.setVisibility(View.GONE);
                    addMapAddress.setVisibility(View.GONE);
                    model.setState(model.STATE_G_LOOKING);
                }
                showFragment(listItemFragment);
                break;

            case R.id.mapLocation:
                showFragment(mapsFragment);
                break;

        }
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    public Button getAddMapAddress() {
        return addMapAddress;
    }

    @Override
    public void requestFragment(int FragmentId) {
        changeFragment(FragmentId);
    }
}