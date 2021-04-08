package com.DivineInspiration.experimenter.Activity.UI.Scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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
import com.DivineInspiration.experimenter.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanFragment extends Fragment {
//    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private CodeScanner mCodeScanner;
    boolean allowCamera = false;
    boolean openCamera = false;
    CodeScannerView scannerView;
    Button scan;
    Button debug;
    // scanned code
    String scanned;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // from https://www.youtube.com/watch?v=drH63NpSWyk & https://github.com/yuriy-budiyev/code-scanner
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        scannerView = root.findViewById(R.id.scanner);
        // check camera permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera();
                mCodeScanner.startPreview();
            } else {
                // request camera permissions
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, 401);
            }
        } else {
            // if version is below m then write code here,
            Toast.makeText(this.getContext(), "Please update the minimum SDK version", Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        scannerView = view.findViewById(R.id.scannerView);
        scan = view.findViewById(R.id.scanButton);
        debug = view.findViewById(R.id.debug_btn);
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

        // when debug btn is clicked
        debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRCodeDialogFragment frag = new QRCodeDialogFragment();
//                    frag.setArguments(args);
                frag.show(getChildFragmentManager(), "QR code fragment");
            }
        });
    }

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
                        scanned = result.getText();

                        // TODO: turn string to ??

                        Toast.makeText(getActivity(), String.format("Code scanned: %s", scanned), Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // request code
//        Log.e("Permissions code:", String.valueOf(requestCode));
//        if (requestCode == 401) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openCamera();
//                allowCamera = true;
//            } else {
//                Toast.makeText(this.getContext(), "Please accept camera permissions, otherwise, the scan will not work", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    /**
     * When the fragment is back
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!openCamera && ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
//            Log.v("Run", "1");
            mCodeScanner.startPreview();
        }
    }

    /**
     * When the fragment is paused
     */
    @Override
    public void onPause() {
        // remove QR scanner resources (I think it close the camera?)
        if (openCamera) mCodeScanner.releaseResources();
        super.onPause();
    }
}