package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExperimentManager extends ArrayList<Experiment> {
    //Singleton ArrayList
    private ArrayList<Experiment> experiments;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String TAG = "Sample";

    private ExperimentManager(){
        experiments = new ArrayList<>();
    }

    public ArrayList<Experiment> getExperiments(){
        //TODO
        return experiments;
    }

    private static ExperimentManager singleton;

    public static ExperimentManager getInstance(){
        if(singleton ==null){
            singleton = new ExperimentManager();
        }
        return singleton;
    }

    public void addExperiment(Experiment experiment) {
        Map<String, Object> doc = new HashMap<>();
        doc.put("Name", experiment.getExperimentName());
        doc.put("ownerId", experiment.getExperimentOwnerID());
        doc.put("Description", experiment.getExperimentDescription());
        Map<String, Object> Subscribers = new HashMap<>();
        Subscribers.put("0", experiment.getExperimentOwnerID() );

        db.collection("Experiments")
                .document(experiment.getExperimentID())
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Experiment has been added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Experiment could not be added!" + e.toString());
                    }
                });

    }

    /**
     * Checks if an experiment is in the database
     * @param experiment
     * @return
     *      returns true if that experiment exists in the database
     *      returns false if the experiment does not exist in the database
     */
    public boolean hasExperiment(Experiment experiment) {
        //TODO
        return true;
    }

    /**
     * Checks if an experiment is in the database and if it is it deletes it
     * @param experiment
     */
    public void deleteExperiment(Experiment experiment) {
        if (hasExperiment(experiment) == false) {
            throw new IllegalArgumentException();
        }
        db.collection("Experiments").document(experiment.getExperimentID())
                .delete().addOnSuccessListener(new OnSuccessListener < Void > () {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Experiment has been Deleted Successfully");
            }
        });

    }

}
