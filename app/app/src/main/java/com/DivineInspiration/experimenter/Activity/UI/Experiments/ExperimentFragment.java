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
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.DivineInspiration.experimenter.Activity.UI.Profile.ExperimentDialogFragment;
import com.DivineInspiration.experimenter.Activity.UI.TrialTests.BinomialTest;
import com.DivineInspiration.experimenter.Activity.UI.TrialTests.CountTest;
import com.DivineInspiration.experimenter.Activity.UI.TrialTests.MeasureTest;
import com.DivineInspiration.experimenter.Activity.UI.TrialTests.NonNegativeTest;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class ExperimentFragment extends Fragment {
    // text views
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

    // managers
    ExperimentManager experimentManager = ExperimentManager.getInstance();
    UserManager userManager = UserManager.getInstance();

    // nav
    ViewPager2 pager;
    HomeFragmentAdapter adapter;
    TabLayout tabLayout;

    // tab names
    String[] tabNames = {"Trials", "Comments", "Stats"};
    private ArrayList<User> subscribers  = new ArrayList<>();

    Experiment currentExperiment;

    /**
     * On create
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_experiment, container, false);
    }

    /**
     * On view
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        // get experiment and set the text
        currentExperiment = (Experiment)getArguments().getSerializable("experiment");

        updateText(currentExperiment);


        // view pager
        pager = view.findViewById(R.id.expPager);
        adapter = new HomeFragmentAdapter(this);
        pager.setAdapter(adapter);
        tabLayout = view.findViewById(R.id.Tablayout);
        // set tab names
        new TabLayoutMediator(tabLayout, pager,true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }).attach();

        // get experiment that you subbed to
        UserManager.getInstance().queryExperimentSubs(currentExperiment.getExperimentID(), new UserManager.OnUserListReadyListener() {
            @Override
            public void onUserListReady(ArrayList<User> users) {
                subscribers.clear();
                subscribers.addAll(users);
                for (int i = 0; i < subscribers.size(); i++){
                    if (UserManager.getInstance().getLocalUser().getUserId().equals(subscribers.get(i).getUserId())){
                        subSwitch.setChecked(true);
                        addButton.setVisibility(View.VISIBLE);
                    }
                }

                ((TextView) getView().findViewById(R.id.expFragSubCount)).setText(subscribers.size() + " subscribers");
            }
        });

        // toggle sub to experiment
        subSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    experimentManager.subToExperiment(userManager.getLocalUser().getUserId(), currentExperiment.getExperimentID(), null);
                    // on check, it should be set visible again
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    experimentManager.unSubFromExperiment(userManager.getLocalUser().getUserId(), currentExperiment.getExperimentID(), null);
                    // if changed to unsub, it should be set invisible again
                    addButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        // when add trial button is clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new trial
                switch (currentExperiment.getTrialType()) {
                    case Trial.COUNT:
                        BinomialTest trialTestBinomial = new BinomialTest(userManager.getLocalUser().getUserId(), currentExperiment.getExperimentID());
                        break;
                    case Trial.MEASURE:
                        NonNegativeTest trialTestNonNegative = new NonNegativeTest(userManager.getLocalUser().getUserId(), currentExperiment.getExperimentID());
                        break;
                    case Trial.NONNEGATIVE:
                        MeasureTest trialTestMeasure = new MeasureTest(userManager.getLocalUser().getUserId(), currentExperiment.getExperimentID());
                        break;
                    default:
                        CountTest trialTestCount = new CountTest(userManager.getLocalUser().getUserId(), currentExperiment.getExperimentID());
                        break;
                }
            }
        });

        // setting and profile view
        View setting = view.findViewById(R.id.setting);
        View profile = view.findViewById(R.id.profile);

        // when local user is owner
        if (UserManager.getInstance().getLocalUser().getUserId().equals(currentExperiment.getOwnerID())) {
          profile.setVisibility(View.GONE);
          setting.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putSerializable("exp", currentExperiment);

                  ExperimentDialogFragment frag = new ExperimentDialogFragment(new ExperimentDialogFragment.OnExperimentOperationDoneListener() {
                        @Override
                        public void onOperationDone(Experiment experiment) {
                            if(experiment != null){
                                updateText(experiment);
                                currentExperiment = experiment;
                            }
                            else{
                                /*https://stackoverflow.com/a/57013964/12471420*/
                                Navigation.findNavController(view).popBackStack();
                            }
                        }
                    });
                  frag.setArguments(args);
                  frag.show(getChildFragmentManager(), "edit experiment frag");

              }
          });
        } else {
           setting.setVisibility(View.GONE);
           // when the profile is clicked
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("user",currentExperiment.getOwnerID().toString());
                    Navigation.findNavController(v).navigate(R.id.expTouser, bundle);
                }
            });
        }



    }
    private void init(View view){
        // get text views
        experimentName = view.findViewById(R.id.experimentName_expFrag);
        ownerName = view.findViewById(R.id.ownerName_expFrag);
        subNumber = view.findViewById(R.id.expFragSubCount);
        trialNumber = view.findViewById(R.id.trialNumber_expFrag);
        trialType = view.findViewById(R.id.trialType_expFrag);
        expAbout = view.findViewById(R.id.experimentDescription_expFrag);
        expCity = view.findViewById(R.id.experimentRegion_expFrag);
        subSwitch = view.findViewById(R.id.subscribeSwitch);
        status = view.findViewById(R.id.status_exp);
        addButton = view.findViewById(R.id.addTrial);
    }

    private void updateText(Experiment exp){
        experimentName.setText(exp.getExperimentName());
        ownerName.setText("Created by " + exp.getOwnerName());
        expCity.setText(exp.getRegion() + " city");
        trialNumber.setText(String.valueOf(exp.getMinimumTrials()) + " trials needed");
        trialType.setText(exp.getTrialType());
        expAbout.setText(exp.getExperimentDescription());
        status.setText(String.format("Status: %s", exp.getStatus()));
    }

    /**
     * Home fragment
     */
    public class HomeFragmentAdapter extends FragmentStateAdapter {

        public HomeFragmentAdapter(Fragment frag){
            super(frag);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    return new TrialsTabFragment();
                case 1:
                    return new DiscussionForumFragment();
                case 2:
                    return new StatsTabFragment();
                default:
                    return new PlaceHolderFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    /**
     * Placeholder fragment
     */
    public static class PlaceHolderFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.test, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ListView list = view.findViewById(R.id.placeHolderList);

            String[] items = {"Russell’s", "Paradox", "tellswhat", "us", "that", "Humans", "are", "bad", "at", "math.", "Our", "intuitions", "lead", "us", "astray.", "Things", "that", "look", "reasonable,", "can", "be", "completely", "wrong.", "So", "we", "have", "to", "be", "very", "very", "careful,", "very", "very", "precise,", "very", "very", "logical.", "We", "don’t", "want", "to", "be,", "but", "we", "have", "to", "be.", "Or", "we’ll", "get", "into", "all", "kinds", "of", "trouble.", "So", "let’s", "describe", "the", "grammar", "of", "math,", "which", "is", "logic!"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.test_item, items);

            list.setAdapter(adapter);
        }
    }
}
