package com.DivineInspiration.experimenter.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;

import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LocalUserManager implements IdGen.IDCallBackable {
    public static class ContextAlreadyExistException extends RuntimeException{

    }

    public static class ContextNotSetException extends RuntimeException{

    }

    public interface UserReadyCallback{
        public void onUserReady();    }


/*
Name:Obaro Ogbo
Link:https://www.androidauthority.com/how-to-store-data-locally-in-android-app-717190/
Date:September 21, 2016
License: unknown
Usage: android local storage
 */

    private SharedPreferences pref;
    private Context context;
    private static LocalUserManager local= null;
    private User user;
    private AlertDialog dialog;
    private UserReadyCallback callback;

    private LocalUserManager()  {


    }

    public User getUser(){
        return user;
    }

    public void setReadyCallback(UserReadyCallback callback){
        this.callback = callback;

        //if context, callback is set, but user is not loaded yet, then its time to initialize
        if(user == null) {
            initialize();
            }

    }

    public void setContext(Context context)   {
        if(this.context != null){
            throw new ContextAlreadyExistException();
        }
        else{
            this.context = context;
        }
    }
    public static LocalUserManager getInstance(){
        if (local == null){
            local = new LocalUserManager();
        }
        return local;
    }
    private void initialize()   {
        if(context == null){
            throw new ContextNotSetException();
        }
        pref = context.getSharedPreferences("USER_CONFIG",Context.MODE_PRIVATE);

        Log.d("stuff", String.valueOf(pref.contains("User")));
        if(pref.contains("User")){
            Gson gson = new Gson();
            user = gson.fromJson(pref.getString("User", ""), User.class);

            if(callback != null){
                callback.onUserReady();
                callback = null;
            }
        }
        else{
            //initializing loading pop up
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            builder.setView(inflater.inflate(R.layout.loading,null));
            builder.setCancelable(false);
            dialog = builder.create();
            //no id currently exist, needs to create a new one
            IdGen.genUserId(this);
            dialog.show();
        }
    }
    @Override
    public void onIdReady(String id){

        updateUser(new User(id));
    }

    public void updateUser(User newUser){
        user = newUser;
        Gson gson = new Gson();
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString("User", gson.toJson(user));
        prefEditor.commit();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> doc = new HashMap<>();
        doc.put("UserDescription", "default");
        doc.put("UserName", "default");
        Map<String, Object> contact = new HashMap<>();
        contact.put("Address", user.getContactInfo().getAddress());
        contact.put("CityName", user.getContactInfo().getCityName());
        contact.put("Email", user.getContactInfo().getEmail());
        contact.put("PhoneNumber", user.getContactInfo().getPhoneNumber());
        doc.put("Contacts", contact);
        db.collection("Users").document(user.getUniqueID()).set(doc).addOnSuccessListener(aVoid -> {
            if(dialog != null){
                dialog.hide();
            }

            if(callback != null){
                callback.onUserReady();
                callback = null;
            }
        });
    }


}
