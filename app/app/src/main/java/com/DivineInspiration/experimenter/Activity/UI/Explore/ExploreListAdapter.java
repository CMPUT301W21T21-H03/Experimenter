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
    // https://developer.android.com/guide/topics/ui/layout/recyclerview

    // experiment list
    private List<Experiment> experiments = new ArrayList<>();

    // constructors
    public ExploreListAdapter() {}
    public ExploreListAdapter(List<Experiment> exp){
        this.experiments = exp;
    }

    /**
     * On create
     * @param parent
     * parent view
     * @param viewType
     * type of view
     * @return
     * a view holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create the each experiment list item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiment_item, parent, false);

        return new ViewHolder(v);
    }

    /**
     * Where each item in list is stored
     * @param holder
     * parent view holder (list)
     * @param position
     * position in list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getExpNameTextView().setText(experiments.get(position).getExperimentName());
        holder.getExpOwnerNameTextView().setText(experiments.get(position).getOwnerName());
        holder.getExpTrialTypeText().setText(experiments.get(position).getTrialType());
        holder.getExpDescriptionText().setText(experiments.get(position).getExperimentDescription());
        if(experiments.get(position).isRequireGeo()){
            holder.getExpLocationText().setText(experiments.get(position).getRegion() + " - Geolocation: On");
        }else {
            holder.getExpLocationText().setText(experiments.get(position).getRegion() + " - Geolocation: Off ");
        }
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explore is kinda SUS <= what does that mean?
                Bundle args = new Bundle();
                args.putSerializable("experiment", experiments.get(position));
                Navigation.findNavController(v).navigate(R.id.exploreToEx, args);
            }
        });
    }

    /**
     * Gets the number of item in adapter
     * @return
     * size of list
     */
    @Override
    public int getItemCount() {
        return experiments.size();
    }

    /**
     * Sets data in adapter
     * @param experimentData
     * the experiment list
     */
    public void setData(List<Experiment> experimentData) {
        // clears and adds the data back
        experiments.clear();
        experiments.addAll(experimentData);
        // update visual
        notifyDataSetChanged();

//        Log.d("ExperimentAdapter", "New Data -> " + experiments.toString());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // the card and text of the item
        private final TextView expNameText;
        private final TextView expOwnerNameText;
        private final TextView expTrialTypeText;
        private final TextView expLocationText;
        private final TextView expDescriptionText;

        private final CardView card;

        public ViewHolder(View v) {
            super(v);

            // gets text and card
            expNameText = v.findViewById(R.id.expListTitle);
            card = v.findViewById(R.id.experimentItemCard);
            expOwnerNameText = v.findViewById(R.id.expListOwnerName);
            expTrialTypeText = v.findViewById(R.id.expListType);
            expLocationText = v.findViewById(R.id.locationInfo);
            expDescriptionText = v.findViewById(R.id.expListAbout);
        }

        /**
         * Get text
         * @return
         * text
         */

        public TextView getExpNameTextView() {
            return expNameText;
        }

        /**
         * Get text
         * @return
         * text
         */

        public TextView getExpOwnerNameTextView(){
            return expOwnerNameText;
        }

        /**
         * Get text
         * @return
         * text
         */

        public TextView getExpTrialTypeText() {
            return expTrialTypeText;
        }

        /**
         * Get text
         * @return
         * text
         */

        public TextView getExpLocationText() {
            return expLocationText;
        }

        /**
         * Get text
         * @return
         * text
         */

        public TextView getExpDescriptionText() {
            return expDescriptionText;
        }

        /**
         * Get card
         * @return
         * card view
         */
        public CardView getCardView() {
            return card;
        }
}}
