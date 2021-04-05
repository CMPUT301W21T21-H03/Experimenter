package com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Model.Comment;
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
    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public void setTrials(List<Trial> trials) {
        this.trials.clear();
        this.trials.addAll(trials);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // card and text

        public ViewHolder(View v) {
            super(v);

        }
    }
}
