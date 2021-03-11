package com.DivineInspiration.experimenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.DivineInspiration.experimenter.Controller.LocalUserManager;
import com.DivineInspiration.experimenter.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements LocalUserManager.UserReadyCallback {

    LocalUserManager manager =LocalUserManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //setup local user
        manager.setContext(this);
        manager.setReadyCallback(this);



    }


    @Override
    public void onUserReady() {

    }
}