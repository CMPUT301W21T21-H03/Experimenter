package com.DivineInspiration.experimenter.Activity.UI.TrialsUI;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;

public class CommandFromString {


    interface CommandCallback{
        void onCommandMade(QRTrialCommand command);
    }

    public static QRTrialCommand make(String input, CommandCallback callback) {



        String[] values = input.split("-"); //in order: expId, type, needLoc, passNum/count/value, failNum

        QRTrialCommand output = new QRTrialCommand();


        output.expId = values[0];
        output.type = values[1];
        output.needLocation = Boolean.parseBoolean(values[2]);

        if(output.type.equals(Trial.BINOMIAL)){
            output.success = Integer.parseInt(values[3]);
            output.fails = Integer.parseInt(values[4]);
        }
        else if(output.type.equals(Trial.MEASURE)){
            output.value = Double.parseDouble(values[3]);
        }
        else{
            output.counts = Integer.parseInt(values[3]);
        }
        return output;
    }

    public static class QRTrialCommand {


        private int success = 0;
        private int fails = 0;
        private int counts = 0;
        private double value = 0;
        private String expId = "";
        private String type = "";

        public String getExpId() {
            return expId;
        }

        public String getType() {
            return type;
        }

        public boolean isNeedLocation() {
            return needLocation;
        }

        private  boolean needLocation = false;

        private QRTrialCommand() {

        }

        public int getSuccess() {
            return success;
        }

        public int getFails() {
            return fails;
        }

        public int getCounts() {
            return counts;
        }

        public double getValue() {
            return value;
        }


    }

}
