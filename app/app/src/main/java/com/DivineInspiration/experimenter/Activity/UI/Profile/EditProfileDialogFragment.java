package com.DivineInspiration.experimenter.Activity.UI.Profile;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.DivineInspiration.experimenter.Controller.UserManager;

import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;



public class EditProfileDialogFragment extends DialogFragment {
    // inits
    TextView editName;
    TextView editAbout;
    TextView editCity;
    TextView editEmail;

    // user manager
    UserManager newManager = UserManager.getInstance();
    UserManager.OnUserReadyListener callback;

    public static String TAG = "edit Profile";

    /**
     * Constructor
     * @param callback
     */
    public EditProfileDialogFragment(UserManager.OnUserReadyListener callback){
        super();
        this.callback = callback;
    }

    private boolean checkEmailValid(String email) {
        // TODO: check if email is valid
        return true;
    }

    /**
     * When dialog is created
     * @param savedInstanceState
     * @return
     * dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // new view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_profile_dialog_fragment, null);
        // get current user
        User newUser = newManager.getLocalUser();

        // get views
        editName = view.findViewById(R.id.editExperimentName);
        editAbout = view.findViewById(R.id.editExperimentAbout);
        editCity = view.findViewById(R.id.editExperimentCity);
        editEmail = view.findViewById(R.id.editTrialType);

        // set it to the right values
        editName.setText(newUser.getUserName());
        editAbout.setText(newUser.getDescription());
        editCity.setText(newUser.getContactInfo().getCityName());
        editEmail.setText(newUser.getContactInfo().getEmail());

        // generate alert message
        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setMessage("Edit Profile")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get strings
                        String editNameText = editName.getText().toString();
                        String editAboutText = editAbout.getText().toString();
                        String editCityText = editCity.getText().toString();
                        String editEmailText = editEmail.getText().toString();

                        // TODO: check validity
                        if (editAboutText.length()==0) {
                            // TODO: error
                            return;
                        } else if (editCityText.length()==0) {
                            return;
                        } else if (editEmailText.length()==0 || !checkEmailValid(editAboutText)) {
                            return;
                        }

                        // new user
                        newUser.setUserName(editNameText);
                        newUser.setDescription(editAboutText);
                        newUser.getContactInfo().setCityName(editCityText);
                        newUser.getContactInfo().setEmail(editEmailText);

                        // updates manager by changes an existing user
                        newManager.updateUser(new User(newUser.getUserName(),newUser.getUserId(),newUser.getContactInfo(),newUser.getDescription()), callback);

                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
    }
}