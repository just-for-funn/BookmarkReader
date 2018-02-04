package com.codezilla.bookmarkreader.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.text.format.DateUtils;
import android.util.Log;




/**
 * Created by davut on 9/4/2017.
 */

public class MyJopScheduler {
    private final static String TAG = "SAR.Scheduler";
    private static final int SYNC_JOB_ID = 123456;
    private final Context context;
    public MyJopScheduler(Context context) {
        this.context = context;
    }

    public void schedule() {
        Log.i(TAG,"scheduling new jop");
//        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        alarmMgr.setWindow(AlarmManager.RTC_WAKEUP , DateUtils.HOUR_IN_MILLIS /2 ,DateUtils.HOUR_IN_MILLIS/2, alarmIntent);
//        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP ,SystemClock.elapsedRealtime() +
//               20*  60 * 1000 , DateUtils.HOUR_IN_MILLIS/3 , alarmIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            scheduleJobService();
        }
        else
        {
            Log.i(TAG , "");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void scheduleJobService() {
        ComponentName cn = new ComponentName(context , ScheduledSynchronizer.class );
        JobInfo inf =  new JobInfo.Builder(SYNC_JOB_ID , cn )
                .setPeriodic(DateUtils.HOUR_IN_MILLIS /4 , DateUtils.HOUR_IN_MILLIS/2 )
                .build();
        JobScheduler jobScheduler  = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result =  jobScheduler.schedule(inf);
        Log.i(TAG, "scheduleJobService: schedule result : "+result);
    }

    public void cancel()
    {
        JobScheduler jobScheduler  = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancelAll();
        Log.i(TAG , "Scheduled jobs canceled");
    }
}
