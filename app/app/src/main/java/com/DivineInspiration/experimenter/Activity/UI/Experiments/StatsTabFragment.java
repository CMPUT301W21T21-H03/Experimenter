package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Activity.UI.Refreshable;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;

import java.time.LocalDate;
import java.util.ArrayList;

public class StatsTabFragment extends Fragment implements Refreshable {

    Chart chart;
    View buttonGroup;
    AppCompatImageButton backButton;

    ViewGroup graphHolder;
    ArrayList<Trial> trialList;

    private short currentlyVisible = 0;
    private static final short BUTTONS = 0;
    private static final short LINE = 1;
    private static final short BAR = 2;



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

    private void init(View view){
        Log.d("woah", "entering init");

        buttonGroup = view.findViewById(R.id.statButtonGroup);
        backButton = view.findViewById(R.id.statBackButton);
        graphHolder = view.findViewById(R.id.graphHolder);

        ArrayList<Trial> counts = new ArrayList<>();
        for(int i =0; i < 100; i++){
            counts.add(new NonNegativeTrial());
        }

        graphHolder.addView(GraphMaker.makeHistogram(counts, getContext()));
        Log.d("woah", "graph added");
        view.findViewById(R.id.histogramButton).setOnClickListener(v -> {
            showHistogram();
        });

        view.findViewById(R.id.lineGraphButton).setOnClickListener(v->{
            showLineGraph();
        });

        view.findViewById(R.id.statBackButton).setOnClickListener(v->{
            showButtons();
        });
    }

    private void showHistogram(){
        currentlyVisible = BAR;

        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
    }

    private void showButtons(){
        currentlyVisible = BUTTONS;

        backButton.setVisibility(View.GONE);
        buttonGroup.setVisibility(View.VISIBLE);
    }

    private void showLineGraph(){


        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
    }


    @Override
    public void refresh() {

    }
}
