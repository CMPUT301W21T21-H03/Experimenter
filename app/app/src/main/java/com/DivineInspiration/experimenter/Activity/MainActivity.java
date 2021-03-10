package com.DivineInspiration.experimenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;

public class MainActivity extends AppCompatActivity implements IdGen.IDCallBackable {

    // on mount
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Add a new document with a generated ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        IdGen.genUserId(this);


    }

    public void onIdReady(long id){
        Log.d("stuff", "Id generated: " + String.valueOf(id));
        Log.d("stuff", "Id generated in base 36: " + IdGen.base10To36(id));

    }
}