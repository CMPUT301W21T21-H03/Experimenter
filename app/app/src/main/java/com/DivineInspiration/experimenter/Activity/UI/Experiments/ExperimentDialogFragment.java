package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
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

import com.DivineInspiration.experimenter.Controller.CommentManager;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


/**
 * This class provides the UI for an owner to creating or edit an experiment.
 * Runs either when: addButton is clicked while on Experiments tab of home page (creates new experiment)
 *                   settings button is clicked in the ExperimentFragment
 * @see com.DivineInspiration.experimenter.R.layout#experiment_dialog_fragment - XML layout file for this fragment
 */
public class ExperimentDialogFragment extends DialogFragment {
    // Instance variables
    private OnExperimentOperationDoneListener callback;
    private ExperimentManager expManager = ExperimentManager.getInstance();
    private TextView editExperimentName;
    private Spinner trialSpinner;
    private TextView editCity;
    private TextView editExperimentAbout;
    private TextView minTrial;
    private CheckBox requireGeo;
    private Spinner statusSpinner;

    private Experiment exp;
    private Fragment parentFrag;

    // Options for the type of experiment (will be displayed in a drop-down fashion).
    private String[] expOptions = {"Counting", "Binomial", "NonNegative", "Measuring"};
    private String[] expValues = {Trial.COUNT, Trial.BINOMIAL, Trial.NONNEGATIVE, Trial.MEASURE};
    private String currentExpSelection;

    // Options for the status of the experiment (will be displayed in a drop-down fashion).
    private String[] statusOptions = {"On going", "Hidden"};
    private String[] statusValues = {Experiment.ONGOING, Experiment.HIDDEN};
    private String currentStatusSelection;

    private TextInputLayout nameInput;
    private TextInputLayout countInput;
    private AlertDialog dialog;

    /**
     * Interface definition for a callback to be invoked when
     * {@link com.DivineInspiration.experimenter.Activity.UI.Experiments.ExperimentDialogFragment}
     * creates a new {@link com.DivineInspiration.experimenter.Model.Experiment}
     */
    public interface OnExperimentOperationDoneListener {

        /**
         * Called when {@link com.DivineInspiration.experimenter.Activity.UI.Experiments.ExperimentDialogFragment}
         * creates a new {@link com.DivineInspiration.experimenter.Model.Experiment}
         * @param experiment
         * The experiment that was created
         */
        void onOperationDone(Experiment experiment);
    }

    /**
     * Fragment Constructor
     * @param callback callback function
     */
    public ExperimentDialogFragment(OnExperimentOperationDoneListener callback) {
        super();
        this.callback = callback;
    }

    /**
     * Shows alert message on the bottom of the parent fragment page
     * @param error if the alert an error
     * @param message alert message to display
     */
    private void showAlert(boolean error, String message) {
        Snackbar snackbar = Snackbar.make(parentFrag.getView(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor(error ? "#913c3c" : "#2e6b30")); // Depending on error chose green or red color
        snackbar.show();
    }

    /**
     * Runs when the dialog is created.
     * @param savedInstanceState
     * @return dialog instance
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_dialog_fragment, null);
        User localUser = UserManager.getInstance().getLocalUser();

        init(view);     // Initialize the View's in the dialog UI
        // Shows the dialog (must be called at start)
        dialog.show();

        if(exp!= null && exp.getStatus().equals(Experiment.ENDED)){
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        }

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editExperimentNameText = editExperimentName.getText().toString();
                String editCityText = editCity.getText().toString();
                String editExperimentAboutText = editExperimentAbout.getText().toString();      // Optional

                // If entered data is invalid or empty, show error
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

                // If not valid in any step in the 2 if's above, return
                if (!validFlag) {
                    return;
                }

                if (exp == null) {  // Here, we create a new experiment using info entered in the dialog
                    // Construct a new Experiment object and add it using experiment manager
                    Experiment temp = new Experiment(editExperimentNameText, localUser.getUserId(), localUser.getUserName(), editExperimentAboutText, currentExpSelection, editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked(), Experiment.ONGOING);
                    expManager.getInstance().addExperiment(temp, successful -> {
                        if (successful) {
                            // Show success message at bottom of screen
                            showAlert(false, "Created new experiment!");
                            callback.onOperationDone(temp);
                        } else {
                            // Show failed to create experiment message at bottom of screen
                            showAlert(true, "Failed to create experiment!");
                        }
                    });
                } else { // Here we update and already existing experiment using info entered in the dialog
                    showAlert(false, "Created new experiment!");

                    exp.setExperimentName(editExperimentNameText);
                    exp.setStatus(currentStatusSelection);
                    exp.setRequireGeo(requireGeo.isChecked());
                    exp.setMinimumTrials( Integer.parseInt(minTrial.getText().toString()));
                    exp.setExperimentDescription(editExperimentAboutText);
                    exp.setRegion(editCityText);

                    expManager.updateExperiment(exp, successful -> {
                        if (successful) {
                            // Show success message at bottom of screen
                            showAlert(false, "Edit experiment successful!");
                            callback.onOperationDone(exp);
                        } else {
                            // Show failed to create experiment message at bottom of screen
                            showAlert(true, "Failed to edit experiment!");
                        }
                    });
                }
                dialog.dismiss();       // Close dialog
            }
        });

        return dialog;
    }

    /**
     * This method initializes the views (instance variables)
     * @param view the dialog view
     */
    private void init(View view) {
        // Dialog for creating a new experiment
        dialog = new AlertDialog.Builder(getContext(), R.style.dialogColor)
                .setView(view)
                .setTitle("Create new experiment")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

       // dialog.getWindow().setBackgroundDrawableResource(R.color.black1);
        parentFrag = getParentFragment();

        // Initializing all the parts of dialog
        editExperimentName = view.findViewById(R.id.editExperimentName);
        editCity = view.findViewById(R.id.editExperimentCity);
        editExperimentAbout = view.findViewById(R.id.editExperimentAbout);
        minTrial = view.findViewById(R.id.editExperimentMin);
        requireGeo = view.findViewById(R.id.editExperimentGeo);
        // errors
        nameInput = view.findViewById(R.id.qrNameInput);
        countInput = view.findViewById(R.id.expCountInput);

        // Trial spinner (dropdown to choose the type of the trial the experiment needs)
        trialSpinner = view.findViewById(R.id.editExperimentSpinner);
        trialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentExpSelection = expValues[position];      // The selection of trial made by the user
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentExpSelection = expValues[0];             // Default selection of trial
            }
        });
        ArrayAdapter trialAdapter = new ArrayAdapter(getContext(), R.layout.create_experiment_spinner_item, expOptions);
        trialAdapter.setDropDownViewResource(R.layout.create_experiment_spinner_item);
        trialSpinner.setAdapter(trialAdapter);


        // Status spinner (dropdown to choose the status of the experiment)
        statusSpinner = view.findViewById(R.id.statusSpinner);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentStatusSelection = statusValues[position];    // The selection of status made by the user
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentStatusSelection = statusValues[0];           // Default selection of trial
            }
        });
        ArrayAdapter statusAdapter = new ArrayAdapter(getContext(), R.layout.create_experiment_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(R.layout.create_experiment_spinner_item);
        statusSpinner.setAdapter(statusAdapter);


        // Here, we deal with initialization when the dialog is created to edit an existing experiment
        // We come here when the settings icon is clicked on the right hand top corner
        Bundle args = getArguments();
        if (args != null) {
            dialog.setTitle("Edit experiment");
            exp = (Experiment) args.getSerializable("exp");

            if (exp.getStatus().equals(Experiment.ENDED)) {     // If the experiment has ended, only delete is available
                editExperimentAbout.setVisibility(View.GONE);
                minTrial.setVisibility(View.GONE);
                requireGeo.setVisibility(View.GONE);
                nameInput.setVisibility(View.GONE);
                countInput.setVisibility(View.GONE);
                editExperimentName.setVisibility(View.GONE);
                statusSpinner.setVisibility(View.GONE);
                trialSpinner.setVisibility(View.GONE);
                view.findViewById(R.id.endExp).setVisibility(View.GONE);
                view.findViewById(R.id.editAboutInput).setVisibility(View.GONE);
                view.findViewById(R.id.editRegionInput).setVisibility(View.GONE);
                view.findViewById(R.id.ownerButtons).setVisibility(View.VISIBLE);

                dialog.setTitle("This experiment has ended. Delete?");
            }
            else {                                          // If the experiment is still ongoing
                // Get edited information from the dialog
                editExperimentName.setText(exp.getExperimentName());
                editCity.setText(exp.getRegion());
                editExperimentAbout.setText(exp.getExperimentDescription());
                minTrial.setText(String.valueOf(exp.getMinimumTrials()));
                requireGeo.setChecked(exp.isRequireGeo());
                statusSpinner.setSelection(indexOf(statusValues, exp.getStatus()));
                trialSpinner.setVisibility(View.GONE);
                requireGeo.setVisibility(View.GONE);

                view.findViewById(R.id.ownerButtons).setVisibility(View.VISIBLE);

                view.findViewById(R.id.endExp).setOnClickListener(v -> {    // If the owner chooses to end the experiment
                    exp.setStatus(Experiment.ENDED);
                    //TODO add a warning dialog
                    expManager.updateExperiment(exp, successful -> {
                        dismiss();
                        callback.onOperationDone(exp);
                    });
                    //TODO display a success message
                });
            }

            view.findViewById(R.id.deleteExp).setOnClickListener(v -> {
                // If the owner chooses to delete the experiment
                // TODO: add a warning dialog!!

                TrialManager.getInstance().deleteAllTrialOfExperiment(exp.getExperimentID());
                CommentManager.getInstance().deleteAllCommentOfExperiment(exp.getExperimentID());
                expManager.deleteExperiment(exp.getExperimentID(), successful -> {
                    dismiss();
                    callback.onOperationDone(null);
                });
                // TODO: display a success message
            });
        }
    }

    /**
     * Index at a particular value
     * @param arr
     * array to find value
     * @param val
     * value to find
     * @return
     * index of the value
     */
    private int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(val)) return i;
        }
        // NOTE: 0???
        return 0;
    }
}