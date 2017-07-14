package com.example.gobelievedemo;

import android.app.Application;

import com.IMKIT;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        IMKIT.init(this);
    }
}
