package com.DivineInspiration.experimenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.DivineInspiration.experimenter.LocalUserManager;
import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;

public class MainActivity extends AppCompatActivity implements LocalUserManager.UserReadyCallback {

    LocalUserManager manager =LocalUserManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        manager.setContext(this);
        manager.setReadyCallback(this);



    }


    @Override
    public void onUserReady() {
        Log.d("stuff", manager.getUser().toString());
    }
}