package com.DivineInspiration.experimenter.Activity.UI.Scan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Activity.MainActivity;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class ScanFragment extends Fragment {
//    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private CodeScanner mCodeScanner;
    boolean allowCamera = false;
    boolean openCamera = false;
    CodeScannerView scannerView;
    Button scan;
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

    @Override
    public void onResume() {
        super.onResume();
        if (!openCamera && ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        }
        if (allowCamera) mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        if (allowCamera) mCodeScanner.releaseResources();
        super.onPause();
    }

}