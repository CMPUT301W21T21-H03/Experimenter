package com.DivineInspiration.experimenter;

import android.util.Log;

import com.google.firebase.installations.FirebaseInstallations;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class TestInstallation {

    //TODO WHAT IS THIS???
    public static long testInstallation(){
        final AtomicLong output = new AtomicLong(0);
        final AtomicBoolean done = new AtomicBoolean(false);
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            Log.d("stuff","entering get Id");
            if(task.isSuccessful()){
                Log.d("stuff",task.getResult() + "||||||||||||||| Task is successful");
                output.set(333);
            }
            else{
                Log.d("stuff","task failed");
            }
            Log.d("stuff","existing getId");
            done.set(true);
        });
        Log.d("stuff", "done?    "+ String.valueOf(done.get()));
//        while(!done.get()){
//            try {
//                Thread.sleep(25L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
        return output.get();
    }
}
