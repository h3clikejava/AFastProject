package com.h3c.afastproject.demo;

import android.app.Application;

import com.h3c.afastproject.AFastProject;

/**
 * Created by H3c on 16/5/23.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AFastProject.init(this, BuildConfig.DEBUG);
    }
}
