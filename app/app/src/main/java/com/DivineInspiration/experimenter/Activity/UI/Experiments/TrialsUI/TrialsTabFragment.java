package com.DivineInspiration.experimenter.Activity.UI.Experiments.TrialsUI;

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

import com.DivineInspiration.experimenter.Controller.TrialManager;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

import static com.DivineInspiration.experimenter.Activity.UI.Profile.EditProfileDialogFragment.TAG;

public class TrialsTabFragment extends Fragment implements TrialManager.OnTrialListReadyListener, CreateTrialDialogFragment.OnTrialCreatedListener {

    private TrialListAdapter adapter;
    private List<Trial> trialArrayList = new ArrayList<>();
    String experimentID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new TrialListAdapter(trialArrayList);
        View view = inflater.inflate(R.layout.trial_list, container, false);
        Bundle bundle = getArguments();
        experimentID = bundle.getString("experimentToTrialID");
        TrialManager.getInstance().getExperimentTrials(experimentID, this);
//        CommentManager.getInstance().getExperimentComments(experiment, this);
        RecyclerView recycler = view.findViewById(R.id.trialList);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onTrialAdded(Trial trial) {

        trialArrayList.add(0, trial);
        adapter.setTrials(trialArrayList);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onTrialsReady(List<Trial> trials) {
        trialArrayList = trials;
        adapter.setTrials(trials);
    }




}
