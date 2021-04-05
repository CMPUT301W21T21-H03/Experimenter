package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
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
    static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //!! this assumes all trials are of the same kind
    public static Chart makeHistogram(ArrayList<Trial> trials, Context context) {
        List<List<Trial>> trialsDateBucket = groupTrialByDate(trials);
        return null;
    }


    public static Chart makeLineChart(ArrayList<Trial> trials, Context context) {

        List<List<Trial>> trialsDateBucket = groupTrialByDate(trials);
        switch (trials.get(0).getTrialType()) {
            case Trial.COUNT:
                return makeCountLineChart(trialsDateBucket, context);

            default:
                return null;
        }
    }

    /*https://stackoverflow.com/a/29812532/12471420*/
    private static Chart makeCountLineChart(List<List<Trial>> trialsBucket, Context context) {

        float sum = 0;
        LocalDate currentDate = trialsBucket.get(0).get(0).getTrialDate();
        LocalDate lastDate = trialsBucket.get(trialsBucket.size() - 1).get(0).getTrialDate();

        List<Entry> data = new ArrayList<>();
        int i = 0;
        while (currentDate.isBefore(lastDate)) {
            if (trialsBucket.get(i).get(0).getTrialDate().equals(currentDate)) {
                sum += StatsMaker.calcSum(trialsBucket.get(i));
                i++;
            }
            data.add(new Entry(i, sum));
            currentDate = currentDate.plusDays(1);
        }
        LineChart chart = new LineChart(context);

        //dataset for marker
        LineDataSet dataSet = new LineDataSet(data, "Count over time");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setDrawValues(false);


        chart.setData(new LineData(dataSet));


        styleLineBarChart(context, chart);
        return chart;
    }

    private static void styleLineBarChart(Context context, BarLineChartBase chart) {
        final int beige1 = ContextCompat.getColor(context, R.color.beige1);

        //marker for dataset
        ClickMarker marker = new ClickMarker(context, R.layout.marker_content);
        chart.setMarker(marker);

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
                df.format(trial.getTrialDate()))).values());
        //sort by date
        output.sort((l1, l2) -> l1.get(0).getTrialDate().compareTo(l2.get(0).getTrialDate()));
        return output;
    }


}

/*
https://github.com/PhilJay/MPAndroidChart/wiki/IMarker-Interface
show marker on click
 */

class ClickMarker extends MarkerView {

    private TextView content;
    private DecimalFormat fmt;

    public ClickMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        fmt = new DecimalFormat("0.##");
        // find your layout components
        content = (TextView) findViewById(R.id.markerContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        content.setText(fmt.format(e.getY()));

        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight() * 1.35f);
        }

        return mOffset;
    }
}