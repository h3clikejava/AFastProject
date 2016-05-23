package com.h3c.afastproject.base;

import java.lang.ref.SoftReference;

/**
 * Created by H3c on 16/5/22.
 */

public abstract class BasePresenter<T> {
    private SoftReference<T> ui;

    public BasePresenter(T t) {
        ui = new SoftReference<T>(t);
    }

    public T getUI() {
        if(ui != null && ui.get() != null) {
            return ui.get();
        }

        return null;
    }

    public SoftReference<T> getSRUI() {
        return ui;
    }

    abstract public void destroy();
}
