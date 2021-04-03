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

public class ExploreFragment extends Fragment implements ExperimentManager.OnExperimentListReadyListener {

    // adapter list of the experiments in the explore tab
    private ExploreListAdapter exploreListAdapter;
    // data list contains all the experiments in the database
    private List<Experiment> dataList = new ArrayList<>();
    // shown list only contains of experiments shown on screen
    // TODO?: if there is time and for more efficiency, this could be an int list that only contain
    // the indexes of the data list shown
    private List<Experiment> shownList = new ArrayList<>();

    // search text
    private CharSequence searchText;

    /**
     * Fragment initializer
     * @param savedInstanceState
     * the bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inits all three lists
        this.exploreListAdapter = new ExploreListAdapter();
        this.dataList = new ArrayList<>();
        this.shownList = new ArrayList<>();
    }

    /**
     * When view is created
     * @param inflater
     * @param container
     * Container containing fragment
     * @param savedInstanceState
     * the bundle
     * @return
     * the view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Log.d("ExploreFragment", "Begin queryAll");

        // query from database
        ExperimentManager.getInstance().queryAll(this);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        // initing explore list
        RecyclerView experimentListView = root.findViewById(R.id.list_experiments);
        experimentListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        experimentListView.setAdapter(exploreListAdapter);
        experimentListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // Create the listener
//        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
//            public void onItemClick(AdapterView<?> listDrinks, View itemView, int position, long id) {
//                // TODO: What happens when we click?
//            }
//        };


        // Assign the listener to the recycler view
        // experimentList.setOnItemClickListener(itemClickListener);

        EditText search = root.findViewById(R.id.explore_search_bar);

        // search -> filter results
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchText = charSequence;
                // Debug
                // Log.v("Thing:", charSequence.toString());
                // filter text
                filter();
            }
        });

        return root;
    }

    /**
     * Simple filter function that updates the array list
     */
    public void filter() {
        // if string is empty, reinit with all data
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
            // store the new filtered result and refresh adapter
            exploreListAdapter.setData(shownList);
            exploreListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * When experiment is ready from the database
     * @param queryList
     * list of experiment
     */
    @Override
    public void onExperimentsReady(List<Experiment> queryList) {
        // debug print
//        for (Experiment experiment : queryList) {
//            Log.d("ExploreFragment", experiment.getExperimentID());
//        }

        // store the data from the database
        dataList = queryList;
        shownList = queryList;
        exploreListAdapter.setData(shownList);
    }
}