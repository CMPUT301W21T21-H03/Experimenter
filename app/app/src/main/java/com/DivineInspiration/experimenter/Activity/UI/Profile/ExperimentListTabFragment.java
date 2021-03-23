package com.DivineInspiration.experimenter.Activity.UI.Profile;

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

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperimentListTabFragment extends Fragment implements CreateExperimentDialogFragment.OnExperimentAddedListener
{
    private ExperimentAdapter adapter;
    ArrayList<Experiment> exps = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("stuff", "onCreateView");
        return inflater.inflate(R.layout.experiment_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new ExperimentAdapter(exps);
        RecyclerView recycler = view.findViewById(R.id.experimentList);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        ExperimentManager.getInstance().queryUserExperiment(UserManager.getInstance().getLocalUser().getUserId(), new ExperimentManager.OnExperimentListReadyListener() {
            @Override
            public void onExperimentsReady(List<Experiment> experiments) {
                exps.clear();
                exps.addAll(experiments);
                exps.sort(new Experiment.sortByDescDate());
                adapter.notifyDataSetChanged();
            }
        });



    }


    @Override
    public void onExperimentAdded(Experiment experiment) {
        exps.add(0,experiment);
        adapter.notifyItemInserted(0);
    }


}
