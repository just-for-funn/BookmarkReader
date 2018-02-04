package com.codezilla.bookmarkreader.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.codezilla.bookmarkreader.domainmodel.IUpdateListener;
import com.codezilla.bookmarkreader.domainmodel.RealmLogRepositoryImp;

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

        RealmLogRepositoryImp realmLogRepositoryImp = new RealmLogRepositoryImp(getApplicationContext());
        realmLogRepositoryImp.info("Scheduled Sycronizer onStartJob");

        this.bAckgroundUpdaterTask = new BackgroundUpdaterTask(getApplicationContext() , IUpdateListener.NULL)
        {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                jobFinished(params , false);
            }
        };
        bAckgroundUpdaterTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        if(bAckgroundUpdaterTask!= null)
        {
            bAckgroundUpdaterTask.stop();
            bAckgroundUpdaterTask.cancel(true);
        }
        return true;
    }
}
