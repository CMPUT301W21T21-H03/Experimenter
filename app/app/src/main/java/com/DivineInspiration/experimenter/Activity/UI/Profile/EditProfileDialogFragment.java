package com.DivineInspiration.experimenter.Activity.UI.Profile;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;


public class EditProfileDialogFragment extends DialogFragment {
    // inits
    TextView editName;
    TextView editAbout;
    TextView editCity;
    TextView editEmail;
    // error texts
    TextView editProfileError1;
    TextView editProfileError2;
    TextView editProfileError3;

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

    /**
     * Checks if name is taken
     * @param email
     * @return
     */
    private boolean nameTaken(String email) {
        // TODO: check if name is taken
        return false;
    }

    /**
     * Checks if email is valid
     * @param email
     * @return
     */
    private boolean checkEmailValid(String email) {
        // check if email is valid
        // from https://stackoverflow.com/questions/8204680/java-regex-email second answer
        // by Jason Buberel: https://stackoverflow.com/users/202275/jason-buberel
        // on stackoverflow.com
        Pattern p = Pattern.compile("^(.+)@(.+)$");
        // Log.v("notice: ", String.valueOf(p.matcher(email).find()));
        return p.matcher(email).find();
    }

    /**
     * Shows alert message on the bottom of the parent fragment page
     * @param error
     * is the alert an error
     * @param message
     * message
     */
    private void showAlert(boolean error, String message) {
        Snackbar snackbar = Snackbar.make(getParentFragment().getView(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor(error ? "#913c3c" : "#2e6b30"));
        snackbar.show();
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
        editName = view.findViewById(R.id.editProfileName);
        editAbout = view.findViewById(R.id.editExperimentAbout);
        editCity = view.findViewById(R.id.editProfileCity);
        editEmail = view.findViewById(R.id.editTrialType);
        editProfileError1 = view.findViewById(R.id.profileError1);
        editProfileError2 = view.findViewById(R.id.profileError2);
        editProfileError3 = view.findViewById(R.id.profileError3);

        // set it to the right values
        editName.setText(newUser.getUserName());
        editAbout.setText(newUser.getDescription());
        editCity.setText(newUser.getContactInfo().getCityName());
        editEmail.setText(newUser.getContactInfo().getEmail());

        // generate alert message
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setMessage("Edit Profile")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel",null)
                .create();

        // shows dialog (must be called at start)
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get strings
                String editNameText = editName.getText().toString();
                String editAboutText = editAbout.getText().toString();
                String editCityText = editCity.getText().toString();
                String editEmailText = editEmail.getText().toString();

                // reset all
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
                //city may be blank
//                if (editCityText.length()==0) {
//                    editProfileError3.setVisibility(TextView.VISIBLE);
//                    validFlag = false;
//                }

                // if not valid in any step, return
                if (!validFlag) {
                    return;
                }

                // new user
                newUser.setUserName(editNameText);
                newUser.setDescription(editAboutText);
                newUser.getContactInfo().setCityName(editCityText);
                newUser.getContactInfo().setEmail(editEmailText);

                //try catch is not needed here

                // updates manager by changes an existing user
                //first check if the user name exist already
                newManager.queryUserByName(newUser.getUserName(), user -> {
                    //if the user return is null, then the desired user name is not used
                    //or if the user fetched is the user currently logged in
                    if(newUser.getUserName().equals("Anonymous") || user == null || (user.getUserName().equals(newUser.getUserName()) && user.getUserId().equals(newUser.getUserId())) ){
                        String currentName = user == null? "":user.getUserName(); //save user name only if trying to update current user, as in, no name update

                        //attempt to update user account
                        newManager.updateUser(newUser, user1 -> {
                            if(user1 != null){
                                showAlert(false, "Profile changed successfully");
                                // show success
                                if(!user1.getUserName().equals(currentName)){ // update owner name of all experiments only if the user name changed
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