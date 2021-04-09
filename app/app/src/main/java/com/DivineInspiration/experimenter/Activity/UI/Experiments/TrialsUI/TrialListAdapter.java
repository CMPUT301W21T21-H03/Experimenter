package com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.google.firebase.firestore.GeoPoint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TrialListAdapter extends RecyclerView.Adapter<TrialListAdapter.ViewHolder> {

    private List<Trial> trials = new ArrayList<>();
    private TrialManager.OnTrialListReadyListener callback;

    // Constructor
    public TrialListAdapter() {
        super();
    }

    public TrialListAdapter(List<Trial> trials, TrialManager.OnTrialListReadyListener callback) {
        super();
        Log.d("woah trial adapter", "" +   trials.size());
        this.trials=trials;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial_item, parent, false);
        return new TrialListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trial myTrial = trials.get(position);
        String ans = "";
        String type = myTrial.getTrialType();
        switch (type) {
            case "Binomial trial":
                if (((BinomialTrial) myTrial).getPass()) {
                    ans = "Pass";
                } else {
                    ans = "False";
                }
                break;
            case "Count trial":
                ans = String.valueOf(((CountTrial) myTrial).getCount());
                break;
            case "Non-Negative trial":
                ans = String.valueOf(((NonNegativeTrial) myTrial).getCount());
                break;
            case "Measurement trial":
                ans = String.valueOf(((MeasurementTrial) myTrial).getValue());
                break;
            default:
                break;
        }


        holder.getTrialResult().setText("Result: " + ans + (myTrial.isIgnored() ? " - Ignored" : ""));
        holder.getExperimenterName().setText("Experimenter: " + myTrial.getTrialOwnerName());
        holder.getTrialDate().setText(myTrial.getTrialDate().toString());
        holder.getTrialCard().setOnClickListener(v -> {
            holder.getBanButton().setVisibility(View.VISIBLE);
            holder.getBanButton().setOnClickListener(v1 -> {
                holder.getBanButton().setVisibility(View.GONE);
                ExperimentManager.getInstance().banUserFromExperiment(myTrial.getTrialUserID(), myTrial.getTrialExperimentID(), done -> instantBanUpdate(myTrial.getTrialUserID()));
            });
        });

        if(myTrial.getLocation()!=null){
            GeoPoint geoPoint = TrialManager.getInstance().latLngToGeoPoint(myTrial.getLocation());
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            String LAT = decimalFormat.format(geoPoint.getLatitude());
            String LONG = decimalFormat.format(geoPoint.getLongitude());
            holder.getTrialLocation().setText("Location: " + LAT + " , " + LONG);
        }else{

        }


    }

    private void instantBanUpdate(String bannedId) {
        Log.d("woah", "instant updating");
        List<Trial> updatedTrials = new ArrayList<>();
        for (Trial t : trials) {
            if (t.getTrialUserID().equals(bannedId)) {
                t.setIgnored(true);
            }
            updatedTrials.add(t);
        }
        Log.d("woah instantUpdate", ""+trials.size());
        callback.onTrialsReady(updatedTrials);
    }


    @Override
    public int getItemCount() {
        return trials.size();
    }

    public void setTrials(List<Trial> trials) {
        this.trials.clear();
        this.trials.addAll(trials);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // card and text
        private final CardView trialCard;
        private final TextView experimenterName;
        private final TextView trialDate;
        private final TextView trialLocation;
        private final TextView trialResult;


        private final Button banButton;

        public ViewHolder(View v) {
            super(v);

            trialCard = v.findViewById(R.id.trialItemCard);
            experimenterName = v.findViewById(R.id.experimenterName);
            trialDate = v.findViewById(R.id.trialDate);
            trialLocation = v.findViewById(R.id.trialCity);
            trialResult = v.findViewById(R.id.trialResult);
            banButton = v.findViewById(R.id.trialBan);

        }

        public CardView getTrialCard() {
            return trialCard;
        }

        public TextView getExperimenterName() {
            return experimenterName;
        }

        public TextView getTrialDate() {
            return trialDate;
        }

        public TextView getTrialLocation() {
            return trialLocation;
        }

        public TextView getTrialResult() {
            return trialResult;
        }

        public Button getBanButton() {
            return banButton;
        }
    }
}
