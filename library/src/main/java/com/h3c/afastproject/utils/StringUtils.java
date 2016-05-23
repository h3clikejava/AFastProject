package com.h3c.afastproject.utils;

import android.support.annotation.StringRes;

/**
 * Created by H3c on 16/5/23.
 */

public class StringUtils {
    public static String getString(@StringRes int resId) {
        return ResourceUtils.getResources().getString(resId);
    }
}
