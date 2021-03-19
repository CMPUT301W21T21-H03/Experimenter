package com.DivineInspiration.experimenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  ExperimentManager.ExperimentReadyCallback, UserManager.QueryExpSubCallback{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Log.d("stuff", "main, onCreate");
        Log.d("stuff", "main, user ready");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_scan, R.id.navigation_explore)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


//        
//        Experiment testExp = new Experiment("Test", "ownerID", "woah dude!", 1, "testing region", 20, false);
//
//        ExperimentManager manager = ExperimentManager.getInstance();
//
//        manager.addExperiment(testExp);
//
//
//        UserManager.getInstance().queryExperimentSubs("EXPQQ7LRLL0BHLTB", this);




    }

    @Override
    public void onExperimentsReady(List<Experiment> experiments) {
        for(Experiment e : experiments){
            Log.d("stuff", e.getExperimentID());
        }
    }

    @Override
    public void onQueryUserSubsReady(ArrayList<User> users) {
        Log.d("stuff", "check");
        for(User u : users){
            Log.d("stuff", "User from sub id:" + u.getUserId());
        }
    }
}