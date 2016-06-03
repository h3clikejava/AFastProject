package com.h3c.afastproject.utils;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by H3c on 16/5/23.
 */

public class TaskUtils {
    public static void timer(long time, TimeUnit timeUnit, Action1 action) {
        if(action == null || timeUnit == null) return;
        PublishSubject.create().timer(time, timeUnit).subscribe(action);
    }

    public static void runOnUI(Action0 action) {
        if(action == null) return;
        AndroidSchedulers.mainThread().createWorker().schedule(action);
    }

    public static void runIO(Action0 action) {
        if(action == null) return;
        Schedulers.io().createWorker().schedule(action);
    }

    public static void runComputation(Action0 action) {
        if(action == null) return;
        Schedulers.computation().createWorker().schedule(action);
    }

    public static void runNewThread(Action0 action) {
        if(action == null) return;
        Schedulers.newThread().createWorker().schedule(action);
    }
}
