package com.DivineInspiration.experimenter.Activity.UI.TrialTests;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;

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
     */
    public BinomialTest() {
        super(R.layout.trial_binomial_count);
//        BinomialTrial trial = new BinomialTrial(trialUserID, trialExperimentID);
//        current = trial;
    }

    /**
     * Shows alert message on the bottom of the parent fragment page
     * @param error   is the alert an error
     * @param message message
     */
    private void showAlert(boolean error, String message) {
        Snackbar snackbar = Snackbar.make(getParentFragment().getView(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor(error ? "#913c3c" : "#2e6b30"));
        snackbar.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle extra = getArguments();
        current = (BinomialTrial) extra.getSerializable("trial");

        passBtn = view.findViewById(R.id.pass);
        failBtn = view.findViewById(R.id.fail);
        requireGeo = view.findViewById(R.id.require_geo_location2);
        submit = view.findViewById(R.id.count_submit);
        passCount = view.findViewById(R.id.binomialCount);
        failCount = view.findViewById(R.id.binomialCount2);

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
                if (current.getSuccess() == 0 && current.getFailure() == 0) {
                    // counts as cancel for now
                    // show error
                    showAlert(true,"Trial not recorded");
                } else {
                    // record to experiment manage and exit
                }
                // return
                Navigation.findNavController(view).popBackStack();
            }
        });

    }


    public Trial getTrial() {
        return current;
    }
}
