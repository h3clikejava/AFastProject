package com.h3c.afastproject.utils;

import android.util.Log;

import com.h3c.afastproject.AFastProject;
import com.h3c.afastproject.BuildConfig;

/**
 * Created by H3c on 16/5/23.
 */

public class LogUtils {
    public static void e(String info) {
        if(BuildConfig.DEBUG) {
            Log.e(AFastProject.APPTAG, info);
        }
    }
}
