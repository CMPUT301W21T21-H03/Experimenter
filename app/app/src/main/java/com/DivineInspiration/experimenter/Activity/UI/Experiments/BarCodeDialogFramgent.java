package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

public class BarCodeDialogFramgent extends DialogFragment {


    String params;
    boolean allowCamera = false;
    boolean openCamera = false;
    CodeScannerView scannerView;
    Button scan;
    private CodeScanner mCodeScanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.scan_fragment, container);

        params = getArguments().getString("message");

        scannerView = view.findViewById(R.id.scanner);
        scan = view.findViewById(R.id.scanButton);
        // check camera permissions
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
            mCodeScanner.startPreview();
        } else {
            // request camera permissions
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, 401);
        }

        return view;

    }

    void openCamera(){

    }
}

