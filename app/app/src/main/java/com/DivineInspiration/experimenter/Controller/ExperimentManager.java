package com.DivineInspiration.experimenter.Controller;

import com.DivineInspiration.experimenter.Model.Experiment;

import java.util.ArrayList;
import java.util.List;

public class ExperimentManager {
    private List<Experiment> experiments = new ArrayList<>();

    /**
     * Adds experiment to list
     * @param experiment
     */
    public void add(Experiment experiment) {
        experiments.add(experiment);
        // TODO:
    }

    /**
     * Removes experiment from list
     * @param experiment
     * @return
     * if removing the experiment is successful
     */
    public boolean remove(Experiment experiment) {
        // TO DO
        return false;
    }

    /**
     * Gets an experiment at position
     * @param position
     * position of experiment
     * @return
     * the individial experiment
     */
    public Experiment getExperiment(int position) {
        // TO DO
        return experiments.get(position);
    }
}
