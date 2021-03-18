package com.DivineInspiration.experimenter.Activity.UI.Explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        populate(root);

        return root;
    }

    private void populate(View root) {
        // Get search bar
        EditText search = root.findViewById(R.id.editText_searchBar);

        // debug items
        String testItems[] = {"Experiment A", "Experiment B", "Experiment C"};

        //Get the List
        ListView experimentList = root.findViewById(R.id.list_experiments);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, testItems);
        experimentList.setAdapter(listAdapter);

        // Create the listener
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listDrinks, View itemView, int position, long id) {
                        // TODO: What happens when we click?
                    }
                };
        //Assign the listener to the list view
        experimentList.setOnItemClickListener(itemClickListener);
    }
}