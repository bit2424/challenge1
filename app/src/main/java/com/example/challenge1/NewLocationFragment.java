package com.example.challenge1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class NewLocationFragment extends Fragment implements View.OnClickListener {

    private EditText inputLocName;
    private ImageButton addImageBT;
    private ImageButton addAddressBT;
    private Button addLocationBT;
    private TextView selectedAddress;
    private ResultReceiver resultReceiver;



    private OnChangeFragment observer2;

    private AppModel model;


    private String imgUrl;

    public NewLocationFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static NewLocationFragment newInstance() {
        NewLocationFragment fragment = new NewLocationFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 1);

        resultReceiver = new AddressResultReceiver(new Handler());

        View root = inflater.inflate(R.layout.fragment_new_location, container, false);
        inputLocName = root.findViewById(R.id.inputLocName);
        addImageBT = root.findViewById(R.id.addImageBT);
        addAddressBT = root.findViewById(R.id.addAddressBT);
        addLocationBT = root.findViewById(R.id.addLocationBT);
        selectedAddress = root.findViewById(R.id.selectedAddress);

        if(model.getNewItem().getMyLocation() != null){
            Location location = new Location("providerNA");
            location.setLatitude(model.getNewItem().getMyLocation().latitude);
            location.setLongitude(model.getNewItem().getMyLocation().longitude);
            fetchAddressFromLatLong(location);
        }

        addImageBT.setOnClickListener(this);
        addAddressBT.setOnClickListener(this);
        addLocationBT.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        /*Toast toast = Toast.makeText(getContext(), "Ay pana", Toast.LENGTH_SHORT);
        toast.show();*/

        switch (v.getId()) {
            case R.id.addImageBT:
                break;
            case R.id.addAddressBT:
                Button addButton = (Button) getActivity().findViewById(R.id.addMapAddress);
                addButton.setVisibility(View.VISIBLE);
                model.setCreating(true);
                observer2.requestFragment(R.id.mapLocation);
                break;
            case R.id.addLocationBT:
                break;

        }
    }


    private void fetchAddressFromLatLong(Location location){
        Toast.makeText(getActivity(), "trying", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
        intent.putExtra(Constants.RECIVER,resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        getActivity().startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode == Constants.SUCCESS_RESULT){
                selectedAddress.setText(resultData.getString(Constants.RESULT_DATA_KEY)+" lol");
            }else{
                Toast.makeText(getActivity(), resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void setObserver2(OnChangeFragment observer2) {
        this.observer2 = observer2;
    }

    public void setModel(AppModel model) { this.model = model; }
}