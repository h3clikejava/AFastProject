package com.h3c.afastproject;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by H3c on 16/5/23.
 */

public class AFastProject {
    private static Application gContext;

    // 初始化全局Context
    public static void init(Application context) {
        gContext = context;

        // 初始化Java8时间类
        AndroidThreeTen.init(context);
    }

    public static Context getApplicationContext() {
        return gContext;
    }
}
