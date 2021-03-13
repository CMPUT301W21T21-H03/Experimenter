<<<<<<< HEAD:app/app/src/main/java/com/DivineInspiration/experimenter/Activity/UI/Notifications/NotificationsViewModel.java
package com.DivineInspiration.experimenter.Activity.UI.Notifications;
=======
package com.DivineInspiration.experimenter.Activity.ui.explore;
>>>>>>> Aniket:app/app/src/main/java/com/DivineInspiration/experimenter/Activity/ui/explore/NotificationsViewModel.java

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}