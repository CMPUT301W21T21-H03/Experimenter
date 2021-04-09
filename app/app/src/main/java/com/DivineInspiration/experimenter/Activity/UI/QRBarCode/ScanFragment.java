package com.DivineInspiration.experimenter.Activity.UI.QRBarCode;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.DivineInspiration.experimenter.Activity.UI.TrialsUI.CreateTrialDialogFragment;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class ScanFragment extends Fragment {
    private boolean allowCamera = false;
    private boolean openCamera = false;
    private Button scan;

    // scanned code
    private String[] scanned;
    private double myLat = 0;
    private double myLong = 0;
    private CodeScannerView scannerView;
    private CodeScanner mCodeScanner;

    /**
     * When creating view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     * view itself
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
     * code request
     * @param resultCode
     * result of request
     * @param data
     * data
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


                        //id, type, params
                        scanned = result.getText().split("_");

                        if (scanned.length == 1) {
                            //must've scanned a bar code
                            SharedPreferences pref = getContext().getSharedPreferences("Barcode", Context.MODE_PRIVATE);
                            Map<String, ?> map = pref.getAll();
                            for (String key : map.keySet()) {
                                if (key.equals(scanned[0])) {
                                    //found a match barcode
                                    scanned = ((String) map.get(key)).split("_");
                                    Toast.makeText(getContext(), "Found matching barcode", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (scanned.length == 1) {
                                Toast.makeText(getContext(), "No matching barcode found", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        String userId = UserManager.getInstance().getLocalUser().getUserId();
                        String userName = UserManager.getInstance().getLocalUser().getUserName();

                        ExperimentManager manager = ExperimentManager.getInstance();
                        manager.queryUserSubs(userId, experiments -> {
                            boolean subbed = false;

                            for (Experiment exp : experiments) {
                                if (exp.getOwnerID().equals(userId)) {
                                    subbed = true;
                                    break;
                                }

                            }
                            if (subbed) {
                                Bundle args = new Bundle();
                                //expId, type, arg1, arg2
                                Log.d("Woah barcode", scanned[0]);
                                manager.queryExperimentFromId(scanned[0], experiment -> {

                                    args.putSerializable("experiment", experiment);
                                    args.putString("experimenterID", userId);
                                    args.putString("experimenterName", userName);
                                    args.putBoolean("isScan", true);
                                    if (scanned[1].equals(Trial.BINOMIAL)) {
                                        args.putInt("Pass", Integer.parseInt(scanned[2]));
                                        args.putInt("Fail", Integer.parseInt(scanned[3]));
                                    } else if (scanned[1].equals(Trial.MEASURE)) {
                                        args.putString("Value", scanned[2]);
                                    } else {
                                        args.putInt("Count", Integer.parseInt(scanned[2]));
                                    }
                                    CreateTrialDialogFragment dialogFragment = new CreateTrialDialogFragment(trial -> {
                                        Toast.makeText(getContext(), "Trial has been added", Toast.LENGTH_LONG).show();
                                    });
                                    dialogFragment.setArguments(args);
                                    dialogFragment.show(getChildFragmentManager(), "Add trial from scan");

                                });
                            } else {
                                manager.queryExperimentFromId(scanned[0], experiment -> {
                                    AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.dialogColor)
                                            .setTitle("Sub to: " + experiment.getExperimentName() + "?")
                                            .setPositiveButton("Subscribe", ((dialog1, which) -> {
                                                dialog1.dismiss();
                                                manager.subToExperiment(userId, experiment.getExperimentID(), null);
                                                Toast.makeText(getContext(), "You are now subsribed to: " + experiment.getExperimentName(), Toast.LENGTH_LONG).show();
                                            })).setNegativeButton("Cancel", null).create();

                                        dialog.show();
                                });
                            }
                        });
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
    private void getLocation() {
        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 301);
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