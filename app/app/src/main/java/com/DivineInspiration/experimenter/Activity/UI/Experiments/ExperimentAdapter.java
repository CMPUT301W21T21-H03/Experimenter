package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;

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

/**
 * A custom RecyclerView Adapter class. Displays a list of experiment. Used by
 * {@link com.DivineInspiration.experimenter.Activity.UI.Experiments.ExperimentListTabFragment}
 * to create display experiments
 * @see <a href="https://developer.android.com/guide/topics/ui/layout/recyclerview"> https://developer.android.com/guide/topics/ui/layout/recyclerview </a>
 */
public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ViewHolder> {

    private List<Experiment> experiments = new ArrayList<>();

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
     * position in adapter
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Experiment exp = experiments.get(position);

        holder.getExpNameTextView().setText(exp.getExperimentName());
        holder.getExpOwnerNameTextView().setText(exp.getOwnerName());
        holder.getExpTrialTypeText().setText(exp.getTrialType() + " - "+ exp.getStatus() );
        holder.getExpDescriptionText().setText(exp.getExperimentDescription());

        holder.getExpLocationText().setText(exp.getRegion() + " - Geolocation: " + (experiments.get(position).isRequireGeo()?"On":"Off"));

        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            // when card is clicked it ...?
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("experiment", experiments.get(position));
                Navigation.findNavController(v).navigate(R.id.homeToExp, bundle);
            }
        });
    }

    /**
     * Gets the number of items
     * @return
     * the number of items
     */
    @Override
    public int getItemCount() {
        return experiments.size();
    }

    /**
     * Sets data
     * @param experimentData
     * list of experiment to set
     */
    public void setData(List<Experiment> experimentData) {
        // clear and add all data to adapter
        experiments.clear();
        experiments.addAll(experimentData);
        // notify changes such that screen refreshes
        notifyDataSetChanged();
    }

    /**
     * A custom {@link RecyclerView.ViewHolder} class. Displays experiment information in a card
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // card and text
        private final TextView expNameText;
        private final TextView expOwnerNameText;
        private final TextView expTrialTypeText;
        private final TextView expLocationText;
        private final TextView expDescriptionText;

        private final CardView card;

        public ViewHolder(View v) {
            super(v);

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
    }
}
