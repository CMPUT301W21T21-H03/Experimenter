package com.DivineInspiration.experimenter.Activity.UI.Stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatsMaker {


    /**
     * Makes a view containing statistic depending on the type of trial given.
     * @throws IllegalArgumentException if the list of trials given is empty or null
     * @param context
     * @param trials
     * list of trials
     * @return a view containing statics appropriate to the trials given.
     */
    @SuppressLint("DefaultLocale")
    public static View makeStatsView(Context context, List<Trial> trials) {
        if(trials == null){
            return LayoutInflater.from(context).inflate(R.layout.stat_warning, null);
        }

        trials = TrialManager.getInstance().filterIgnoredTrials(trials);
        if(trials.size() < 3){
            return LayoutInflater.from(context).inflate(R.layout.stat_warning, null);
        }
        final String type = trials.get(0).getTrialType();
        DecimalFormat fmt = new DecimalFormat("0.##");

        View view = LayoutInflater.from(context).inflate(R.layout.stat_view, null);

        TextView total = view.findViewById(R.id.statTotal);
        TextView median = view.findViewById(R.id.statMedian);
        TextView mean = view.findViewById(R.id.statMean);
        TextView stdDev = view.findViewById(R.id.statStdDev);
        TextView quartiles = view.findViewById(R.id.statQuartiles);
        TextView minMax = view.findViewById(R.id.statMinMax);
        TextView trialCount = view.findViewById(R.id.statTrialCount);
        TextView passes = view.findViewById(R.id.statPass);
        switch (type) {
            case Trial.COUNT:
                total.setText(String.format("Total count:  %d", (int) calcSum(trials)));
                break;
            case Trial.NONNEGATIVE:
                total.setVisibility(View.GONE);
                break;
            case Trial.BINOMIAL:
                //For binomial, total, median, min max, and Q1 Q3 are meaningless
                total.setVisibility(View.GONE);
                median.setVisibility(View.GONE);
                minMax.setVisibility(View.GONE);
                quartiles.setVisibility(View.GONE);
                double[] binomialStats = calcBinomialStats(trials); //get binomial related stats
                passes.setText(String.format("Passes:  %s,  Fails:  %s, Ratio:  %.2f", fmt.format(binomialStats[0]), fmt.format(binomialStats[1]), binomialStats[2]));
            case Trial.MEASURE:
                total.setVisibility(View.GONE);
        }

        if (!type.equals(Trial.BINOMIAL)) {
            //the following are applicable to all types except
            double[] quarts = calcQuartiles(trials);
            passes.setVisibility(View.GONE);
            median.setText(String.format("Median:  %.4f", calcMedian(trials)));
            minMax.setText(String.format("Min:  %s,  Max:  %s", fmt.format(quarts[2]), fmt.format(quarts[3])));
            quartiles.setText(String.format("Q1: %s, Q3: %s", fmt.format(quarts[0]), fmt.format(quarts[1])));
        }

        trialCount.setText(String.format("Total Trial Count:  %d", trials.size()));
        mean.setText(String.format("Mean:  %.4f", calcMean(trials)));
        stdDev.setText(String.format("Standard Deviation:  %.4f", calcStd(trials)));


        return view;
    }

    /**
     * Does all the calculations fro teh binomial stats
     * @param trials
     * list of trials
     * @return double[3], # of success, # of fails, success/total. In order.
     */
    public static double[] calcBinomialStats(List<Trial> trials) {
        //pass, fail, ratio
        double[] val = {0, 0, 0};

        for (Trial t : trials) {
            //add 1 to val[0] if success, to val[1] if fail
            val[((BinomialTrial) t).getPass() ? 0 : 1]++;
        }
        val[2] = 100 * val[0] / trials.size();
        return val;
    }


    /**
     * Calculates the standard deviation of all the trials
     * @param trials
     * list of trials
     * @return standard deviation of the value in the ist of trials given.
     */
    public static double calcStd(List<Trial> trials) {

        double mean = calcMean(trials);
        List<Double> values = getDoubles(trials);
        double SS = 0;
        for (double val : values) {
            SS += (val - mean) * (val - mean);
        }
        return Math.sqrt(SS / values.size());
    }

    /**
     * Calculates the quartiles info on the list of trials
     * @param trials
     * list of trials
     * @return A double[4], which is Q1, Q3, Min, Max of values in the list of trials, in order.
     */
    public static double[] calcQuartiles(List<Trial> trials) {
        List<Double> values = sortDouble(getDoubles(trials));
        int size = values.size();
        List<Double> upper = values.subList((size / 2) + (size % 2 == 1? 1:0), size);
        List<Double> lower = values.subList(0, size/2);

        //Q1, Q3, min, max
        return new double[]{calcMedianSortedDouble(lower), calcMedianSortedDouble(upper), values.get(0), values.get(size - 1)};
    }


    /**
     * Calculates the median of the trials
     * @param trials
     * list of trials
     * @return median of the values of the list of trials given.
     */
    public static double calcMedian(List<Trial> trials) {
        /*https://stackoverflow.com/a/51747735/12471420*/
        List<Double> values = getDoubles(trials);
        sortDouble(values);
        int size = values.size();
        //returns median regardless if its the list is odd or even

        if(size % 2 == 1){
            return values.get(size/2);
        }
        else{
        return (values.get(size / 2 - 1) + values.get((size / 2))) / 2;
        }
    }

    public static double calcMedianSortedDouble(List<Double> values) {
        /*https://stackoverflow.com/a/51747735/12471420*/

        int size = values.size();
        //returns median regardless if its the list is odd or even

        if(size % 2 == 1){
            return values.get(size/2);
        }
        else{
            return (values.get(size / 2 - 1) + values.get((size / 2))) / 2;
        }
    }

    /**
     * Sorting the doubles of the trials
     * @param doubles
     * @return Sorted list of doubles.
     */
    public static List<Double> sortDouble(List<Double> doubles) {
        doubles.sort(Double::compare);
        return doubles;
    }

    /**
     * Gets the double of all the trials
     * @param trials
     * list of trials
     * @return A list of doubles extract from the list of trials given, to be used by other functions.
     */
    public static List<Double> getDoubles(List<Trial> trials) {
        final String type = trials.get(0).getTrialType();
        List<Double> values;
        switch (type) {
            case Trial.BINOMIAL:
                values = trials.stream().map(trial -> (double) (((BinomialTrial) trial).getPass() ? 1 : 0)).collect(Collectors.toList()); //type casting madness
                break;
            case Trial.NONNEGATIVE:
                values = trials.stream().map(trial -> (double) ((NonNegativeTrial) trial).getCount()).collect(Collectors.toList()); //type casting madness
                break;
            case Trial.COUNT:
                values = trials.stream().map(trial -> (double) ((CountTrial) trial).getCount()).collect(Collectors.toList()); //type casting madness
                break;
            case Trial.MEASURE:
                values = trials.stream().map(trial -> ((MeasurementTrial) trial).getValue()).collect(Collectors.toList()); //type casting madness
                break;
            default:
                values = new ArrayList<>();
                break;
        }

        return values;
    }


    /**
     * Calculates the mean of the trials
     * @param trials
     * list of trials
     * @return The mean of all values in the list of trials given.
     */
    public static double calcMean(List<Trial> trials) {
        return calcSum(trials) / trials.size();
    }


    /**
     * Calculates the sum of all the values
     * @param trials
     * list of trials
     * @return The sum of all values in the list of trials given, type checking built in.
     */
    public static double calcSum(List<Trial> trials) {
        double sum = 0;

        final String type = trials.get(0).getTrialType();
        for (Trial t : trials) {
            switch (type) {
                case Trial.COUNT:
                    sum += ((CountTrial) t).getCount();
                    break;
                case Trial.NONNEGATIVE:
                    sum += ((NonNegativeTrial) t).getCount();
                    break;
                case Trial.BINOMIAL:
                    sum += ((BinomialTrial) t).getPass() ? 1 : 0;
                    break;
                case Trial.MEASURE:
                    sum += ((MeasurementTrial) t).getValue();
                    break;
            }
        }
        return sum;
    }
}
