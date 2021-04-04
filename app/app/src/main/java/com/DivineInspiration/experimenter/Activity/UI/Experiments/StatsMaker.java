package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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




}
