package com.h3c.afastproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

/**
 * Created by H3c on 16/6/6.
 */

public class BitmapUtils {

    public static Bitmap getBmpFromRes(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(ResourceUtils.getResources(), resId);
    }

    public static Bitmap changeBitmapRealColor(@DrawableRes int redId, @ColorInt int toColor) {
        return changeBitmapColor(getBmpFromRes(redId), toColor);
    }

    /**
     * 改变图得颜色
     */
    public static Bitmap changeBitmapColor(Bitmap bmp, @ColorInt int toColor) {
        if(bmp == null || bmp.isRecycled()) return bmp;
        Bitmap resultBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap newBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(0x0, toColor);
        p.setAntiAlias(true);
        p.setColorFilter(filter);

        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        resultBitmap.recycle();
        return newBitmap;
    }
}
