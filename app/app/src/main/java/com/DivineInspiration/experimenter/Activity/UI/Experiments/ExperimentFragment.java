package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.DivineInspiration.experimenter.Activity.Observer;
import com.DivineInspiration.experimenter.Activity.Subject;

import com.DivineInspiration.experimenter.Activity.UI.Comments.CreateCommentDialogFragment;
import com.DivineInspiration.experimenter.Activity.UI.Comments.DiscussionForumFragment;
import com.DivineInspiration.experimenter.Activity.UI.Map.TrialMapTabFramgent;
import com.DivineInspiration.experimenter.Activity.UI.TrialsUI.CreateTrialDialogFragment;
import com.DivineInspiration.experimenter.Activity.UI.TrialsUI.TrialsTabFragment;

import com.DivineInspiration.experimenter.Activity.UI.Stats.StatsTabFragment;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


/**
 * This class deals with the UI for displaying the experiment details. (It also contains 4 tabs: Trials, Comments, Stats, Data)
 * @see R.layout#experiment_fragment
 */
public class ExperimentFragment extends Fragment implements Subject, TrialManager.OnTrialListReadyListener{
    List<Trial> currentTrials = new ArrayList<>();            // The trials performed for the experiment

    // Text views to display experiment information
    private TextView experimentName;
    private TextView ownerName;
    private TextView subNumber;
    private TextView trialNumber;
    private TextView trialType;
    private TextView expAbout;
    private TextView expCity;
    private SwitchCompat subSwitch;
    private TextView status;
    private FloatingActionButton addButton;

    ViewPager2 pager;
    ExperimentTabsAdapter adapter;
    TabLayout tabLayout;
    CollapsingToolbarLayout toolbar;
    AppBarLayout appBar;

    ExperimentManager experimentManager = ExperimentManager.getInstance();
    UserManager userManager = UserManager.getInstance();

    boolean currentUserSubbed = false;

    String[] tabNames = {"Trials", "Comments", "Stats", "Map"};
    Experiment currentExperiment;


    /**
     * Runs when the view is created. Similar to the activity's onCreate
     * @param inflater
     * @param container
     * @param  savedInstanceState
     * @return: view created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.experiment_fragment, container, false);
    }

    /**
     * Runs when the view is fully created.
     * @param view
     * view itself
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the text views
        experimentName = view.findViewById(R.id.experimentName_expFrag);
        ownerName = view.findViewById(R.id.ownerName_expFrag);
        subNumber = view.findViewById(R.id.expFragSubCount);
        trialNumber = view.findViewById(R.id.trialNumber_expFrag);
        trialType = view.findViewById(R.id.trialType_expFrag);
        expAbout = view.findViewById(R.id.experimentDescription_expFrag);
        expCity = view.findViewById(R.id.experimentRegion_expFrag);
        subSwitch = view.findViewById(R.id.subscribeSwitch);
        status = view.findViewById(R.id.status_exp);
        addButton = view.findViewById(R.id.experiment_fragment_add_button);

        // Title is transparent when expanded
        toolbar = view.findViewById(R.id.expCollapsingToolbar);
        toolbar.setCollapsedTitleTextAppearance(R.style.toolBarCollapsed);
        toolbar.setExpandedTitleTextAppearance(R.style.toolBarExpanded);

        // Get experiment and set details to be displayed
        currentExperiment = (Experiment) getArguments().getSerializable("experiment");
        updateText(currentExperiment);

        // View pager
        pager = view.findViewById(R.id.expPager);
        adapter = new ExperimentTabsAdapter(this);
        pager.setAdapter(adapter);
        tabLayout = view.findViewById(R.id.Tablayout);
        appBar = view.findViewById(R.id.expAppbar);

        // Set tab names
        new TabLayoutMediator(tabLayout, pager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }).attach();

        // Display the floating add button on bottom left only on the first 2 tabs (trials and comments)
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();
                if ((position == 0 || position == 1) && currentUserSubbed && !currentExperiment.getStatus().equals(Experiment.ENDED) ){
                    addButton.show();
                } else {
                    addButton.hide();
                }

                if (position == 3){
                    pager.setUserInputEnabled(false);
                    appBar.setExpanded(false, true);
                }
                else{
                    pager.setUserInputEnabled(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });


        // Get the subscribers to the current experiment and do appropriate actions (see below);
        // if the current local user is a subscriber to the experiment.
        // If so: we toggle the sub switch, display the add button (for tabs 1 & 2), and set currentUserSubbed to true.
        UserManager.getInstance().queryExperimentSubs(currentExperiment.getExperimentID(),
                new UserManager.OnUserListReadyListener() {
                    @Override
                    public void onUserListReady(ArrayList<User> users) {
                        for (int i = 0; i < users.size(); i++) {

                            // Go through all the subscribers and check if current local user is one of them
                            if (UserManager.getInstance().getLocalUser().getUserId().equals(users.get(i).getUserId())
                                    && !currentExperiment.getStatus().equals(Experiment.ENDED)) {

                                subSwitch.setChecked(true);
                                addButton.show();
                                currentUserSubbed = true;
                            }
                        }
                        ((TextView) getView().findViewById(R.id.expFragSubCount))
                                .setText(users.size() + " subscribers");    // Display the no. of subscribers in the info
                    }
                });

        // Toggle sub to experiment (When button on the top right is toggled)
        subSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    experimentManager.subToExperiment(userManager.getLocalUser().getUserId(),
                            currentExperiment.getExperimentID(), null);

                    if(!currentExperiment.getStatus().equals(Experiment.ENDED)) {
                        currentUserSubbed = true;
                        addButton.show();
                    }

                } else {

                    experimentManager.unSubFromExperiment(userManager.getLocalUser().getUserId(),
                            currentExperiment.getExperimentID(), null);

                    addButton.hide();
                    currentUserSubbed = false;
                }
            }
        });

        // When add button is clicked. Depending on the tab it was clicked on, there will be different actions.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Button action changes depending on the current tab
                switch (tabLayout.getSelectedTabPosition()) {

                    case 0:                 // On Trials tab
                        TrialDialogSelect();
                        break;

                    case 1:                 // On Comments tab

                        // Bundle comment relevant information
                        Bundle bundle = new Bundle();
                        bundle.putString("commenterID", userManager.getLocalUser().getUserId());
                        bundle.putString("commenterName", userManager.getLocalUser().getUserName());
                        bundle.putString("experimentID", currentExperiment.getExperimentID());

                        CreateCommentDialogFragment dialog = new CreateCommentDialogFragment((CreateCommentDialogFragment.OnCommentCreatedListener) getChildFragmentManager().findFragmentByTag("f1"));
                        dialog.setArguments(bundle);
                        dialog.show(getChildFragmentManager(), "ExperimentFragment");
                        break;

                    default:
                }
            }
        });

        // Settings and profile view of the owner
        View setting = view.findViewById(R.id.setting);
        View profile = view.findViewById(R.id.profile);

        // When local user is owner himself, we don't display the profile icon
        // but, when the local user is owner, we display the settings button so he can edit experiment details.
        if (userManager.getLocalUser().getUserId().equals(currentExperiment.getOwnerID())) {    // Local user is the owner of the experiment

            profile.setVisibility(View.GONE);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Bundle args = new Bundle();
                        args.putSerializable("exp", currentExperiment);

                        // User(i.e, owner wants to edit the experiment)
                        ExperimentDialogFragment frag = new ExperimentDialogFragment(
                                experiment -> {
                                    if (experiment != null) {

                                        updateText(experiment);
                                        currentExperiment = experiment;

                                        if(currentExperiment.getStatus().equals(Experiment.ENDED)){
                                            addButton.hide();
                                        }
                                    } else {
                                        /* https://stackoverflow.com/a/57013964/12471420 */
                                        Navigation.findNavController(view).popBackStack();
                                    }
                                });
                        frag.setArguments(args);
                        frag.show(getChildFragmentManager(), "edit experiment frag");
                }
            });
        } else {   // If Local user is not the owner of the experiment

            setting.setVisibility(View.GONE);

            // When the profile icon is clicked. User wants to view the profile of the owner
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("user", currentExperiment.getOwnerID());               // Get the owner's id
                    Navigation.findNavController(v).navigate(R.id.expTouser, bundle);       // Go to view the owner's profile
                }
            });
        }
    }

    /**
     * When the fragment is resumed
     */
    @Override
    public void onResume() {
        super.onResume();

        TrialManager.getInstance().queryExperimentTrials(currentExperiment.getExperimentID(), trials -> {
            currentTrials.clear();
            currentTrials.addAll(trials);
            updateAll();
        });
    }

    /**
     * This method initializes the views (instance variables)
     * @param view the view from onCreateView
     */
    private void init(View view) {
        // Get the text views
        experimentName = view.findViewById(R.id.experimentName_expFrag);
        ownerName = view.findViewById(R.id.ownerName_expFrag);
        subNumber = view.findViewById(R.id.expFragSubCount);
        trialNumber = view.findViewById(R.id.trialNumber_expFrag);
        trialType = view.findViewById(R.id.trialType_expFrag);
        expAbout = view.findViewById(R.id.experimentDescription_expFrag);
        expCity = view.findViewById(R.id.experimentRegion_expFrag);
        subSwitch = view.findViewById(R.id.subscribeSwitch);
        status = view.findViewById(R.id.status_exp);
        addButton = view.findViewById(R.id.experiment_fragment_add_button);

        // Title is transparent when expanded
        toolbar = view.findViewById(R.id.expCollapsingToolbar);
        toolbar.setCollapsedTitleTextAppearance(R.style.toolBarCollapsed);
        toolbar.setExpandedTitleTextAppearance(R.style.toolBarExpanded);
    }

    /**
     * This method updates the experiment information that is displayed
     * @param exp the experiment to display the information for
     */
    private void updateText(Experiment exp) {

        experimentName.setText(exp.getExperimentName());
        ownerName.setText("Created by " + exp.getOwnerName());
        expCity.setText("Region: " + exp.getRegion() + (exp.isRequireGeo()?" - GeoLocation: On":" - GeoLocation: Off"));
        trialNumber.setText(exp.getMinimumTrials() + " trials needed");
        trialType.setText(exp.getTrialType());
        expAbout.setText(exp.getExperimentDescription());
        status.setText(String.format("Status: %s", exp.getStatus()));
        toolbar.setTitle(exp.getExperimentName());
    }

    /**
     * This method is called when the add button is selected when the current tab is Trial
     * Creates and displays a CreateTrialDialogFragment to create a new trial
     */
    public void TrialDialogSelect() {

        // Prepare a bundle with the relevant information
        Bundle trialBundle = new Bundle();
        trialBundle.putString("experimenterID", userManager.getLocalUser().getUserId());
        trialBundle.putString("experimenterName", userManager.getLocalUser().getUserName());
        trialBundle.putBoolean("isScan", false);
        trialBundle.putSerializable("experiment", currentExperiment);

        // Create a trial creation dialog fragment
        CreateTrialDialogFragment dialogTrial = new CreateTrialDialogFragment(trial -> {
            currentTrials.add(0, trial);
            updateAll();
        });

        dialogTrial.setArguments(trialBundle);
        dialogTrial.show(getChildFragmentManager(), "create trial frag");
    }

    /**
     * This method is called when info has changed and all the observers need to update their views
     */
    @Override
    public void updateAll() {

        for(Observer observer : observers){
            observer.update(currentTrials);
        }
    }

    /**
     * When the trials are ready from FireStore, updated list
     * @param trials
     * list of trials
     */
    @Override
    public void onTrialsReady(List<Trial> trials) {
        currentTrials = trials;
        updateAll();
    }

    /**
     * A customised {@link FragmentStateAdapter}
     */
    public class ExperimentTabsAdapter extends FragmentStateAdapter {

        /**
         * Experiment adapter
         * @param frag
         * fragment
         */
        public ExperimentTabsAdapter(Fragment frag) {
            super(frag);
        }

        /**
         * Creates a new fragment. Fragment type depends on the position of the tab
         * @param position
         * Position of the fragment in the adapter
         * @return A new Fragment instance.
         * If position == 0: A TrialTabFragment.
         * If position == 1: A DiscussionForumFragment
         * If position == 2: A StatsTabFragment
         * If position == 3: A TrialMapTabFragment
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {

            Bundle bundle = new Bundle();
            bundle.putString("experimentID", currentExperiment.getExperimentID());
            bundle.putSerializable("experiment", currentExperiment);
            Fragment tabFragment;

            switch (position) {
                case 0:
                    tabFragment = new TrialsTabFragment(ExperimentFragment.this);
                    tabFragment.setArguments(bundle);
                    addObserver((TrialsTabFragment)tabFragment);
                    updateAll();
                    return tabFragment;

                case 1:
                    tabFragment = new DiscussionForumFragment();
                    tabFragment.setArguments(bundle);
                    return tabFragment;

                case 2:
                    tabFragment = new StatsTabFragment();
                    tabFragment.setArguments(bundle);
                    addObserver((StatsTabFragment)tabFragment);
                    updateAll();
                    return tabFragment;

                case 3:
                    tabFragment = new TrialMapTabFramgent();
                    tabFragment.setArguments(bundle);
                    addObserver((TrialMapTabFramgent)tabFragment);
                    updateAll();
                    return tabFragment;

                default:
                    return null;
            }
        }

        /**
         * Get item count
         * @return int - Number of items in list
         */
        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
