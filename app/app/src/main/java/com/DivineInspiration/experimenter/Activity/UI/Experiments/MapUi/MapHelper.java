package com.DivineInspiration.experimenter.Activity.UI.Experiments.MapUi;

import android.annotation.SuppressLint;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class MapHelper {

    static DecimalFormat decimalFormat = new DecimalFormat("0.##");
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @SuppressLint("DefaultLocale")
    public static String getShortTrialDescription(Trial trial) {

        if (trial == null) {
            return "";
        }

        switch (trial.getTrialType()) {
            case Trial.COUNT:
                CountTrial count = (CountTrial) trial;
                return String.format("Count Trial: %d \n%s\n%.4f, %.4f",
                        count.getCount(),
                        timeFormatter.format(count.getTrialDate()),
                        count.getLocation().latitude,
                        count.getLocation().longitude);
            case Trial.BINOMIAL:
                BinomialTrial binomialTrial = (BinomialTrial) trial;
                return String.format("Binomial Trial: %s counts\n%s\n%.4f, %.4f",
                        binomialTrial.getPass()?"Pass":"Fail",
                        timeFormatter.format(binomialTrial.getTrialDate()),
                        binomialTrial.getLocation().latitude,
                        binomialTrial.getLocation().longitude);
            case Trial.MEASURE:
                MeasurementTrial measure = (MeasurementTrial) trial;
                return String.format("Measurement Trial: %.2f \n%s\n%.4f, %.4f",
                        measure.getValue(),
                        timeFormatter.format(measure.getTrialDate()),
                        measure.getLocation().latitude,
                        measure.getLocation().longitude);
            case Trial.NONNEGATIVE:
                NonNegativeTrial nonNeg = (NonNegativeTrial) trial;
                return String.format("Non-negative Trial: %d \n%s\n%.4f, %.4f",
                        nonNeg.getCount(),
                        timeFormatter.format(nonNeg.getTrialDate()),
                        nonNeg.getLocation().latitude,
                        nonNeg.getLocation().longitude);
            default:
                return "";
        }

    }
}
