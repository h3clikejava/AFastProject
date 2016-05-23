package com.h3c.afastproject.utils;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by H3c on 16/5/23.
 */

public class TaskUtils {
    public static void timer(long time, TimeUnit timeUnit, Action1 action) {
        PublishSubject.create().timer(time, timeUnit).subscribe(action);
    }

    public static void runOnUI(Action0 action) {
        AndroidSchedulers.mainThread().createWorker().schedule(action);
    }
}
