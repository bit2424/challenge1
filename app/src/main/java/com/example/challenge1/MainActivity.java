package com.example.challenge1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
                            model.setCreating(false);
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
                showFragment(newLocationFragment);
                break;

            case R.id.listLocation:
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