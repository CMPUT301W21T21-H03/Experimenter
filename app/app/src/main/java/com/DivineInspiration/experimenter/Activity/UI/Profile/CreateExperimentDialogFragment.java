package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import org.w3c.dom.Text;

public class CreateExperimentDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {


    // inits
    OnExperimentAddedListener callback;
    ExperimentManager newExperiment = ExperimentManager.getInstance();
    TextView editExperimentName;
    TextView editExperimentNameError;
    Spinner trialSpinner;
    TextView editCity;
    TextView editExperimentAbout;
    TextView minTrial;
    CheckBox requireGeo;

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
        editExperimentNameError = view.findViewById(R.id.experimentNameError);
        trialSpinner = view.findViewById(R.id.editExperimentSpinner);
        editCity = view.findViewById(R.id.editExperimentCity);
        editExperimentAbout = view.findViewById(R.id.editExperimentAbout);
        minTrial = view.findViewById(R.id.editExperimentMin);
        requireGeo = view.findViewById(R.id.editExperimentGeo);

        trialSpinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.create_experiment_spinner_item, options);
        adapter.setDropDownViewResource(R.layout.create_experiment_spinner_item);
        trialSpinner.setAdapter(adapter);

        final AlertDialog dialog =  new AlertDialog.Builder(getContext())
                .setView(view)
                .setMessage("Create Experiment")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editExperimentNameText = editExperimentName.getText().toString();
                String editCityText = editCity.getText().toString();
                // optional?
                String editExperimentAboutText = editExperimentAbout.getText().toString();

                // TODO: if invalid or empty, show error
                if (editExperimentNameText.length() == 0) {
                    // display error
                    editExperimentNameError.setVisibility(TextView.VISIBLE);
                    return;
                } else if (editCityText.length() == 0) {
                    return;
                }

                // generate new experiment and add to experiment manager
                Experiment temp = new Experiment(editExperimentNameText, newUser.getUserId(), newUser.getUserName(), editExperimentAboutText, currentSelection, editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked());
                ExperimentManager.getInstance().addExperiment(temp);
                callback.onExperimentAdded(temp);
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
