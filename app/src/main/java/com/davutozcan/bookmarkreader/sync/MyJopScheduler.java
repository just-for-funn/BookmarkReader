package com.davutozcan.bookmarkreader.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;


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
        FirebaseJobDispatcher dispatcher = getFirebaseJobDispatcher();
        Job job = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(ScheduledSynchronizer.class)
                .setTag(JOB_TAG)
                .setReplaceCurrent(false)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0, 10))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_CHARGING)
                .build();

        dispatcher.mustSchedule(job);
    }

    @NonNull
    private FirebaseJobDispatcher getFirebaseJobDispatcher() {
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    public void cancel() {
        getFirebaseJobDispatcher().cancel(JOB_TAG);
    }
}
