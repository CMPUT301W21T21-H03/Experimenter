package com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.Controller.CommentManager;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Comment;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import java.time.LocalDate;
import java.util.Date;

import static com.DivineInspiration.experimenter.Activity.UI.Profile.EditProfileDialogFragment.TAG;

public class CreateTrialDialogFragment extends DialogFragment {


    private final OnTrialCreatedListener callback;
    String trialTypeCheck;


    public interface OnTrialCreatedListener{
        void onTrialAdded(Trial trial);

    }

    public CreateTrialDialogFragment(OnTrialCreatedListener callback){
        super();
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_trial_dialog_fragment, null);
        Bundle args = getArguments();
        Experiment exp = (Experiment) args.getSerializable("experiment");

        trialTypeCheck = exp.getTrialType();


        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.dialogColor)
                .setView(view)
                .setMessage("Create Trial")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (trialTypeCheck){
                    case "Binomial trial":
                        binomialTrialDialog(args, exp);
                        break;
                    case "Count trial":
                        countTrialDialog(args, exp);
                        break;
                    case "Non-Negative trial":
                        nonNegativeTrialDialog(args, exp);
                        break;
                    case "Measurement trial":
                        measurementTrialDialog(args, exp);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();

            }

        });

        return dialog;


    }

    public void binomialTrialDialog(Bundle args, Experiment exp){
        BinomialTrial binomialTrial = new BinomialTrial(
                args.getString("experimenterID"),
                args.getString("experimenterName"),
                exp.getExperimentID()

        );
        TrialManager.getInstance().addTrial(binomialTrial,trials -> {});
        callback.onTrialAdded(binomialTrial);


    }

    public void countTrialDialog(Bundle args, Experiment exp){

    }

    public void nonNegativeTrialDialog(Bundle args, Experiment exp){

    }

    public void measurementTrialDialog(Bundle args, Experiment exp){

    }
}
