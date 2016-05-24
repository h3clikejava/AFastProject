package com.h3c.afastproject.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.h3c.afastproject.AFastProject;

import rx.functions.Action0;

/**
 * Created by H3c on 16/5/24.
 */

public class ToastUtils {
    public static void show(@StringRes int stringRes) {
        show(StringUtils.getString(stringRes));
    }
    public static void show(final String info) {
        TaskUtils.runOnUI(new Action0() {
            @Override
            public void call() {
                Toast.makeText(AFastProject.getApplicationContext(), info, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
