package com.DivineInspiration.experimenter.Activity.UI.Profile;

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

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;

public class CreateExperimentDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {


    // inits
    OnExperimentAddedListener callback;
    ExperimentManager newExperiment = ExperimentManager.getInstance();
    TextView editExperimentName;
    Spinner trialSpinner;
    TextView editCity;
    TextView editExperimentAbout;
    TextView minTrial;
    CheckBox requireGeo;
    // error text
    TextView experimentError1;
    TextView experimentError2;
    TextView experimentError3;

    // options for experiment
    private String[] options = {"Counting", "Binomial", "NonNegative", "Measuring"};
    private String[] values = {Trial.COUNT, Trial.BINOMIAL, Trial.NONNEGATIVE, Trial.MEASURE};
    private String currentSelection;

    public static String TAG = "create experiment";

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
    public CreateExperimentDialogFragment(OnExperimentAddedListener callback) {
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
        Snackbar snackbar = Snackbar.make(getParentFragment().getView(), message, Snackbar.LENGTH_LONG);
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_experiment_dialog_fragment, null);
        // get current user
        User newUser = UserManager.getInstance().getLocalUser();

        // inits all the parts of dialog
        editExperimentName = view.findViewById(R.id.editExperimentName);
        trialSpinner = view.findViewById(R.id.editExperimentSpinner);
        editCity = view.findViewById(R.id.editExperimentCity);
        editExperimentAbout = view.findViewById(R.id.editExperimentAbout);
        minTrial = view.findViewById(R.id.editExperimentMin);
        requireGeo = view.findViewById(R.id.editExperimentGeo);
        // errors
        experimentError1 = view.findViewById(R.id.experimentError1);
        experimentError2 = view.findViewById(R.id.experimentError2);
        experimentError3 = view.findViewById(R.id.experimentError3);

        //hide error at on start
        experimentError1.setVisibility(TextView.GONE);
        experimentError2.setVisibility(TextView.GONE);
        experimentError3.setVisibility(TextView.GONE);

        trialSpinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.create_experiment_spinner_item, options);
        adapter.setDropDownViewResource(R.layout.create_experiment_spinner_item);
        trialSpinner.setAdapter(adapter);

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

                // reset all
                experimentError1.setVisibility(TextView.GONE);
                experimentError2.setVisibility(TextView.GONE);
                experimentError3.setVisibility(TextView.GONE);

                // if invalid or empty, show error
                boolean validFlag = true;
                if (editExperimentNameText.length() == 0) {
                    // display error
                    experimentError1.setVisibility(TextView.VISIBLE);
                    validFlag = false;
                }
                if (editCityText.length() == 0) {
                    experimentError2.setVisibility(TextView.VISIBLE);
                    validFlag = false;
                }
                if (minTrial.length() == 0 || Integer.valueOf(minTrial.getText().toString()) == 0) {
                    experimentError3.setVisibility(TextView.VISIBLE);
                    validFlag = false;
                }

                // if not valid in any step, return
                if (!validFlag) {
                    return;
                }

                // try block below?
                // generate new experiment and add to experiment manager
                try {
                    Experiment temp = new Experiment(editExperimentNameText, newUser.getUserId(), newUser.getUserName(), editExperimentAboutText, currentSelection, editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked());
                    ExperimentManager.getInstance().addExperiment(temp);
                    callback.onExperimentAdded(temp);
                    // show success
                    showAlert(false, "Created new experiment!");
                } catch (Exception e) {
                    // failed to create experiment
                    showAlert(true, "Failed to create experiment!");
                }

                // closes dialog
                dialog.dismiss();
            }
        });

        return  dialog;
    }

    /**
     * When an item is selected
     * @param parent
     * parent of list
     * @param view
     * view
     * @param position
     * position of item selected
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView < ? > parent, View view, int position, long id) {
        currentSelection = values[position];
    }

    /**
     * When nothing is selected, the current will be set to the first one
     * @param parent
     * parent
     */
    @Override
    public void onNothingSelected(AdapterView < ? > parent) {
        currentSelection = values[0];
    }
}
