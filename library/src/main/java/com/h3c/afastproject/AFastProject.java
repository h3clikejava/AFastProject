package com.h3c.afastproject;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.h3c.afastproject.utils.DeviceUtils;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

/**
 * Created by H3c on 16/5/23.
 */

public class AFastProject {
    private static Application gContext;
    public static String APPTAG = "H3c";
    public static boolean DEBUG = false;

    // 初始化全局Context
    // debug 会控制是否输出日志
    public static void init(Application context, boolean isDebug) {
        gContext = context;
        DEBUG = isDebug;

        // 初始化Java8时间类
        AndroidThreeTen.init(context);
    }

    public static void setAppTag(String tag) {
        APPTAG = tag;
    }

    public static Context getApplicationContext() {
        return gContext;
    }

    private static Integer gPrimaryDarkColor;
    public static void setPrimaryDarkColor(@ColorInt int primaryColor) {
        gPrimaryDarkColor = primaryColor;
    }
    public static Integer getPrimaryDarkColor() {
        return gPrimaryDarkColor;
    }
    private static int gAppMainColor = Color.BLACK;
    public static void setAppMainColor(@ColorInt int appMainColor) {
        gAppMainColor = appMainColor;
    }
    public static int getAppMainColor() {
        return gAppMainColor;
    }


    /**
     * 检查当前进程是否为主进程
     * @return
     */
    protected static boolean shouldInit() {
        if(gContext == null) return false;
        ActivityManager am = ((ActivityManager) gContext.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = DeviceUtils.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
