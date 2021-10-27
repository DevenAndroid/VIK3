package com.vik3.prefraceMaager;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.vik3.ui.activities.LogInActivity;
import com.vik3.ui.models.ModelLogInData;

public class PreferenceManager {

    // Context
    Context _context;

    /**
     * Preference
     */
    public static final String PREF_LOG_IN = "PREF_LOG_IN";

    /**
     * User Personal Detail
     */
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String MODEL_LOG_IN = "modelLogIn";
    private static final String USER_ID = "id";
    private static final String AVATAR = "avatar";


    /**
     * Constructor
     * @param context
     * current Activity
     */
    public PreferenceManager(Context context) {
        this._context = context;
    }

    /**
     * Set Personal Detail of User
     * @param modelLogIn contains user detail
     */
    public void setPrefData(ModelLogInData modelLogIn){
        String json = new Gson().toJson(modelLogIn);
        _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).edit()
                .putBoolean(IS_LOGIN, true)
                .putString(MODEL_LOG_IN, json)
                .apply();
    }

    /**
     * Check Log In
     * @return
     * weather the user login or not
     */
    public boolean isLogIn(){
        return _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).getBoolean(IS_LOGIN, false);
    }

    /**
     * Define User have credentials or not
     */
    public void getActivity(){
//        ActivityTransaction.nextActivity(_context, isLogIn() ? MainActivity.class : LogInActivity.class);
    }

    /**
     * Provides User Data
     * @return User personal detail
     */
    public ModelLogInData getPrefData(){
        String json = _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).getString(MODEL_LOG_IN, null);
        return new Gson().fromJson(json, ModelLogInData.class);
    }

    /**
     * Log Out
     */
    public void logOut() {
        _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).edit().clear().apply();
        _context.startActivity(new Intent(_context, LogInActivity.class));
//        lastActivity(_context, LogInActivity.class);
    }
}
