package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeFragment extends Fragment implements UserManager.LocalUserCallback, UserManager.QuerySingleUserCallback {





    public HomeFragment(){
        super(R.layout.fragment_home);
        Log.d("stuff", "fragment, constructor");
    }

    CollapsingToolbarLayout toolbar;

    FloatingActionButton fab;
    Button editProfileButton;
    UserManager manager = UserManager.getInstance();
    ExperimentManager experimentManager = ExperimentManager.getInstance();
    ViewPager2 pager;
    HomeFragmentAdapter adapter;
    TabLayout tabLayout;

    // Declaring TextView
    TextView userID_home;
    TextView userName_home;
    TextView userCity_home;
    TextView userEmail_home;
    TextView userDescription_home;
    View dividerLineName_home;
    View dividerLineAbout_home;

    private final String[] tabNames = {"Experiments", "Subscriptions", "Trials"};


    @Override
    public void onLocalUserReady(User user) {
        // Display user information on toolbar
        displayUserToolbar(user);
//        experiments.addExperiment(new Experiment("try1",manager.getLocalUser(),"lol") );
    }

    @Override
    public void onQueryUserReady(User user) {
        Experiment testExp = new Experiment("Testing exp", user.getUserId(), "woah dude!", 1, "testing region", 20);
        experimentManager.addExperiment(testExp);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.CollaspingToolBar);
        fab = view.findViewById(R.id.fab);
        editProfileButton = view.findViewById(R.id.edit_profile_button);

        // Setting up the Text View in ToolBar
        userName_home = view.findViewById(R.id.userName_Home);
        userID_home = view.findViewById(R.id.userID_Home);
        userEmail_home = view.findViewById(R.id.email_Home);
        userCity_home = view.findViewById(R.id.userCity_home);
        userDescription_home = view.findViewById(R.id.userDescription_home);
        dividerLineName_home = view.findViewById(R.id.sectionDivideLineName_home);
        dividerLineAbout_home = view.findViewById(R.id.sectionDivideLineAbout_home);


        //viewpager
        pager = view.findViewById(R.id.pager);
        adapter = new HomeFragmentAdapter(this);
        pager.setAdapter(adapter);

        tabLayout = view.findViewById(R.id.Tablayout);

        new TabLayoutMediator(tabLayout, pager,true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //hide fab when on trials or subscriptions
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    fab.show();
                }
                else{
                    fab.hide();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //setup local user
        manager.setContext(getContext());
        manager.initializeLocalUser(this);


        // title is transparent when expanded
        toolbar.setCollapsedTitleTextAppearance(R.style.toolBarCollapsed);
        toolbar.setExpandedTitleTextAppearance(R.style.toolBarExpanded);




        // fab onclick
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              Snackbar.make(view, "Woaaaaaah dude!!!", Snackbar.LENGTH_LONG).show();
              manager.queryUser("XDASQK0FE7", HomeFragment.this);
            }
        });


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditProfileDialogFragment(HomeFragment.this).show(getChildFragmentManager(),"Edit Profile");
            }
        });

    //    experiments.addExperiment(new Experiment("try1",manager.getLocalUser(),"lol") );


        /*
        appbar stuff
        https://stackoverflow.com/questions/31662416/show-collapsingtoolbarlayout-title-only-when-collapsed
         */
//        appBar = (AppBarLayout)view.findViewById(R.id.appBar);
//        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean showing = true;
//            int scrollRange = -1;
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(scrollRange == -1){
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if(scrollRange + verticalOffset == 0){
//                    showing = true;
//                    toolbar.setTitle("Put user here");
//                }
//                else if (showing){
//                    showing = false;
//                 toolbar.setTitle(" ");
//                }
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(manager.getLocalUser() != null){
            displayUserToolbar(manager.getLocalUser());
        }

    }

    private void displayUserToolbar(User user) {
        // Displaying User iD

        toolbar.setTitle(user.getUserName());

        userID_home.setText(user.getUserId());
        userCity_home.setText(user.getContactInfo().getCityName());
        userEmail_home.setText(user.getContactInfo().getEmail());
        userDescription_home.setText(user.getDescription());
        userName_home.setText(user.getUserName());

        // Setting Visibility of text Views
        // Visibility for City and Email
        String cityText = user.getContactInfo().getCityName();
        if(cityText.isEmpty()){
            userCity_home.setVisibility(View.GONE);
        }else {
            userCity_home.setVisibility(View.VISIBLE);
        }
        String emailText =user.getContactInfo().getEmail();
        if(emailText.isEmpty()){
            userEmail_home.setVisibility(View.GONE);
        }else{
            userEmail_home.setVisibility(View.VISIBLE);
        }
        if(cityText.isEmpty() && emailText.isEmpty()){
            dividerLineName_home.setVisibility(View.GONE);
        }else {
            dividerLineName_home.setVisibility(View.VISIBLE);
        }
        // Setting Visibility of User Description
        String descriptionText = user.getDescription();
        if(descriptionText.isEmpty()){
            userDescription_home.setVisibility(View.GONE);
            dividerLineAbout_home.setVisibility(View.GONE);
        }else{
            userDescription_home.setVisibility(View.VISIBLE);
            dividerLineAbout_home.setVisibility(View.VISIBLE);
        }
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
                    return  new ProfileExpFrag();
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

