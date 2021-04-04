package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.content.Context;
import android.view.View;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.NotImplementedError;

public class StatsMaker {

    public static View makeStatsView(Context context, List<Trial>trials){

        switch (trials.get(0).getTrialType()){
            case Trial.COUNT:
                return makeCountStats(context,  trials);

            default:
                return  null;
        }
    }

    private static View makeCountStats(Context context, List<Trial>trials){

        return null;
    }

    private static float calcStd(List<Trial> trials){


        return 0;
    }

    public static double[] calcQuartiles(List<Trial> trials){
        List<Double> values = getSortedDoubles(trials);
        int size = values.size();

        double[] outputs = {values.get(size/4), values.get(3*size/4), values.get(0), values.get(size -1)};
        return  outputs;
    }

    public static double calcMedian(List<Trial> trials) {
        /*https://stackoverflow.com/a/51747735/12471420*/
        List<Double> values = getSortedDoubles(trials);

        int size = values.size();
        //returns median regardless if its the list is odd or even
        return (values.get(size/2 - 1) + values.get((size/2)))/ 2;

    }

    public static List<Double> getSortedDoubles(List<Trial> trials){
        final String type = trials.get(0).getTrialType();
        List<Double> values;
        switch (type) {
            case Trial.BINOMIAL:
                //TODO to be implemented, return a list of ratio for each unique user
                throw new NotImplementedError();

            case Trial.NONNEGATIVE:
                values = trials.stream().map(trial -> (double)((NonNegativeTrial) trial).getCount()).collect(Collectors.toList()); //type casting madness
                break;
            case Trial.COUNT:
                values = trials.stream().map(trial -> (double)((CountTrial) trial).getCount()).collect(Collectors.toList()); //type casting madness
                break;
            case Trial.MEASURE:
                values = trials.stream().map(trial -> ((MeasurementTrial) trial).getValue()).collect(Collectors.toList()); //type casting madness
                break;
            default:
                values = new ArrayList<>();
                break;
        }
        values.sort((f1, f2) -> Double.compare(f1, f2));
        return values;
    }


    public static double calcMean(List<Trial> trials){
        double sum = 0;

        final String type = trials.get(0).getTrialType();
        for(Trial t : trials){
            switch (type){
                case Trial.COUNT:
                    sum += ((CountTrial) t).getCount();

                    break;
                case Trial.NONNEGATIVE:
                    sum += ((NonNegativeTrial) t).getCount();

                    break;
                case Trial.BINOMIAL:

                    sum +=((BinomialTrial) t).getPass()?1:0;

                    break;
                case Trial.MEASURE:
                    sum += ((MeasurementTrial)t).getValue();
                    break;
            }
        }

       return sum/trials.size();
    }




}
