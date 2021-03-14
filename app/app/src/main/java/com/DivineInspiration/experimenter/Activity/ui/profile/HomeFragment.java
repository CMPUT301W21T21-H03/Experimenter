package com.DivineInspiration.experimenter.Activity.ui.profile;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.DivineInspiration.experimenter.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    /*view pager madness*
    https://developer.android.com/guide/navigation/navigation-swipe-view-2
    */


    public HomeFragment(){
        super(R.layout.fragment_home);
    }

   CollapsingToolbarLayout toolbar;
    AppBarLayout appBar;
    FloatingActionButton fab;
    Button editProfileButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.CollaspingToolBar);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        editProfileButton = (Button)view.findViewById(R.id.edit_profile_button);
        toolbar.setCollapsedTitleTextAppearance(R.style.toolBarCollapsed);
        toolbar.setExpandedTitleTextAppearance(R.style.toolBarExpanded);




        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Woah dude", Snackbar.LENGTH_LONG).show();
            }
        });


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new editProfileDialogFragment().show(getChildFragmentManager(),editProfileDialogFragment.TAG);
            }
        });



        /*
        appbar stuff
        https://stackoverflow.com/questions/31662416/show-collapsingtoolbarlayout-title-only-when-collapsed
         */
        appBar = (AppBarLayout)view.findViewById(R.id.appBar);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean showing = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    showing = true;
                    toolbar.setTitle("Put user here");
                }
                else if (showing){
                    showing = false;
                 toolbar.setTitle(" ");
                }
            }
        });
    }

    private class PagerAdapter extends FragmentStateAdapter {

        public PagerAdapter(Fragment frag){
            super(frag);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment frag = new TestFrag();
            Bundle args = new Bundle();
            args .putString("stuff", String.valueOf((position + 1) * 1000));
            return null;
        }


        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class TestFrag extends Fragment{

    }
}

