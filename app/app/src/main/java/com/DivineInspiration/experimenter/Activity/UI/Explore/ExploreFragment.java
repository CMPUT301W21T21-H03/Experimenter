package com.DivineInspiration.experimenter.Activity.UI.Explore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Activity.UI.Profile.ExperimentAdapter;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment implements ExperimentManager.ExperimentReadyCallback {

    public ExploreListAdapter exploreListAdapter;
    public List<Experiment> dataList = new ArrayList<>();

    /**
     * Fragment initializer
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.exploreListAdapter = new ExploreListAdapter();
        this.dataList = new ArrayList<>();
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

        // Create the listener
//        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
//            public void onItemClick(AdapterView<?> listDrinks, View itemView, int position, long id) {
//                // TODO: What happens when we click?
//            }
//        };

        // Assign the listener to the recycler view
        // experimentList.setOnItemClickListener(itemClickListener);

        // TODO: search -> filter results

        return root;
    }

    @Override
    public void onExperimentsReady(List<Experiment> queryList) {

        Log.d("ExploreFragment", "Experiment query ready");
        for (Experiment experiment : queryList) {
            Log.d("ExploreFragment", experiment.getExperimentID());
        }

        dataList = queryList;
        exploreListAdapter.setData(dataList);
        Log.d("ExploreFragment", dataList.toString());
    }
}