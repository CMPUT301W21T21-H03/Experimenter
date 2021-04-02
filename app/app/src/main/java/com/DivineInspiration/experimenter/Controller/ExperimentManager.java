package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Experiment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExperimentManager extends ArrayList<Experiment> {
    // Singleton ArrayList
    private static ExperimentManager singleton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String localUserId;
    private String TAG = "DATABASE";

    /**
     * When experiment is ready
     */
    public interface OnExperimentListReadyListener {
        void onExperimentsReady(List<Experiment> experiments);
    }


    public interface OnOperationDone {
        void done(boolean successful);
    }

    /**
     * Constructor
     */
    private ExperimentManager() {

    }

    /**
     * Get singleton instance
     *
     * @return experiment manager
     */
    public static ExperimentManager getInstance() {
        if (singleton == null) {
            singleton = new ExperimentManager();
        }

        return singleton;
    }


    private void initLocalUserId(){
        if(localUserId == null) localUserId = UserManager.getInstance().getLocalUser().getUserId();
    }


    /**
     * Update owner name
     */
    public void updateOwnerName(String ownerId, String newName, OnOperationDone callback) {
        initLocalUserId();
        /*
        https://stackoverflow.com/a/53379134/12471420
         */
        db.collection("Experiments").whereEqualTo("OwnerID", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Map<Object, String> map = new HashMap<>();
                        map.put("OwnerName", newName);
                        db.collection("Experiments").document(doc.getId()).set(map, SetOptions.merge());
                    }
                }
                if (callback != null) {
                    callback.done(task.isSuccessful() && task.getResult().size() == 0);
                }
            }
        });
    }

    /**
     * Unsubscribe from experiment
     *
     * @param userId       user ID
     * @param experimentId experiment to unsub from
     */
    public void unSubFromExperiment(String userId, String experimentId, OnOperationDone callback) {
        initLocalUserId();
        db.collection("Experiments").document(experimentId).update("SubscriberIDs", FieldValue.arrayRemove(userId)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "subbing to experiment failed!(most likely no such experiment exsit)");

                }
                if (callback != null) {
                    callback.done(task.isSuccessful());
                }
            }
        });
    }

    /**
     * Add subscriber to experiment
     *
     * @param userId       user ID
     * @param experimentId experiment ID of subscriber
     */
    public void subToExperiment(String userId, String experimentId, OnOperationDone callback) {
        initLocalUserId();
        // TODO  handle on sub failed?
        db.collection("Experiments").document(experimentId).update("SubscriberIDs", FieldValue.arrayUnion(userId)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "subbing to experiment failed!(most likely no such experiment exsit)");

                }
                if (callback != null) {
                    callback.done(task.isSuccessful());
                }
            }
        });
    }

    // TODO An experiment in the database acts as both a collection and a document. Do we now need to
    //      delete the comments in the experiment collection as well?

    /**
     * Delete experiment from database
     *
     * @param experimentId experiment ID
     */
    public void deleteExperiment(String experimentId, OnOperationDone callback) {
        initLocalUserId();
        // TODO handle on delete failed?
        db.collection("Experiments").document(experimentId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "delete experiment failed!(most likely there is no experiment with this id in the database)");
                    callback.done(false);
                }
                else{
                    callback.done(true);
                }
            }
        });
    }

    /**
     * Adds an experiment to database
     *
     * @param experiment experiment
     */
    public void addExperiment(Experiment experiment, OnOperationDone callback) {
        initLocalUserId();
        //TODO handle on add failed?

        // put into database
        Map<String, Object> doc = new HashMap<>();
        doc.put("ExperimentName", experiment.getExperimentName());
        doc.put("OwnerID", experiment.getOwnerID());
        doc.put("OwnerName", experiment.getOwnerName());
        doc.put("ExperimentDescription", experiment.getExperimentDescription());
        doc.put("TrialType", experiment.getTrialType());
        doc.put("Region", experiment.getRegion());
        doc.put("MinimumTrials", experiment.getMinimumTrials());
        doc.put("RequireGeo", experiment.isRequireGeo());
        doc.put("Status", experiment.getStatus());
        doc.put("SubscriberIDs", new ArrayList<>());


        db.collection("Experiments").document(experiment.getExperimentID()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "New experiment failed to be committed to database!");
                }
                if (callback != null) {
                    callback.done(task.isSuccessful());
                }
            }
        });
    }

    public void updateExperiment(Experiment experiment, OnOperationDone callback) {
        //how to rename functions 101
        initLocalUserId();
        //TODO handle on add failed?

        // put into database
        Map<String, Object> doc = new HashMap<>();
        doc.put("ExperimentName", experiment.getExperimentName());

        doc.put("OwnerName", experiment.getOwnerName());
        doc.put("ExperimentDescription", experiment.getExperimentDescription());
        doc.put("TrialType", experiment.getTrialType());
        doc.put("Region", experiment.getRegion());
        doc.put("MinimumTrials", experiment.getMinimumTrials());
        doc.put("RequireGeo", experiment.isRequireGeo());
        doc.put("Status", experiment.getStatus());



        db.collection("Experiments").document(experiment.getExperimentID()).set(doc, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "New experiment failed to be committed to database!");
                }
                if (callback != null) {
                    callback.done(task.isSuccessful());
                }
            }
        });
    }

    public void updateExperiment(Experiment experiment, Map<String, Object> fieldsToUpdate, OnOperationDone callback) {
        initLocalUserId();
        //how to rename functions 101
        db.collection("Experiments").document(experiment.getExperimentID()).set(fieldsToUpdate, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "New experiment failed to be committed to database!");
                }
                if (callback != null) {
                    callback.done(task.isSuccessful());
                }
            }
        });
    }


    /**
     * TODO ?
     *
     * @param keywords
     * @param callback
     */
    public void querySearch(String keywords, OnExperimentListReadyListener callback) {
        //TODO to be implemented
        callback.onExperimentsReady(null);
    }

    /**
     * Gets experiments that the user is subscribed to
     *
     * @param userId   user ID
     * @param callback callback function
     */
    public void queryUserSubs(String userId, OnExperimentListReadyListener callback) {
        initLocalUserId();
        db.collection("Experiments").whereArrayContains("SubscriberIDs", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (callback != null) {
                        List<Experiment> output = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            output.add(expFromSnapshot(snapshot));
                        }
                        callback.onExperimentsReady(output);
                    }
                } else {
                    Log.d("stuff", "query user subscriptions failed!");
                    callback.onExperimentsReady(null);
                }
            }
        });
    }

    /**
     * Gets all experiments that belong to the owner
     *
     * @param userId   user ID of owner
     * @param callback callback function
     */
    public void queryUserExperiment(String userId, OnExperimentListReadyListener callback) {
        initLocalUserId();
        db.collection("Experiments").whereEqualTo("OwnerID", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (callback != null) {
                        List<Experiment> output = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            Experiment exp = expFromSnapshot(snapshot);
                            if(!exp.getStatus().equals(Experiment.ENDED) || exp.getOwnerID().equals(localUserId)){
                                output.add(exp);
                            }
                        }
                        callback.onExperimentsReady(output);
                    }
                } else {
                    Log.d(TAG, "query user subscriptions failed!");
                    callback.onExperimentsReady(null);
                }
            }
        });
    }

    /**
     * Gets all the experiments
     *
     * @param callback callback function
     */
    public void queryAll(OnExperimentListReadyListener callback) {
        initLocalUserId();
        db.collection("Experiments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (callback != null) {
                        List<Experiment> output = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            Experiment exp = expFromSnapshot(snapshot);
                            if(!exp.getStatus().equals(Experiment.ENDED) || exp.getOwnerID().equals(localUserId)){
                                output.add(exp);
                            }
                        }
                        callback.onExperimentsReady(output);
                    }
                } else {
                    Log.d(TAG, "query user subscriptions failed!");
                    callback.onExperimentsReady(null);
                }
            }
        });
    }

    /**
     * Gets experiments from snapshot
     *
     * @param snapshot query snapshot
     * @return a new experiment instance from snapshot
     */
    private Experiment expFromSnapshot(QueryDocumentSnapshot snapshot) {

        // returns a new experiment instance from snapshot
        return new Experiment(
                snapshot.getId(),
                snapshot.getString("ExperimentName"),
                snapshot.getString("OwnerID"),
                snapshot.getString("OwnerName"),
                snapshot.getString("ExperimentDescription"),
                snapshot.getString("TrialType"),
                snapshot.getString("Region"),
                snapshot.getLong("MinimumTrials").intValue(),
                snapshot.getBoolean("RequireGeo"),
                snapshot.getString("Status")
        );

    }

}