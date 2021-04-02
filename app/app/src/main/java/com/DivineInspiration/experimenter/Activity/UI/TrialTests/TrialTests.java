package com.DivineInspiration.experimenter.Activity.UI.TrialTests;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.R;

public abstract class TrialTests extends Fragment {
    protected Trial current;

    public Trial getTrial() {
        return current;
    }
}
