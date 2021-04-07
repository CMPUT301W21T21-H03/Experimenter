package com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class CreateTrialDialogFragment extends DialogFragment {


    private final OnTrialCreatedListener callback;
    String trialTypeCheck;
    CheckBox geoTrial;
    EditText measurementTextBox;
    TextView countNNTrial;
    TextView failNumTrial;
    TextView trueNumTrial;
    Button negativeCountNNButton;
    Button positiveCountNNButton;
    Button passButton;
    Button failButton;
    Button subButtonOne;
    Button subButtonTwo;
    int failNum = 0;
    int passNum = 0;
    int count = 0;
    double myLat = 0;
    double myLong = 0;
    GeoPoint trialLocation = null;

    public interface OnTrialCreatedListener {
        void onTrialAdded(Trial trial);

    }

    public CreateTrialDialogFragment(OnTrialCreatedListener callback) {
        super();
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_trial_dialog_fragment, null);
        Bundle args = getArguments();
        Experiment exp = (Experiment) args.getSerializable("experiment");
        init(view);
        trialTypeCheck = exp.getTrialType();
        visibility(trialTypeCheck);
        gettingLocation();

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

                switch (trialTypeCheck) {
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
                        String measure = measurementTextBox.getText().toString();
                        measurementTrialDialog(args, exp, measure);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();

            }

        });

        return dialog;


    }

    public void binomialTrialDialog(Bundle args, Experiment exp) {
        trialLocation = new GeoPoint(myLat,myLong);
        for (int i = 0; i < passNum; i++) {
            BinomialTrial binomialTrial = new BinomialTrial(
                    args.getString("experimenterID"),
                    args.getString("experimenterName"),
                    exp.getExperimentID(),
                    true,
                    trialLocation
            );
            TrialManager.getInstance().addTrial(binomialTrial, trials -> {
            });
            callback.onTrialAdded(binomialTrial);
        }
        for (int i = 0; i < failNum; i++) {
            BinomialTrial binomialTrial = new BinomialTrial(
                    args.getString("experimenterID"),
                    args.getString("experimenterName"),
                    exp.getExperimentID(),
                    false,
                    trialLocation
            );
            TrialManager.getInstance().addTrial(binomialTrial, trials -> {
            });
            callback.onTrialAdded(binomialTrial);
        }


    }

    public void countTrialDialog(Bundle args, Experiment exp) {
        trialLocation = new GeoPoint(myLat,myLong);
        CountTrial countTrial = new CountTrial(
                args.getString("experimenterID"),
                args.getString("experimenterName"),
                exp.getExperimentID(),
                count,
                trialLocation
        );

        TrialManager.getInstance().addTrial(countTrial, trials -> {
        });
        callback.onTrialAdded(countTrial);
    }

    public void nonNegativeTrialDialog(Bundle args, Experiment exp) {
        trialLocation = new GeoPoint(myLat,myLong);
        NonNegativeTrial nonNegativeTrial = new NonNegativeTrial(
                args.getString("experimenterID"),
                args.getString("experimenterName"),
                exp.getExperimentID(),
                count,
                trialLocation
        );
        TrialManager.getInstance().addTrial(nonNegativeTrial, trials -> {
        });
        callback.onTrialAdded(nonNegativeTrial);
    }

    public void measurementTrialDialog(Bundle args, Experiment exp, String measure) {
        trialLocation = new GeoPoint(myLat,myLong);
        double measureValue = Double.valueOf(measure);
        MeasurementTrial measurementTrial = new MeasurementTrial(
                args.getString("experimenterID"),
                args.getString("experimenterName"),
                exp.getExperimentID(),
                measureValue,
                trialLocation
        );
        TrialManager.getInstance().addTrial(measurementTrial, trials -> {
        });
        callback.onTrialAdded(measurementTrial);
    }

    public void init(View view) {
        measurementTextBox = view.findViewById(R.id.editMeasurementValue);
        countNNTrial = view.findViewById(R.id.value_trial);
        failButton = view.findViewById(R.id.binomial_fail_button);
        passButton = view.findViewById(R.id.binomial_pass_button);
        negativeCountNNButton = view.findViewById(R.id.decrease_trial_value);
        positiveCountNNButton = view.findViewById(R.id.increase_trial_value);
        failNumTrial = view.findViewById(R.id.binomial_fail_textView);
        trueNumTrial = view.findViewById(R.id.binomial_pass_textView);
        subButtonOne = view.findViewById(R.id.binomial_fail_decrement);
        subButtonTwo = view.findViewById(R.id.binomial_pass_decrement);
    }

    public void gettingLocation() {


        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },301);
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                myLong = location.getLongitude();
                myLat = location.getLatitude();
                Log.d("woah", "updating!");
                mLocationManager.removeUpdates(this);
            }
        });

    }

    public void visibility(String trialTypeCheck){
        measurementTextBox.setVisibility(View.GONE);
        countNNTrial.setVisibility(View.GONE);
        failButton.setVisibility(View.GONE);
        passButton.setVisibility(View.GONE);
        negativeCountNNButton.setVisibility(View.GONE);
        positiveCountNNButton.setVisibility(View.GONE);
        failNumTrial.setVisibility(View.GONE);
        trueNumTrial.setVisibility(View.GONE);
        subButtonOne.setVisibility(View.GONE);
        subButtonTwo.setVisibility(View.GONE);
        switch (trialTypeCheck){
            case "Binomial trial":
                passButton.setVisibility(View.VISIBLE);
                failButton.setVisibility(View.VISIBLE);
                failNumTrial.setVisibility(View.VISIBLE);
                trueNumTrial.setVisibility(View.VISIBLE);
                subButtonOne.setVisibility(View.VISIBLE);
                subButtonTwo.setVisibility(View.VISIBLE);
                BinomialTrialButtonController();

                break;
            case "Count trial":
                negativeCountNNButton.setVisibility(View.VISIBLE);
                positiveCountNNButton.setVisibility(View.VISIBLE);
                countNNTrial.setVisibility(View.VISIBLE);
                CountTrialButtonController();
                break;
            case "Non-Negative trial":
                negativeCountNNButton.setVisibility(View.VISIBLE);
                positiveCountNNButton.setVisibility(View.VISIBLE);
                countNNTrial.setVisibility(View.VISIBLE);
                NNTrialButtonController();
                break;
            case "Measurement trial":
                measurementTextBox.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }

    public void BinomialTrialButtonController(){
        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passNum = passNum + 1;
                trueNumTrial.setText(String.valueOf(passNum));
            }
        });

        failButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failNum = failNum + 1;
                Log.d("wtf", String.valueOf(failNum));
                failNumTrial.setText( String.valueOf(failNum));
            }
        });
        subButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failNum = failNum - 1;
                failNumTrial.setText( String.valueOf(failNum));
            }
        });

        subButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passNum = passNum - 1;
                trueNumTrial.setText(String.valueOf(passNum));
            }
        });

    }

    public void CountTrialButtonController(){
        positiveCountNNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + 1;
                countNNTrial.setText(String.valueOf(count));
            }
        });
        negativeCountNNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count - 1;
                countNNTrial.setText(String.valueOf(count));
            }
        });
    }

    public void NNTrialButtonController(){
        positiveCountNNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + 1;
                countNNTrial.setText(String.valueOf(count));
            }
        });
        negativeCountNNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 0){
                    count = count - 1;
                    countNNTrial.setText(String.valueOf(count));
                }else{

                }


            }
        });

    }
}
