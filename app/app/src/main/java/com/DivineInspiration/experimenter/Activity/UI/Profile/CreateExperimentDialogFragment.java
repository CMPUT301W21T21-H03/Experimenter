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

public class CreateExperimentDialogFragment extends DialogFragment  implements AdapterView.OnItemSelectedListener {

    public interface OnExperimentAddedListener {
         void onExperimentAdded(Experiment experiment);
    }


    OnExperimentAddedListener callback;
    ExperimentManager newExperiment = ExperimentManager.getInstance();
    TextView editExperimentName;
    Spinner trialSpinner;
    TextView editCity;
    TextView editExperimentAbout;
    TextView minTrial;
    CheckBox requireGeo;

    private String[] options = {"Counting", "Biomial", "NonNegative", "Measuring"};
    private String[] values = {Trial.COUNT, Trial.BINOMIAL, Trial.NONNEGATIVE, Trial.MEASURE };
    private String currentSelection;

  public CreateExperimentDialogFragment( OnExperimentAddedListener callback){
      super();
      this.callback = callback;
  }

    /**
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
          View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_experiment_dialog_fragment,null);
          User newUser = UserManager.getInstance().getLocalUser();


      editExperimentName = view.findViewById(R.id.editExperimentName);
      trialSpinner = view.findViewById(R.id.editExperimentSpinner);
      editCity = view.findViewById(R.id.editExperimentCity);
      editExperimentAbout = view.findViewById(R.id.editExperimentAbout);
      minTrial = view.findViewById(R.id.editExperimentMin);
      requireGeo = view.findViewById(R.id.editExperimentGeo);

        trialSpinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item , options);
adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
trialSpinner.setAdapter( adapter);


      return new AlertDialog.Builder(getContext())
              .setView(view)
              .setMessage("Create Experiment")
              .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                    String editExperimentNameText = editExperimentName.getText().toString();
                    String editCityText = editCity.getText().toString();
                    String editExperimentAboutText = editExperimentAbout.getText().toString();
                    Experiment temp = new Experiment(editExperimentNameText, newUser.getUserId(),newUser.getUserName(),editExperimentAboutText,currentSelection,editCityText, Integer.parseInt(minTrial.getText().toString()), requireGeo.isChecked());
                    ExperimentManager.getInstance().addExperiment(temp);
                    callback.onExperimentAdded(temp);
                  }
              })
              .setNegativeButton("cancel",null)
              .create();
    }
    public static String TAG = "create experiment";


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentSelection = values[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        currentSelection = values[0];
    }
}
