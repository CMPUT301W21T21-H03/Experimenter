package com.DivineInspiration.experimenter.Activity.UI.Map;

import android.annotation.SuppressLint;

import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class MapHelper {

    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Gets the short trial description
     * @param trial
     * trial to get description
     * @return
     * short description
     */
    @SuppressLint("DefaultLocale")
    public static String getShortTrialDescription(Trial trial) {

        if (trial == null) {
            return "";
        }

        switch (trial.getTrialType()) {
            case Trial.COUNT:
                CountTrial count = (CountTrial) trial;
                return String.format("Author: %s\nCount Trial: %d \n%s\n%.4f, %.4f",
                        count.getTrialOwnerName(),
                        count.getCount(),
                        timeFormatter.format(count.getTrialDate()),
                        count.getLocation().latitude,
                        count.getLocation().longitude);

            case Trial.BINOMIAL:
                BinomialTrial binomialTrial = (BinomialTrial) trial;
                return String.format("Author: %s\nBinomial Trial: %s counts\n%s\n%.4f, %.4f",
                        binomialTrial.getTrialOwnerName(),
                        binomialTrial.getPass()?"Pass":"Fail",
                        timeFormatter.format(binomialTrial.getTrialDate()),
                        binomialTrial.getLocation().latitude,
                        binomialTrial.getLocation().longitude);

            case Trial.MEASURE:
                MeasurementTrial measure = (MeasurementTrial) trial;
                return String.format("Author: %s\nMeasurement Trial: %.2f \n%s\n%.4f, %.4f",
                        measure.getTrialOwnerName(),
                        measure.getValue(),
                        timeFormatter.format(measure.getTrialDate()),
                        measure.getLocation().latitude,
                        measure.getLocation().longitude);

            case Trial.NONNEGATIVE:
                NonNegativeTrial nonNeg = (NonNegativeTrial) trial;
                return String.format("Author: %s\nNon-negative Trial: %d \n%s\n%.4f, %.4f",
                        nonNeg.getTrialOwnerName(),
                        nonNeg.getCount(),
                        timeFormatter.format(nonNeg.getTrialDate()),
                        nonNeg.getLocation().latitude,
                        nonNeg.getLocation().longitude);

            default:
                return null;
        }
    }
}
