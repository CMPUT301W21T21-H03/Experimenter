package com.DivineInspiration.experimenter.Activity.UI.Profile;

import android.annotation.SuppressLint;
import android.os.Bundle;

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

import com.DivineInspiration.experimenter.Activity.UI.Experiments.ExperimentDialogFragment;
import com.DivineInspiration.experimenter.Activity.UI.Experiments.ExperimentListTabFragment;
import com.DivineInspiration.experimenter.Activity.UI.QRBarCode.BarCodeListFragment;
import com.DivineInspiration.experimenter.Activity.UI.Refreshable;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * This class deals with the UI for displaying the experiment details. (It also contains 4 tabs: Trials, Comments, Stats, Data)
 * @see com.DivineInspiration.experimenter.R.layout#profile_fragment (Contains 3 tabs: Experiments, Subscriptions, Trials)
 */
public class ProfileFragment extends Fragment {

    // Instance variables
    private FloatingActionButton floating_addButton;
    private Button editProfileButton;
    private ViewPager2 pager;
    private HomeFragmentAdapter adapter;
    private TabLayout tabLayout;
    private boolean otherUser = false;          // otherUser is true when the user is viewing profile of another user; if false then displaying info for local user
    private String otherUserId;

    // Manager classes (The controllers that act as an interface between 'Model' and 'View')
    private UserManager user_manager = UserManager.getInstance();
    private ExperimentManager experimentManager = ExperimentManager.getInstance();

    // Declaring TextView
    private TextView userID_home;
    private TextView userName_home;
    private TextView userCity_home;
    private TextView userEmail_home;
    private TextView userDescription_home;
    private View dividerLineName_home;
    private View dividerLineAbout_home;

    // The different tabs that will be displayed
    private final String[] tabNames = {"Experiments", "Subscriptions", "Barcodes"};
    private CollapsingToolbarLayout toolbar;        // Toolbar 'disappears' as you scroll down.

    /**
     * Constructor
     */
    public ProfileFragment() {
        super(R.layout.profile_fragment);
    }

    /**
     * Runs when the view is fully created
     * @param view
     * the view itself
     * @param savedInstanceState 
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the view instance variables
        toolbar = view.findViewById(R.id.expCollapsingToolbar);
        floating_addButton = view.findViewById(R.id.fab);
        editProfileButton = view.findViewById(R.id.edit_profile_button);

        // Setting up the the Views to display the user information (on the home page)
        userName_home = view.findViewById(R.id.userName_Home);
        userID_home = view.findViewById(R.id.userID_Home);
        userEmail_home = view.findViewById(R.id.email_Home);
        userCity_home = view.findViewById(R.id.userCity_home);
        userDescription_home = view.findViewById(R.id.userDescription_home);
        dividerLineName_home = view.findViewById(R.id.sectionDivideLineName_home);
        dividerLineAbout_home = view.findViewById(R.id.sectionDivideLineAbout_home);

        if (getArguments() != null) {       // This means the current instance of the object was not instantiated by another class, hence arguments are null
            String userID = getArguments().getString("user");
            if (!userID.equals(user_manager.getLocalUser().getUserId())) {
                otherUser = true;
                otherUserId = userID;
            }
        } else {
            otherUser = false;
            otherUserId = null;
        }

        // https://proandroiddev.com/the-little-secret-of-android-animatelayoutchanges-e4caab2fddec
        pager = view.findViewById(R.id.expPager);
        if (otherUser) {
            adapter = new HomeFragmentAdapter(this, otherUserId);
        } else {
            adapter = new HomeFragmentAdapter(this);
        }

        pager.setAdapter(adapter);

        tabLayout = view.findViewById(R.id.Tablayout);      // To display the tab headings

        // Setting the tab layout
        new TabLayoutMediator(tabLayout, pager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }).attach();

        // When a new tab is selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

            // Hide fab when on trials or subscriptions
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if ((tab.getPosition() == 0) && (!otherUser)) {
                    floating_addButton.show();      // Only show the add button on 1st tab (Experiments)
                } else {
                    floating_addButton.hide();
                }
            }
        });

        // Setup local user
        if (!otherUser) {       // If we want to display info for current local user
            user_manager.setContext(getContext());
            user_manager.initializeLocalUser(new UserManager.OnUserReadyListener() {
                @Override
                public void onUserReady(User user) {
                    displayUserToolbar(user);
                }
            });
        } else {                // If we want to display info when some user is viewing another's profile
            UserManager.getInstance().queryUserById(otherUserId, new UserManager.OnUserReadyListener() {
                @Override
                public void onUserReady(User user) {
                    displayUserToolbar(user);
                }
            });
            floating_addButton.setVisibility(View.GONE);    // Remove the floating action button as a user should have no write control when viewing another user
            editProfileButton.setVisibility(View.GONE);     // Remove the edit profile button as ,,         ,,          ,,          ,,      ,,      ,,      ,,
        }

        // Title is transparent when expanded
        toolbar.setCollapsedTitleTextAppearance(R.style.toolBarCollapsed);
        toolbar.setExpandedTitleTextAppearance(R.style.toolBarExpanded);

        // Floating action button onClick (Happens when local user wants to add an experiment), we go and create ExperimentDialogFragment
        floating_addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // https://stackoverflow.com/a/61178226/12471420
                new ExperimentDialogFragment((ExperimentDialogFragment.OnExperimentOperationDoneListener) getChildFragmentManager().findFragmentByTag("f0")).show(getChildFragmentManager(), "create experiment");
            }
        });

        // When edit profile button is clicker -> trigger dialog box (create EditProfileDialogFragment)
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditProfileDialogFragment(new UserManager.OnUserReadyListener() {
                    @Override
                    public void onUserReady(User user) {
                        displayUserToolbar(user);
                        //a bit of a hack, reload experiment tab when profile is changed
                        Fragment expListFrag = getChildFragmentManager().findFragmentByTag("f0");
                        if (expListFrag instanceof Refreshable) {
                            ((Refreshable) expListFrag).refresh();
                        }
                    }
                }).show(getChildFragmentManager(), "Edit Profile");
            }
        });
    }

    /**
     * OnResume
     */
    @Override
    public void onResume() {
        super.onResume();
        if (otherUserId != null) {
            user_manager.queryUserById(otherUserId, user -> {
                displayUserToolbar(user);
            });
        } else {
            displayUserToolbar(user_manager.getLocalUser());
        }

    }

    /**
     * Displaying the user info on the screen
     * @param user the user to display the toolbar for
     */
    private void displayUserToolbar(User user) {
        if (user == null) {  // Safety check
            return;
        }

        // Setting the user info in UI
        toolbar.setTitle(user.getUserName());
        userID_home.setText(user.getUserId());
        userCity_home.setText(user.getContactInfo().getCityName());
        userEmail_home.setText(user.getContactInfo().getEmail());
        userDescription_home.setText(user.getDescription());
        userName_home.setText(user.getUserName());

        // Setting Visibility of text Views
        // Visibility for UserID
        userID_home.setVisibility(otherUser ? View.GONE: View.VISIBLE);
        // Visibility for City and Email
        String cityText = user.getContactInfo().getCityName();
        userCity_home.setVisibility(cityText.isEmpty() ? View.GONE : View.VISIBLE);

        String emailText = user.getContactInfo().getEmail();
        userEmail_home.setVisibility(emailText.isEmpty() ? View.GONE : View.VISIBLE);

        if (cityText.isEmpty() && emailText.isEmpty()) {
            dividerLineName_home.setVisibility(View.GONE);
        } else {
            dividerLineName_home.setVisibility(View.VISIBLE);
        }

        // Setting Visibility of User Description
        String descriptionText = user.getDescription();
        if (descriptionText.isEmpty()) {
            userDescription_home.setVisibility(View.GONE);
            dividerLineAbout_home.setVisibility(View.GONE);
        } else {
            userDescription_home.setVisibility(View.VISIBLE);
            dividerLineAbout_home.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Subclass
     * Home fragment
     */
    public class HomeFragmentAdapter extends FragmentStateAdapter {
        String changeUserID = null;

        /**
         * Constructor
         * @param frag home fragment
         */
        public HomeFragmentAdapter(Fragment frag) {
            super(frag);
        }

        /**
         * Constructor
         * @param frag fragment
         * @param userID ID of user
         */
        public HomeFragmentAdapter(Fragment frag, String userID) {
            super(frag);
            changeUserID = userID;
        }

        /**
         * Create the appropriate fragment depending on the position of the tab
         * @param position position in adapter
         * @return fragment
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle bundle = new Bundle();
            ExperimentListTabFragment experimentListTabFragment = new ExperimentListTabFragment();
            switch (position) {
                case 0:
                    bundle.putString("type", "exp");
                    if (changeUserID != null) {
                        bundle.putString("userId", otherUserId);
                    }
                    experimentListTabFragment.setArguments(bundle);
                    return experimentListTabFragment;
                case 1:
                    bundle.putString("type", "sub");
                    if (changeUserID != null) {
                        bundle.putString("userId", otherUserId);
                    }
                    experimentListTabFragment.setArguments(bundle);
                    return experimentListTabFragment;
                case 2:
                    return new BarCodeListFragment();
                default:
                    return new TestFrag();
            }
        }

        /**
         * Get item count
         * @return number of items in list
         */
        @Override
        public int getItemCount() {
            return otherUser?2:3;
        }
    }

    /**
     * Test fragment
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
         * the view itself
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