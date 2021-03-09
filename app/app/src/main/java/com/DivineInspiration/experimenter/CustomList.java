package com.DivineInspiration.experimenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    public CustomList(Context context,  ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.experiments = experiments;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.experiment_list, parent, false);
        }

        Experiment experiment = experiments.get(position);

        //*******
        // TO DO
        // ADD TEXT VIEW STUFF
        //********

        return view;

    }
}
