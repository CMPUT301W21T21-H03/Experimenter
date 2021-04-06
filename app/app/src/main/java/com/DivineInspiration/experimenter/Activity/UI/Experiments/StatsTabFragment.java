package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Activity.UI.Refreshable;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.github.mikephil.charting.charts.Chart;

import java.util.ArrayList;

public class StatsTabFragment extends Fragment implements Refreshable {

    private static final short BUTTONS = 0;
    private static final short LINE = 1;
    private static final short BAR = 2;
    Chart chart;
    View buttonGroup;
    AppCompatImageButton backButton;
    ViewGroup graphHolder;
    ViewGroup statHolder;
    ArrayList<Trial> trialList;
    private short currentlyVisible = 0;


//    public StatsTabFragment(ArrayList<Trial> trials){
//        trialList = trials;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.experiment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);


    }

    private void init(View view) {
        Log.d("woah", "entering init");

        buttonGroup = view.findViewById(R.id.statButtonGroup);
        backButton = view.findViewById(R.id.statBackButton);
        graphHolder = view.findViewById(R.id.graphHolder);
        statHolder = view.findViewById(R.id.statHolder);

        trialList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            trialList.add(new NonNegativeTrial());
        }

        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

        ((ViewGroup) view.findViewById(R.id.statFrame)).setLayoutTransition(layoutTransition);

        view.findViewById(R.id.histogramButton).setOnClickListener(v -> {
            showHistogram();
        });

        view.findViewById(R.id.lineGraphButton).setOnClickListener(v -> {
            showLineGraph();
        });

        view.findViewById(R.id.statBackButton).setOnClickListener(v -> {
            showStats();
        });

        showStats();
    }


    private void showHistogram() {
        currentlyVisible = BAR;

        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
        graphHolder.removeAllViews();
        graphHolder.addView(GraphMaker.makeHistogram(trialList, getContext()));
        statHolder.removeAllViews();
    }

    private void showStats() {
        currentlyVisible = BUTTONS;

        backButton.setVisibility(View.GONE);
        buttonGroup.setVisibility(View.VISIBLE);
        graphHolder.removeAllViews();
        statHolder.addView(StatsMaker.makeStatsView(getContext(), trialList));

    }

    private void showLineGraph() {
        currentlyVisible = LINE;

        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
        graphHolder.removeAllViews();
        graphHolder.addView(GraphMaker.makeLineChart(trialList, getContext()));
        statHolder.removeAllViews();
    }


    @Override
    public void refresh() {

    }
}
