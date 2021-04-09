package com.DivineInspiration.experimenter.Model;


import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;

import kotlin.NotImplementedError;

/**
 * This class is used to generate unique Id's for users, experiments, trials & comments
 */
public class IdGen {

    static int counter = (int) (System.currentTimeMillis() % 1296);

    /**
     * Event listener
     */
    public interface onIdReadyListener {
        void onIdReady(String id);
    }

    /**
     * Generates a user id, a 53 bit long. Where the first 32 bits is epoch time, in seconds.
     * The latter 20 bits are first 4 digit of firebase installation id(base 64) loosely converted to base 36
     * @param callable
     * callback function
     */
    public static void genUserId(onIdReadyListener callable){
        /*
        Name: Rajeev Singh
        Link: https://www.callicoder.com/distributed-unique-id-sequence-number-generator/
        Date: Jun 8, 2018
        License: Unknown
        Usage: Introduction to twitter snowflake, the below is a rough implementation similar to twitter snowflake.
         */
        /*
        Name: MH.
        Link: https://stackoverflow.com/a/8675243/12471420
        Date: Dec 30, 2011
        License: CC BY-SA 3.0
        Usage: To use callback interface to handle async functions
         */
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            long output = 0;
            if (task.isSuccessful()){
                String fid = task.getResult();
                assert fid != null;
                fid = fid.substring(0, 4).toUpperCase();
                StringBuilder temp = new StringBuilder();
                for(int i = 0; i < 4; i++){
                    if(!inRange(fid.charAt(i), 65, 90) && !inRange(fid.charAt(i), 30, 39)){
                        temp.append((fid.charAt(i) % 26)+65);
                    }
                    else{
                        temp.append(fid.charAt(i));
                    }
                }
                fid = temp.toString();
                long fidVal = base36To10(fid);
                //in base 36, the first 4 digits are the
                output=((System.currentTimeMillis()/1000)  << 21);
                output=(output|fidVal);
            }
            else{
                output=(-1);
            }
           callable.onIdReady(base10To36(output));
        });
    }

    /**
     * Generates the experiment ID
     * @param userId
     * experiment belonging to this user
     * @return
     * the experiment ID string
     */
    public static String genExperimentId(String userId){
        counter = (counter + 1) % 1296;
        // This is not necessarily unique. Should it be?
        return "EXP" + base10To36((System.currentTimeMillis()/1000))+base10To36(counter) +userId.substring(5);
    }

    /**
     * Generates the trial ID
     * @param userId
     * ID of user
     * @return
     * a large int as a string
     */
    public static String genTrialsId(String userId){
        counter = (counter + 1) % 1296;
        return "TRI" + base10To36((System.currentTimeMillis()/1000)) +base10To36(counter)+userId.substring(5);
    }

    /**
     * Converts a long in base10 to base36 as a string
     * @param source
     * the number to be converted
     * @return
     * converted string
     */
    public static String base10To36(long source) {
        StringBuilder out = new StringBuilder();
        while (source != 0){
            int temp =(int)( source % 36);
            if(inRange(temp, 0, 9)){
                out.insert(0, (char) (temp + 48));
            }
            else{
                out.insert(0, (char)(temp + 55));
            }
            source = source/36;
        }
        return out.toString();
    }

    /**
     * @param experimentID
     * ID of experiment
     * @return
     * the unique comment ID
     */
    public static String genCommentId(String experimentID) {
        return "COM" + base10To36((System.currentTimeMillis()/1000)) + experimentID.substring(3);
    }

    /**
     * Converts a base36 String to a base10 long
     * @param source
     * string to be converted
     * @return
     * converted string
     */
    public static long base36To10(String source){
        long output = 0;
        for(int i = 0; i < source.length(); i++){
            int val = source.charAt(i);
            if(inRange(val, 65, 90)){
                val -= 55;
            }else if(inRange(val, 48, 57)){
                val -= 48;
            }
            else{
                //a non b36 digit found
                return -1L;
            }
            output += val * Math.pow(36, source.length() - 1 -i);
        }
        return output;
    }

    /**
     * Checks if val is in range of min and max
     * @param val
     * value to be compared
     * @param min
     * min value
     * @param max
     * max value
     * @return
     * if the val is in range
     */
    private static boolean inRange(int val, int min, int max){
        return val >= min && val <= max;
    }
}
