package com.codezilla.bookmarkreader.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.codezilla.bookmarkreader.application.OkHttpClientImp;
import com.codezilla.bookmarkreader.domainmodel.ILogRepository;
import com.codezilla.bookmarkreader.domainmodel.IUpdateListener;
import com.codezilla.bookmarkreader.domainmodel.RealmLogRepositoryImp;
import com.codezilla.bookmarkreader.domainmodel.RealmRepositoryImp;
import com.codezilla.bookmarkreader.domainmodel.UpdateContext;
import com.codezilla.bookmarkreader.domainmodel.WebUnitContentUpdater;
import com.codezilla.bookmarkreader.summary.HtmlComparerImp;

/**
 * Created by davut on 9/7/2017.
 */

public class BackgroundUpdaterTask extends AsyncTask<Void ,Void , Boolean > {
    final static String TAG = BackgroundUpdaterTask.class.getSimpleName();
    private final IUpdateListener updateListener;
    Context context;
    private WebUnitContentUpdater contentUpdater;

    public BackgroundUpdaterTask(Context context , IUpdateListener updateListener)
    {
        this.context = context;
        this.updateListener = updateListener;
    }

    @Override
    protected void onPreExecute() {
        this.updateListener.onStart();
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        Log.i(TAG, "Background updater tast started");
        ILogRepository logRepository = new LogCatDecorator(new RealmLogRepositoryImp(context));
        try
        {
            this.contentUpdater = new WebUnitContentUpdater(
                    new UpdateContext(new OkHttpClientImp(), new RealmRepositoryImp(context), new HtmlComparerImp(), logRepository, new FaviconExtractor(), updateListener));
            contentUpdater.updateAll();
            Log.i(TAG, "Background updater task finished");
            return new Boolean(true);
        }
        catch (Exception e)
        {
            logRepository.error("Syncronization failed:"+e.getMessage());
            return new Boolean(false);
        }
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        this.updateListener.onFinish();
    }

    public void stop()
    {
        this.contentUpdater.stop();
    }
}
