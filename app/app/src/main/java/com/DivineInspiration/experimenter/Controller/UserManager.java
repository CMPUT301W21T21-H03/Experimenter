package com.DivineInspiration.experimenter.Controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import androidx.annotation.NonNull;

import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.Model.User;

import com.DivineInspiration.experimenter.Model.UserContactInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UserManager implements IdGen.IDCallBackable {

    public class ContextNotSetException extends RuntimeException{}

    public interface UserReadyCallback{
         void onUserReady(User user);
    }

/*
Name:Obaro Ogbo
Link:https://www.androidauthority.com/how-to-store-data-locally-in-android-app-717190/
Date:September 21, 2016
License: unknown
Usage: android local storage
 */
    private SharedPreferences pref;
    private static UserManager local= null;
    private UserReadyCallback callbackHolder; //janky solution 101
    private User user;


    private UserManager()  {
    }

    /**
     * Gets the current user
     * @return the current user
     */
    public User getLocalUser(){

        return user;
    }




    /**
     * Provide context so that LocalUserManager can fetch the sharedPreference
     * @param context context to be used.
     */
    public void setContext(Context context)  {
        pref = context.getSharedPreferences("USER_CONFIG", Context.MODE_PRIVATE);
    }

    /**
     * Makes an instances of LocalUserManager
     * <b>Note:</b> if making an instance of this class for the first time, initialize it via {@link #setContext(Context context)}, and if needed register callback via
     * @return an instance of LocalUserManager
     */
    public static UserManager getInstance(){
        if (local == null){
            local = new UserManager();
        }
        return local;
    }

    public void initializeLocalUser(UserReadyCallback callback)  {
        if(pref == null){
            throw new ContextNotSetException();
        }
        if(pref.contains("User")){

            Gson gson = new Gson();
            user = gson.fromJson(pref.getString("User", ""), User.class);
            updateUser(user, null);
            Log.d("stuff", user.toString());
            if(callback != null){
                callback.onUserReady(user);
            }
        }
        else{
            //no id currently exist, needs to create a new one
            callbackHolder = callback;
            IdGen.genUserId(this);
        }
    }


    /**
     * Please dont call this method owo
     * @param id
     */
    @Override
    public void onIdReady(String id){
        updateUser(new User(id), callbackHolder);
    }


    /**
     * Updates the local user. This method will update the user stored in memory, locally, and in firebase.
     * If the user's Id already exist, then the exist user document will be updated(replaced).
     * <b>Note:</b> Changing user id or creating new users requires UserReadyCalled to be registered to LocalUserManager
     * <b>Note2:</b> Upon changing user id, user should be given the option to permanently delete the old profile. Then be switched to the new profile
     * @throws ContextNotSetException Throws exception if no context has ever been set for this LocalUserManager
     * @param newUser user to be made or updated.
     */
    public void updateUser(User newUser , UserReadyCallback callback){
        if(pref == null){
            throw new ContextNotSetException();
        }
        user = newUser;
        Gson gson = new Gson();
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString("User", gson.toJson(user));
        prefEditor.apply();
        Log.d("inside manager", user.toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> doc = new HashMap<>();
            doc.put("UserDescription", user.getDescription());
            doc.put("UserName", user.getUserName());
                Map<String, Object> contact = new HashMap<>();

                        contact.put("CityName", user.getContactInfo().getCityName());
                        contact.put("Email", user.getContactInfo().getEmail());

        doc.put("Contacts", contact);
        db.collection("Users").document(user.getUserId()).set(doc).addOnSuccessListener(aVoid -> {
            if(callback != null){
                callback.onUserReady(user);

            }
        });
    }


    public void queryUser(String id, UserReadyCallback callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference doc = db.collection("Users").document(id);
        doc.get(Source.DEFAULT).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Map<String, Object> contact = (Map<String, Object> )document.get("Contacts");
                        String description = document.getString("UserDecription");
                        String name = document.getString("UserName");
                        callback.onUserReady(new User(name, id,
                                new UserContactInfo(contact.get("CityName").toString(), contact.get("Email").toString()
                        ), description));

                    }

                }
                else{
                    callback.onUserReady(null);
                }
            }
        });
    }


}
