package com.DivineInspiration.experimenter.Activity.ui.profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.R;

public class HomeFragment extends Fragment {


    public HomeFragment(){
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /*
    https://stackoverflow.com/questions/31231609/creating-a-button-in-android-toolbar
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editProfileButt:
                Toast.makeText(getContext(), "stuff", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}