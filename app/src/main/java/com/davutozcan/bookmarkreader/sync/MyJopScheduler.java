package com.davutozcan.bookmarkreader.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

import static androidx.work.State.CANCELLED;


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
        Log.i("MyJopScheduler" , "No previous scheduled job found recheduling");
        WorkManager mWorkManager = WorkManager.getInstance();
        PeriodicWorkRequest.Builder notificationWorkBuilder =
                new PeriodicWorkRequest.Builder(BackgroundWorker.class,3, TimeUnit.HOURS)
                        .addTag(JOB_TAG);
        PeriodicWorkRequest request = notificationWorkBuilder.build();
        mWorkManager.enqueueUniquePeriodicWork(JOB_TAG , ExistingPeriodicWorkPolicy.KEEP, request);
    }


    public void cancel() {
//        WorkManager mWorkManager = WorkManager.getInstance();
//        mWorkManager.cancelAllWork();
    }
}
