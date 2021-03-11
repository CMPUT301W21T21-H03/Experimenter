package com.DivineInspiration.experimenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.DivineInspiration.experimenter.Model.IdGen;

public class LocalUserManager implements IdGen.IDCallBackable {
    public static class ContextAlreadyExistException extends RuntimeException{

    }

    public static class ContextNotSetException extends RuntimeException{

    }



/*
Name:Obaro Ogbo
Link:https://www.androidauthority.com/how-to-store-data-locally-in-android-app-717190/
Date:September 21, 2016
License: unknown
Usage: android local storage
 */
    private SharedPreferences.Editor prefEditor;
    private SharedPreferences pref;
    private Context context;
    private static LocalUserManager local= null;
    private String userId = "";
    private LocalUserManager()  {
        pref = context.getSharedPreferences("USER_CONFIG",Context.MODE_PRIVATE);
        prefEditor = pref.edit();
        initialize();
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
        if(pref.contains("UserId")){
            //default val is null
            userId = pref.getString("UserId", null);
        }
        else{
            //no id currently exist, needs to create a new one
            IdGen.genUserId(this);

        }
    }
    @Override
    public void onIdReady(long id){

    }
}
