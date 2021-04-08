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

/**
 * This class talks to the Firestore database
 * in order to store and retrieve experiment data.
 * The class uses singleton pattern.
 */
public class ExperimentManager extends ArrayList<Experiment> {
    // Singleton object
    private static ExperimentManager singleton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String localUserId;
    private String TAG = "DATABASE";

    /**
     * When experiment data is retrieved from database is ready,
     * it is passed along as a parameter by the interface method.
     * Utilized for: queryAll, queryUserExperiment, queryUserSubs
     */
    public interface OnExperimentListReadyListener {
        void onExperimentsReady(List<Experiment> experiments);
    }

    /**
     * When operation on database is done, the parameter indicates
     * success or failure talking to the database
     * Utilized for: updateOwnerName, unSubFromExperiment. subFromExperiment, deleteExperiment
     *              addExperiment, updateExperiment
     */
    public interface OnOperationDone {
        void done(boolean successful);
    }

    /**
     * Empty constructor
     */
    private ExperimentManager() { }

    /**
     * Get singleton instance of the class
     * @return: singleton:ExperimentManager
     */
    public static ExperimentManager getInstance() {
        if (singleton == null) {
            singleton = new ExperimentManager();
        }
        return singleton;
    }

    /**
     * Initializes the local user of the device
     * @return: void
     */
    private void initLocalUserId() {
        if (localUserId == null)
            localUserId = UserManager.getInstance().getLocalUser().getUserId();
    }


    public void banUserFromExperiment(String userId, String experimentId, OnOperationDone callback){
        initLocalUserId();

        Map<String, Object> update = new HashMap<>();
        List<String> ids = new ArrayList<>();
        ids.add(userId);
        update.put("BannedIds", ids);
       db.collection("BlackList").document(experimentId).get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               if(task.getResult().exists()){

                   db.collection("BlackList").document(experimentId).update("BannedIds", FieldValue.arrayUnion(userId)).addOnCompleteListener( task1 -> {
                       callback.done(task1.isSuccessful());
                   });
               }
               else{
                   db.collection("BlackList").document(experimentId).set(update, SetOptions.merge()).addOnCompleteListener( task1 -> {
                       callback.done(task1.isSuccessful());
                   });
               }
           }
       });
    }

    /**
     * Updates owner name in the Firestore database
     * @param: ownerId:String, newName:String, callback:OnOperationDone
     * @return: void
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
     * Unsubscribe the given user from the given experiment.
     * @param: userId:String (The user that wants to unsubscribe)
     * @param: experimentId:String (The experiment the user wants to unsubscribe from)
     * @param: callback:OnOperationDone (The class to call after the operation is done).
     * @return: void
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
     * Subscribe the given user from the given experiment.
     * @param: userId:String (The user that wants to unsubscribe).
     * @param: experimentId:String (The experiment the user wants to subscribe to).
     * @param: callback:OnOperationDone (The class to call after the operation is done.
     * @return: void
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

    /**
     * Delete the given experiment from database.
     * @param: experimentId:String (experiment we want to delete).
     * @param: callback:OnOperationDone (The class to call after the operation is done).
     * @return: void
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
     * Adds a new experiment to database.
     * @param: experiment:Experiment (experiment we want to add).
     * @param: callback:OnOperationDone (The class to call after the operation is done).
     * @return: void
     */
    public void addExperiment(Experiment experiment, OnOperationDone callback) {
        initLocalUserId();
        //TODO handle on add failed?

        // Put into database
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

        // Get document and add to database
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

    /**
     * Updates an existing experiment to database.
     * @param: experimentId:String (experiment we want to add).
     * @param: callback:OnOperationDone (The class to call after the operation is done).
     * @return: void
     */
    public void updateExperiment(Experiment experiment, OnOperationDone callback) {
        initLocalUserId();
        //TODO handle on add failed?

        // Put into database
        Map<String, Object> doc = new HashMap<>();
        doc.put("ExperimentName", experiment.getExperimentName());
        doc.put("OwnerName", experiment.getOwnerName());
        doc.put("ExperimentDescription", experiment.getExperimentDescription());
        doc.put("TrialType", experiment.getTrialType());
        doc.put("Region", experiment.getRegion());
        doc.put("MinimumTrials", experiment.getMinimumTrials());
        doc.put("RequireGeo", experiment.isRequireGeo());
        doc.put("Status", experiment.getStatus());

        // Get document and add to database
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

    /**
     * Updates an existing experiment to database. This is an overloaded method.
     * @param: experimentId:String (experiment we want to add).
     * @param: callback:OnOperationDone (The class to call after the operation is done).
     * @return: void
     */
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
     * Queries the experiments that the given user is subscribed to.
     * @param: userId:String (The user to query experiments for).
     * @param: callback:OnExperimentListReadyListener (We return an ArrayList of Experiment by passing it as parameter of the callback method)
     * @return: void
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
     * Queries the database for all the experiments that belong to the owner.
     * @param: userId:String (The user to query experiments for).
     * @param: callback:OnExperimentListReadyListener (We return an ArrayList of Experiment by passing it as parameter of the callback method)
     * @return: void
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
     * Queries all the experiments that are currently in the database.
     * @param: callback:OnExperimentListReadyListener (We return an ArrayList of Experiment by passing it as parameter of the callback method)
     * @return: void
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
     * This method returns a Experiment object by constructing it using the data from the document snapshot.
     * @param: snapshot:QueryDocumentSnapshot (The Firestore document to retrieve the experiment details from).
     * @return: Experiment (Constructed using info from document).
     */
    private Experiment expFromSnapshot(QueryDocumentSnapshot snapshot) {
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