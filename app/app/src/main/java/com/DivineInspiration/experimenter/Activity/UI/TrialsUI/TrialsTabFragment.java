package com.DivineInspiration.experimenter.Activity.UI.TrialsUI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Activity.Observer;
import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * An Activity Fragment that displays an experiments trials. A tab in the Experiment fragment labelled "Trials."
 * @see com.DivineInspiration.experimenter.R.layout#trial_list - Associated xml file
 */
public class TrialsTabFragment extends Fragment implements Observer {

    private TrialListAdapter adapter;
    private List<Trial> trialList = new ArrayList<>();
    private TrialManager.OnTrialListReadyListener callback;

    /**
     * Constructor
     * @param callback
     * callback function for when it is done
     */
    public TrialsTabFragment(TrialManager.OnTrialListReadyListener callback){
        this.callback = callback;
    }

    /**
     * when the fragment is created
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.trialList = new ArrayList<>();
        this.adapter = new TrialListAdapter();
    }

    /**
     * Runs when the view is created. Similar to the activity's onCreate.
     * @return A view created by inflating container with inflater
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trial_list, container, false);

        adapter = new TrialListAdapter(trialList, callback, (Experiment) getArguments().getSerializable("experiment"));

        RecyclerView recycler = view.findViewById(R.id.trialList);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Implementation of the observer interface. This method updates its data
     * when update method is called from ExperimentFragment (which is the observable).
     * @param data
     * A {@link java.util.List} of trials to replace the old trialList
     */
    @Override
    public void update(Object data) {
      Log.d("woah trial tab", "" +   ((List<Trial>) data).size());  //DEBUG
      trialList.clear();
      trialList.addAll((List<Trial>) data);
      Log.d("woah trial tab", "" +   ((List<Trial>) data).size());  //DEBUG

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}