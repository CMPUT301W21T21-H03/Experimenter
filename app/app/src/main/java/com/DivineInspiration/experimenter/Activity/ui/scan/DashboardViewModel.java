<<<<<<< HEAD:app/app/src/main/java/com/DivineInspiration/experimenter/Activity/UI/Dashboard/DashboardViewModel.java
package com.DivineInspiration.experimenter.Activity.UI.Dashboard;
=======
package com.DivineInspiration.experimenter.Activity.ui.scan;
>>>>>>> Aniket:app/app/src/main/java/com/DivineInspiration/experimenter/Activity/ui/scan/DashboardViewModel.java

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}