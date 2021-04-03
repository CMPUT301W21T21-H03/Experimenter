package com.DivineInspiration.experimenter.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Talks to firebase
public class TrialManager extends ArrayList<Trial> {

    private static TrialManager singleton;
    private ArrayList<Trial> trials;
    private FirebaseFirestore db;
    private String TAG = "TrialManager";

    // Callback when trials are ready
    public interface OnTrialsReadyListener {
        void onTrialsReady(List<Trial> trials);
    }


    /**
     * Trial Manager constructor
     */
    public TrialManager() {
        this.db = FirebaseFirestore.getInstance();
        this.trials = new ArrayList<>();
    }

    /**
     * Get singleton instance
     * @return
     * experiment manager
     */
    public static TrialManager getInstance() {
        if (singleton == null) {
            singleton = new TrialManager();
        }
        return singleton;
    }

    /**
     * Adds a trial to database
     * @param trial
     * Trial to be added
     */
    public void addTrial(Trial trial) {

        // Store standard trial data
        Map<String, Object> doc = new HashMap<>();
        doc.put("TrialType", trial.getTrialType());
        doc.put("TrialId", trial.getTrialID());
        doc.put("Date", trial.getTrialDate());
        doc.put("OwnerID", trial.getTrialUserID());
        doc.put("ExperimentID", trial.getTrialExperimentID());

        // Store data specific to a trial type
        switch (trial.getTrialType()) {
            case Trial.BINOMIAL: addBinomialTrial((BinomialTrial) trial, doc);
            break;

            case Trial.COUNT: addCountTrial((CountTrial) trial, doc);
            break;

            case Trial.MEASURE: addMeasurementTrial((MeasurementTrial) trial, doc);
            break;

            case Trial.NONNEGATIVE: addNonNegativeTrial((NonNegativeTrial) trial, doc);
        }

        db.collection("Trials").document(trial.getTrialID()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "New trial failed to be committed to database!");
                }
            }
        });
    }

    /**
     * Adds BinomialTrial attributes to a map
     * @param trial
     * The trial whose attributes are being stored
     * @param doc
     * The map containing trial attributes
     */
    private void addBinomialTrial(BinomialTrial trial, Map<String, Object> doc) {
        doc.put("Success", trial.getSuccess());
        doc.put("Failure", trial.getFailure());
    }

    /**
     * Adds CountTrial attributes to a map
     * @param trial
     * The trial whose attributes are being stored
     * @param doc
     * The map containing trial attributes
     */
    private void addCountTrial(CountTrial trial, Map<String, Object> doc) {
        doc.put("Count", trial.getCount());
    }

    /**
     * Adds MeasurementTrial attributes to a map
     * @param trial
     * The trial whose attributes are being stored
     * @param doc
     * The map containing trial attributes
     */
    private void addMeasurementTrial(MeasurementTrial trial, Map<String, Object> doc) {
        doc.put("Measurements", trial.getMeasurements());
    }

    /**
     * Adds NonNegativeTrial attributes to a map
     * @param trial
     * The trial whose attributes are being stored
     * @param doc
     * The map containing trial attributes
     */
    private void addNonNegativeTrial(NonNegativeTrial trial, Map<String, Object> doc) {
        doc.put("Count", trial.getCount());
    }

    /**
     * Gets all trials created by an experimenter
     * @param userId
     * user ID of owner
     * @param callback
     * callback function
     */
    public void getUserTrials(String userId, OnTrialsReadyListener callback) {

        db.collection("Trials").whereEqualTo("OwnerID", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                }
                else {
                    Log.d(TAG, "getUserTrials failed");
                    callback.onTrialsReady(null);
                }
            }
        });
    }

    /**
     * Gets all trials created for an experiment
     * @param experimentId
     * user ID of owner
     * @param callback
     * callback function
     */
    public void getExperimentTrials(String experimentId, OnTrialsReadyListener callback) {

        db.collection("Trials").whereEqualTo("ExperimentID", experimentId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                }
                else {
                    Log.d(TAG, "getExperimentTrials failed");
                    callback.onTrialsReady(null);
                }
            }
        });
    }

    /**
     * Converts data from a QueryDocumentSnapshot into a Trial object
     * @param snapshot
     * The QueryDocumentSnapshot storing the trial data
     * @return
     * A Trial object containing all of the data contained in snapshot.
     */
    private Trial trialFromSnapshot(QueryDocumentSnapshot snapshot) {

        Trial trial = null;
        switch (snapshot.getString("TrialType")) {

            case Trial.BINOMIAL:
                trial = new BinomialTrial(
                        snapshot.getString("TrialId"),
                        snapshot.getDate("Date"),
                        snapshot.getString("OwnerID"),
                        snapshot.getString("ExperimentID"),
                        Math.toIntExact(snapshot.getLong("Success")),
                        Math.toIntExact(snapshot.getLong("Failure"))
                );
                break;

            case Trial.COUNT:
                    trial = new CountTrial(
                    snapshot.getString("TrialId"),
                    snapshot.getDate("Date"),
                    snapshot.getString("OwnerID"),
                    snapshot.getString("ExperimentID"),
                    Math.toIntExact(snapshot.getLong("Count"))
                    );
                    break;

            case Trial.MEASURE:
                trial = new MeasurementTrial(
                        snapshot.getString("TrialId"),
                        snapshot.getDate("Date"),
                        snapshot.getString("OwnerID"),
                        snapshot.getString("ExperimentID"),
                        (ArrayList<Float>) snapshot.get("Measurements")
                );
                break;

            case Trial.NONNEGATIVE:
                trial = new NonNegativeTrial(
                        snapshot.getString("TrialId"),
                        snapshot.getDate("Date"),
                        snapshot.getString("OwnerID"),
                        snapshot.getString("ExperimentID"),
                        Math.toIntExact(snapshot.getLong("Count"))
                );
                break;

            default:
                throw new IllegalArgumentException();
        }

        return trial;
    }
}
