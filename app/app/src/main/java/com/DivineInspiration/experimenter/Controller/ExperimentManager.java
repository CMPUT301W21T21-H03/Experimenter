package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.IdGen;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExperimentManager extends ArrayList<Experiment> {
    //Singleton ArrayList
    private static ExperimentManager singleton;
    private ArrayList<Experiment> experiments;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface ExperimentReadyCallback{
         void onExperimentsReady(List<Experiment> experiments);

    }
    private ExperimentManager(){
        experiments = new ArrayList<>();
    }


    public static ExperimentManager getInstance(){
        if(singleton == null){
            singleton = new ExperimentManager();
        }

        return singleton;
    }




    public void unSubFromExperiment(String userId, String experimentId){
        db.collection("Experiments").document(experimentId).update("SubscriberIDs", FieldValue.arrayRemove(userId)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Log.d("stuff", "subbing to experiment failed!(most likely no such experiment exsit)");
                }
            }
        });
    }

    //TODO  handle on sub failed?
    public void subToExperiment(String userId, String experimentId){
        db.collection("Experiments").document(experimentId).update("SubscriberIDs", FieldValue.arrayUnion(userId)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Log.d("stuff", "subbing to experiment failed!(most likely no such experiment exsit)");
                }
            }
        });
    }

    //TODO handle on delete failed?
    public void deleteExperiment(String experimentId){
        db.collection("Experiments").document(experimentId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.d("stuff", "delete experiment failed!(most likely there is no experiment with this id in the database)");
                }
            }
        });
    }

    //TODO handle on add failed?
    public void addExperiment(Experiment experiment) {
        Map<String, Object> doc = new HashMap<>();
        doc.put("ExperimentName", experiment.getExperimentName());
        doc.put("OwnerID", experiment.getOwnerID());
        doc.put("OwnerName", experiment.getOwnerName());
        doc.put("ExperimentDescription", experiment.getExperimentDescription());
        doc.put("TrialType", experiment.getTrialType());
        doc.put("Region", experiment.getRegion());
        doc.put("MinimumTrials", experiment.getMinimumTrials());
        doc.put("RequireGeo", experiment.isRequireGeo());
        doc.put("SubscriberIDs", new ArrayList<>());

        db.collection("Experiments").document(experiment.getExperimentID()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d("stuff", "add new experiment failed!");
                }
            }
        });
    }

    public void querySearch(String keywords, ExperimentReadyCallback callback){
        //TODO to be implemented
        callback.onExperimentsReady(null);
    }

    public void queryUserSubs(String userId, ExperimentReadyCallback callback){
        db.collection("Experiments").whereArrayContains("SubscriberIDs", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(callback!=null){
                        List<Experiment> output = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot: task.getResult()){
                            output.add(expFromSnapshot(snapshot));
                        }
                        callback.onExperimentsReady(output);
                    }
                }
                else{
                    Log.d("stuff", "query user subscriptions failed!");
                    callback.onExperimentsReady(null);
                }
            }
        });
    }

    public void queryUserExperiment(String userId, ExperimentReadyCallback callback){
        db.collection("Experiments").whereEqualTo("OwnerID", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(callback!=null){
                        List<Experiment> output = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot: task.getResult()){
                            output.add(expFromSnapshot(snapshot));
                        }
                        callback.onExperimentsReady(output);
                    }
                }
                else{
                    Log.d("stuff", "query user subscriptions failed!");
                    callback.onExperimentsReady(null);
                }
            }
        });
    }

    public void queryAll(ExperimentReadyCallback callback) {
        db.collection("Experiments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(callback!=null){
                        List<Experiment> output = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot: task.getResult()){
                            output.add(expFromSnapshot(snapshot));
                        }
                        callback.onExperimentsReady(output);
                    }
                }
                else{
                    Log.d("stuff", "query user subscriptions failed!");
                    callback.onExperimentsReady(null);
                }
            }
        });    }

    private Experiment expFromSnapshot(QueryDocumentSnapshot snapshot){
        return new Experiment(
                snapshot.getId(),
                snapshot.getString("ExperimentName"),
                snapshot.getString("OwnerID"),
                snapshot.getString("OwnerName"),
                snapshot.getString("ExperimentDescription"),
                Objects.requireNonNull(snapshot.getLong("TrialType")).intValue(),
                snapshot.getString("Region"),
                Objects.requireNonNull(snapshot.getLong("MinimumTrials")).intValue(),
                snapshot.getBoolean("RequireGeo")

        );

    }



//    public ArrayList<Experiment> getExperiments1(){
//
//        Task<QuerySnapshot> queryTask = db.collection("Experiments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//<<<<<<< Updated upstream
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "Success retrieving documents");
//                } else {
//                    Log.d(TAG, "Error retrieving documents", task.getException());
//=======
//                if(task.isSuccessful()){
//                    if(callback!=null){
//                        List<Experiment> output = new ArrayList<>();
//                        for (QueryDocumentSnapshot snapshot: task.getResult()){
//                            output.add(expFromSnapshot(snapshot));
//                        }
//                        callback.onExperimentsReady(output);
//                    }
//                }
//                else{
//                    Log.d("stuff", "query user experiments failed!");
//>>>>>>> Stashed changes
//                }
//            }
//        });
//
//        while(!queryTask.isComplete()) { }
//
//        QuerySnapshot query = queryTask.getResult();
//        List<DocumentSnapshot> queryData = query.getDocuments();
//
//        experiments.clear();
//        for (DocumentSnapshot doc : queryData) {
//
//            String name = doc.get("Name").toString();
//            String oID = doc.get("OwnerID").toString();
//            String description = doc.get("Description").toString();
//            String eID = doc.get("ExperimentID").toString();
//
//            Task<DocumentSnapshot> userQueryTask = db.collection("Users").document(oID).get();
//            while(!userQueryTask.isComplete()) { }
//
//            DocumentSnapshot userQuery = userQueryTask.getResult();
//
//            String username = userQuery.get("UserName").toString();
//            String userDescription = userQuery.get("UserDescription").toString();
//            String address = userQuery.get("Address").toString();
//            String city = userQuery.get("CityName").toString();
//            String email = userQuery.get("Email").toString();
//            String phone = userQuery.get("PhoneNumber").toString();
//
//            UserContactInfo contactInfo = new UserContactInfo(city, email);
//            User owner = new User(username, oID, contactInfo, userDescription);
//
//            Experiment experiment = new Experiment(name, owner, description, eID);
//        }
//
//        return experiments;
//    }
//

//    /**
//    * Adds an experiment to the database
//    * @param experiment
//    * @return
//     *      returns true if the experiment is added successfully
//     *      returns false if the experiment is not added successfully
//     */
//    public boolean addExperiment1(Experiment experiment) {
//
//        Log.d(TAG, "addExperiment: Working");
//
//        boolean successful = true;
//        Map<String, Object> doc = new HashMap<>();
//        doc.put("Name", experiment.getExperimentName());
//        doc.put("OwnerID", experiment.getExperimentOwner().getUserId());
//        doc.put("ExperimentID", experiment.getExperimentID());
//        doc.put("Description", experiment.getExperimentDescription());
//        Map<String, Object> Subscribers = new HashMap<>();
//        Subscribers.put("0", experiment.getExperimentOwner().getUserId() );
//
//        Log.d(TAG, "working 2");
//
//        db.collection("Experiments")
//                    .document(experiment.getExperimentID())
//                    .set(doc)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "working 4");
//                        }
//                    });
//
//
//            return true;
//    }
//
//    /**
//     * Checks if an experiment is in the database. Searches by id
//     * @param experiment
//     * @return
//     *      returns true if that experiment exists in the database
//     *      returns false if the experiment does not exist in the database
//     */
//    public boolean hasExperiment1(Experiment experiment) {
//
//        String experimentID = experiment.getExperimentID();
//        Task<DocumentSnapshot> queryTask = db.collection("Experiments").document(experimentID).get();
//
//        while(!queryTask.isComplete()) { }
//
//        return queryTask.isSuccessful();
//    }
//
//    /**
//     * Checks if an experiment is in the database. Searches by id
//     * @param experimentID
//     * @return
//     *      returns true if that experiment exists in the database
//     *      returns false if the experiment does not exist in the database
//     */
//    public boolean hasExperiment1(String experimentID) {
//        Log.d(TAG, "working Has experiment");
//        Task<DocumentSnapshot> queryTask = db.collection("Experiments").document(experimentID).get();
//        Log.d(TAG, "working Has experiment Task");
//        while(!queryTask.isComplete()) { }
//
//        Log.d(TAG, "working Has experiment2");
//        return queryTask.isSuccessful();
//    }
//=======
//    private Experiment expFromSnapshot(QueryDocumentSnapshot snapshot){
//        return new Experiment(
//               snapshot.getId(),
//                snapshot.getString("ExperimentName"),
//                snapshot.getString("OwnerID"),
//                snapshot.getString("ExperimentDescription"),
//                Objects.requireNonNull(snapshot.getLong("TrialType")).intValue(),
//                snapshot.getString("Region"),
//                Objects.requireNonNull(snapshot.getLong("MinimumTrials")).intValue()
//        );
//    }
//
//    public static ExperimentManager getInstance(){
//        if(singleton == null){
//            singleton = new ExperimentManager();
//        }
//
//        return singleton;
//    }
//
//





//    public ArrayList<Experiment> getExperiments1(){
//
//        Task<QuerySnapshot> queryTask = db.collection("Experiments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "Success retrieving documents");
//                } else {
//                    Log.d(TAG, "Error retrieving documents", task.getException());
//                }
//            }
//        });
//
//        while(!queryTask.isComplete()) { }
//
//        QuerySnapshot query = queryTask.getResult();
//        List<DocumentSnapshot> queryData = query.getDocuments();
//
//        experiments.clear();
//        for (DocumentSnapshot doc : queryData) {
//
//            String name = doc.get("Name").toString();
//            String oID = doc.get("OwnerID").toString();
//            String description = doc.get("Description").toString();
//            String eID = doc.get("ExperimentID").toString();
//
//            Task<DocumentSnapshot> userQueryTask = db.collection("Users").document(oID).get();
//            while(!userQueryTask.isComplete()) { }
//
//            DocumentSnapshot userQuery = userQueryTask.getResult();
//
//            String username = userQuery.get("UserName").toString();
//            String userDescription = userQuery.get("UserDescription").toString();
//            String address = userQuery.get("Address").toString();
//            String city = userQuery.get("CityName").toString();
//            String email = userQuery.get("Email").toString();
//            String phone = userQuery.get("PhoneNumber").toString();
//
//            UserContactInfo contactInfo = new UserContactInfo(city, email);
//            User owner = new User(username, oID, contactInfo, userDescription);
//
//            Experiment experiment = new Experiment(name, owner, description, eID);
//        }
//
//        return experiments;
//    }
//
//    public static ExperimentManager getInstance(){
//        if(singleton == null){
//            singleton = new ExperimentManager();
//        }
//
//        return singleton;
//    }
//
//    /**
//    * Adds an experiment to the database
//    * @param experiment
//    * @return
//     *      returns true if the experiment is added successfully
//     *      returns false if the experiment is not added successfully
//     */
//    public boolean addExperiment1(Experiment experiment) {
//
//        Log.d(TAG, "addExperiment: Working");
//
//        boolean successful = true;
//        Map<String, Object> doc = new HashMap<>();
//        doc.put("Name", experiment.getExperimentName());
//        doc.put("OwnerID", experiment.getExperimentOwner().getUserId());
//        doc.put("ExperimentID", experiment.getExperimentID());
//        doc.put("Description", experiment.getExperimentDescription());
//        Map<String, Object> Subscribers = new HashMap<>();
//        Subscribers.put("0", experiment.getExperimentOwner().getUserId() );
//
//        Log.d(TAG, "working 2");
//
//        db.collection("Experiments")
//                    .document(experiment.getExperimentID())
//                    .set(doc)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "working 4");
//                        }
//                    });
//
//
//            return true;
//    }
//
//    /**
//     * Checks if an experiment is in the database. Searches by id
//     * @param experiment
//     * @return
//     *      returns true if that experiment exists in the database
//     *      returns false if the experiment does not exist in the database
//     */
//    public boolean hasExperiment1(Experiment experiment) {
//
//        String experimentID = experiment.getExperimentID();
//        Task<DocumentSnapshot> queryTask = db.collection("Experiments").document(experimentID).get();
//
//        while(!queryTask.isComplete()) { }
//
//        return queryTask.isSuccessful();
//    }
//
//    /**
//     * Checks if an experiment is in the database. Searches by id
//     * @param experimentID
//     * @return
//     *      returns true if that experiment exists in the database
//     *      returns false if the experiment does not exist in the database
//     */
//    public boolean hasExperiment1(String experimentID) {
//        Log.d(TAG, "working Has experiment");
//        Task<DocumentSnapshot> queryTask = db.collection("Experiments").document(experimentID).get();
//        Log.d(TAG, "working Has experiment Task");
//        while(!queryTask.isComplete()) { }
//
//        Log.d(TAG, "working Has experiment2");
//        return queryTask.isSuccessful();
//    }

//    /**
//     * Checks if an experiment is in the database and if it is it deletes it
//     * @param experiment
//     */
//    public void deleteExperiment(Experiment experiment) {
//        if (hasExperiment(experiment) == false) {
//            throw new IllegalArgumentException();
//        }
//        db.collection("Experiments").document(experiment.getExperimentID())
//                .delete().addOnSuccessListener(new OnSuccessListener < Void > () {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "Experiment has been Deleted Successfully");
//            }
//        });
//
//    }
//
//    public Experiment getExperiment(String experimentID) {
//
//        Experiment experiment = null;
//
//        if (hasExperiment(experimentID)) {
//
//            Task<DocumentSnapshot> queryTask = db.collection("Experiments").document(experimentID).get();
//            while(!queryTask.isComplete()) { }
//
//            DocumentSnapshot query = queryTask.getResult();
//
//            String name = query.get("Name").toString();
//            String oID = query.get("OwnerID").toString();
//            String description = query.get("Description").toString();
//            String eID = query.get("ExperimentID").toString();
//
//            queryTask = db.collection("Users").document(oID).get();
//            while(!queryTask.isComplete()) { }
//
//            query = queryTask.getResult();
//
//            String username = query.get("UserName").toString();
//            String userDescription = query.get("UserDescription").toString();
//            String address = query.get("Address").toString();
//            String city = query.get("CityName").toString();
//            String email = query.get("Email").toString();
//            String phone = query.get("PhoneNumber").toString();
//
//            UserContactInfo contactInfo = new UserContactInfo(city, email);
//            User owner = new User(username, oID, contactInfo, userDescription);
//            experiment = new Experiment(name, owner, description, eID);
//        }
//
//        return experiment;
//    }
}
