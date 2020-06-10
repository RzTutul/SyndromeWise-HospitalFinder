package com.example.hospitalfinder;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class HospitalFounder extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
