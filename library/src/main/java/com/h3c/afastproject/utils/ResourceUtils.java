package com.h3c.afastproject.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;

import com.h3c.afastproject.AFastProject;

/**
 * Created by H3c on 16/5/23.
 */

public class ResourceUtils {

    public static Resources getResources() {
        if(AFastProject.getApplicationContext() != null) {
            return AFastProject.getApplicationContext().getResources();
        }

        return null;
    }

    public static int getAppMainColor() {
        return Color.BLACK;
    }

    public static int getColor(@ColorRes int resource) {
        return getResources().getColor(resource);
    }

    public static Drawable getDrawable(int resource) {
        return getResources().getDrawable(resource);
    }

    public static String[] getStringArray(int resource) {
        return getResources().getStringArray(resource);
    }

    public static int[] getIntArray(int resource) {
        return getResources().getIntArray(resource);
    }

    public static Integer[] getResourceArray(int resource) {
        TypedArray imgs = getResources().obtainTypedArray(resource);
        Integer[] resArr = new Integer[imgs.length()];
        for (int n = 0; n < imgs.length(); n++) {
            resArr[n] = imgs.getResourceId(n, -1);
        }
        imgs.recycle();
        return resArr;
    }
}
