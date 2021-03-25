package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import static com.DivineInspiration.experimenter.Activity.UI.Profile.EditProfileDialogFragment.TAG;


public class SettingsExperimentDialogFragment extends Fragment {
    Button deleteExperimentSettings;
    TextView settingsExperimentSettings;
    ExperimentManager experimentManager = ExperimentManager.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_experiment_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsExperimentSettings = view.findViewById(R.id.settings_experiment_name);
        deleteExperimentSettings = view.findViewById(R.id.delete_experiment_button);

        Experiment exp = (Experiment)getArguments().getSerializable("settingsExperiment");

        settingsExperimentSettings.setText(exp.getExperimentName());

        deleteExperimentSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experimentManager.deleteExperiment(exp.getExperimentID());
               Navigation.findNavController(view).navigateUp();
               Navigation.findNavController(view).navigateUp();

            }
        });
    }


}
