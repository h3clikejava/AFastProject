package com.h3c.afastproject.utils;

import android.content.res.Resources;
import android.graphics.Color;

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
}
