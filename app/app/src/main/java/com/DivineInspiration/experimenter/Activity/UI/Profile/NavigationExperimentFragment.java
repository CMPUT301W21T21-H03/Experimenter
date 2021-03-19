package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;


public class NavigationExperimentFragment extends Fragment {
    private TextView experimentName;
    private TextView ownerName;
    private TextView subNumber;
    private TextView trialNumber;
    private TextView trialType;
    private  TextView expAbout;
    private TextView expCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_experiment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        experimentName = view.findViewById(R.id.experimentName_expFrag);
        ownerName = view.findViewById(R.id.ownerName_expFrag);
        subNumber = view.findViewById(R.id.subscribersNumber_expFrag);
        trialNumber = view.findViewById(R.id.trialNumber_expFrag);
        trialType = view.findViewById(R.id.trialType_expFrag);
        expAbout = view.findViewById(R.id.experimentDescription_expFrag);
        expCity = view.findViewById(R.id.experimentRegion_expFrag);

        Experiment exp = (Experiment)getArguments().getSerializable("experiment");
        experimentName.setText(exp.getExperimentName());
        ownerName.setText(exp.getOwnerName());
        expCity.setText(exp.getRegion());
        trialNumber.setText(String.valueOf(exp.getMinimumTrials()));
        trialType.setText(String.valueOf(exp.getTrialType()));
        expAbout.setText(exp.getExperimentDescription());
    }
}