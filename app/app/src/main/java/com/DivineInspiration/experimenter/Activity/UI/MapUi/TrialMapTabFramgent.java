package com.DivineInspiration.experimenter.Activity.UI.MapUi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Activity.Observer;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.LOCATION_SERVICE;

public class TrialMapTabFramgent extends Fragment implements Observer, OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    List<Trial> trials = new ArrayList<>();
    GoogleMap map;
    Experiment currentExperiment;
    Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trial_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        https://developers.google.com/maps/documentation/android-sdk/start
         */


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        assert getArguments() != null;
        currentExperiment = (Experiment)getArguments().getSerializable("experiment");

    }


    @Override
    public void update(Object data) {
        trials.clear();
        trials.addAll((List<Trial>) data);
        makeMarkers();
    }

    private void makeMarkers() {
        if (map != null) {
            for (Trial trial : trials) {

                if(!trial.isIgnored()){
                    map.addMarker(new MarkerOptions().position(trial.getLocation()).snippet(com.DivineInspiration.experimenter.Activity.UI.Experiments.MapUI.MapHelper.getShortTrialDescription(trial)));
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setInfoWindowAdapter(new TrialInfoAdapter());
        checkMapLocationPermission();
        makeMarkers();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        /*
        https://stackoverflow.com/a/45564994/12471420
         */
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @AfterPermissionGranted(123)
    private void checkMapLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
                 myLocation();
        } else {
            EasyPermissions.requestPermissions(this, "We need Location Services permission to add Trials",
                    123, perms);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
      myLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @SuppressLint("MissingPermission")
    public void myLocation(){
        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // mapController.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
                map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).snippet("Current Location").icon(bitmapDescriptorFromVector(getContext(), R.drawable.current_location_icon)));
                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                //    map.moveCamera(CameraUpdateFactory.zoomTo(9));
                mLocationManager.removeUpdates(this);
            }
        });
    }


    class TrialInfoAdapter implements GoogleMap.InfoWindowAdapter {
/*
https://stackoverflow.com/a/13904784/12471420
custom info
 */

        @Override
        public View getInfoWindow(Marker marker) {

            View v = LayoutInflater.from(getContext()).inflate(R.layout.marker_content, null);



            ((TextView) v.findViewById(R.id.markerContent)).setText(marker.getSnippet());

            if(marker.getSnippet().equals("Current Location")){
                ((TextView) v.findViewById(R.id.markerContent)).setText("Current Location");
            }
            v.setClickable(false);
            return v;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return  null;
        }
    }
}




