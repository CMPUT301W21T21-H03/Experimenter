package com.DivineInspiration.experimenter.Activity.UI.Explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    View search;
    View experimentList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // get search bar
        search = root.findViewById(R.id.explore_searchbar);

        // TODO: eventOnChange listener driving a filter on items
        // ...

        // debug items
        ArrayList<String> testItems = new ArrayList<>();
        testItems.add("Thing1");
        testItems.add("Thing2");
        testItems.add("Thing3");
        testItems.add("Thing4");

        // get list
        experimentList = root.findViewById(R.id.explore_experiments);

        // get recycler list
        experimentList.findViewById(R.id.experimentList);
        // TODO: recycler item

        return root;
    }
//      There shouldn't be any explore button in explore frag
//    /**
//     * After clicking on settings in the explore menu
//     */
//    public void settings() {
//        // TODO: new profile view??
//        Log.v("Change to settings", " Success");
//    }
}