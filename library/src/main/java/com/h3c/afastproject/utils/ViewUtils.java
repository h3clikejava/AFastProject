package com.h3c.afastproject.utils;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

/**
 * Created by H3c on 16/5/23.
 */

public class ViewUtils {
    public static TextView fTV(View view, @IdRes int resId) {
        if(view == null) return null;
        return (TextView) view.findViewById(resId);
    }
}
