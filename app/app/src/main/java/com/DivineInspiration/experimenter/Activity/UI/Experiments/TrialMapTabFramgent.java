package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.SimpleInvalidationHandler;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class TrialMapTabFramgent extends Fragment {
    MapView map;
    List<Trial> trials;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trial_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = view.findViewById(R.id.trialMap);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(9.5);


        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 301);

        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                mapController.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));

                mLocationManager.removeUpdates(this);
            }
        });


        Bundle args = getArguments();
        if (args != null) {
            TrialManager.getInstance().queryExperimentTrials(args.getString("experimentID", ""), trialsReturned -> {
                trials = trialsReturned;


                updateMarkers();
            });


        }
    }


    private void updateMarkers() {
        List<Marker> markers = new ArrayList<>();
        for (Trial t : trials) {
            if (t.getLocation() == null) continue;
            Log.d("Woah", map.toString());
            Marker m = new Marker(map);
            m.setPosition(t.getLocation());
            m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            m.setIcon(getResources().getDrawable(R.drawable.location, null));
            m.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    return false;
                }
            });
            markers.add(m);
        }
        map.getOverlays().addAll(markers);
        map.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
      //  map.onResume();
        /*
        https://github.com/osmdroid/osmdroid/issues/277#issuecomment-412099853
        fixes for map not loading after view switch
         */
        MapTileProviderBasic tileProvider = new MapTileProviderBasic(getContext().getApplicationContext(), TileSourceFactory.MAPNIK);
        SimpleInvalidationHandler mTileRequestCompleteHandler = new SimpleInvalidationHandler(map);
        tileProvider.getTileRequestCompleteHandlers().add(mTileRequestCompleteHandler);
        map.setTileProvider(tileProvider);
        map.invalidate();

        Bundle args = getArguments();
        if (args != null) {
            TrialManager.getInstance().queryExperimentTrials(args.getString("experimentID", ""), trialsReturned -> {
                trials = trialsReturned;
                updateMarkers();
            });


        }

    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();

    }


}
