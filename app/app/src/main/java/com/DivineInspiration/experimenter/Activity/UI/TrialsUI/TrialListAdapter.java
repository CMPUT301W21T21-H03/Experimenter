package com.DivineInspiration.experimenter.Activity.UI.TrialsUI;

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
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.firebase.firestore.GeoPoint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom RecyclerView Adapter class. Displays a list of trials. Used by
 * {@link com.DivineInspiration.experimenter.Activity.UI.TrialsUI.TrialsTabFragment}
 * to create display trials.
 * @see <a href="https://developer.android.com/guide/topics/ui/layout/recyclerview"> https://developer.android.com/guide/topics/ui/layout/recyclerview </a>
 */
public class TrialListAdapter extends RecyclerView.Adapter<TrialListAdapter.ViewHolder> {

    private List<Trial> trials = new ArrayList<>();
    private TrialManager.OnTrialListReadyListener callback;
    private Experiment experiment;
    private User localUser;

    // Constructor
    public TrialListAdapter() {
        super();
    }

    /**
     * Constructor
     * @param trials
     * list of trials
     * @param callback
     * callback function for when it is done
     * @param experiment
     * the experiment it belongs to
     */
    public TrialListAdapter(List<Trial> trials, TrialManager.OnTrialListReadyListener callback, Experiment experiment) {
        super();

        this.trials = trials;
        this.callback = callback;
        localUser = UserManager.getInstance().getLocalUser();
        this.experiment = experiment;
    }

    /**
     * When view is created
     * @param parent
     * parent if adapter
     * @param viewType
     * type of view
     * @return
     * view holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial_item, parent, false);
        return new TrialListAdapter.ViewHolder(v);
    }

    /**
     * on view bind holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trial myTrial = trials.get(position);
        String value = null;
        String type = myTrial.getTrialType();

        switch (type) {
            case Trial.BINOMIAL:
                if (((BinomialTrial) myTrial).getPass()) {
                    value = "Success";
                } else {
                    value = "Fail";
                }
                break;

            case Trial.COUNT:
                value = String.valueOf(((CountTrial) myTrial).getCount());
                break;

            case Trial.NONNEGATIVE:
                value = String.valueOf(((NonNegativeTrial) myTrial).getCount());
                break;

            case Trial.MEASURE:
                value = String.valueOf(((MeasurementTrial) myTrial).getValue());
                break;

            default:
                break;
        }

        holder.getTrialResult().setText("Result: " + value + (myTrial.isIgnored() ? " - Ignored" : ""));
        holder.getExperimenterName().setText("Experimenter: " + myTrial.getTrialOwnerName());
        holder.getTrialDate().setText(myTrial.getTrialDate().toString());

        holder.getTrialCard().setOnClickListener(v -> {

            Button banButton = holder.getBanButton();
            if (banButton.getVisibility() == View.GONE && localUser.getUserId().equals(experiment.getOwnerID())) {
                banButton.setVisibility(View.VISIBLE);
            } else if (banButton.getVisibility() == View.VISIBLE) {
                banButton.setVisibility(View.GONE);
            }

            banButton.setOnClickListener(v1 -> {
                ExperimentManager.getInstance().banUserFromExperiment(myTrial.getTrialUserID(), myTrial.getTrialExperimentID(), done -> instantBanUpdate(myTrial.getTrialUserID()));
            });
        });

        if (myTrial.getLocation() != null) {

            GeoPoint geoPoint = TrialManager.getInstance().latLngToGeoPoint(myTrial.getLocation());
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            String LAT = decimalFormat.format(geoPoint.getLatitude());
            String LONG = decimalFormat.format(geoPoint.getLongitude());
            holder.getTrialLocation().setText("Location: " + LAT + " , " + LONG);
        }
        else {
            holder.getTrialLocation().setText("");
        }
    }

    /**
     * Bans the user of ID
     * @param bannedId
     * user to ban
     */
    private void instantBanUpdate(String bannedId) {
        List<Trial> updatedTrials = new ArrayList<>();
        for (Trial t : trials) {
            if (t.getTrialUserID().equals(bannedId)) {
                t.setIgnored(true);
            }
            updatedTrials.add(t);
        }

        callback.onTrialsReady(updatedTrials);
    }

    /**
     * Get number of items
     * @return
     * number of items
     */
    @Override
    public int getItemCount() {
        return trials.size();
    }

    /**
     * Resets the adapter with a new list
     * @param trials
     * Trials to update list with
     */
    public void setTrials(List<Trial> trials) {
        this.trials.clear();
        this.trials.addAll(trials);
        notifyDataSetChanged();
    }

    /**
     * Each card
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // card and text
        private final CardView trialCard;
        private final TextView experimenterName;
        private final TextView trialDate;
        private final TextView trialLocation;
        private final TextView trialResult;
        private final Button banButton;

        /**
         * View holder
         * @param v
         * view
         */
        public ViewHolder(View v) {
            super(v);

            trialCard = v.findViewById(R.id.trialItemCard);
            experimenterName = v.findViewById(R.id.experimenterName);
            trialDate = v.findViewById(R.id.trialDate);
            trialLocation = v.findViewById(R.id.trialCity);
            trialResult = v.findViewById(R.id.trialResult);
            banButton = v.findViewById(R.id.trialBan);
        }

        /**
         * Get the card
         * @return
         * card
         */
        public CardView getTrialCard() {
            return trialCard;
        }

        /**
         * Get the experiment name
         * @return
         * name of experiment
         */
        public TextView getExperimenterName() {
            return experimenterName;
        }

        /**
         * Get the trial date
         * @return
         * date of trial as text view
         */
        public TextView getTrialDate() {
            return trialDate;
        }

        /**
         * Gets lcoation of trial
         * @return
         * location
         */
        public TextView getTrialLocation() {
            return trialLocation;
        }

        /**
         * Gets the results of the trial
         * @return
         * result text view
         */
        public TextView getTrialResult() {
            return trialResult;
        }

        /**
         * Gets the ban button
         * @return
         * button that bans
         */
        public Button getBanButton() {
            return banButton;
        }
    }
}
