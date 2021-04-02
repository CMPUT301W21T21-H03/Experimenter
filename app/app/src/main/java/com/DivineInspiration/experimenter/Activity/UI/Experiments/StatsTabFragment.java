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

import com.DivineInspiration.experimenter.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

public class StatsTabFragment extends Fragment {

    LineChart lineChart;
    BarChart barChart;
    View buttonGroup;
    Button backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.experiment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.hitogramButton).setOnClickListener(v -> {

        });
    }

    private void init(View v){
        lineChart = v.findViewById(R.id.lineChart);
        barChart = v.findViewById(R.id.barChart);
        buttonGroup = v.findViewById(R.id.statButtonGroup);
        backButton = v.findViewById(R.id.statBackButton);
    }

    private void showHistogram(){
        lineChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        //backButton.setVisibility();
    }

    private void showButtons(){

    }

    private void showLineGraph(){

    }

}
