package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

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
import java.util.List;
import java.util.Map;

/**
 * This class talks to the Firestore database
 * in order to store and retrieve trial data.
 * The class uses singleton pattern.
 */
public class TrialManager extends ArrayList<Trial> {

    private static TrialManager singleton;

    private FirebaseFirestore db;
    private String TAG = "TrialManager";
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * When trial data is retrieved from database is ready,
     * it is passed along as a parameter by the interface method.
     * Utilized for: getUserTrials, queryExperimentTrials
     */
    public interface OnTrialListReadyListener {
        void onTrialsReady(List<Trial> trials);
    }

    /**
     * When trial datum is retrieved from database is ready,
     * it is passed along as a parameter by the interface method.
     * Utilized for: addTrial
     */
    public interface OnTrialReadyListener {
        void onTrialReady(Trial trials);
    }

    /**
     * Trial Manager constructor
     */
    public TrialManager() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Get singleton instance of the class
     * @return: singleton:TrialManager
     */
    public static TrialManager getInstance() {
        if (singleton == null) {
            singleton = new TrialManager();
        }
        return singleton;

    }

    public GeoPoint latLngToGeoPoint(LatLng latLng) {
        return latLng == null ? null : (new GeoPoint(latLng.latitude, latLng.longitude));
    }

    public LatLng geoPointToLatLng(GeoPoint geoPoint) {
        return geoPoint == null ? null : (new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()));
    }

    /**
     * Adds a new trial to database
     * @param: trial:Trial (trial we want to add).
     * @param: callback:OnTrialReadyListener (The class to call after the operation is done).
     * @return: void
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

        db.collection("Trials").document(trial.getTrialID()).set(doc)
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
     * Queries the trials that the given user is performed.
     * @param: userId:String (The user to query trials for).
     * @param: callback:OnTrialReadyListener (The class to call after the operation is done).
     * @return: void
     */
    public void getUserTrials(String userId, OnTrialListReadyListener callback) {

        db.collection("Trials").whereEqualTo("OwnerID", userId).get()
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
                                Log.d(TAG, "getUserTrials successful");
                            }
                        } else {
                            Log.d(TAG, "getUserTrials failed");
                            callback.onTrialsReady(null);
                        }
                    }
                });
    }

    /**
     * Queries the trials that performed for a given experiment.
     * @param: experimentId:String (The user to query trials for).
     * @param: callback:OnTrialReadyListener (The class to call after the operation is done).
     *         The data is passed as a parameter of this method.
     * @return: void
     */
    public void queryExperimentTrials(String experimentId, OnTrialListReadyListener callback) {
        db.collection("Trials").whereEqualTo("ExperimentID", experimentId).get()
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
                                Log.d(TAG, "getExperimentTrials successful");
                            }
                        } else {
                            Log.d(TAG, "getExperimentTrials failed");
                            callback.onTrialsReady(null);
                        }
                    }
                });
    }

    /**
     * This method returns a Trial object by constructing it using the data from the document snapshot.
     * @param: snapshot:QueryDocumentSnapshot (The Firestore document to retrieve the trial details from).
     * @return: :Trial (Constructed using info from document).
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