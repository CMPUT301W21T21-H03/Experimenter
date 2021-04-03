package com.DivineInspiration.experimenter.Activity.UI.TrialTests;

import android.graphics.Color;
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
import androidx.navigation.Navigation;

import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;

public class MeasureTest extends Fragment {
    private MeasurementTrial current;
    private Experiment currentExp;
    // text views
    TextView countTextBox;
    CheckBox requireGeo;
    TextView submit;

    private boolean requireGeoCheck;

    /**
     * Constructor
     */
    public MeasureTest() {
        super(R.layout.trial_count);
//        MeasurementTrial trial = new MeasurementTrial(trialUserID, trialExperimentID);
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
        current = (MeasurementTrial) extra.getSerializable("trial");
        if(current == null){
            currentExp = (Experiment)extra.getSerializable("experiment");
            assert(currentExp != null);
            current = new MeasurementTrial(currentExp.getOwnerID(), currentExp.getExperimentID());
        }

        countTextBox = view.findViewById(R.id.editTextNumber);
        requireGeo = view.findViewById(R.id.require_geo_location2);
        submit = view.findViewById(R.id.count_submit);

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
                String countText = countTextBox.getText().toString();
                if (countText.length() == 0) {
                    // counts as cancel for now
                    // show error
                    showAlert(true,"Trial not recorded");
                } else {
                    current.addMeasurement(Float.parseFloat(countText));
                    // record to experiment manager
                    TrialManager.getInstance().addTrial(current);
                    showAlert(false,"Trial was successfully recorded!");
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
