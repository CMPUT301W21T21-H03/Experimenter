package com.DivineInspiration.experimenter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    /**
     * On create
     * @param savedInstanceState
     * the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set to main layout
        setContentView(R.layout.activity_main);

        // bottom nav bar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_scan, R.id.navigation_explore
        ).build();

        // bottom nav controller
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Log.d("ALERT", "Main created!");

        // Create directory for QR code images
        File dir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "/QRCodes");
        if (!dir.exists()) {
            if (dir.mkdir()) {
                Log.d("Files QR", dir.getAbsolutePath() + " created");
            }
            else {
                Log.d("Files QR", dir.getAbsolutePath() + " failed to be created");
            }
        }
        else {
            Log.d("Files QR", "directory already exists - " + dir.getPath());
        }

    }
}