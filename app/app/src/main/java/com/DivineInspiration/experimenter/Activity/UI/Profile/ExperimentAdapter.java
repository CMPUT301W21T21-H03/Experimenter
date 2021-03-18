package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ViewHolder> {

    /*
    https://developer.android.com/guide/topics/ui/layout/recyclerview
     */

    private List<Experiment> experiments = new ArrayList<>();

    public ExperimentAdapter(){}
    public ExperimentAdapter(List<Experiment> exp){
        this.experiments = exp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(experiments.get(position).getExperimentName());
    }

    @Override
    public int getItemCount() {
        return experiments.size();
    }



    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private final TextView text;


        public ViewHolder (View v){
            super(v);

            text = v.findViewById(R.id.expListTitle);
        }

        public TextView getTextView() {
            return text;
        }
    }

}
