package com.example.challenge1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class NewLocationFragment extends Fragment implements View.OnClickListener {

    private static final int CAMERA_CALLBACK = 10;
    private EditText inputLocName;
    private ImageButton addImageBT;
    private ImageButton addAddressBT;
    private Button addLocationBT;
    private TextView selectedAddress;
    private ImageView preViewImg;


    private OnChangeFragment observer2;

    private AppModel model;
    private File imagFile;

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
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);

        View root = inflater.inflate(R.layout.fragment_new_location, container, false);
        inputLocName = root.findViewById(R.id.inputLocName);
        addImageBT = root.findViewById(R.id.addImageBT);
        addAddressBT = root.findViewById(R.id.addAddressBT);
        addLocationBT = root.findViewById(R.id.addLocationBT);
        selectedAddress = root.findViewById(R.id.selectedAddress);
        preViewImg = root.findViewById(R.id.preViewImg);

        Location location = new Location("providerNA");

        if(model.getNewItem().getMyLocation() != null){
            location.setLatitude(model.getNewItem().getMyLocation().latitude);
            location.setLongitude(model.getNewItem().getMyLocation().longitude);
            fetchAddressFromLatLong(location);
            selectedAddress.setText(model.getNewItem().getAddress());
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
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagFile = new File( this.getContext().getExternalFilesDir(null) + "/photo.png");
                Log.e("<<<<",imagFile.getAbsolutePath());
                Uri uri = FileProvider.getUriForFile(this.getContext(),this.getContext().getPackageName(),imagFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(i, CAMERA_CALLBACK);
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


    private void fetchAddressFromLatLong(Location location) {

        try{
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), RESULT_OK);
            model.getNewItem().setAddress(addresses.get(0).getAddressLine(0).toString());
        }catch (IOException e){

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CALLBACK && resultCode == RESULT_OK){
            //adjuntar la direccion de la imagen
            Bitmap image = BitmapFactory.decodeFile(imagFile.getPath());
            Bitmap thumbnail = Bitmap.createScaledBitmap(
              image,image.getWidth()/4, image.getHeight()/4,true
            );
            preViewImg.setImageBitmap(thumbnail);
        }
    }

    public void setObserver2(OnChangeFragment observer2) {
        this.observer2 = observer2;
    }

    public void setModel(AppModel model) { this.model = model; }
}