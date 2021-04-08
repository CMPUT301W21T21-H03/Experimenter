package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Activity.Observer;
import com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI.CreateTrialDialogFragment;
import com.DivineInspiration.experimenter.Activity.UI.Refreshable;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment to handle displaying stats and graphs of a experiment
 */
public class StatsTabFragment extends Fragment implements Observer {


    View buttonGroup;
    AppCompatImageButton backButton;
    ViewGroup graphHolder;
    ViewGroup statHolder;
    List<Trial> trialList = new ArrayList<>();
    TextView warningText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.experiment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init(getView());

    }

    private void init(View view) {


        buttonGroup = view.findViewById(R.id.statButtonGroup);
        backButton = view.findViewById(R.id.statBackButton);
        graphHolder = view.findViewById(R.id.graphHolder);
        statHolder = view.findViewById(R.id.statHolder);
        warningText = view.findViewById(R.id.statWarningText);


        // Testing data
//        for (int i = 0; i < 5; i++) {
//            trialList.add(new MeasurementTrial());
//        }

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


        if (trialList.size() > 2) {
            showStats();
        }else
        {
            showWarning();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
//        if (trialList != null && trialList.size() == 0) {
//            Log.d("woah", "Loc B");
//            showWarning();
//        }

    }

    private void showHistogram() {

        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
        graphHolder.removeAllViews();
        graphHolder.addView(GraphMaker.makeHistogram(trialList, getContext()));
        statHolder.removeAllViews();
        warningText.setVisibility(View.GONE);
    }

    private void showStats() {

        backButton.setVisibility(View.GONE);
        buttonGroup.setVisibility(View.VISIBLE);
        graphHolder.removeAllViews();
        statHolder.removeAllViews();
        statHolder.addView(StatsMaker.makeStatsView(getContext(), trialList));
        warningText.setVisibility(View.GONE);
    }

    private void showLineGraph() {

        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
        graphHolder.removeAllViews();
        graphHolder.addView(GraphMaker.makeLineChart(trialList, getContext()));
        statHolder.removeAllViews();
        warningText.setVisibility(View.GONE);
    }


    private void showWarning() {
        warningText.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.GONE);
        buttonGroup.setVisibility(View.GONE);
        graphHolder.removeAllViews();
        statHolder.removeAllViews();
    }


    /**
     * To be called when the content trialList should be updated
     */



    @Override
    public void update(Object data) {
        trialList.clear();
        trialList.addAll((List<Trial>) data);

        if(getView() != null){
            if (trialList.size() > 2) {
                showStats();
            }else
            {
                showWarning();
            }
        }

    }
}
