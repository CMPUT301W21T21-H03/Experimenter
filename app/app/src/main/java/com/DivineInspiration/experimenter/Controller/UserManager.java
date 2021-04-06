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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager{

    /*
    Name: Obaro Ogbo
    Link: https://www.androidauthority.com/how-to-store-data-locally-in-android-app-717190/
    Date: September 21, 2016
    License: unknown
    Usage: android local storage
     */
    private SharedPreferences pref;
    private static UserManager local= null;

    private String TAG = "USER";

    private User user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    /**
     * Required extends
     */
    public class ContextNotSetException extends RuntimeException {}

    // required interfaces
    public interface OnUserReadyListener {
        void onUserReady(User user);
    }

    // when user is ready
    public interface OnUserListReadyListener {
        void onUserListReady(ArrayList<User> users);
    }

//    private UserManager()  {
//    }

    /**
     * Gets the current user
     * @return the current user
     * @Warning getLocalUser might return null if used during init
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
    public void initializeLocalUser(OnUserReadyListener callback)  {
        if(pref == null){
            throw new ContextNotSetException();
        }
        if(pref.contains("User")){

            Gson gson = new Gson();
            user = gson.fromJson(pref.getString("User", ""), User.class);
            updateUser(user, null);
            if(callback != null){
                callback.onUserReady(user);
            }
        }
        else{
            // no id currently exist, needs to create a new one
            IdGen.genUserId(new IdGen.onIdReadyListener(){
                @Override
                public void onIdReady(String id) {
                    updateUser(new User(id), callback);
                }
            });
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
    public void queryUserById(String id, OnUserReadyListener callback){

        DocumentReference doc = db.collection("Users").document(id);
        doc.get(Source.DEFAULT).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null &&document.exists() && document.get("Contacts") instanceof Map){

                        callback.onUserReady(userFromSnapshot(document));
                    }

                }
                else{
                    callback.onUserReady(null);
                }
            }
        });
    }

    /**
     * returns a single user matching the name
     * @param name
     * @param callback
     */
    public void queryUserByName(String name, OnUserReadyListener callback){

        db.collection("Users").whereEqualTo("UserName", name).limit(1).get().addOnCompleteListener(task -> {

           if(task.isSuccessful() && callback!= null){
               if (task.getResult().size() == 0){
                   callback.onUserReady(null);
               }
               else{
                   for(QueryDocumentSnapshot doc: task.getResult()){
                       callback.onUserReady(userFromSnapshot(doc));
                   }
               }
           } else{
               callback.onUserReady(null);
           }
        });
    }

    /**
     * Get user
     * @param document
     * document
     * @return
     * the user
     */
    private User userFromSnapshot(DocumentSnapshot document){
        Map<String, Object> contact = (Map<String, Object> )document.get("Contacts");
        // typo => update firebase and other stuff (lots of work)
        String description = document.getString("UserDescription");
        String name = document.getString("UserName");

        // if no contacts assert error
        assert contact != null;
        User temp = new User(name, document.getId(),
                    new UserContactInfo(contact.get("CityName").toString(), contact.get("Email").toString()
                ), description);
        return temp;
    }

    /**
     * Query experiments from database
     * @param expId
     * experiment ID
     * @param callback
     * callback function
     */
    @SuppressWarnings("unchecked")
    public void queryExperimentSubs(String expId, OnUserListReadyListener callback){
        db.collection("Experiments").document(expId).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                             ArrayList<String> userIds = (ArrayList<String>)task.getResult().get("SubscriberIDs");

                             if (userIds == null || userIds.size() ==0){
                                 callback.onUserListReady(new ArrayList<User>());
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
                                             callback.onUserListReady(output);
                                         }
                                     }
                                 });
                             }
                        } else {
                            // TODO: error
                            Log.d(TAG, "oh no!");
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
    public void updateUser(User newUser, OnUserReadyListener callback){
        if(pref == null){
            throw new ContextNotSetException();
        }
        user = newUser;
        Gson gson = new Gson();
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString("User", gson.toJson(user));
        prefEditor.apply();


        Map<String, Object> doc = new HashMap<>();
            doc.put("UserDescription", user.getDescription());
            doc.put("UserName", user.getUserName());
                Map<String, Object> contact = new HashMap<>();

                        contact.put("CityName", user.getContactInfo().getCityName());
                        contact.put("Email", user.getContactInfo().getEmail());

        doc.put("Contacts", contact);
        db.collection("Users").document(user.getUserId()).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(callback != null){
                        callback.onUserReady(user);
                    }
                }
            }
        });
    }
}
