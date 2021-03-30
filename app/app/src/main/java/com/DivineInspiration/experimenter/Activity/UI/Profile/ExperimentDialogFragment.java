package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;

public class ExperimentDialogFragment extends DialogFragment  {


    // inits
    OnExperimentAddedListener callback;
    ExperimentManager expManager = ExperimentManager.getInstance();
    TextView editExperimentName;
    Spinner trialSpinner;
    TextView editCity;
    TextView editExperimentAbout;
    TextView minTrial;
    CheckBox requireGeo;
    Spinner statusSpinner;
    // error text
    TextView experimentError1;
    TextView experimentError2;
    TextView experimentError3;

    Experiment exp;

    Fragment parentFrag;

    // options for experiment
    private String[] expOptions = {"Counting", "Binomial", "NonNegative", "Measuring"};
    private String[] expValues = {Trial.COUNT, Trial.BINOMIAL, Trial.NONNEGATIVE, Trial.MEASURE};
    private String currentExpSelection;

    //options for status
    private String[] statusOptions = {"On going", "Hidden"};
    private String[] statusValues = {Experiment.ONGOING, Experiment.HIDDEN};
    private String currentStatusSelection;


    /**
     * When experiment is added
     */
    public interface OnExperimentAddedListener {
        void onExperimentAdded(Experiment experiment);
    }

    /**
     * Fragment Constructor
     * @param callback
     * callback function
     */
    public ExperimentDialogFragment(OnExperimentAddedListener callback) {
        super();
        this.callback = callback;
    }

    /**
     * Shows alert message on the bottom of the parent fragment page
     * @param error
     * is the alert an error
     * @param message
     * message
     */
    private void showAlert(boolean error, String message) {
        Snackbar snackbar = Snackbar.make(parentFrag.getView(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor(error ? "#913c3c" : "#2e6b30"));
        snackbar.show();
    }

    /**
     * Created dialog
     * @param savedInstanceState
     * the bundle
     * @return
     * dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // create view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_dialog_fragment, null);
        // get current user
        User localUser = UserManager.getInstance().getLocalUser();



        init(view);


        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setMessage("Create Experiment")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

        // shows dialog (must be called at start)
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editExperimentNameText = editExperimentName.getText().toString();
                String editCityText = editCity.getText().toString();
                // optional?
                String editExperimentAboutText = editExperimentAbout.getText().toString();



                // if invalid or empty, show error
                boolean validFlag = true;
                if (editExperimentNameText.length() == 0) {
                    // display error
                    experimentError1.setVisibility(TextView.VISIBLE);
                    validFlag = false;
                }

                if (minTrial.length() == 0 || Integer.parseInt(minTrial.getText().toString()) == 0) {
                    experimentError3.setVisibility(TextView.VISIBLE);
                    validFlag = false;
                }

                // if not valid in any step, return
                if (!validFlag) {
                    return;
                }


                if(exp == null){
                    //no extra arguments => just create new experiment
                    // generate new experiment and add to experiment manager

                    Experiment temp = new Experiment(editExperimentNameText, localUser.getUserId(), localUser.getUserName(), editExperimentAboutText, currentExpSelection, editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked(), Experiment.ONGOING);
                    expManager.getInstance().addExperiment(temp, successful -> {
                        if(successful){

                            // show success
                            showAlert(false, "Created new experiment!");
                            callback.onExperimentAdded(temp);
                        }
                        else{
                            // failed to create experiment
                            showAlert(true, "Failed to create experiment!");
                        }
                    });
                }
                else{
                    showAlert(false, "Created new experiment!");
                    //edit an existing id
                    Experiment temp = new Experiment(exp.getExperimentID(),editExperimentNameText, exp.getOwnerID(), exp.getOwnerName(), editExperimentAboutText, currentExpSelection, editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked(), currentStatusSelection);
                   expManager.updateExperiment(temp, successful -> {

                        if(successful){
                            // show success
                            showAlert(false, "Edit experiment successful!");
                            callback.onExperimentAdded(temp);
                        }
                        else{
                            // failed to create experiment
                            showAlert(true, "Failed to edit experiment!");
                        }
                    });
                }

                // closes dialog
                dialog.dismiss();
            }
        });

        return  dialog;
    }

    private void init(View view){

        parentFrag = getParentFragment();

        // inits all the parts of dialog
        editExperimentName = view.findViewById(R.id.editExperimentName);

        editCity = view.findViewById(R.id.editExperimentCity);
        editExperimentAbout = view.findViewById(R.id.editExperimentAbout);
        minTrial = view.findViewById(R.id.editExperimentMin);
        requireGeo = view.findViewById(R.id.editExperimentGeo);
        // errors
        experimentError1 = view.findViewById(R.id.experimentError1);
        experimentError2 = view.findViewById(R.id.experimentError2);
        experimentError3 = view.findViewById(R.id.experimentError3);
        // reset all
        experimentError1.setVisibility(TextView.GONE);
        experimentError2.setVisibility(TextView.GONE);
        experimentError3.setVisibility(TextView.GONE);

        //trial spinner
        trialSpinner = view.findViewById(R.id.editExperimentSpinner);
        trialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentExpSelection = expValues[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentExpSelection = expValues[0];
            }
        });

        ArrayAdapter trialAdapter = new ArrayAdapter(getContext(), R.layout.create_experiment_spinner_item, expOptions);
        trialAdapter.setDropDownViewResource(R.layout.create_experiment_spinner_item);
        trialSpinner.setAdapter(trialAdapter);


        //status spinner
        statusSpinner = view.findViewById(R.id.statusSpinner);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentStatusSelection = statusValues[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentStatusSelection = statusValues[0];
            }
        });

        ArrayAdapter statusAdapter = new ArrayAdapter(getContext(), R.layout.create_experiment_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(R.layout.create_experiment_spinner_item);
        statusSpinner.setAdapter(statusAdapter);


        Bundle args = getArguments();
        if(args != null){
             exp = (Experiment)args.getSerializable("exp");

             editExperimentName .setText( exp.getExperimentName());
             editCity.setText( exp.getRegion());
             editExperimentAbout.setText(exp.getExperimentDescription());
             minTrial.setText(String.valueOf(exp.getMinimumTrials()));
             requireGeo.setChecked(exp.isRequireGeo());

             statusSpinner.setSelection(indexOf(statusValues, exp.getStatus()));
            trialSpinner.setSelection(indexOf(expValues, exp.getTrialType()));

        }

    }

    private int indexOf(String[] arr, String val){
        for(int i = 0; i< arr.length; i++){
            if(arr[i].equals(val)) return i;
        }
        return 0;
    }
}
