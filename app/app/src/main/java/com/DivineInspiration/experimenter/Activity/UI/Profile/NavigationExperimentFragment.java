package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class NavigationExperimentFragment extends Fragment implements  UserManager.QueryExpSubCallback{
    ExperimentManager experimentManager = ExperimentManager.getInstance();
    UserManager userManager = UserManager.getInstance();
    private TextView experimentName;
    private TextView ownerName;
    private TextView subNumber;
    private TextView trialNumber;
    private TextView trialType;
    private  TextView expAbout;
    private TextView expCity;
    private SwitchCompat subSwitch;


    ViewPager2 pager;
    HomeFragmentAdapter adapter;
    TabLayout tabLayout;


    String[] tabNames = {"Trials", "Comments", "Stats"};
    private ArrayList<User> subscribers  = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_experiment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        experimentName = view.findViewById(R.id.experimentName_expFrag);
        ownerName = view.findViewById(R.id.ownerName_expFrag);
        subNumber = view.findViewById(R.id.expFragSubCount);
        trialNumber = view.findViewById(R.id.trialNumber_expFrag);
        trialType = view.findViewById(R.id.trialType_expFrag);
        expAbout = view.findViewById(R.id.experimentDescription_expFrag);
        expCity = view.findViewById(R.id.experimentRegion_expFrag);
        subSwitch = view.findViewById(R.id.subscribeSwitch);



        Experiment exp = (Experiment)getArguments().getSerializable("experiment");
        experimentName.setText(exp.getExperimentName());
        ownerName.setText(exp.getOwnerName());
        expCity.setText(exp.getRegion());
        trialNumber.setText(String.valueOf(exp.getMinimumTrials())+" trials needed");
        trialType.setText(exp.getTrialType());
        expAbout.setText(exp.getExperimentDescription());



        //view pager
        pager = view.findViewById(R.id.expPager);
        adapter = new HomeFragmentAdapter(this);
        pager.setAdapter(adapter);
        tabLayout = view.findViewById(R.id.Tablayout);
        new TabLayoutMediator(tabLayout, pager,true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }).attach();




        UserManager.getInstance().queryExperimentSubs(exp.getExperimentID(), this);

        subSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    experimentManager.subToExperiment(userManager.getLocalUser().getUserId(),exp.getExperimentID());
                }else{
                    experimentManager.unSubFromExperiment(userManager.getLocalUser().getUserId(),exp.getExperimentID());
                }
            }
        });


        View setting =   view.findViewById(R.id.setting);
        View profile =  view.findViewById(R.id.profile);
        if(UserManager.getInstance().getLocalUser().getUserId().equals(exp.getOwnerID())){
          profile.setVisibility(View.GONE);
          setting.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Snackbar.make(view, "settings clicked", Snackbar.LENGTH_LONG).show();
              }
          });
        }
        else{
           setting.setVisibility(View.GONE);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(view, "profile clicked", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onQueryUserSubsReady(ArrayList<User> users) {
        subscribers.clear();
        subscribers.addAll(users);
        for(int i = 0;i<subscribers.size();i++){
            if(UserManager.getInstance().getLocalUser().getUserId().equals(subscribers.get(i).getUserId())){
                subSwitch.setChecked(true);
            }

        }

        ((TextView) getView().findViewById(R.id.expFragSubCount)).setText(subscribers.size()+" subscribers");
    }
    public  class HomeFragmentAdapter extends FragmentStateAdapter {

        public HomeFragmentAdapter(Fragment frag){
            super(frag);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {


            switch (position){
                case 0:
                    return new TestFrag();
                case 1:
                    return new TestFrag();
                case 2:
                    return  new TestFrag();
                default:
                    return  new TestFrag();
            }

        }


        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public static class TestFrag extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.test, container, false);
        }




        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ListView list = view.findViewById(R.id.testList);

            String[] items = {"Russell’s", "Paradox", "tells", "us", "that", "Humans", "are", "bad", "at", "math.", "Our", "intuitions", "lead", "us", "astray.", "Things", "that", "look", "reasonable,", "can", "be", "completely", "wrong.", "So", "we", "have", "to", "be", "very", "very", "careful,", "very", "very", "precise,", "very", "very", "logical.", "We", "don’t", "want", "to", "be,", "but", "we", "have", "to", "be.", "Or", "we’ll", "get", "into", "all", "kinds", "of", "trouble.", "So", "let’s", "describe", "the", "grammar", "of", "math,", "which", "is", "logic!"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.test_item, items);

            list.setAdapter(adapter);

        }
    }
}