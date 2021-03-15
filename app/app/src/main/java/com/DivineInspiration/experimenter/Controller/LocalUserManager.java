package com.DivineInspiration.experimenter.Controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.Model.User;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LocalUserManager implements IdGen.IDCallBackable {

    public class ContextNotSetException extends RuntimeException{}

    public interface UserReadyCallback{
         void onUserReady();
    }

/*
Name:Obaro Ogbo
Link:https://www.androidauthority.com/how-to-store-data-locally-in-android-app-717190/
Date:September 21, 2016
License: unknown
Usage: android local storage
 */
    private SharedPreferences pref;
    private static LocalUserManager local= null;
    private User user;
    private UserReadyCallback callback;

    private LocalUserManager()  {
    }

    /**
     * Gets the current user
     * @return the current user
     */
    public User getUser(){
        if(user == null){
            initialize();
        }
        return user;
    }

    /**
     * Registers a class that is ready to be called back when an asynchronous operation is done on a user record(such as making a new user, or changing user id).
     * All other changes to user are safe to do without registering callback.
     * @param callback class to be called when user is ready.
     */
    public void setReadyCallback(UserReadyCallback callback){
        this.callback = callback;
        //if context, callback is set, but user is not loaded yet, then its time to initialize
        if(user == null) {
            initialize();
            }

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
     * <b>Note:</b> if making an instance of this class for the first time, initialize it via {@link #setContext(Context context)}, and if needed register callback via {@link #setReadyCallback(UserReadyCallback callback)}
     * @return an instance of LocalUserManager
     */
    public static LocalUserManager getInstance(){
        if (local == null){
            local = new LocalUserManager();
        }
        return local;
    }

    private void initialize()  {
        if(pref == null){
            throw new ContextNotSetException();
        }
        if(pref.contains("User")){
            Gson gson = new Gson();
            user = gson.fromJson(pref.getString("User", ""), User.class);
            //TODO when to call back here??
            updateUser(user);
            Log.d("stuff", user.toString());
            if(callback != null){
                callback.onUserReady();
                callback = null;
            }
        }
        else{
            //no id currently exist, needs to create a new one
            IdGen.genUserId(this);
        }
    }


    /**
     * Please dont call this method owo
     * @param id
     */
    @Override
    public void onIdReady(String id){
        updateUser(new User(id));
    }


    /**
     * Updates the local user. This method will update the user stored in memory, locally, and in firebase.
     * If the user's Id already exist, then the exist user document will be updated(replaced).
     * <b>Note:</b> Changing user id or creating new users requires UserReadyCalled to be registered to LocalUserManager
     * <b>Note2:</b> Upon changing user id, user should be given the option to permanently delete the old profile. Then be switched to the new profile
     * @throws ContextNotSetException Throws exception if no context has ever been set for this LocalUserManager
     * @param newUser user to be made or updated.
     */
    public void updateUser(User newUser){
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
                        contact.put("Address", user.getContactInfo().getAddress());
                        contact.put("CityName", user.getContactInfo().getCityName());
                        contact.put("Email", user.getContactInfo().getEmail());
                        contact.put("PhoneNumber", user.getContactInfo().getPhoneNumber());
        doc.put("Contacts", contact);
        db.collection("Users").document(user.getUserId()).set(doc).addOnSuccessListener(aVoid -> {
            if(callback != null){
                callback.onUserReady();
                callback = null;
            }
        });
    }
}
