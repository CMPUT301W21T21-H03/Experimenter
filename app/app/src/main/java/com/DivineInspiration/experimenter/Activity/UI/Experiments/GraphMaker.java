package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphMaker {

    @SuppressLint("SimpleDateFormat")
    static  DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //!! this assumes all trials are of the same kind
    public static Chart makeHistogram(ArrayList<Trial> trials, Context context){
        List<List<Trial>> trialsDateBucket = groupTrialByDate(trials);


        return null;
    }


    public static Chart makeLineChart(ArrayList<Trial> trials, Context context){

        List<List<Trial>> trialsDateBucket = groupTrialByDate(trials);
        switch (trials.get(0).getTrialType()){
            case Trial.COUNT:
                return makeCountLineChart(trialsDateBucket, context);

            default:
                return  null;
        }
    }

    /*https://stackoverflow.com/a/29812532/12471420*/
    private static Chart makeCountLineChart (List<List<Trial>> trialsBucket, Context context){

        int sum = 0;
        LocalDate currentDate = trialsBucket.get(0).get(0).getTrialDate();
        LocalDate lastDate = trialsBucket.get(trialsBucket.size()-1).get(0).getTrialDate();

        List<Entry> data = new ArrayList<>();
        int i = 0;
        while(currentDate.isBefore(lastDate) ){

            if(trialsBucket.get(i).get(0).getTrialDate().equals(currentDate)){
                sum += getCountSum(trialsBucket.get(i));
                i++;
            }
            data.add(new Entry(i, sum));
            currentDate= currentDate.plusDays(1);
        }
        LineDataSet dataSet = new LineDataSet(data, "Count over time");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineChart chart = new LineChart(context);
        chart.setData(new LineData(dataSet));
        chart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Log.d("woah", "exiting line chart");
        return chart;
    }

    public static List<List<Trial>> groupTrialByDate(ArrayList<Trial> trials){
        Log.d("woah", "entering grouping data");
        //divides trials into buckets by date
        List<List<Trial>> temp = new ArrayList<>(trials.stream().collect(Collectors.groupingBy(trial ->
                df.format(trial.getTrialDate()))).values());
        //sort by date
        temp.sort((l1, l2) -> l1.get(0).getTrialDate().compareTo(l2.get(0).getTrialDate()));
        return temp;
    }


    public static int getCountSum(List<Trial> trials){

        int sum = 0;
        for(Trial t : trials){
            if(t.getTrialType().equals(Trial.COUNT)){
                sum+=( (CountTrial)t).getCount();
            }
            else if(t.getTrialType().equals(Trial.NONNEGATIVE)){
                sum+=( (NonNegativeTrial)t).getCount();
            }
        }
        return sum;
    }

}
