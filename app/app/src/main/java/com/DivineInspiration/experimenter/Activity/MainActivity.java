package com.DivineInspiration.experimenter.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.time.LocalDate;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /**
     * On create
     *
     * @param savedInstanceState the bundle
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

//        ExperimentManager.getInstance().deleteAllExperimentFromTitle("test");
//        UserManager.getInstance().setContext(this);
//        UserManager.getInstance().initializeLocalUser(user -> {
//            Experiment measureDemo = new Experiment(IdGen.genExperimentId(user.getUserId()), "Demo Measurement Experiment", user.getUserId(), user.getUserName(),
//                    "A Demo Measurement Experiment for presentation.", Trial.MEASURE, "Demolandia", 300, true, Experiment.ONGOING);
//
//            ExperimentManager.getInstance().addExperiment(measureDemo, done -> {
//            });
//
//            double[] vals = {2, 2.3, 4, 5.1, 1, 2.1, 6, 5.6, 7, 3, 8, 2.3, 1, 4.2, 6.2, 9, 5, 3.4, 4.8, 7, 3.5, 6.1, 3.4, 2.9, 6, 9, 3.6, 2.9, 2.4, 3.8, 1.9, 0.4, 7.3, 5.5};
//
//            TrialManager trialManager = TrialManager.getInstance();
//            Random rng = new Random();
//
//            for (Double val : vals) {
//                trialManager.addTrial(
//                        new MeasurementTrial(IdGen.genTrialsId(user.getUserId()),
//                                user.getUserId(),
//                                user.getUserName(),
//                                measureDemo.getExperimentID(),
//                                LocalDate.now().plusDays(rng.nextInt(40) - 20),
//                                val, new LatLng(rng.nextDouble() * 180 - 90, rng.nextDouble() * 360 - 180)), trials -> {});
//            }
//
//            Experiment binomialDemo =  new Experiment(IdGen.genExperimentId(user.getUserId()), "Demo Binomial Experiment", user.getUserId(), user.getUserName(),
//                    "A Demo Measurement Experiment for presentation.", Trial.BINOMIAL, "Demolandia, Binomial Prov.", 300, true, Experiment.ONGOING);
//
//            ExperimentManager.getInstance().addExperiment(binomialDemo, done -> {
//            });
//            for (int i = 0;  i < 100; i++){
//                trialManager.addTrial(
//                        new BinomialTrial(IdGen.genTrialsId(user.getUserId()),
//                                user.getUserId(),
//                                user.getUserName(),
//                                binomialDemo.getExperimentID(),
//                                LocalDate.now().plusDays(rng.nextInt(40) - 20),
//                                rng.nextBoolean(), new LatLng(rng.nextDouble() * 180 - 90, rng.nextDouble() * 360 - 180)), trial ->{});
//            }
//        });

        // Create directory for QR code images
        File dir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "/QRCodes");
        if (!dir.exists()) {
            if (dir.mkdir()) {
                Log.d("Files QR", dir.getAbsolutePath() + " created");
            } else {
                Log.d("Files QR", dir.getAbsolutePath() + " failed to be created");
            }
        } else {
            Log.d("Files QR", "directory already exists - " + dir.getPath());
        }
    }
}