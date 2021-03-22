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

public class MainActivity extends AppCompatActivity  {


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




    }


}