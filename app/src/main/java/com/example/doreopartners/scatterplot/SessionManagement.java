package com.example.doreopartners.scatterplot;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";


    // Email address (make variable public to access from outside)
    public static final String KEY_STAFF_NAME  = "staffname";


    // User name (make variable public to access from outside)
    public static final String KEY_STAFF_ID = "staffid";
    public static final String KEY_ROLE = "role";


    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SessionManagement() {

    }


    public void CreateLoginSession(String staffname, String staffid, String staff_role){
        // Storing username in pref
        editor.putString(KEY_STAFF_NAME, staffname);
        // Storing role in pref
        editor.putString(KEY_STAFF_ID, staffid);
        editor.putString(KEY_ROLE, staff_role);

        // commit changes
        editor.commit();
    }
    public void staffid(String staffid){
        // Storing username in pref

        // Storing role in pref
        editor.putString(KEY_STAFF_ID, staffid);


        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_STAFF_NAME, pref.getString(KEY_STAFF_NAME, null));

        // user email id
        user.put(KEY_STAFF_ID, pref.getString(KEY_STAFF_ID, null));

        user.put(KEY_ROLE,pref.getString(KEY_ROLE,null));


        // return user
        return user;
    }


}