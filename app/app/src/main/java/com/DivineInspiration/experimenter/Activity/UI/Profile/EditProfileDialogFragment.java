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
    UserManager newManager = UserManager.getInstance();
    TextView editName;
    TextView editAbout;
    TextView editCity;
    TextView editEmail;
    UserManager.LocalUserCallback callback;

    public EditProfileDialogFragment(UserManager.LocalUserCallback callback){
        super();
        this.callback = callback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_profile_dialog_fragment, null);
        User newUser = newManager.getLocalUser();

        editName = view.findViewById(R.id.editExperimentName);
        editAbout = view.findViewById(R.id.editExperimentAbout);
        editCity = view.findViewById(R.id.editExperimentCity);
        editEmail = view.findViewById(R.id.editTrialType);

        editName.setText(newUser.getUserName());
        editAbout.setText(newUser.getDescription());
        editCity.setText(newUser.getContactInfo().getCityName());
        editEmail.setText(newUser.getContactInfo().getEmail());


        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setMessage("Edit Profile")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editNameText = editName.getText().toString();
                        String editAboutText = editAbout.getText().toString();
                        String editCityText = editCity.getText().toString();
                        String editEmailText = editEmail.getText().toString();

                        newUser.setUserName(editNameText);
                        newUser.setDescription(editAboutText);
                        newUser.getContactInfo().setCityName(editCityText);
                        newUser.getContactInfo().setEmail(editEmailText);

                        newManager.updateUser(new User(newUser.getUserName(),newUser.getUserId(),newUser.getContactInfo(),newUser.getDescription()), callback);

                    }
                })
                .setNegativeButton("cancel",null)
                .create();
    }
    public static String TAG = "edit Profile";
}