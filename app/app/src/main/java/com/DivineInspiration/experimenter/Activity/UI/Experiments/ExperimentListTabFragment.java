package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Activity.UI.Refreshable;
import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;


/**
 * This class deals with the UI for displaying the experiments lists of experiments.
 * Used in the Experiments and Subscriptions tabs of Profile fragment.
 * @see R.layout#experiment_list - XML layout file for this fragment
 */
public class ExperimentListTabFragment extends Fragment implements ExperimentDialogFragment.OnExperimentOperationDoneListener, Refreshable {

    private ExperimentAdapter adapter;
    private ArrayList<Experiment> exps = new ArrayList<>();     // All the experiments of the local user (i.e. all that the user has created).

    /**
     * Runs when the view is created. Similar to the activity's onCreate.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     * view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.experiment_list, container, false);
    }

    /**
     * Runs when the view is fully created
     * @param view
     * the view itself
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ExperimentAdapter(exps);      // Initialize the adapter

        // Get the recycler list
        RecyclerView recycler = view.findViewById(R.id.experimentList);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        refresh();      // Refresh the display
    }

    /**
     * This is the method that must be implemented due to interface inheritance of ExperimentDialogFragment.OnExperimentOperationDoneListener
     * When experiment is added, update list
     * @param experiment that is passed by the experiment manager class
     */
    @Override
    public void onOperationDone(Experiment experiment) {
        exps.add(0, experiment);            // Add experiment to start of list to maintain chronological order
        adapter.notifyItemInserted(0);    // Refresh the display
    }

    /**
     * Refreshes the experiment list.
     */
    @Override
    public void refresh() {
        // Get all experiments from database for the particular user
        String userID;
        String type;
        Bundle bundle = this.getArguments();
        assert (bundle != null);
        type = bundle.getString("type");

        if (bundle.containsKey("userId")) {        // If we are given the user id (Occurs when we want to view the profile of another user and not local user).
            userID = bundle.getString("userId");
        } else {                                  // This occurs most of the time when we want to display experiments of the local user.
            userID = UserManager.getInstance().getLocalUser().getUserId();
        }

        if(type.equals("sub")) {                    // If we want to display the experiments the user has subscribed to
            ExperimentManager.getInstance().queryUserSubs(userID, experiments -> loadList(experiments));
        } else if(type.equals("exp")) {             // If we want to display the experiments the user owns
            ExperimentManager.getInstance().queryUserExperiment(userID, experiments -> loadList(experiments));
        } else {
            throw new RuntimeException("oh no!");
        }
    }

    /**
     * This method is run to update the experiments with ones given by the parameter.
     * @param  experiments list of all experiments
     */
    private void loadList(List<Experiment> experiments){
        // Update experiment list
        exps.clear();
        exps.addAll(experiments);
        exps.sort(new Experiment.sortByDescDate());
        adapter.notifyDataSetChanged();
    }
}

