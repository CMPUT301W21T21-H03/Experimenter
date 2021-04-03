package com.DivineInspiration.experimenter.Activity.UI.TrialTests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

public class NonNegativeTest extends Fragment {
    private NonNegativeTrial current;

    // text views
    TextView countTextBox;
    CheckBox requireGeo;
    TextView submit;

    private boolean requireGeoCheck;

    /**
     * Constructor
     */
//     * String trialUserID, String trialExperimentID
//     * @param trialUserID
//     * local user
//     * @param trialExperimentID
//     * experiment id
    public NonNegativeTest() {
        super(R.layout.trial_count);
//        NonNegativeTrial trial = new NonNegativeTrial(trialUserID, trialExperimentID);
//        current = trial;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle extra = getArguments();
        current = (NonNegativeTrial) extra.getSerializable("trial");

        countTextBox = view.findViewById(R.id.editTextNumber);
        requireGeo = view.findViewById(R.id.require_geo_location2);
        submit = view.findViewById(R.id.count_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countTextBox.toString() == "") {
                    // counts as cancel for now
                    // todo: return invalid
                } else {
                    current.setCount(Integer.parseInt(countTextBox.toString()));
                    // record to experiment manage and exit
                }
            }
        });

        requireGeo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // set geo check to the state of checkbox
                requireGeoCheck = b;
                // do something? or store in Trial?
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo: return and add to manager
            }
        });

    }

    public Trial getTrial() {
        return current;
    }
}
