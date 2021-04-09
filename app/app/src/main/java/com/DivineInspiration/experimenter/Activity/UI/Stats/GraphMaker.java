package com.DivineInspiration.experimenter.Activity.UI.Stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.DivineInspiration.experimenter.BuildConfig;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class uses a factory pattern, handles making Chart objects, which can either be a Histogram/Bar chart or Line graph/line + box plot.
 */
@SuppressLint("DefaultLocale")
public class GraphMaker {
    /*
    on using MpAndroid
    https://weeklycoding.com/mpandroidchart-documentation/setting-data/
     */

    @SuppressLint("SimpleDateFormat")
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("MM/dd");


    /**
     * <b>Note: </b> This function assumes all given trials are of the same type as the first element.
     * Makes a Histogram like chart, depending on the types of trial given
     *
     * @param trials  a list of trial to be used.
     * @param context a context to inflate view from
     * @return an appropriate chart for the given trial type
     * @throws IllegalArgumentException if given list is empty
     */
    public static View makeHistogram(List<Trial> trials, Context context) {

        if(trials == null){
            return LayoutInflater.from(context).inflate(R.layout.stat_warning, null);
        }

        trials = TrialManager.getInstance().filterIgnoredTrials(trials);
        if(trials.size() < 3){
            return LayoutInflater.from(context).inflate(R.layout.stat_warning, null);
        }
        switch (trials.get(0).getTrialType()) {
            case Trial.COUNT:
                return makeCountBarGraph(trials, context);
            case Trial.BINOMIAL:
                return makeBinomialBarGraph(trials, context);
            case Trial.NONNEGATIVE:
                return makeNonNegativeHistogram(trials, context);
            case Trial.MEASURE:
                return makeMeasurementHistogram(trials, context);
            default:
                return null;
        }
    }


    /**
     * <b>Note: </b> This function assumes all given trials are of the same type as the first element.
     * Makes a Line graph like chart, depending on the types of trial given
     * @param trials  a list of trial to be used.
     * @param context a context to inflate view from
     * @return an appropriate chart for the given trial type
     * @throws IllegalArgumentException if given list is empty
     */
    public static View makeLineChart(List<Trial> trials, Context context) {
        if(trials == null){
            return LayoutInflater.from(context).inflate(R.layout.stat_warning, null);
        }

        trials = TrialManager.getInstance().filterIgnoredTrials(trials);
        if(trials.size() < 3){
            return LayoutInflater.from(context).inflate(R.layout.stat_warning, null);
        }

        //first put trials in date buckets since all 3 functions needs it
        List<List<Trial>> trialsDateBucket = groupTrialByDate(trials);
        switch (trials.get(0).getTrialType()) {
            case Trial.COUNT:
                return makeCountLineGraph(trialsDateBucket, context);

            case Trial.BINOMIAL:
                return makeBinomialLineGraph(trialsDateBucket, context);
            case Trial.MEASURE://switch abuse
            case Trial.NONNEGATIVE:
                return makeCandlestick(trialsDateBucket, context);

            default:
                return null;
        }
    }

    /**
     * Makes a candle stick graph + line graph for Non-negative and measurement trials.
     * The candle sticks represents the current Q1, median and Q3, while the line graph represents the current mean.
     * @param trialsBucket
     * trial matrix
     * @param context
     * current context
     * @return
     * chart
     */
    private static Chart<?> makeCandlestick(List<List<Trial>> trialsBucket, Context context) {
        /*
        https://medium.com/@neerajmoudgil/candlestick-chart-using-philjay-mpandroidchart-library-how-to-bf657ddf3a28
        how make candle stick chart with MPandroid
         */

        LocalDate currentDate = trialsBucket.get(0).get(0).getTrialDate();
        LocalDate lastDate = trialsBucket.get(trialsBucket.size() - 1).get(0).getTrialDate();

        List<Entry> lineEntry = new ArrayList<>();
        List<CandleEntry> candleEntries = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<Trial> currentTrials = new ArrayList<>();

        int dateIndex = 0;
        int entryIndex = 0;

        //iterate over each from start to end, even if there is new data for that day
        while (!currentDate.isAfter(lastDate)) {
            if (trialsBucket.get(dateIndex).get(0).getTrialDate().equals(currentDate)) {
                currentTrials.addAll(trialsBucket.get(dateIndex));
                dateIndex++;
            }
            lineEntry.add(new Entry(entryIndex, (float) StatsMaker.calcMean(currentTrials)));
            double[] quartiles = StatsMaker.calcQuartiles(currentTrials);
            candleEntries.add(new CandleEntry(entryIndex, (float) quartiles[1], (float) quartiles[0], (float) quartiles[1], (float) quartiles[0]));
            dates.add(shortFormatter.format(currentDate));
            entryIndex++;
            currentDate = currentDate.plusDays(1);
        }

        return makeCombinedChart(context, lineEntry, candleEntries, dates);
    }

    /**
     * Using relevant data to make a combined chart that includes candle sticks and line graph
     * @param context
     * @param lineEntries
     * @param candleEntries
     * @param labels
     * @return
     */
    private static CombinedChart makeCombinedChart(Context context, List<Entry> lineEntries, List<CandleEntry> candleEntries, List<String> labels) {

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Count Mean Over Time");
        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, "Quartiles over time");
        LineData lineData = new LineData();
        CandleData candleData = new CandleData();
        lineData.addDataSet(lineDataSet);

        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleDataSet.setColor(Color.WHITE);
        candleDataSet.setShadowColor(ContextCompat.getColor(context, R.color.beige1));
        candleDataSet.setIncreasingColor(ContextCompat.getColor(context, R.color.green1));
        candleDataSet.setIncreasingPaintStyle(Paint.Style.STROKE);
        candleDataSet.setDecreasingColor(ContextCompat.getColor(context, R.color.green1));
        candleDataSet.setDecreasingPaintStyle(Paint.Style.STROKE);

        candleData.addDataSet(candleDataSet);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(candleData);

        CombinedChart chart = new CombinedChart(context);
        chart.setData(combinedData);

        XAxisLabelFormatter formatter = new XAxisLabelFormatter(labels);
        chart.getXAxis().setValueFormatter(formatter);
        chart.getXAxis().setGranularity(1f);

        styleLineBarChart(context, chart, formatter);

        return chart;
    }


    /**
     * Makes a histogram for measurement trial. This method will by default divide given data into 10 buckets, or the number of trials if there are too few trials.
     * @param trials
     * list of trials
     * @param context
     * @return
     * chart
     */
    private static Chart<?> makeMeasurementHistogram(List<Trial> trials, Context context) {
        List<List<Trial>> trialBuckets = groupTrialByRange(trials, Math.min(10, trials.size()));

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        DecimalFormat deciFormat = new DecimalFormat("0.#");
        int i = 0;
        for (List<Trial> list : trialBuckets) {
            entries.add(new BarEntry(i, (float) list.size()));
            labels.add(String.format("%s-%s", deciFormat.format(findMinMeasurement(list)), deciFormat.format(findMaxMeasurement(list))));
            i++;
        }
        BarChart chart = new BarChart(context);


        BarData data = new BarData(new BarDataSet(entries, "Distribution of Measurement Trials submitted"));
        chart.setData(data);
        chart.setFitBars(true);
        //axis settings
        XAxisLabelFormatter formatter = new XAxisLabelFormatter(labels);
        chart.getXAxis().setValueFormatter(formatter);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setSpaceTop(25f);
        chart.getAxisLeft().setGranularity(1f);
        //chart.setScaleEnabled(false);

        styleLineBarChart(context, chart, formatter);
        return chart;
    }


    /**
     * Makes a histogram showing the distribution of NonNegative trials submitted.
     * @param trials
     * @param context
     * @return
     */
    private static Chart<?> makeNonNegativeHistogram(List<Trial> trials, Context context) {
        List<List<Trial>> trialBuckets = groupTrialsByValue(trials);

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        int i = 0;
        for (List<Trial> list : trialBuckets) {
            entries.add(new BarEntry(i, (float) list.size()));
            labels.add(String.valueOf(((NonNegativeTrial) list.get(0)).getCount()));
            i++;
        }
        BarChart chart = new BarChart(context);

        BarData data = new BarData(new BarDataSet(entries, "Distribution of NonNeg Trials submitted"));
        chart.setData(data);
        chart.setFitBars(true);


        //axis settings
        XAxisLabelFormatter formatter = new XAxisLabelFormatter(labels);
        chart.getXAxis().setValueFormatter(formatter);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setSpaceTop(25f);
        chart.getAxisLeft().setGranularity(1f);
        //chart.setScaleEnabled(false);

        styleLineBarChart(context, chart, formatter);
        return chart;
    }

    /**
     * Makes a line graph representing the mean success rate over time.
     * @param trialsBucket
     * @param context
     * @return
     * chart
     */
    private static Chart<?> makeBinomialLineGraph(List<List<Trial>> trialsBucket, Context context) {
        double success = 0;
        double fail = 0;
        LocalDate currentDate = trialsBucket.get(0).get(0).getTrialDate();
        LocalDate lastDate = trialsBucket.get(trialsBucket.size() - 1).get(0).getTrialDate();

        List<Entry> data = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        int dateIndex = 0;
        int entryIndex = 0;
        while (!currentDate.isAfter(lastDate)) {
            if (trialsBucket.get(dateIndex).get(0).getTrialDate().equals(currentDate)) {
                double[] stats = StatsMaker.calcBinomialStats(trialsBucket.get(dateIndex));
                success += stats[0];
                fail += stats[1];
                dateIndex++;
            }
            data.add(new Entry(entryIndex, (float) (success / (success + fail))));
            entryIndex++;
            dates.add(shortFormatter.format(currentDate));
            currentDate = currentDate.plusDays(1);
        }

        LineChart chart = new LineChart(context);

        //dataset for marker
        LineDataSet dataSet = new LineDataSet(data, "Ratio of Success/Total");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        chart.setData(new LineData(dataSet));
        chart.getXAxis().setGranularity(1f);
        XAxisLabelFormatter formatter = new XAxisLabelFormatter(dates);
        chart.getXAxis().setValueFormatter(formatter);

        styleLineBarChart(context, chart, formatter);
        return chart;
    }

    /**
     * Makes a bar graph consisting of 2 bars. Indicating number of successes and fails.
     * @param trials
     * list of trials
     * @param context
     * @return
     * chart
     */
    private static Chart<?> makeBinomialBarGraph(List<Trial> trials, Context context) {
        double[] stats = StatsMaker.calcBinomialStats(trials);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, (float) stats[0]));
        entries.add(new BarEntry(2, (float) stats[1]));

        BarData data = new BarData(new BarDataSet(entries, String.format("Binomial Trial - %.2f%% Success", stats[2])));


        BarChart chart = new BarChart(context);
        chart.setData(data);
        chart.setFitBars(true);

        List<String> labels = new ArrayList<>();
        labels.add("Success");
        labels.add("");
        labels.add("Fail");

        //axis settings
        XAxisLabelFormatter formatter = new XAxisLabelFormatter(labels);
        chart.getXAxis().setValueFormatter(formatter);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum((float) Math.max(stats[0], stats[1]) * 1.3f);
        chart.setScaleEnabled(false);

        styleLineBarChart(context, chart, formatter);
        return chart;
    }


    /**
     * Makes a single, mostly meaning bar to represent the total count of the Count trials submitted so far.
     * @param trials
     * list of trials
     * @param context
     * @return
     * chart
     */
    private static Chart<?> makeCountBarGraph(List<Trial> trials, Context context) {
        double sum = StatsMaker.calcSum(trials);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, (float) sum));
        BarData data = new BarData(new BarDataSet(entries, "A single bar of total count, as requested"));

        BarChart chart = new BarChart(context);
        chart.setData(data);
        chart.setFitBars(true);

        List<String> labels = new ArrayList<>();
        labels.add("Single bar!");

        //axis settings
        XAxisLabelFormatter formatter = new XAxisLabelFormatter(labels);
        chart.getXAxis().setValueFormatter(formatter);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum((float) sum * 1.3f);

        chart.setScaleEnabled(false);

        styleLineBarChart(context, chart, formatter);
        return chart;

    }


    /**
     * Makes a line graph to represent the growth of total count over time.
     * @param trialsBucket
     * @param context
     * @return
     * chart
     */
    private static Chart<?> makeCountLineGraph(List<List<Trial>> trialsBucket, Context context) {
        /*https://stackoverflow.com/a/29812532/12471420*/
        float sum = 0;
        LocalDate currentDate = trialsBucket.get(0).get(0).getTrialDate();
        LocalDate lastDate = trialsBucket.get(trialsBucket.size() - 1).get(0).getTrialDate();

        List<Entry> data = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        int dateIndex = 0;
        int entryIndex = 0;
        while (!currentDate.isAfter(lastDate)) {
            if (trialsBucket.get(dateIndex).get(0).getTrialDate().equals(currentDate)) {
                sum += StatsMaker.calcSum(trialsBucket.get(dateIndex));
                dateIndex++;
            }
            data.add(new Entry(entryIndex, sum));
            entryIndex++;
            dates.add(shortFormatter.format(currentDate));
            currentDate = currentDate.plusDays(1);
        }

        LineChart chart = new LineChart(context);

        //dataset for marker
        LineDataSet dataSet = new LineDataSet(data, "Count over time");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        chart.setData(new LineData(dataSet));
        chart.getXAxis().setGranularity(1f);
        XAxisLabelFormatter formatter = new XAxisLabelFormatter(dates);
        chart.getXAxis().setValueFormatter(formatter);

        styleLineBarChart(context, chart, formatter);
        return chart;
    }


    /**
     * Common chart styling settings for other chart making functions.
     * @param context
     * current context
     * @param chart
     * chart
     * @param formatter
     */
    private static void styleLineBarChart(Context context, BarLineChartBase chart, ValueFormatter formatter) {
        final int beige1 = ContextCompat.getColor(context, R.color.beige1);

        //marker for dataset
        ClickMarker marker = new ClickMarker(context, formatter, R.layout.marker_content);
        chart.setMarker(marker);
        chart.getData().setDrawValues(false);

        //chart styling options
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setTextColor(beige1);
        chart.getAxisLeft().setAxisLineColor(beige1);
        chart.getXAxis().setAxisLineColor(beige1);
        chart.getXAxis().setTextColor(beige1);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getLegend().setTextColor(beige1);


        chart.getXAxis().setAxisMinimum(-2);
        chart.getXAxis().setAxisMaximum(chart.getXAxis().getAxisMaximum() + 2);


        chart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        chart.setBorderColor(R.color.beige1);
        chart.setDescription(null);
        chart.setNoDataText("No data available yet!");
        chart.setNoDataTextColor(ContextCompat.getColor(context, R.color.beige1));
    }

    /**
     * Groups a list of trial into ranges, given the number of desired ranges.
     * @param trials
     * list of trials
     * @param numberOfBuckets
     * @return a List of List of trials, where each inner list has trials belonging in a common range.
     */
    public static List<List<Trial>> groupTrialByRange(List<Trial> trials, int numberOfBuckets) {

        //TODO theres got to be a better way write the following size
        double bucketSize = (findMaxMeasurement(trials) -
                findMinMeasurement(trials)) / numberOfBuckets;
        //divide by bucket size then take floor/ceil should put each value in a range
        List<List<Trial>> output = new ArrayList<>(trials.stream().collect(Collectors.groupingBy(trial ->
                Math.ceil(((MeasurementTrial) trial).getValue() / bucketSize))).values());

        output.sort((list1, list2) -> Double.compare(((MeasurementTrial) list1.get(0)).getValue(), ((MeasurementTrial) list2.get(0)).getValue()));
        return output;
    }

    /**
     * Helper function to find the max value in a list of measurement trials
     * @param trials
     * list of trials
     * @return
     */
    public static double findMaxMeasurement(List<Trial> trials) {
        return ((MeasurementTrial) Collections.max(trials, (t1, t2) -> Double.compare(((MeasurementTrial) t1).getValue(), ((MeasurementTrial) t2).getValue()))).getValue();
    }

    /**
     * Heler function to find the min value in a list of measurement trials
     * @param trials
     * list of trials
     * @return
     * minimum measurement
     */
    public static double findMinMeasurement(List<Trial> trials) {
        return ((MeasurementTrial) Collections.min(trials, (t1, t2) -> Double.compare(((MeasurementTrial) t1).getValue(), ((MeasurementTrial) t2).getValue()))).getValue();
    }


    /**
     * Groups a list of trial into buckets by their creation date
     * @param trials
     * list of trials
     * @return a List of List of trials, where each inner list has trials has date in common
     */
    public static List<List<Trial>> groupTrialByDate(List<Trial> trials) {
        //divides trials into buckets by date
        List<List<Trial>> output = new ArrayList<>(trials.stream().collect(Collectors.groupingBy(trial ->
                dateFormatter.format(trial.getTrialDate()))).values());
        //sort by date
        output.sort((l1, l2) -> l1.get(0).getTrialDate().compareTo(l2.get(0).getTrialDate()));
        return output;
    }


    /**
     * Groups a list of non negative trials into buckets by their value
     * @param trials
     * list of trials
     * @return a List of List of trials, where each inner list has trials belonging in a common count.
     */
    public static List<List<Trial>> groupTrialsByValue(List<Trial> trials) {
        //currently only applicable/useful to non negative trial
        //assert type is Non negative
        if (BuildConfig.DEBUG && !(trials.get(0).getTrialType().equals(Trial.NONNEGATIVE))) {
            throw new AssertionError("Assertion failed");
        }

        //group by count
        List<List<Trial>> output = new ArrayList<>(trials.stream().collect(
                Collectors.groupingBy(trial -> ((NonNegativeTrial) trial).getCount())).values()
        );
        //sort
        output.sort((list1, list2) -> Integer.compare(((NonNegativeTrial) list1.get(0)).getCount(), ((NonNegativeTrial) list2.get(0)).getCount()));
        return output;
    }


    /**
     * This class is used to format axisLabels.
     * Using a given set of labels, it will map floats to their corresponding labels
     */
    private static class XAxisLabelFormatter extends ValueFormatter {

        List<String> labels;

        /**
         * Constructor
         * @param labels
         * all the labels
         */
        XAxisLabelFormatter(List<String> labels) {
            this.labels = labels;
        }

        /**
         * Gets the axis label
         * @param value
         * value
         * @param axis
         * axis
         * @return
         * string of the axis label
         */
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Log.d("Graph formatter", "Size" + labels.size()+ "Value:" + value);
            return (Math.abs(value - (int) value) < 0.01f) && (value < labels.size()) && (value >= 0) ? labels.get((int) value) : "";
        }
    }


}

/*
https://github.com/PhilJay/MPAndroidChart/wiki/IMarker-Interface
show marker on click
 */

class ClickMarker extends MarkerView {

    private final TextView content;
    private final DecimalFormat fmt;
    private final ValueFormatter valFormatter;
    private MPPointF mOffset;

    /**
     * Constructor
     * @param context
     * current context
     * @param formatter
     * @param layoutResource
     */
    public ClickMarker(Context context, ValueFormatter formatter, int layoutResource) {
        super(context, layoutResource);
        fmt = new DecimalFormat("0.##");
        // find your layout components
        content = findViewById(R.id.markerContent);
        valFormatter = formatter;
    }

    /**
     * callbacks every time the MarkerView is redrawn, can be used to update the content (user-interface)
     * @param e
     * @param highlight
     */
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        content.setText(String.format("%s, %s", valFormatter.getAxisLabel(e.getX(), null), fmt.format(e.getY())));

        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    /**
     * Gets the offset
     * @return
     * point f
     */
    @Override
    public MPPointF getOffset() {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight() * 1.35f);
        }

        return mOffset;
    }
}