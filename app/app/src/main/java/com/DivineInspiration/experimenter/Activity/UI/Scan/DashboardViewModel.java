package com.DivineInspiration.experimenter.Activity.UI.Scan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    /**
     * Constructor
     */
    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    /**
     * Gets text
     * @return
     * live text data
     */
    public LiveData<String> getText() {
        return mText;
    }
}