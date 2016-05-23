package com.h3c.afastproject.base.baseInterface;

/**
 * Created by H3c on 2/2/15.
 */
public interface DataCallback<T> {
    /**
     * @param t 返回需要加载的数据。
     * @param info 可以放合适的信息，以便界面显示。
     */
    void dataLoadSuccess(T t, Object... info);

    /**
     * 返回错误信息
     * @param info 可以放合适的信息，以便界面显示。
     */
    void dataLoadFail(Object... info);
}
