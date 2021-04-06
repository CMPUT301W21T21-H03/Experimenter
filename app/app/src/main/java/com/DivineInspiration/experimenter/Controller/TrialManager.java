package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Talks to firebase
public class TrialManager extends ArrayList<Trial> {

    private static TrialManager singleton;

    private FirebaseFirestore db;
    private String TAG = "TrialManager";
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Callback when trials are ready
    public interface OnTrialListReadyListener {
        void onTrialsReady(List<Trial> trials);
    }

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
     * Get singleton instance
     *
     * @return experiment manager
     */
    public static TrialManager getInstance() {
        if (singleton == null) {
            singleton = new TrialManager();
        }
        return singleton;
    }

    public com.google.firebase.firestore.GeoPoint osmToFireStore(GeoPoint geoPoint) {
        return geoPoint == null ? null : (new com.google.firebase.firestore.GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
    }

    public GeoPoint fireStoreToOsm(com.google.firebase.firestore.GeoPoint geoPoint) {
        return geoPoint == null ? null : (new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
    }

    /**
     * Adds a trial to database
     *
     * @param trial Trial to be added
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
        doc.put("Location", osmToFireStore(trial.getLocation()));

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
     * Gets all trials created by an experimenter
     *
     * @param userId   user ID of owner
     * @param callback callback function
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
     * Gets all trials created for an experiment
     *
     * @param experimentId user ID of owner
     * @param callback     callback function
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
     * Converts data from a QueryDocumentSnapshot into a Trial object
     *
     * @param snapshot The QueryDocumentSnapshot storing the trial data
     * @return A Trial object containing all of the data contained in snapshot.
     */
    private Trial trialFromSnapshot(QueryDocumentSnapshot snapshot) {

        Trial trial = null;
        GeoPoint geoPoint = fireStoreToOsm(snapshot.getGeoPoint("Location"));
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
