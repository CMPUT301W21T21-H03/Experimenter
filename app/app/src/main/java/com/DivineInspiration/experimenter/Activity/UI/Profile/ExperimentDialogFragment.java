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
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ExperimentDialogFragment extends DialogFragment {


    // inits
    OnExperimentOperationDoneListener callback;
    ExperimentManager expManager = ExperimentManager.getInstance();
    TextView editExperimentName;
    Spinner trialSpinner;
    TextView editCity;
    TextView editExperimentAbout;
    TextView minTrial;
    CheckBox requireGeo;
    Spinner statusSpinner;
    // error text

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

    TextInputLayout nameInput;

    TextInputLayout countInput;


    AlertDialog dialog;

    /**
     * When experiment is added
     */
    public interface OnExperimentOperationDoneListener {
        void onOperationDone(Experiment experiment);
    }

    /**
     * Fragment Constructor
     *
     * @param callback callback function
     */
    public ExperimentDialogFragment(OnExperimentOperationDoneListener callback) {
        super();
        this.callback = callback;
    }

    /**
     * Shows alert message on the bottom of the parent fragment page
     *
     * @param error   is the alert an error
     * @param message message
     */
    private void showAlert(boolean error, String message) {
        Snackbar snackbar = Snackbar.make(parentFrag.getView(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor(error ? "#913c3c" : "#2e6b30"));
        snackbar.show();
    }

    /**
     * Created dialog
     *
     * @param savedInstanceState the bundle
     * @return dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // create view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_dialog_fragment, null);
        // get current user
        User localUser = UserManager.getInstance().getLocalUser();


        init(view);


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
                    nameInput.setError("Name cannot be empty!");
                    validFlag = false;
                }

                if (minTrial.length() == 0 || Integer.parseInt(minTrial.getText().toString()) <= 0) {
                    countInput.setError("A valid number is required!");
                    validFlag = false;
                }

                // if not valid in any step, return
                if (!validFlag) {
                    return;
                }


                if (exp == null) {
                    //no extra arguments => just create new experiment
                    // generate new experiment and add to experiment manager

                    Experiment temp = new Experiment(editExperimentNameText, localUser.getUserId(), localUser.getUserName(), editExperimentAboutText, currentExpSelection, editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked(), Experiment.ONGOING);
                    expManager.getInstance().addExperiment(temp, successful -> {
                        if (successful) {

                            // show success
                            showAlert(false, "Created new experiment!");
                            callback.onOperationDone(temp);
                        } else {
                            // failed to create experiment
                            showAlert(true, "Failed to create experiment!");
                        }
                    });
                } else {
                    showAlert(false, "Created new experiment!");
                    //edit an existing id
                    Experiment temp = new Experiment(exp.getExperimentID(), editExperimentNameText, exp.getOwnerID(), exp.getOwnerName(), editExperimentAboutText, currentExpSelection, editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked(), currentStatusSelection);
                    expManager.updateExperiment(temp, successful -> {

                        if (successful) {
                            // show success
                            showAlert(false, "Edit experiment successful!");
                            callback.onOperationDone(temp);
                        } else {
                            // failed to create experiment
                            showAlert(true, "Failed to edit experiment!");
                        }
                    });
                }

                // closes dialog
                dialog.dismiss();
            }
        });

        return dialog;
    }

    private void init(View view) {

        dialog = new AlertDialog.Builder(getContext(), R.style.dialogColor)
                .setView(view)
                .setTitle("Create new experiment")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

       // dialog.getWindow().setBackgroundDrawableResource(R.color.black1);
        parentFrag = getParentFragment();

        // inits all the parts of dialog
        editExperimentName = view.findViewById(R.id.editExperimentName);

        editCity = view.findViewById(R.id.editExperimentCity);
        editExperimentAbout = view.findViewById(R.id.editExperimentAbout);
        minTrial = view.findViewById(R.id.editExperimentMin);
        requireGeo = view.findViewById(R.id.editExperimentGeo);
        // errors
        nameInput = view.findViewById(R.id.expNameInput);
        countInput = view.findViewById(R.id.expCountInput);




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
        if (args != null) {
            dialog.setTitle("Edit experiment");
            exp = (Experiment) args.getSerializable("exp");

            editExperimentName.setText(exp.getExperimentName());
            editCity.setText(exp.getRegion());
            editExperimentAbout.setText(exp.getExperimentDescription());
            minTrial.setText(String.valueOf(exp.getMinimumTrials()));
            requireGeo.setChecked(exp.isRequireGeo());

            statusSpinner.setSelection(indexOf(statusValues, exp.getStatus()));
            trialSpinner.setSelection(indexOf(expValues, exp.getTrialType()));

            view.findViewById(R.id.ownerButtons).setVisibility(View.VISIBLE);

            view.findViewById(R.id.deleteExp).setOnClickListener(v -> {
                //TODO add a warning dialog!!
                expManager.deleteExperiment(exp.getExperimentID(), successful -> {
                    dismiss();
                    callback.onOperationDone(null);
                });
                //TODO display a success message
            });

            view.findViewById(R.id.endExp).setOnClickListener(v -> {
                exp.setStatus(Experiment.ENDED);
                //TODO add a warning dialog
                expManager.updateExperiment(exp, successful -> {
                    dismiss();
                    callback.onOperationDone(exp);
                });
                //TODO display a success message
            });
        }


    }


    private int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(val)) return i;
        }
        return 0;
    }
}
