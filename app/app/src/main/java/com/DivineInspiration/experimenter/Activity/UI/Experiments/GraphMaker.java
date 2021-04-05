package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.DivineInspiration.experimenter.BuildConfig;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("DefaultLocale")
public class GraphMaker {
    /*
    on using MpAndroid
    https://weeklycoding.com/mpandroidchart-documentation/setting-data/
     */

    @SuppressLint("SimpleDateFormat")
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("MM/dd");

    //!! this assumes all trials are of the same kind
    public static Chart<?> makeHistogram(ArrayList<Trial> trials, Context context) {
        switch (trials.get(0).getTrialType()) {
            case Trial.COUNT:
                return makeCountBarGraph(trials, context);
            case Trial.BINOMIAL:
                return makeBinomialBarGraph(trials, context);
            default:
                return null;
        }
    }

    public static Chart<?> makeLineChart(ArrayList<Trial> trials, Context context) {

        List<List<Trial>> trialsDateBucket = groupTrialByDate(trials);
        switch (trials.get(0).getTrialType()) {
            case Trial.COUNT:
                return makeCountLineChart(trialsDateBucket, context);

            case Trial.BINOMIAL:
                return makeBinomialLineGraph(trialsDateBucket, context);
            default:
                return null;
        }
    }

    private static Chart<?> makeNonNegativeHistogram(ArrayList<Trial> trials, Context context) {
        List<List<Trial>> trialBuckets = groupTrialsByValue(trials);

        List<BarEntry> entries = new ArrayList<>();

        for(List<Trial> list : trialBuckets){
            //entries.add(StatsMaker.calcSum(list));
        }
        return null;
    }

    //
    private static Chart<?> makeBinomialLineGraph(List<List<Trial>> trialsBucket, Context context) {
        double success = 0;
        double fail = 0;
        LocalDate currentDate = trialsBucket.get(0).get(0).getTrialDate();
        LocalDate lastDate = trialsBucket.get(trialsBucket.size() - 1).get(0).getTrialDate();

        List<Entry> data = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        int i = 0;
        while (currentDate.isBefore(lastDate)) {
            if (trialsBucket.get(i).get(0).getTrialDate().equals(currentDate)) {
                double[] stats = StatsMaker.calcBinomialStats(trialsBucket.get(i));
                success += stats[0];
                fail += stats[1];
                i++;
            }
            data.add(new Entry(i, (float) (success / (success + fail))));
            dates.add(shortFormatter.format(currentDate));
            currentDate = currentDate.plusDays(1);
        }

        LineChart chart = new LineChart(context);

        //dataset for marker
        LineDataSet dataSet = new LineDataSet(data, "Ratio of Success/Total");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        chart.setData(new LineData(dataSet));
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setValueFormatter(new XAxisLabelFormatter(dates));

        styleLineBarChart(context, chart);
        return chart;
    }

    private static Chart<?> makeBinomialBarGraph(List<Trial> trials, Context context) {
        double[] stats = StatsMaker.calcBinomialStats(trials);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, (float) stats[0]));
        entries.add(new BarEntry(1, (float) stats[1]));
        BarData data = new BarData(new BarDataSet(entries, String.format("Binomial Trial - %.2f% Success", stats[2])));


        BarChart chart = new BarChart(context);
        chart.setData(data);
        chart.setFitBars(true);

        List<String> labels = new ArrayList<>();
        labels.add("Success");
        labels.add("Fail");

        //axis settings
        chart.getXAxis().setValueFormatter(new XAxisLabelFormatter(labels));
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum((float) Math.max(stats[0], stats[1]) * 1.35f);
        chart.setScaleEnabled(false);

        styleLineBarChart(context, chart);
        return chart;
    }

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
        chart.getXAxis().setValueFormatter(new XAxisLabelFormatter(labels));
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum((float) sum * 1.3f);

        chart.setScaleEnabled(false);

        styleLineBarChart(context, chart);
        return chart;

    }

    /*https://stackoverflow.com/a/29812532/12471420*/
    private static Chart<?> makeCountLineChart(List<List<Trial>> trialsBucket, Context context) {

        float sum = 0;
        LocalDate currentDate = trialsBucket.get(0).get(0).getTrialDate();
        LocalDate lastDate = trialsBucket.get(trialsBucket.size() - 1).get(0).getTrialDate();

        List<Entry> data = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        int i = 0;
        while (currentDate.isBefore(lastDate)) {
            if (trialsBucket.get(i).get(0).getTrialDate().equals(currentDate)) {
                sum += StatsMaker.calcSum(trialsBucket.get(i));
                i++;
            }
            data.add(new Entry(i, sum));
            dates.add(shortFormatter.format(currentDate));
            currentDate = currentDate.plusDays(1);
        }

        LineChart chart = new LineChart(context);

        //dataset for marker
        LineDataSet dataSet = new LineDataSet(data, "Count over time");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        chart.setData(new LineData(dataSet));
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setValueFormatter(new XAxisLabelFormatter(dates));

        styleLineBarChart(context, chart);
        return chart;
    }

    private static void styleLineBarChart(Context context, BarLineChartBase chart) {
        final int beige1 = ContextCompat.getColor(context, R.color.beige1);

        //marker for dataset
        ClickMarker marker = new ClickMarker(context, R.layout.marker_content);
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

        chart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        chart.setBorderColor(R.color.beige1);
        chart.setDescription(null);
        chart.setNoDataText("No data available yet!");
        chart.setNoDataTextColor(ContextCompat.getColor(context, R.color.beige1));


    }

    public static List<List<Trial>> groupTrialByDate(ArrayList<Trial> trials) {
        //divides trials into buckets by date
        List<List<Trial>> output = new ArrayList<>(trials.stream().collect(Collectors.groupingBy(trial ->
                dateFormatter.format(trial.getTrialDate()))).values());
        //sort by date
        output.sort((l1, l2) -> l1.get(0).getTrialDate().compareTo(l2.get(0).getTrialDate()));
        return output;
    }

    //currently only applicable/useful to non negative trial
    public static List<List<Trial>> groupTrialsByValue(List<Trial> trials) {
        //assert type is Non negative
        if (BuildConfig.DEBUG && !(trials.get(0).getTrialType().equals(Trial.NONNEGATIVE))) {
            throw new AssertionError("Assertion failed");
        }

        List<List<Trial>> output = new ArrayList<>(trials.stream().collect(
                Collectors.groupingBy(trial -> ((NonNegativeTrial) trial).getCount())).values()
        );
        output.sort((list1, list2) -> Integer.compare(((NonNegativeTrial)list1.get(0)).getCount(), ((NonNegativeTrial)list2.get(0)).getCount()));
        return output;
    }

    private static class XAxisLabelFormatter extends ValueFormatter {

        List<String> labels;

        XAxisLabelFormatter(List<String> labels) {
            this.labels = labels;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return (Math.abs(value - (int) value) < 0.01f) ? labels.get((int) value) : "";
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
    private MPPointF mOffset;

    public ClickMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        fmt = new DecimalFormat("0.##");
        // find your layout components
        content = findViewById(R.id.markerContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        content.setText(fmt.format(e.getY()));

        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight() * 1.35f);
        }

        return mOffset;
    }
}