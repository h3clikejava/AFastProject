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
    public static void timer(long time, TimeUnit timeUnit, final Runnable runnable) {
        if(runnable == null || timeUnit == null) return;
        PublishSubject.create().timer(time, timeUnit).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                runnable.run();
            }
        });
    }

    public static void runOnUI(final Runnable runnable) {
        if(runnable == null) return;
        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                runnable.run();
            }
        });
    }

    public static void runIO(final Runnable runnable) {
        if(runnable == null) return;
        Schedulers.io().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                runnable.run();
            }
        });
    }

    public static void runComputation(final Runnable runnable) {
        if(runnable == null) return;
        Schedulers.computation().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                runnable.run();
            }
        });
    }

    public static void runNewThread(final Runnable runnable) {
        if(runnable == null) return;
        Schedulers.newThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                runnable.run();
            }
        });
    }
}
