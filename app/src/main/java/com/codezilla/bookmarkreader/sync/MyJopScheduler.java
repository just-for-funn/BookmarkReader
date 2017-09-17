package com.codezilla.bookmarkreader.sync;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;




/**
 * Created by davut on 9/4/2017.
 */

public class MyJopScheduler {
    public static final int TWELVE_HOURS = 12 * 60 * 60 * 1000;
    private final static String TAG = "SAR.Scheduler";
    public static final long ONE_HOUR = 1 * 60 * 60 * 1000;
    private static final long ONE_MINUTE = 1 * 60 * 1000;
    private static final long FIVE_MIN = 5* ONE_MINUTE;
    private static final long HALF_HOUR = 30 * ONE_MINUTE;
    private final Context context;
    public MyJopScheduler(Context context) {
        this.context = context;
    }

    public void schedule() {
        Log.i(TAG,"scheduling new jop");
        ComponentName cn = new ComponentName(context , ScheduledSynchronizer.class );
        JobInfo inf =  new JobInfo.Builder(0 , cn )
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(HALF_HOUR)
                .setOverrideDeadline(ONE_HOUR)
                .build();
        JobScheduler jobScheduler  = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(inf);
    }

    public void cancel()
    {
        JobScheduler jobScheduler  = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancelAll();
        Log.i(TAG , "Scheduled jobs canceled");
    }
}
