package com.davutozcan.bookmarkreader.sync;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


/**
 * Created by davut on 9/4/2017.
 */

public class MyJopScheduler {

    public static final String JOB_TAG = "BookMarkReaderTag";
    private final Context context;

    public MyJopScheduler(Context context) {
        this.context = context;
    }

    public void schedule() {
        cancel();
        Log.i("MyJopScheduler" , "No previous scheduled job found recheduling");
        WorkManager mWorkManager = WorkManager.getInstance();
        PeriodicWorkRequest.Builder notificationWorkBuilder =
                new PeriodicWorkRequest.Builder(BackgroundWorker.class,4, TimeUnit.HOURS)
                        .addTag(JOB_TAG);
        PeriodicWorkRequest request = notificationWorkBuilder.build();
        mWorkManager.enqueueUniquePeriodicWork(JOB_TAG , ExistingPeriodicWorkPolicy.KEEP, request);
    }


    public void cancel() {
        WorkManager.getInstance().cancelAllWorkByTag(JOB_TAG);
//        WorkManager mWorkManager = WorkManager.getInstance();
//        mWorkManager.cancelAllWork();
    }
}
