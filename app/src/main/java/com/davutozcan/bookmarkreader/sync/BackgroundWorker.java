package com.davutozcan.bookmarkreader.sync;

import android.support.annotation.NonNull;
import android.util.Log;

import com.davutozcan.bookmarkreader.application.OkHttpClientImp;
import com.davutozcan.bookmarkreader.domainmodel.ILogRepository;
import com.davutozcan.bookmarkreader.domainmodel.IUpdateListener;
import com.davutozcan.bookmarkreader.domainmodel.RealmLogRepositoryImp;
import com.davutozcan.bookmarkreader.domainmodel.RealmRepositoryImp;
import com.davutozcan.bookmarkreader.domainmodel.UpdateContext;
import com.davutozcan.bookmarkreader.domainmodel.WebUnitContentUpdater;
import com.davutozcan.bookmarkreader.summary.HtmlComparerImp;

import androidx.work.Worker;

public class BackgroundWorker extends Worker {
    private WebUnitContentUpdater contentUpdater;

    @NonNull
    @Override
    public Result doWork() {

        ILogRepository logRepository = new LogCatDecorator(new RealmLogRepositoryImp(getApplicationContext()));
        try
        {
            this.contentUpdater = new WebUnitContentUpdater(
                    new UpdateContext(new OkHttpClientImp(),
                            new RealmRepositoryImp(getApplicationContext()),
                            new HtmlComparerImp(),
                            logRepository, new FaviconExtractor(),
                            IUpdateListener.NULL));
            contentUpdater.updateAll();
            Log.i("BackgroundWorker", "Background updater task finished");
            return Result.SUCCESS;
        }
        catch (Exception e)
        {
            logRepository.error("Syncronization failed:"+e.getMessage());
            return Result.RETRY;
        }
    }

}
