package com.DivineInspiration.experimenter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.DivineInspiration.experimenter.Controller.LocalUserManager;
import com.DivineInspiration.experimenter.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements LocalUserManager.UserReadyCallback {

    LocalUserManager manager =LocalUserManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        setContentView(R.layout.activity_main);


=======
        setContentView(R.layout.user_profile);
//        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//          R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this,R.id.mainFrag);
//        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView,navController);
>>>>>>> Stashed changes


        manager.setContext(this);
        manager.setReadyCallback(this);



    }


    @Override
    public void onUserReady() {
        Log.d("stuff", manager.getUser().toString());
    }
}