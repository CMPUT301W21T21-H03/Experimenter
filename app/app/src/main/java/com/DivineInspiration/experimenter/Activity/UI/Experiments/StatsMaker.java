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

import java.util.List;

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

    public static float calcMedian(List<Trial> trials){
        return 0;
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
