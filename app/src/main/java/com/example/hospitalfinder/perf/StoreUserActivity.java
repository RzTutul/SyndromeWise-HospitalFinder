package com.example.hospitalfinder.perf;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreUserActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public StoreUserActivity(Context context) {
        sharedPreferences = context.getSharedPreferences("UserActivity",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setUserName(String name)
    {
        editor.putString("name",name);
        editor.commit();
    }

    public String getUserName()
    {
        return sharedPreferences.getString("name","null");
    }

    public void setGmail(String gmail)
    {
        editor.putString("gmail",gmail);
        editor.commit();
    }

    public String getGmail()
    {
        return sharedPreferences.getString("gmail","null");
    }


}
