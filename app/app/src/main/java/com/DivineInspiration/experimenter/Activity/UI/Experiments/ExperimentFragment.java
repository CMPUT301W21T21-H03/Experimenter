package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

import com.DivineInspiration.experimenter.Activity.Observer;
import com.DivineInspiration.experimenter.Activity.Subject;
import com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI.CreateTrialDialogFragment;
import com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI.TrialsTabFragment;
import com.DivineInspiration.experimenter.Activity.UI.Profile.ExperimentDialogFragment;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class deals with the UI for displaying the experiment details. (It also contains 4 tabs: Trials, Comments, Stats, Data)
 * @see: experiment_fragment (Contains 4 tabs: Trials, Comments, Stats, Map)
 */
public class ExperimentFragment extends Fragment implements Subject {
    List<Trial> currentTrials = new ArrayList<>();          // The trials performed for the experiment

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

    Experiment currentExperiment;           // The current experiment we are displaying the info for

    // Manager classes (The controllers that act as an interface between 'Model' and 'View')
    ExperimentManager experimentManager = ExperimentManager.getInstance();
    UserManager userManager = UserManager.getInstance();

    boolean currentUserSubbed = false;      // Is the local user of the device subscribed to the experiment

    // Navigation
    ViewPager2 pager;                       // Enables swiping between tabs
    ExperimentTabsAdapter adapter;
    TabLayout tabLayout;

    // The different tabs that will be displayed
    String[] tabNames = {"Trials", "Comments", "Stats", "Map"};

    CollapsingToolbarLayout toolbar;

    /**
     * Runs when the view is created. Similar to the activity's onCreate
     * @param: inflater:LayoutInflater, container:ViewGroup, savedInstanceState:Bundle
     * @return: :View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.experiment_fragment, container, false);
    }

    /**
     * Runs when the view is fully created
     * @param: savedInstanceState:Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);         // Initialize the views

        // Get experiment and set details to be displayed
        currentExperiment = (Experiment) getArguments().getSerializable("experiment");
        updateText(currentExperiment);

        // View pager
        pager = view.findViewById(R.id.expPager);
        adapter = new ExperimentTabsAdapter(this);
        pager.setAdapter(adapter);
        tabLayout = view.findViewById(R.id.Tablayout);
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
                if((position == 0 || position == 1) && currentUserSubbed && !currentExperiment.getStatus().equals(Experiment.ENDED) ){
                    addButton.show();
                } else {
                    addButton.hide();
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
                            // We now go through all the subscribers and check if current local user is one of them
                            if (UserManager.getInstance().getLocalUser().getUserId()
                                    .equals(users.get(i).getUserId())) {
                                subSwitch.setChecked(true);
                                addButton.show();
                                currentUserSubbed = true;
                            }
                        }
                        ((TextView) getView().findViewById(R.id.expFragSubCount))
                                .setText(users.size() + " subscribers");    // Display the no. of subscribers in the info part
                    }
                });

        // Toggle sub to experiment (When button on the top right is toggled)
        subSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    experimentManager.subToExperiment(userManager.getLocalUser().getUserId(),
                            currentExperiment.getExperimentID(), null);
                    // On check, it should be set visible again (as the user can add trials and comments if he/she is subscribed)
                    addButton.show();
                    currentUserSubbed = true;
                } else {
                    experimentManager.unSubFromExperiment(userManager.getLocalUser().getUserId(),
                            currentExperiment.getExperimentID(), null);
                    // if changed to unsubscribed, addButton should be set invisible again
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
                    case 0:                 // If add button is clicked on Trials tab, the user wants to add a trial.
                        TrialDialogSelect();
                        break;
                    case 1:                 // If add button is clicked on Trials tab, the user wants to add a comment.
                        // Make a bundle of the info we want to pass
                        Bundle bundle = new Bundle();
                        bundle.putString("commenterID", userManager.getLocalUser().getUserId());
                        bundle.putString("commenterName", userManager.getLocalUser().getUserName());
                        bundle.putString("experimentID", currentExperiment.getExperimentID());

                        CreateCommentDialogFragment dialog = new CreateCommentDialogFragment((CreateCommentDialogFragment.OnCommentCreatedListener) getChildFragmentManager().findFragmentByTag("f1"));
                        dialog.setArguments(bundle);
                        dialog.show(getChildFragmentManager(), "create comment frag");
                        break;
                    default:
                        // Do nothing
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
                                    } else {
                                        /* https://stackoverflow.com/a/57013964/12471420 */
                                        Navigation.findNavController(view).popBackStack();
                                    }
                                });
                        frag.setArguments(args);
                        frag.show(getChildFragmentManager(), "edit experiment frag");
                }
            });
        } else {                                                                            // Local user is not the owner of the experiment
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

    @Override
    public void onResume() {
        super.onResume();

        TrialManager.getInstance().queryExperimentTrials(currentExperiment.getExperimentID(), trials -> {
            currentTrials = trials;
            updateAll();
        });
    }

    /**
     * This method initializes the views (instance variables)
     * @param: view:View (The view from onCreateView)
     * @return: void
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
     * @param: exp:Experiment (The experiment to display the information for)
     * @return: void
     */
    private void updateText(Experiment exp) {
        experimentName.setText(exp.getExperimentName());
        ownerName.setText("Created by " + exp.getOwnerName());
        expCity.setText("Region: " + exp.getRegion());
        trialNumber.setText(exp.getMinimumTrials() + " trials needed");
        trialType.setText(exp.getTrialType());
        expAbout.setText(exp.getExperimentDescription());
        status.setText(String.format("Status: %s", exp.getStatus()));
        toolbar.setTitle(exp.getExperimentName());
    }

    /**
     * This method is called when the add button is selected when the current tab is Trial
     * @param: void
     * @return: void
     */
    public void TrialDialogSelect() {
        // Prepare a bundle with the relevant information
        Bundle trialBundle = new Bundle();
        trialBundle.putString("experimenterID", userManager.getLocalUser().getUserId());
        trialBundle.putString("experimenterName", userManager.getLocalUser().getUserName());
        trialBundle.putSerializable("experiment", currentExperiment);
        // Go and display the UI to create a new trial
        CreateTrialDialogFragment dialogTrial = new CreateTrialDialogFragment(trial -> {
//            ( (CreateTrialDialogFragment.OnTrialCreatedListener) getChildFragmentManager().findFragmentByTag("f0")).onTrialAdded(trial);
//            ( (CreateTrialDialogFragment.OnTrialCreatedListener) getChildFragmentManager().findFragmentByTag("f2")).onTrialAdded(trial);
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
     * Home fragment
     */
    public class ExperimentTabsAdapter extends FragmentStateAdapter {
        public ExperimentTabsAdapter(Fragment frag) {
            super(frag);
        }
        @NonNull
        @Override
        /**
         * Create the appropriate fragment depending on the position of the tab
         * @param: position:int (position in adapter)
         * @return: :Fragment
         */
        public Fragment createFragment(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("experimentID", currentExperiment.getExperimentID());
            Fragment tabFragment;
            switch (position) {
                case 0:
                    tabFragment = new TrialsTabFragment();
                    addObserver((TrialsTabFragment)tabFragment);
                    updateAll();
                    return tabFragment;
                case 1:
                    tabFragment = new DiscussionForumFragment();
                    tabFragment.setArguments(bundle);
                    return tabFragment;
                case 2:
                    tabFragment = new StatsTabFragment();
                    addObserver((StatsTabFragment)tabFragment);
                    updateAll();
                    return tabFragment;

                case 3:
                    tabFragment = new TrialMapTabFramgent();
                    addObserver((TrialMapTabFramgent)tabFragment);
                    updateAll();
                    return tabFragment;
                default:
                    return new PlaceHolderFragment();
            }
        }

        /**
         * Get item count
         * @return: :int (number of items in list)
         */
        @Override
        public int getItemCount() {
            return 4;
        }
    }


    /**
     * Placeholder fragment
     */
    public static class PlaceHolderFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.test, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ListView list = view.findViewById(R.id.placeHolderList);

            String[] items = {"Russell’s", "Paradox", "tells", "us", "that", "Humans", "are", "bad", "at", "math.",
                    "Our", "intuitions", "lead", "us", "astray.", "Things", "that", "look", "reasonable,", "can", "be",
                    "completely", "wrong.", "So", "we", "have", "to", "be", "very", "very", "careful,", "very", "very",
                    "precise,", "very", "very", "logical.", "We", "don’t", "want", "to", "be,", "but", "we", "have",
                    "to", "be.", "Or", "we’ll", "get", "into", "all", "kinds", "of", "trouble.", "So", "let’s",
                    "describe", "the", "grammar", "of", "math,", "which", "is", "logic!"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.test_item, items);

            list.setAdapter(adapter);
        }
    }
}
