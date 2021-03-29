package com.DivineInspiration.experimenter.Activity.UI.Profile;

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


public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ViewHolder> {

    /*
    https://developer.android.com/guide/topics/ui/layout/recyclerview
     */

    // experiment list
    private List<Experiment> experiments = new ArrayList<>();

    /**
     * Constructor
     */
    public ExperimentAdapter() {}

    /**
     * Constructor
     * @param exp
     * list of experiments
     */
    public ExperimentAdapter(List<Experiment> exp){
        this.experiments = exp;
    }

    /**
     * On create
     * @param parent
     * @param viewType
     * @return
     * view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiment_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * On bind view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getTextView().setText(experiments.get(position).getExperimentName());
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            // when card is clicked it ...?
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("experiment", experiments.get(position));
                for (Experiment exp : experiments){
                    Log.d("woah", exp.toString());
                }
                Navigation.findNavController(v).navigate(R.id.homeToExp, bundle);
            }
        });
    }

    /**
     * Gets the number of items
     * @return
     */
    @Override
    public int getItemCount() {
        return experiments.size();
    }

    /**
     * Sets data
     * @param experimentData
     */
    public void setData(List<Experiment> experimentData) {
        // clear and add all data to adapter
        experiments.clear();
        experiments.addAll(experimentData);
        // notify changes such that screen refreshes
        notifyDataSetChanged();
    }

    /**
     * Recycler for experiments
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // card and text
        private final TextView text;
        private final CardView card;

        public ViewHolder(View v) {
            super(v);

            text = v.findViewById(R.id.expListTitle);
            card = v.findViewById(R.id.experimentItemCard);
        }

        /**
         * Get text
         * @return
         * text
         */
        public TextView getTextView() {
            return text;
        }

        /**
         * Get card
         * @return
         * card view
         */
        public CardView getCardView() {
            return card;
        }
    }
}
