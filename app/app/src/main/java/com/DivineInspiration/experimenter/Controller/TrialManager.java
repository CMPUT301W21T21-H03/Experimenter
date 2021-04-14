package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class talks to the FireStore database
 * in order to store and retrieve trial data.
 * The class uses singleton pattern.
 */
public class TrialManager extends ArrayList<Trial> {

    private static TrialManager singleton;

    private FirebaseFirestore db;
    private String TAG = "TrialManager";
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Interface definition for a callback to be invoked when {@link TrialManager} successfully
     * queries a list of {@link Trial} from Firestore
     */
    public interface OnTrialListReadyListener {

        /**
         * Called when {@link TrialManager} successfully queries a list of {@link Trial}
         * from Firestore
         * @param trials
         * The queried trials
         */
        void onTrialsReady(List<Trial> trials);
    }

    /**
     * Interface definition for a callback to be invoked when {@link TrialManager} successfully
     * queries an {@link Trial} from Firestore
     */
    public interface OnTrialReadyListener {
        /**
         * Called when {@link TrialManager} successfully queries an {@link Trial}
         * from Firestore
         * @param trials
         * The queried experiment
         */
        void onTrialReady(Trial trials);
    }

    /**
     * Trial manager constructor
     */
    public TrialManager() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Get singleton instance of the class
     * @return singleton instance
     */
    public static TrialManager getInstance() {
        if (singleton == null) {
            singleton = new TrialManager();
        }
        return singleton;
    }

    /**
     * Convert lat-lng to geo point
     * @param latLng
     * lat-lng form
     * @return
     * geo point form
     */
    public GeoPoint latLngToGeoPoint(LatLng latLng) {
        return latLng == null ? null : (new GeoPoint(latLng.latitude, latLng.longitude));
    }

    /**
     * Convert geo point to lat-lng
     * @param geoPoint
     * geo point form
     * @return
     * lat-lng form
     */
    public LatLng geoPointToLatLng(GeoPoint geoPoint) {
        return geoPoint == null ? null : (new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()));
    }

    /**
     * Filters the ignored trials
     * @param listToFilter
     * the list to be filtered
     * @return
     * filtered list
     */
    public List<Trial> filterIgnoredTrials(List<Trial> listToFilter){
        return listToFilter.stream().filter(trial -> !trial.isIgnored()).collect(Collectors.toList());
    }

    /**
     * Deleting a trial
     * @param trialId the trial ID to be deleted
     */
    public void deleteTrial(String trialId){
        db.collection("Trials").document(trialId).delete();
    }

    /**
     * Deletes all trials for the given experiment
     * @param experimentId the trial ID to be deleted
     */
    public void deleteAllTrialOfExperiment(String experimentId){
        db.collection("Trials")
                .whereEqualTo("ExperimentID", experimentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snap : task.getResult()) {
                                snap.getReference().delete();
                            }
                        }
                    }
                });
    }

    /**
     * Adds a new trial to database
     * @param trial trial we want to add
     * @param callback the class to call after the operation is done
     */
    public void addTrial(Trial trial, OnTrialReadyListener callback) {
        // Store standard trial data
        Map<String, Object> doc = new HashMap<>();
        doc.put("TrialType", trial.getTrialType());
        doc.put("TrialId", trial.getTrialID());
        doc.put("Date", df.format(trial.getTrialDate()));
        doc.put("OwnerID", trial.getTrialUserID());
        doc.put("OwnerName",trial.getTrialOwnerName());
        doc.put("ExperimentID", trial.getTrialExperimentID());
        doc.put("Location", latLngToGeoPoint(trial.getLocation()));

        // Store data specific to a trial type
        switch (trial.getTrialType()) {
            case Trial.BINOMIAL:
                doc.put("Pass", ((BinomialTrial) trial).getPass());
                break;

            case Trial.COUNT:
                doc.put("Count", ((CountTrial) trial).getCount());
                break;

            case Trial.MEASURE:
                doc.put("Value", ((MeasurementTrial) trial).getValue());
                break;

            case Trial.NONNEGATIVE:
                doc.put("Count", ((NonNegativeTrial) trial).getCount());
        }

        db.collection("Trials")
                .document(trial.getTrialID())
                .set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "New trial failed to be committed to database!");
                        } else {
                            callback.onTrialReady(trial);
                        }
                    }
                });
    }

    /**
     * Queries all the trials that the given user has performed.
     * @param userId the user to query trials for
     * @param callback the class to call after the operation is done
     */
    public void getUserTrials(String userId, OnTrialListReadyListener callback) {

        db.collection("Trials")
                .whereEqualTo("OwnerID", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (callback != null) {
                                List<Trial> output = new ArrayList<>();
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    output.add(trialFromSnapshot(snapshot));
                                }
                                callback.onTrialsReady(output);
                                Log.d(TAG, "getUserTrials successful" + output.size());
                            }
                        } else {
                            Log.d(TAG, "getUserTrials failed");
                            callback.onTrialsReady(null);
                        }
                    }
                });
    }

    /**
     * Queries the trials that performed for a given experiment
     * @param experimentId The user to query trials for
     * @param callback  The class to call after the operation is done.
     *         The data is passed as a parameter of this method.
     */
    public void queryExperimentTrials(String experimentId, OnTrialListReadyListener callback) {
        db.collection("BlackList")
                .document(experimentId)
                .get()
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
             List<String> bannedIds = (List<String>) task.getResult().get("BannedIds");

                HashSet<String> bannedIdSet = new HashSet<>();
                if(bannedIds != null){
                    bannedIdSet.addAll(bannedIds);
                }
                db.collection("Trials").whereEqualTo("ExperimentID", experimentId).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (callback != null) {
                                        List<Trial> output = new ArrayList<>();
                                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                            Trial t = trialFromSnapshot(snapshot);
                                            if(bannedIds != null && bannedIdSet.contains(t.getTrialUserID())){
                                                t.setIgnored(true);
                                            }
                                            output.add(t);
                                        }
                                        callback.onTrialsReady(output);
                                        Log.d(TAG, "getExperimentTrials successful");
                                    }
                                } else {
                                    Log.d(TAG, "getExperimentTrials failed");
                                    callback.onTrialsReady(null);
                                }
                            }
                        });
            }
        });

    }

    /**
     * This method returns a Trial object by constructing it using the data from the document snapshot.
     * @param snapshot the Firestore document to retrieve the trial details from
     * @return Trial object constructed using info from document
     */
    private Trial trialFromSnapshot(QueryDocumentSnapshot snapshot) {

        Trial trial = null;
        LatLng geoPoint = geoPointToLatLng(snapshot.getGeoPoint("Location"));
        switch (snapshot.getString("TrialType")) {
            case Trial.BINOMIAL:
                trial = new BinomialTrial(snapshot.getString("TrialId"),
                        snapshot.getString("OwnerID"), snapshot.getString("OwnerName"),snapshot.getString("ExperimentID"), LocalDate.parse(snapshot.getString("Date")),
                        snapshot.getBoolean("Pass"), geoPoint);
                break;
            case Trial.COUNT:
                trial = new CountTrial(snapshot.getString("TrialId"),
                        snapshot.getString("OwnerID"),snapshot.getString("OwnerName") ,snapshot.getString("ExperimentID"), LocalDate.parse(snapshot.getString("Date")),
                        snapshot.getLong("Count").intValue(), geoPoint);
                break;
            case Trial.MEASURE:
                trial = new MeasurementTrial(snapshot.getString("TrialId"),
                        snapshot.getString("OwnerID"),snapshot.getString("OwnerName") ,snapshot.getString("ExperimentID"), LocalDate.parse(snapshot.getString("Date")),
                        snapshot.getDouble("Value"), geoPoint);
                break;
            case Trial.NONNEGATIVE:
                trial = new NonNegativeTrial(snapshot.getString("TrialId"),
                        snapshot.getString("OwnerID"),snapshot.getString("OwnerName") ,snapshot.getString("ExperimentID"), LocalDate.parse(snapshot.getString("Date")),
                        snapshot.getLong("Count").intValue(), geoPoint);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return trial;
    }
}