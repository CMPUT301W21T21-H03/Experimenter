package com.DivineInspiration.experimenter.Activity.UI.Explore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ExploreListAdapter extends RecyclerView.Adapter<ExploreListAdapter.ViewHolder> {

    /*
    https://developer.android.com/guide/topics/ui/layout/recyclerview
     */

    private List<Experiment> experiments = new ArrayList<>();

    public ExploreListAdapter(){}
    public ExploreListAdapter(List<Experiment> exp){
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
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Red is kinda Sus
                Bundle bundle = new Bundle();
                bundle.putInt("lol",position);
                Navigation.findNavController(v).navigate(R.id.homeToExp, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return experiments.size();
    }

    public void setData(List<Experiment> experimentData) {
        experiments.clear();
        experiments.addAll(experimentData);
        notifyDataSetChanged();

        Log.d("ExperimentAdapter", "New Data -> " + experiments.toString());
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        private final TextView text;
        private final CardView card;

        public ViewHolder(View v) {
            super(v);

            text = v.findViewById(R.id.expListTitle);
            card = v.findViewById(R.id.experimentItemCard);
        }

        public TextView getTextView() {
            return text;
        }

        public CardView getCardView() {
            return card;
        }
    }
}
