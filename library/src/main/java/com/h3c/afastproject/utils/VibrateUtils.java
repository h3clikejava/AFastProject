package com.h3c.afastproject.utils;

import android.content.Context;
import android.os.Vibrator;

import com.h3c.afastproject.AFastProject;

/**
 * Created by H3c on 2/17/15.
 */
public class VibrateUtils {
    public static final void shortVibrate() {
        Vibrator vibrator = (Vibrator) AFastProject.getApplicationContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100, 50};   // 停止 开启
        vibrator.vibrate(pattern, -1);
    }
}
