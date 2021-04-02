package com.DivineInspiration.experimenter.Activity.UI.TrialTests;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

public class BinomialTest extends Fragment {
    private BinomialTrial current;

    // text views
    TextView passBtn;
    TextView failBtn;
    CheckBox requireGeo;
    TextView submit;
    TextView passCount;
    TextView failCount;

    private boolean requireGeoCheck;

    /**
     * Constructor
     * @param trialUserID
     * local user
     * @param trialExperimentID
     * experiment id
     */
    public BinomialTest(String trialUserID, String trialExperimentID) {
        super(R.layout.trial_binomial_count);
        BinomialTrial trial = new BinomialTrial(trialUserID, trialExperimentID);
        current = trial;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        passBtn = view.findViewById(R.id.pass);
        failBtn = view.findViewById(R.id.fail);
        requireGeo = view.findViewById(R.id.require_geo_location2);
        submit = view.findViewById(R.id.count_submit);
        passCount = view.findViewById(R.id.binomialCount);
        failCount = view.findViewById(R.id.binomialCount2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current.getSuccess() == 0 && current.getFailure() == 0) {
                    // counts as cancel for now
                    // todo: return invalid
                } else {
                    // record to experiment manage and exit
                }
            }
        });

        // when pass btn is clicked
        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current.addSuccess();
                passCount.setText(String.format("Passed: %d", current.getSuccess()));
            }
        });
        // when fail btn is clicked
        failBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current.addFailure();
                failCount.setText(String.format("Failed: %d", current.getFailure()));
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
