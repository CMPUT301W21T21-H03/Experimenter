package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public static float[] calcQuartiles(List<Trial> trials){
        List<Float> values = getSortedFloats(trials);
        int size = values.size();

        float[] outputs = {values.get(size/4), values.get(3*size/4), values.get(0), values.get(size -1)};
        return  outputs;
    }

    public static float calcMedian(List<Trial> trials) {
        /*https://stackoverflow.com/a/51747735/12471420*/
        List<Float> values = getSortedFloats(trials);

        int size = values.size();
        //returns median regardless if its the list is odd or even
        return values.get(size/2 - 1) + values.get((size/2)) / 2;

    }

    public static List<Float> getSortedFloats(List<Trial> trials){
        final String type = trials.get(0).getTrialType();
        List<Float> values;
        switch (type) {
            case Trial.BINOMIAL:
                values = trials.stream().map(trial -> (float) ((BinomialTrial) trial).getSuccessRatio()).collect(Collectors.toList());
                break;

            case Trial.NONNEGATIVE:
                values = trials.stream().map(trial -> (float) ((NonNegativeTrial) trial).getCount()).collect(Collectors.toList()); //type casting madness
                break;
            case Trial.COUNT:
                values = trials.stream().map(trial -> (float) ((CountTrial) trial).getCount()).collect(Collectors.toList()); //type casting madness
                break;
            case Trial.MEASURE:
                values = new ArrayList<>();
                for (ArrayList<Float> arr : trials.stream().map(trial -> ((MeasurementTrial) trial).getMeasurements()).collect(Collectors.toList())) {
                    values.addAll(arr);
                }//type casting madness
                break;
            default:
                values = new ArrayList<>();
                break;
        }
        values.sort((f1, f2) -> Float.compare(f1, f2));
        return values;
    }


    public static float calcMean(List<Trial> trials){
        float sum = 0;
        int sampleSize = 0;
        final String type = trials.get(0).getTrialType();

        for(Trial t : trials){
            switch (type){
                case Trial.COUNT:
                    sum += ((CountTrial) t).getCount();
                    sampleSize += 1;
                    break;
                case Trial.NONNEGATIVE:
                    sum += ((NonNegativeTrial) t).getCount();
                    sampleSize += 1;
                    break;
                case Trial.BINOMIAL:
                    BinomialTrial temp = ((BinomialTrial) t);
                    sum += temp.getSuccess();
                    sampleSize += temp.getSuccess() + temp.getFailure();
                    break;
                case Trial.MEASURE:

                    for(float f : ((MeasurementTrial) t).getMeasurements()){
                        sum += f;
                    }
                    sampleSize += ((MeasurementTrial) t).getMeasurements().size();
                    break;
            }
        }

       return sum/sampleSize;
    }




}
