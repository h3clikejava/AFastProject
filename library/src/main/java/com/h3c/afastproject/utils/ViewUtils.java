package com.h3c.afastproject.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by H3c on 16/5/23.
 */

public class ViewUtils {
    public static TextView fTV(View view, @IdRes int resId) {
        if(view == null) return null;
        return (TextView) view.findViewById(resId);
    }

    public static EditText fET(View view, @IdRes int resId) {
        if(view == null) return null;
        return (EditText) view.findViewById(resId);
    }

    public static ImageView fIV(View view, @IdRes int resId) {
        if(view == null) return null;
        return (ImageView) view.findViewById(resId);
    }

    public static RecyclerView fRV(View view, @IdRes int resId) {
        if(view == null) return null;
        return (RecyclerView) view.findViewById(resId);
    }

    public static LinearLayout fLL(View view, @IdRes int resId) {
        if(view == null) return null;
        return (LinearLayout) view.findViewById(resId);
    }

    public static RelativeLayout fRL(View view, @IdRes int resId) {
        if(view == null) return null;
        return (RelativeLayout) view.findViewById(resId);
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /** 将一个View转为图片 */
    public static Bitmap createViewBitmap(View v) {
        if(v == null) return null;
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            v.draw(canvas);
        } catch (Exception e) {}
        return bitmap;
    }

    public static void toFullScreen(Activity activity, boolean flag) {
        Window window = activity.getWindow();
        toFullScreen(window, flag);
    }

    public static void toFullScreen(Window window, boolean flag) {
        if(flag) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public static void getViewWH(final View view , final GetViewWHCallback callback) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    if(callback != null) {
                        callback.getViewWH(view.getWidth(), view.getHeight());
                    }
                }
            });
        }
    }

    public interface GetViewWHCallback {
        void getViewWH(int width, int height);
    }
}
