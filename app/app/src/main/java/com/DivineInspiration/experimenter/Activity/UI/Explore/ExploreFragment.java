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

    public ExploreListAdapter exploreListAdapter;
    public List<Experiment> dataList = new ArrayList<>();
    public List<Experiment> shownList = new ArrayList<>();

    private CharSequence searchText;

    /**
     * Fragment initializer
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("ExploreFragment", "Begin queryAll");

        ExperimentManager.getInstance().queryAll(this);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        EditText search = root.findViewById(R.id.explore_search_bar);

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

        // TODO: search -> filter results
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchText = charSequence;
                Log.v("Thing:", charSequence.toString());
                // filter text
                filter();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                String name = dataList.get(i).getExperimentName().toLowerCase();
                if (name.contains(searchText.toString().toLowerCase())) {
                    shownList.add(dataList.get(i));

                }
            }
         exploreListAdapter.setData(shownList);
         exploreListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onExperimentsReady(List<Experiment> queryList) {

        Log.d("ExploreFragment", "Experiment query ready");
        for (Experiment experiment : queryList) {
            Log.d("ExploreFragment", experiment.getExperimentID());
        }

        dataList = queryList;
        shownList = queryList;
        exploreListAdapter.setData(shownList);
        Log.d("ExploreFragment", shownList.toString());
    }
}