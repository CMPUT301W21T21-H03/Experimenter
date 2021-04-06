package com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Comment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

public class TrialListAdapter extends RecyclerView.Adapter<TrialListAdapter.ViewHolder> {

    private List<Trial> trials = new ArrayList<>();

    // Constructor
    public TrialListAdapter() {
        super();
    }

    public TrialListAdapter(List<Trial> trials) {
        super();
        this.trials = trials;
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
        String ans;
        if(((BinomialTrial)myTrial).getPass()){
            ans = "Pass";
        }else{
            ans = "False";
        }
        holder.getTrialResult().setText(ans);
        holder.getExperimenterName().setText("Experimenter: "+ myTrial.getTrialOwnerName());
        holder.getTrialDate().setText(myTrial.getTrialDate().toString());
//        holder.getTrialLocation().setText(myTrial.getLocation().toString());

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




        public ViewHolder(View v) {
            super(v);

            trialCard = v.findViewById(R.id.trialItemCard);
            experimenterName = v.findViewById(R.id.experimenterName);
            trialDate = v.findViewById(R.id.trialDate);
            trialLocation = v.findViewById(R.id.trialCity);
            trialResult = v.findViewById(R.id.trialResult);

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
    }
}
