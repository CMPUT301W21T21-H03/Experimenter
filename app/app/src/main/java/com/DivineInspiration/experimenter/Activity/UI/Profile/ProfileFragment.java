package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.animation.LayoutTransition;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.DivineInspiration.experimenter.Activity.UI.Refreshable;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ProfileFragment extends Fragment {


    // Inits
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

    // main page tab names
    private final String[] tabNames = {"Experiments", "Subscriptions", "Trials"};
    CollapsingToolbarLayout toolbar;

    /**
     * Constructor
     */
    public ProfileFragment() {
        super(R.layout.fragment_home);
        // Log.d("MESSAGE", "fragment, constructor");
    }

    /**
     * When view is created
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // gets everything from view
        toolbar = view.findViewById(R.id.CollaspingToolBar);
        fab = view.findViewById(R.id.fab);
        editProfileButton = view.findViewById(R.id.edit_profile_button);

        // setting up the Text View in ToolBar
        userName_home = view.findViewById(R.id.userName_Home);
        userID_home = view.findViewById(R.id.userID_Home);
        userEmail_home = view.findViewById(R.id.email_Home);
        userCity_home = view.findViewById(R.id.userCity_home);
        userDescription_home = view.findViewById(R.id.userDescription_home);
        dividerLineName_home = view.findViewById(R.id.sectionDivideLineName_home);
        dividerLineAbout_home = view.findViewById(R.id.sectionDivideLineAbout_home);


        // smooth! https://proandroiddev.com/the-little-secret-of-android-animatelayoutchanges-e4caab2fddec
        ((ViewGroup)view.findViewById(R.id.coordinatorRoot)).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // viewpager
        pager = view.findViewById(R.id.expPager);
        adapter = new HomeFragmentAdapter(this);
        pager.setAdapter(adapter);

        tabLayout = view.findViewById(R.id.Tablayout);

        // when new tab is selected
        new TabLayoutMediator(tabLayout, pager,true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }).attach();

        // when a new tab is selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}

            // hide fab when on trials or subscriptions
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    fab.show();
                }
                else{
                    fab.hide();
                }
            }
        });

        // setup local user
        manager.setContext(getContext());
        manager.initializeLocalUser(new UserManager.OnUserReadyListener() {
            @Override
            public void onUserReady(User user) {
                displayUserToolbar(user);
            }
        });

        // title is transparent when expanded
        toolbar.setCollapsedTitleTextAppearance(R.style.toolBarCollapsed);
        toolbar.setExpandedTitleTextAppearance(R.style.toolBarExpanded);

        // fab onclick
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//              Snackbar.make(view, "Woaaaaaah dude!!!", Snackbar.LENGTH_LONG).show();
//              manager.queryUser(manager.getLocalUser().getUserId(), HomeFragment.this);
                new CreateExperimentDialogFragment((CreateExperimentDialogFragment.OnExperimentAddedListener)getChildFragmentManager().findFragmentByTag("f0")).show(getChildFragmentManager(),"create experiment");
            }
        });

        // when edit profile button is clicker -> trigger dialog box
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditProfileDialogFragment(new UserManager.OnUserReadyListener() {
                    @Override
                    public void onUserReady(User user) {
                        displayUserToolbar(user);
                        //a bit of a hack, reload experiment tab when profile is changed
                        Fragment expListFrag = getChildFragmentManager().findFragmentByTag("f0");
                        if(expListFrag instanceof Refreshable){
                            Log.d("woah", "is refreshable");
                            ((Refreshable)expListFrag).refresh();
                        }

                    }
                }).show(getChildFragmentManager(),"Edit Profile");
            }
        });
    }

    /**
     * On resume
     */
    @Override
    public void onResume() {
        super.onResume();
        if(manager.getLocalUser() != null){
            displayUserToolbar(manager.getLocalUser());
        }

    }

    /**
     * Displaying the user info
     * @param user
     * user
     */
    private void displayUserToolbar(User user) {
        // setting the user info in UI
        toolbar.setTitle(user.getUserName());

        userID_home.setText(user.getUserId());
        userCity_home.setText(user.getContactInfo().getCityName());
        userEmail_home.setText(user.getContactInfo().getEmail());
        userDescription_home.setText(user.getDescription());
        userName_home.setText(user.getUserName());

        // TODO: optimize below?? (Low priority)
        // Setting Visibility of text Views
        // Visibility for City and Email
        String cityText = user.getContactInfo().getCityName();
        userCity_home.setVisibility(cityText.isEmpty() ? View.GONE : View.VISIBLE);

        String emailText =user.getContactInfo().getEmail();
        userEmail_home.setVisibility(emailText.isEmpty() ? View.GONE : View.VISIBLE);

        if (cityText.isEmpty() && emailText.isEmpty()){
            dividerLineName_home.setVisibility(View.GONE);
        } else {
            dividerLineName_home.setVisibility(View.VISIBLE);
        }

        // Setting Visibility of User Description
        String descriptionText = user.getDescription();
        if (descriptionText.isEmpty()){
            userDescription_home.setVisibility(View.GONE);
            dividerLineAbout_home.setVisibility(View.GONE);
        } else{
            userDescription_home.setVisibility(View.VISIBLE);
            dividerLineAbout_home.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Home fragment
     */
    public  class HomeFragmentAdapter extends FragmentStateAdapter {

        /**
         * Constructor
         * @param frag
         */
        public HomeFragmentAdapter(Fragment frag){
            super(frag);
        }

        /**
         * When fragment is created
         * @param position
         * position in adapter
         * @return
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new ExperimentListTabFragment();
                case 1:
                    return new SubscriptionTabFragment();
                case 2:
                    return new TestFrag();
                default:
                    return new TestFrag();
            }
        }

        /**
         * Get item count
         * @return
         * number of items in list
         */
        @Override
        public int getItemCount() {
            return 3;
        }
    }

    /**
     * Test frag
     */
    public static class TestFrag extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.test, container, false);
        }

        /**
         * When view is created
         * @param view
         * @param savedInstanceState
         */
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ListView list = view.findViewById(R.id.placeHolderList);

            // TEMP
            String[] items = {"Russell’s", "Paradox", "tells", "us", "that", "Humans", "are", "bad", "at", "math.", "Our", "intuitions", "lead", "us", "astray.", "Things", "that", "look", "reasonable,", "can", "be", "completely", "wrong.", "So", "we", "have", "to", "be", "very", "very", "careful,", "very", "very", "precise,", "very", "very", "logical.", "We", "don’t", "want", "to", "be,", "but", "we", "have", "to", "be.", "Or", "we’ll", "get", "into", "all", "kinds", "of", "trouble.", "So", "let’s", "describe", "the", "grammar", "of", "math,", "which", "is", "logic!"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.test_item, items);

            list.setAdapter(adapter);

        }
    }

}

