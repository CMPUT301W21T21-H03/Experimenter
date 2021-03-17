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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.DivineInspiration.experimenter.Controller.LocalUserManager;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;



public class HomeFragment extends Fragment implements  LocalUserManager.UserReadyCallback {


    /* view pager madness
    https://developer.android.com/guide/navigation/navigation-swipe-view-2

    */


    public HomeFragment(){
        super(R.layout.fragment_home);
        Log.d("stuff", "fragment, constructor");
    }

    CollapsingToolbarLayout toolbar;

    FloatingActionButton fab;
    Button editProfileButton;
    LocalUserManager manager = LocalUserManager.getInstance();
    ViewPager2 pager;
    HomeFragmentAdapter adapter;
    TabLayout tabLayout;
    TextView userID_home;


    private final String[] tabNames = {"Experiments", "Subscriptions", "Trials"};


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.CollaspingToolBar);
        fab = view.findViewById(R.id.fab);
        editProfileButton = view.findViewById(R.id.edit_profile_button);
        userID_home = view.findViewById(R.id.userID_Home);

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




        //setup local user
        manager.setContext(getContext());
        manager.setReadyCallback(this);


        // title is transparent when expanded
        toolbar.setCollapsedTitleTextAppearance(R.style.toolBarCollapsed);
        toolbar.setExpandedTitleTextAppearance(R.style.toolBarExpanded);




        // fab onclick
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Woah dude", Snackbar.LENGTH_LONG).show();
            }
        });


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditProfileDialogFragment().show(getChildFragmentManager(), EditProfileDialogFragment.TAG);
            }
        });







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
    public void onUserReady() {
        toolbar.setTitle(manager.getUser().getUserName());

        // Displaying User iD
        userID_home.setText(manager.getUser().getUserId());

    }

    public  class HomeFragmentAdapter extends FragmentStateAdapter {

        public HomeFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }


        public HomeFragmentAdapter(Fragment frag){
            super(frag);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment frag = new TestFrag();
            Bundle args = new Bundle();
            args.putString("stuff", String.valueOf((position+1) * 100));
            frag.setArguments(args);

            return frag;
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

