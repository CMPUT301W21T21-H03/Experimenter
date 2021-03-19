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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager implements IdGen.IDCallBackable {

    /*
    Name:Obaro Ogbo
    Link:https://www.androidauthority.com/how-to-store-data-locally-in-android-app-717190/
    Date:September 21, 2016
    License: unknown
    Usage: android local storage
     */
    private SharedPreferences pref;
    private static UserManager local= null;
    private LocalUserCallback callbackHolder; // janky solution 101
    private User user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    /**
     * Required extends
     */
    public class ContextNotSetException extends RuntimeException {}

    // required interfaces
    public interface LocalUserCallback {
        void onLocalUserReady(User user);
    }

    public interface QuerySingleUserCallback{
        void onQueryUserReady(User user);

    }

    public interface QueryExpSubCallback {
        void onQueryUserSubsReady(ArrayList<User> users);
    }

//    private UserManager()  {
//    }

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

    /**
     * Initializes a local user
     * @param callback
     * callback
     */
    public void initializeLocalUser(LocalUserCallback callback)  {
        if(pref == null){
            throw new ContextNotSetException();
        }
        if(pref.contains("User")){

            Gson gson = new Gson();
            user = gson.fromJson(pref.getString("User", ""), User.class);
            Log.d("stuff", user.toString());
            if(callback != null){
                callback.onLocalUserReady(user);
            }
        }
        else{
            // no id currently exist, needs to create a new one
            callbackHolder = callback;
            IdGen.genUserId(this);
        }
    }

    /**
     * Gets user from firebase
     * @param id
     * ID of the user
     * @param callback
     * callback
     */
    @SuppressWarnings("unchecked")
    public void queryUser(String id, QuerySingleUserCallback callback){

        DocumentReference doc = db.collection("Users").document(id);
        doc.get(Source.DEFAULT).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null &&document.exists() && document.get("Contacts") instanceof Map){

                        callback.onQueryUserReady(userFromSnapshot(document));
                    }

                }
                else{
                    callback.onQueryUserReady(null);
                }
            }
        });
    }

    private User userFromSnapshot(DocumentSnapshot document){
        Map<String, Object> contact = (Map<String, Object> )document.get("Contacts");
        String description = document.getString("UserDecription");
        String name = document.getString("UserName");

        assert contact != null;
        User temp = new User(name, document.getId(),
                new UserContactInfo(contact.get("CityName").toString(), contact.get("Email").toString()
                ), description);
        return  temp;
    }

    @SuppressWarnings("unchecked")
    public void queryExperimentSubs(String expId, QueryExpSubCallback callback){
        db.collection("Experiments").document(expId).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                             ArrayList<String> userIds = (ArrayList<String>)task.getResult().get("SubscriberIDs");

                             if(userIds.size() ==0){
                                 callback.onQueryUserSubsReady(new ArrayList<User>());
                             }
                             else{
                                 db.collection("Users").whereIn("__name__", userIds).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                     @Override
                                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                         ArrayList<User> output = new ArrayList<>();
                                         if(task.isSuccessful()){
                                             for(DocumentSnapshot snap: task.getResult()){
                                                 output.add(userFromSnapshot(snap));
                                             }
                                             callback.onQueryUserSubsReady(output);
                                         }
                                     }
                                 });
                             }

                        }else
                        {
                            Log.d("stuff", "oh no!");
                        }
                    }
                }
        );
    }



    /**
     * Updates the local user. This method will update the user stored in memory, locally, and in firebase.
     * If the user's Id already exist, then the exist user document will be updated(replaced).
     * <b>Note:</b> Changing user id or creating new users requires UserReadyCalled to be registered to LocalUserManager
     * <b>Note2:</b> Upon changing user id, user should be given the option to permanently delete the old profile. Then be switched to the new profile
     * @throws ContextNotSetException Throws exception if no context has ever been set for this LocalUserManager
     * @param newUser
     * user to be made or updated
     * @param callback
     * callback
     */
    public void updateUser(User newUser, LocalUserCallback callback){
        if(pref == null){
            throw new ContextNotSetException();
        }
        user = newUser;
        Gson gson = new Gson();
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString("User", gson.toJson(user));
        prefEditor.apply();
        Log.d("inside manager", user.toString());

        Map<String, Object> doc = new HashMap<>();
            doc.put("UserDescription", user.getDescription());
            doc.put("UserName", user.getUserName());
                Map<String, Object> contact = new HashMap<>();

                        contact.put("CityName", user.getContactInfo().getCityName());
                        contact.put("Email", user.getContactInfo().getEmail());

        doc.put("Contacts", contact);
        db.collection("Users").document(user.getUserId()).set(doc).addOnSuccessListener(aVoid -> {
            if(callback != null){
                callback.onLocalUserReady(user);
            }
        });
    }

    /**
     * Please don't call this method although it is necessary so don't delete me
     * @param id
     * ID of the user
     */
    @Override
    public void onIdReady(String id){
        updateUser(new User(id), callbackHolder);
    }
}
