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
import com.DivineInspiration.experimenter.Controller.LocalUserManager;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;



public class EditProfileDialogFragment extends DialogFragment {
    LocalUserManager newManager = LocalUserManager.getInstance();
    TextView editName;
    TextView editAbout;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_profile_dialog_fragment, null);
        User newUser = newManager.getUser();

        editName = view.findViewById(R.id.editUserName);
        editAbout = view.findViewById(R.id.editAbout);

        editName.setText(newManager.getUser().getUserName());
        editAbout.setText(newManager.getUser().getDescription());

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setMessage("Edit Profile")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editNameText = editName.getText().toString();
                        String editAboutText = editAbout.getText().toString();

                        newManager.getUser().setUserName(editNameText);
                        newManager.getUser().setDescription(editAboutText);

                        newManager.updateUser(new User(newUser.getUserName(),newUser.getUserId(),newUser.getContactInfo(),newUser.getDescription()));

                    }
                })
                .setNegativeButton("cancel",null)
                .create();
    }
    public static String TAG = "edit Profile";
}