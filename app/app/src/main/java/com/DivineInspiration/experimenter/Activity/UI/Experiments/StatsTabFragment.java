package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Activity.UI.Refreshable;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class StatsTabFragment extends Fragment implements Refreshable {

    LineChart lineChart;
    BarChart barChart;
    View buttonGroup;
    Button backButton;

    ArrayList<Trial> trialList;

    private short currentlyVisible = 0;
    private static final short BUTTONS = 0;
    private static final short LINE = 1;
    private static final short BAR = 2;



    public StatsTabFragment(ArrayList<Trial> trials){
        trialList = trials;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.experiment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void init(View view){
        lineChart = view.findViewById(R.id.lineChart);
        barChart = view.findViewById(R.id.barChart);
        buttonGroup = view.findViewById(R.id.statButtonGroup);
        backButton = view.findViewById(R.id.statBackButton);


        view.findViewById(R.id.hitogramButton).setOnClickListener(v -> {
            showHistogram();
        });

        view.findViewById(R.id.lineChart).setOnClickListener(v->{
            showLineGraph();
        });

        view.findViewById(R.id.statBackButton).setOnClickListener(v->{
            showButtons();
        });
    }

    private void showHistogram(){
        currentlyVisible = BAR;
        lineChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
    }

    private void showButtons(){
        currentlyVisible = BUTTONS;
        lineChart.setVisibility(View.GONE);
        barChart.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        buttonGroup.setVisibility(View.VISIBLE);
    }

    private void showLineGraph(){

        lineChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
    }


    @Override
    public void refresh() {

    }
}
