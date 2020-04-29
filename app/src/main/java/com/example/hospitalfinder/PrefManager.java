package com.example.hospitalfinder;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    private static final String PRE_NAME="hostpital_founder";
    private static final String IS_FIRST_TIME_LUNCH="IsFirstTimeLanuch";

    public PrefManager(Context context)
    {
        this.context = context;
        preferences= context.getSharedPreferences(PRE_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void  setIsFirstTimeLunch(boolean isFirstTimeLunch)
    {
        editor.putBoolean(IS_FIRST_TIME_LUNCH,isFirstTimeLunch);
        editor.commit();

    }
    public boolean isFirstTimeLunch()
    {
        return preferences.getBoolean(IS_FIRST_TIME_LUNCH,true);
    }
}
