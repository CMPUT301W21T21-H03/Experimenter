package com.DivineInspiration.experimenter.Activity.UI.Explore;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This class deals with the UI for finding publicly available experiments
 * @see R.layout#fragment_explore
 */
public class ExploreFragment extends Fragment implements ExperimentManager.OnExperimentListReadyListener {

    // TODO?: if there is time and for more efficiency, shownList could be an int list that only contain indexes of the datalist (probably not)

    private ExploreListAdapter exploreListAdapter;          // Adapter list of the experiments in the explore tab
    private List<Experiment> dataList = new ArrayList<>();  // Data list contains all the experiments in the database
    private List<Experiment> shownList = new ArrayList<>(); // Shown list only contains of experiments shown on screen

    private CharSequence searchText;                        // Search text entered by the user

    /**
     * Fragment initializer, similar to activity's onCreate
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing the 3 lists
        this.exploreListAdapter = new ExploreListAdapter();
        this.dataList = new ArrayList<>();
        this.shownList = new ArrayList<>();
    }

    /**
     * When view is created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view that was created
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Query the experiments from database, the Experiments will be returned via a callback to onExperimentsReady
        ExperimentManager.getInstance().queryAll(this);

        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        // Initializing explore list
        RecyclerView experimentListView = root.findViewById(R.id.list_experiments);
        experimentListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        experimentListView.setAdapter(exploreListAdapter);
        experimentListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        EditText search = root.findViewById(R.id.explore_search_bar);

        // Search -> filter results
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchText = charSequence;
                filter();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return root;
    }

    /**
     * Filters the experiments so as to display only the experiments that related to the search text
     */
    private void filter() {

        // if string is empty, re-initialize with all data (i.e., all the experiments)
        if (searchText.length() == 0) {
            exploreListAdapter.setData(dataList);
        } else {
            shownList = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                // checks if search text is in the experiment (case insensitive)
                String name = dataList.get(i).getExperimentName().toLowerCase();
                String ownerName = dataList.get(i).getOwnerName().toLowerCase();
                String description = dataList.get(i).getExperimentDescription().toLowerCase();
                // this is probably inefficient :(
                if (name.contains(searchText.toString().toLowerCase()) || ownerName.contains(searchText.toString().toLowerCase()) || description.contains(searchText.toString().toLowerCase())) {
                    shownList.add(dataList.get(i));
                }
            }
            // Store the new filtered result and refresh adapter
            exploreListAdapter.setData(shownList);
            exploreListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * This as a interface implementation method; when the experiment data requested is ready,
     * ExperimentManager calls this method and passes the data as a parameter.
     * The method then updates the list of experiments that are being shown
     * @param queryList list of experiment
     */
    @Override
    public void onExperimentsReady(List<Experiment> queryList) {
        dataList = queryList;
        shownList = queryList;
        exploreListAdapter.setData(shownList);
    }
}