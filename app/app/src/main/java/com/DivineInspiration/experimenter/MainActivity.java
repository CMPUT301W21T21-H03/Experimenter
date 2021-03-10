package com.DivineInspiration.experimenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("stuff", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("stuff", "Error adding document", e);
                    }
                });

        Log.d("stuff", "starting main");
        Log.d("stuff", String.valueOf(IdGen.genUserId()));
        Log.d("stuff", "main done.");
=======
        
        FirebaseFirestore db = FirebaseFirestore.getInstance();



    }

    public void onIdReady(long id){
        Log.d("stuff", "Id generated: " + String.valueOf(id));
        Log.d("stuff", "Id generated in base 36: " + IdGen.base10To36(id));
>>>>>>> Stashed changes
    }
}