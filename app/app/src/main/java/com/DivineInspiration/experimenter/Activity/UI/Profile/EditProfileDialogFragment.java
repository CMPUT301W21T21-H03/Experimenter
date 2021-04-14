package com.DivineInspiration.experimenter.Activity.UI.Profile;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;

import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

/**
 * This class provides the UI for a user to create or edit his/her own profile
 * @see edit_profile_dialog_fragment
 * Runs when: 'EDIT' button is clicked on the home page
 */
public class EditProfileDialogFragment extends DialogFragment {

    // Instance variables
    private TextView editName;
    private TextView editAbout;
    private TextView editCity;
    private TextView editEmail;

    // Error texts
    private TextView editProfileError1;
    private TextView editProfileError2;
    private TextView editProfileError3;

    private UserManager newManager = UserManager.getInstance();        // User manager
    private UserManager.OnUserReadyListener callback;                  // Callback for UserManager to tell us when User is ready after retrieval form database

    private static String TAG = "Edit Profile";

    /**
     * Constructor
     * @param callback when the edit is done
     */
    public EditProfileDialogFragment(UserManager.OnUserReadyListener callback){
        super();
        this.callback = callback;
    }

    /**
     * Checks if email entered by the user is valid
     * @param email
     * email string
     * @return
     * if it is valid email or not
     */
    private boolean checkEmailValid(String email) {
        // from https://stackoverflow.com/questions/8204680/java-regex-email second answer
        // by Jason Buberel: https://stackoverflow.com/users/202275/jason-buberel
        // on stackoverflow.com
        Pattern p = Pattern.compile("^(.+)@(.+)$");
        // Log.v("notice: ", String.valueOf(p.matcher(email).find()));
        return p.matcher(email).find();
    }

    /**
     * Shows alert message on the bottom of the parent fragment page is info was successfully edited
     * @param error true is the alert is due to an error, else false
     * @param message message to display
     */
    private void showAlert(boolean error, String message) {
        Snackbar snackbar = Snackbar.make(getParentFragment().getView(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor(error ? "#913c3c" : "#2e6b30"));     // USe appropriate color (green or red) depending on error
        snackbar.show();
    }

    /**
     * Runs when the dialog is created.
     * @param savedInstanceState
     * the bundle
     * @return
     * the dialog to be created
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_profile_dialog_fragment, null);

        // Get the current local user
        User newUser = newManager.getLocalUser();

        // Initialize the views
        editName = view.findViewById(R.id.editProfileName);
        editAbout = view.findViewById(R.id.editExperimentAbout);
        editCity = view.findViewById(R.id.editProfileCity);
        editEmail = view.findViewById(R.id.editTrialType);
        editProfileError1 = view.findViewById(R.id.profileError1);
        editProfileError2 = view.findViewById(R.id.profileError2);
        editProfileError3 = view.findViewById(R.id.profileError3);

        // Display the info
        editName.setText(newUser.getUserName());
        editAbout.setText(newUser.getDescription());
        editCity.setText(newUser.getContactInfo().getCityName());
        editEmail.setText(newUser.getContactInfo().getEmail());

        // Generate alert message
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setMessage("Edit Profile")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel",null)
                .create();

        // Shows dialog (must be called at start)
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get strings
                String editNameText = editName.getText().toString();
                String editAboutText = editAbout.getText().toString();
                String editCityText = editCity.getText().toString();
                String editEmailText = editEmail.getText().toString();

                // Reset all
                editProfileError1.setVisibility(TextView.GONE);
                editProfileError2.setVisibility(TextView.GONE);
                editProfileError3.setVisibility(TextView.GONE);

                boolean validFlag = true;
                // TODO: check validity
                if (editNameText.length()==0) {
                    // if zero, show not valid error
                    editProfileError1.setText("Must have a name");
                    editProfileError1.setVisibility(TextView.VISIBLE);
                    validFlag = false;
                }
                if (editEmailText.length()>0 && !checkEmailValid(editEmailText)) {
                    // editProfileError2.setText(editEmailText.length() == 0 ? "Email field is empty" : "Not a valid email address");
                    editProfileError2.setText("Not a valid email address");
                    editProfileError2.setVisibility(TextView.VISIBLE);
                    validFlag = false;
                }

                // If not valid in any of the above steps, return
                if (!validFlag) {
                    return;
                }

                // New user
                newUser.setUserName(editNameText);
                newUser.setDescription(editAboutText);
                newUser.getContactInfo().setCityName(editCityText);
                newUser.getContactInfo().setEmail(editEmailText);

                // Updates the info in the database through user manager
                // first check if the user name exist already
                newManager.queryUserByName(newUser.getUserName(), user -> {
                    // if the user return is null, then the desired user name is not used
                    // or if the user fetched is the user currently logged in
                    if(newUser.getUserName().equals("Anonymous") || user == null || (user.getUserName().equals(newUser.getUserName()) && user.getUserId().equals(newUser.getUserId())) ){
                        String currentName = user == null? "":user.getUserName(); //save user name only if trying to update current user, as in, no name update

                        //attempt to update user account
                        newManager.updateUser(newUser, user1 -> {
                            if(user1 != null){
                                showAlert(false, "Profile changed successfully");
                                // show success
                                Log.d("woah name from database: " ,user1.getUserName());
                                Log.d("woah currentname: " ,currentName);

                                if(!user1.getUserName().equals(currentName) ||newUser.getUserName().equals("Anonymous")){ // update owner name of all experiments only if the user name changed
                                    ExperimentManager.getInstance().updateOwnerName(user1.getUserId(), user1.getUserName(), bool ->{
                                        callback.onUserReady(user1);
                                    });
                                }
                                else{
                                    //No need to update experiments, just callback profile ui to update views
                                    callback.onUserReady(user1);
                                }
                            }
                            else{
                                //adding user failed
                                showAlert(true, "Failed to change profile");
                            }
                            dialog.dismiss();
                        });
                    }
                    else {
                        editProfileError1.setText("Name taken 😟");
                        editProfileError1.setVisibility(TextView.VISIBLE);
                    }
                });
            }
        });
        return  dialog;
    }
}