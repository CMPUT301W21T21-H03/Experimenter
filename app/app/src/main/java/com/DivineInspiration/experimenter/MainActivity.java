package com.DivineInspiration.experimenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IdGen.IDCallBackable {

    // on mount
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Add a new document with a generated ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task ->{
//            if(task.isSuccessful()){
//                Log.d("stuff", task.getResult() + "success results");
//            }
//            else{
//                Log.d("stuff", "fail");
//            }
//        });
        Log.d("stuff", "before function call");
        IdGen.genUserId(this);
        Log.d("stuff", "after function call");


    }

    public void onIdReady(long id){
        Log.d("stuff", "Id generated: " + String.valueOf(id));
        Log.d("stuff", "Id generated in base 36: " + IdGen.base10To36(id));
    }
}