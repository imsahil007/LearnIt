package com.example.sahil.ashish;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sahil on 3/7/2018.
 */

public class thisapp extends Application {
    public String email;  public static final String dev = "AIzaSyCoVMIdD_G93hdSJI7xRc0hyazYywp1z9Q";
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
 public String DEVELOPER_KEY(){return dev;}
    //public void setLoginEmail(String email){this.email=email;}
}