package com.DivineInspiration.experimenter.Activity.UI.Scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Activity.UI.Experiments.QRCodeDialogFragment;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.content.Context.LOCATION_SERVICE;

public class ScanFragment extends Fragment {
    boolean allowCamera = false;
    boolean openCamera = false;
    Button scan;

    // scanned code
    String[] scanned;
    double myLat = 0;
    double myLong = 0;

    private CodeScanner mCodeScanner;
    CodeScannerView scannerView;

    /**
     * When creating view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // from https://www.youtube.com/watch?v=drH63NpSWyk & https://github.com/yuriy-budiyev/code-scanner
        View root = inflater.inflate(R.layout.scan_fragment, container, false);
        scannerView = root.findViewById(R.id.scanner);

        // check camera permissions
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
            mCodeScanner.startPreview();
        } else {
            // request camera permissions
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, 401);
        }

        return root;
    }

    /**
     * When the view is created
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        scannerView = view.findViewById(R.id.scannerView);

        scan = view.findViewById(R.id.scanButton);

//        new IntentIntegrator(this.getActivity()).initiateScan(); // `this` is the current Activity
//
        // when scan button is clicked
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if camera is open
                if (allowCamera) {
                     mCodeScanner.startPreview();
                } else {
                    Toast.makeText(getActivity(), "A code cannot be scanned if the camera is off", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * Result of the code scanned
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this.getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Opens the camera
     */
    private void openCamera() {
        // from https://www.youtube.com/watch?v=drH63NpSWyk by Code Palace
        // (https://www.youtube.com/channel/UCuudpdbKmQWq2PPzYgVCWlA)
        if (openCamera) {
            return;
        }
        openCamera = true;
        allowCamera = true;

        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // do something after scanning


                        scanned = result.getText().split("-");

                        try {
                            // format: experimentID-trialType-result

                            // TODO Check for subscripton

                            String experimentID = scanned[0];
                            String trialType = scanned[1];
                            String needLocation = scanned[2];
                            LatLng location = null;

                            if (Boolean.parseBoolean(needLocation)) location = new LatLng(myLat, myLong);
                            Trial scannedTrial = null;

                            switch (trialType) {
                                case Trial.BINOMIAL:

                                    int successes = Integer.parseInt(scanned[3]);
                                    int failures = Integer.parseInt(scanned[4]);
                                    for (int i = 0; i < successes; i++) {
                                        scannedTrial = new BinomialTrial(
                                                UserManager.getInstance().getLocalUser().getUserId(),
                                                UserManager.getInstance().getLocalUser().getUserName(),
                                                experimentID,
                                                true,
                                                location
                                        );
                                    }
                                    for (int i = 0; i < failures; i++) {
                                        scannedTrial = new BinomialTrial(
                                                UserManager.getInstance().getLocalUser().getUserId(),
                                                UserManager.getInstance().getLocalUser().getUserName(),
                                                experimentID,
                                                false,
                                                location
                                        );
                                    }

                                    break;
                                case Trial.COUNT:
                                    scannedTrial = new CountTrial(
                                            UserManager.getInstance().getLocalUser().getUserId(),
                                            UserManager.getInstance().getLocalUser().getUserName(),
                                            experimentID,
                                            Integer.parseInt(scanned[3]),
                                            location
                                    );
                                    break;
                                case Trial.MEASURE:
                                    scannedTrial = new MeasurementTrial(
                                            UserManager.getInstance().getLocalUser().getUserId(),
                                            UserManager.getInstance().getLocalUser().getUserName(),
                                            experimentID,
                                            Double.parseDouble(scanned[3]),
                                            location
                                    );
                                    break;
                                case Trial.NONNEGATIVE:
                                    scannedTrial = new NonNegativeTrial(
                                            UserManager.getInstance().getLocalUser().getUserId(),
                                            UserManager.getInstance().getLocalUser().getUserName(),
                                            experimentID,
                                            Integer.parseInt(scanned[3]),
                                            location
                                    );
                                    break;
                                default:
                                    throw new ClassNotFoundException();
                            }

                            TrialManager.getInstance().addTrial(scannedTrial, trials -> {Log.d("Scan Fragment", "Trial added");});

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Code scanned is not valid", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    /**
     * Gets current location
     */
    public void getLocation() {
        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },301);
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                myLong = location.getLongitude();
                myLat = location.getLatitude();
                Log.d("Scan:", "updating!");
                mLocationManager.removeUpdates(this);
            }
        });
    }

    /**
     * When the fragment is back
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!openCamera && ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
            mCodeScanner.startPreview();
        }
    }

    /**
     * When the fragment is paused
     */
    @Override
    public void onPause() {
        // remove QR scanner resources (I think it closes the camera?)
        if (openCamera) mCodeScanner.releaseResources();
        super.onPause();
    }
}