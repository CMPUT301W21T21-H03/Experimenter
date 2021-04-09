package com.DivineInspiration.experimenter.Activity.UI.Stats;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Activity.Observer;
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

    /**
     * When creating view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     * view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.experiment_statistics, container, false);
    }

    /**
     * When view is created
     * @param view
     * view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(getView());
    }

    /**
     * Initializing the fragment
     * @param view
     * view itself
     */
    private void init(View view) {

        buttonGroup = view.findViewById(R.id.statButtonGroup);
        backButton = view.findViewById(R.id.statBackButton);
        graphHolder = view.findViewById(R.id.graphHolder);
        statHolder = view.findViewById(R.id.statHolder);

        // Testing data
//        for (int i = 0; i < 5; i++) {
//            trialList.add(new MeasurementTrial());
//        }

        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

        ((ViewGroup) view.findViewById(R.id.statFrame)).setLayoutTransition(layoutTransition);

        // shows the various features
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

    /**
     * When resuming the fragment
     */
    @Override
    public void onResume() {
        super.onResume();
//        if (trialList != null && trialList.size() == 0) {
//            Log.d("woah", "Loc B");
//            showWarning();
//        }
    }

    /**
     * Displaying the histogram
     */
    private void showHistogram() {
        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
        graphHolder.removeAllViews();
        graphHolder.addView(GraphMaker.makeHistogram(trialList, getContext()));
        statHolder.removeAllViews();
    }

    /**
     * Displaying the stats
     */
    private void showStats() {
        backButton.setVisibility(View.GONE);
        buttonGroup.setVisibility(View.VISIBLE);
        graphHolder.removeAllViews();
        statHolder.removeAllViews();
        statHolder.addView(StatsMaker.makeStatsView(getContext(), trialList));
    }

    /**
     * Displaying the line graph
     */
    private void showLineGraph() {
        backButton.setVisibility(View.VISIBLE);
        buttonGroup.setVisibility(View.GONE);
        graphHolder.removeAllViews();
        graphHolder.addView(GraphMaker.makeLineChart(trialList, getContext()));
        statHolder.removeAllViews();
    }

    /**
     * To be called when the content trialList should be updated
     */
    @Override
    public void update(Object data) {

        trialList.clear();
        trialList.addAll((List<Trial>) data);

//        for(Trial i : trialList){
////            Log.d("woah stats", i.toString());
//        }
//        Log.d("woah","outside list hash"+ System.identityHashCode(trialList));
//        Log.d("woah","outside object hash"+ System.identityHashCode(data));
        if (getView() != null) {
                showStats();
        }
    }
}
