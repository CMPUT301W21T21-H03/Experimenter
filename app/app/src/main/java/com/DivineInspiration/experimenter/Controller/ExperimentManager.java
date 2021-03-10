package com.DivineInspiration.experimenter.Controller;

import com.DivineInspiration.experimenter.Model.Experiment;

import java.util.ArrayList;
import java.util.List;

public class ExperimentManager extends ArrayList<Experiment> {
    //Singleton ArrayList
    private ArrayList<Experiment> experiments;
    private ExperimentManager(){
        experiments = new ArrayList<>();
    }
    public ArrayList<Experiment> getExperiments(){
        return experiments;
    }
    private static ExperimentManager singleton;

    public static ExperimentManager getInstance(){
        if(singleton ==null){
            singleton = new ExperimentManager();
        }
        return singleton;
    }

//  Do we need it? I don't think position of experiments matter as we are dealing ID
//    /**
//     * Gets an experiment at position
//     * @param position
//     * position of experiment
//     * @return
//     * the individial experiment
//     */
//    public Experiment getExperiment(int position) {
//        return experiments.get(position);
//    }
}
