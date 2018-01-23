package com.codezilla.bookmarkreader.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.codezilla.bookmarkreader.domainmodel.IUpdateListener;

import java.util.concurrent.Callable;

/**
 * Created by davut on 9/4/2017.
 */

public class ScheduledSynchronizer extends JobService
{
    final static String TAG = ScheduledSynchronizer.class.getSimpleName();
    private BackgroundUpdaterTask bAckgroundUpdaterTask;

    @Override
    public boolean onStartJob(final JobParameters params)
    {
        Log.i(TAG , "onStart");
        this.bAckgroundUpdaterTask = new BackgroundUpdaterTask(getApplicationContext() , IUpdateListener.NULL)
        {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                jobFinished(params , aBoolean);
            }
        };
        bAckgroundUpdaterTask.execute();
        return true;
    }

    private Callable<Void> stopCallBack()
    {
        return new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                ScheduledSynchronizer.this.jobFinished(null , true);
                return null;
            }
        };
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        return true;
    }
}
