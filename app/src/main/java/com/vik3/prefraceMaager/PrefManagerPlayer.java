package com.vik3.prefraceMaager;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;

public class PrefManagerPlayer {

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
    private static final String IS_PLAY = "modelLogIn";
    private static final String MODEL_LOG_IN = "modelLogIn";
    private static final String USER_ID = "id";
    private static final String AVATAR = "avatar";


    /**
     * Constructor
     * @param context
     * current Activity
     */
    public PrefManagerPlayer(Context context) {
        this._context = context;
    }


    public void setPlay(Boolean isPlay){
        _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).edit()
                .putBoolean(IS_PLAY, isPlay)
                .apply();
    }
    /*public void setPrefData(ModelDataLogIn modelLogIn){
        String json = new Gson().toJson(modelLogIn);
        _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).edit()
                .putBoolean(IS_LOGIN, true)
                .putString(MODEL_LOG_IN, json)
                .apply();
    }
*/
    /**
     * Check Log In
     * @return
     * weather the user login or not
     */
    public boolean isPlay(){
        return _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).getBoolean(IS_PLAY, false);
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
    public Boolean getPrefData(){
        return _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).getBoolean(IS_PLAY, false);
    }

    /**
     * Log Out
     */
    /*public void logOut() {
        _context.getSharedPreferences(PREF_LOG_IN, MODE_PRIVATE).edit().clear().apply();
        lastActivity(_context, LogInActivity.class);
    }*/
}
