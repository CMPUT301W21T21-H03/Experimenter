package com.DivineInspiration.experimenter.Activity.UI.TrialTests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

public class NonNegativeTest extends TrialTests {
    /**
     * Constructor
     * @param trialUserID
     * local user
     * @param trialExperimentID
     * experiment id
     */
    public NonNegativeTest(String trialUserID, String trialExperimentID) {
        Trial trial = new NonNegativeTrial(trialUserID, trialExperimentID);
        current = trial;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.trial_positive_count, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
