package com.DivineInspiration.experimenter.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;

public class ExperimentList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    /**
     * Experiment list contructor
     * @param context
     * context view
     * @param experiments
     * array list of experiments
     */
    public ExperimentList(Context context, ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.experiments = experiments;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.search_bar, parent, false);
        }

        Experiment experiment = experiments.get(position);

        //*******
        // TODO:
        // ADD TEXT VIEW STUFF
        //********

        return view;

    }
}
