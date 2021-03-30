package com.DivineInspiration.experimenter.Activity.UI.Profile;

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

public class ExperimentListTabFragment extends Fragment implements ExperimentDialogFragment.OnExperimentOperationDoneListener, Refreshable {
    private ExperimentAdapter adapter;
    ArrayList<Experiment> exps = new ArrayList<>();

    /**
     * Experiment list fragment on create
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Log.d("stuff", "onCreateView");
        return inflater.inflate(R.layout.experiment_list, container, false);

    }

    /**
     * Experiment list fragment on view
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // inits
        adapter = new ExperimentAdapter(exps);

        // gets recycler list
        RecyclerView recycler = view.findViewById(R.id.experimentList);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        refresh();
    }

    /**
     * When experiment is added, update list
     *
     * @param experiment
     */
    @Override
    public void onOperationDone(Experiment experiment) {
        // add experiment to start of list
        exps.add(0, experiment);
        // refresh
        adapter.notifyItemInserted(0);
    }


    @Override
    public void refresh() {
        // get all experiments from database

        String userID;
        String type;
        Bundle bundle = this.getArguments();
        assert (bundle != null);
        type = bundle.getString("type");

        if (bundle.containsKey("userId")) {
            userID = bundle.getString("userId");
        } else {
            userID = UserManager.getInstance().getLocalUser().getUserId();
        }

        if(type.equals("sub")){
            ExperimentManager.getInstance().queryUserSubs(userID, experiments -> loadList(experiments));
        }else if(type.equals("exp")){
            ExperimentManager.getInstance().queryUserExperiment(userID, experiments -> loadList(experiments));
        }else{
            throw new RuntimeException("oh no!");
        }
    }

    private void loadList(List<Experiment> experiments){
        // update experiment list
        exps.clear();
        exps.addAll(experiments);
        exps.sort(new Experiment.sortByDescDate());
        adapter.notifyDataSetChanged();
    }
}

