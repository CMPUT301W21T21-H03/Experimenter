package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;

public class CreateExperimentDialogFragment extends DialogFragment {
    UserManager.LocalUserCallback callback;
    ExperimentManager newExperiment = ExperimentManager.getInstance();
    TextView editExperimentName;
    TextView editTrialType;
    TextView editCity;
    TextView editExperimentAbout;

  public CreateExperimentDialogFragment(UserManager.LocalUserCallback callback){
      super();
      this.callback = callback;
  }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_experiment_dialog_fragment,null);
      User newUser = UserManager.getInstance().getLocalUser();


      editExperimentName = view.findViewById(R.id.editExperimentName);
      editTrialType = view.findViewById(R.id.editTrial);
      editCity = view.findViewById(R.id.editExperimentCity);
      editExperimentAbout = view.findViewById(R.id.editExperimentAbout);

      return new AlertDialog.Builder(getContext())
              .setView(view)
              .setMessage("Create Experiment")
              .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    String editExperimentNameText = editExperimentName.getText().toString();

                    String editCityText = editCity.getText().toString();
                    String editExperimentAboutText = editExperimentAbout.getText().toString();
                    Experiment temp = new Experiment(editExperimentNameText, newUser.getUserId(),editExperimentAboutText,1,editCityText,20);
                    ExperimentManager.getInstance().addExperiment(temp);

                  }
              })
              .setNegativeButton("cancel",null)
              .create();
    }
    public static String TAG = "create experiment";
}
