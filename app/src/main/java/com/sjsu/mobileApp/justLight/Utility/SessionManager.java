package com.sjsu.mobileApp.justLight.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sjsu.mobileApp.justLight.Customer.Activity.ActivityMainPage;
import com.sjsu.mobileApp.justLight.Customer.Activity.HomeActivity;

import java.util.HashMap;

/**
 * Created by divyankithaRaghavaUrs on 12/2/17.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "userId";
    public static final String KEY_TYPE = "type";


    public SessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String Username, String ID, String type)
    {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, Username);
        editor.putString(KEY_ID,ID);
        editor.putString(KEY_TYPE,type);
        editor.commit();
    }


    public void checkLogin()
    {
        System.out.println("Inside checklogin");
        if(!this.isLoggedIn())
        {
            System.out.println("Inside checklogin if statement");
            //Intent i = new Intent(_context, UserSignInActivity.class);
            Intent i = new Intent(_context, ActivityMainPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

    }


    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_ID, pref.getString(KEY_ID, "000"));
        user.put(KEY_TYPE, pref.getString(KEY_TYPE, null));

        return user;
    }


    public HashMap<String, String> getEmergencyContact()
    {
        HashMap<String, String> E = new HashMap<>();


        return E;
    }

    public void logoutUser()
    {
        // Clearing all data from Shared Preferences
        System.out.println("Inside logout session");
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }


    public boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
