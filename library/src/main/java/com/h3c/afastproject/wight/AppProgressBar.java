package com.h3c.afastproject.wight;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.h3c.afastproject.utils.ResourceUtils;

/**
 * 自定义颜色的圆形进度
 * Created by H3c on 16/5/23.
 */

public class AppProgressBar extends ProgressBar {

    public AppProgressBar(Context context) {
        super(context);
        init();
    }

    public AppProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setColor(ResourceUtils.getAppMainColor());
    }

    public void setColor(int color) {
        getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }
}
