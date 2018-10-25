package com.davutozcan.bookmarkreader.sync;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.annimon.stream.function.Supplier;
import com.davutozcan.bookmarkreader.MainActivity;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.application.OkHttpClientImp;
import com.davutozcan.bookmarkreader.domainmodel.ILogRepository;
import com.davutozcan.bookmarkreader.domainmodel.IUpdateListener;
import com.davutozcan.bookmarkreader.domainmodel.RealmLogRepositoryImp;
import com.davutozcan.bookmarkreader.domainmodel.RealmRepositoryImp;
import com.davutozcan.bookmarkreader.domainmodel.UpdateContext;
import com.davutozcan.bookmarkreader.domainmodel.WebUnitContentUpdater;
import com.davutozcan.bookmarkreader.summary.HtmlComparerImp;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

public class BackgroundWorker extends Worker {

    private WebUnitContentUpdater contentUpdater;
    static final String TAG = BackgroundWorker.class.getSimpleName();

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

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
            if(isCancelled() || isStopped())
                return Result.RETRY;
            notifyUnreadContent(()->getApplicationContext());
            return Result.SUCCESS;
        }
        catch (Exception e)
        {
            logRepository.error("Syncronization failed:"+e.getMessage());
            createNotification(()->getApplicationContext() , "Failure" , e.getLocalizedMessage());
            return Result.RETRY;
        }
    }

    @Override
    public void onStopped(boolean cancelled) {
        if(this.contentUpdater!= null)
            this.contentUpdater.stop();
    }

    public void notifyUnreadContent(Supplier<Context> contextSupplier) {
        int unreadCount = myApp().getWebunitService().getUnreadWebSitesInfos().size();
        Log.i(TAG, "notifyUnreadContent: new content size " + unreadCount);
        if(unreadCount > 0)
        {
            createNotification(contextSupplier, myApp().getString(R.string.new_content_awailable) , String.format("You have %d unread sites. Click to start reading..", unreadCount));
        }else
        {
            createNotification(contextSupplier , "No new content available" , "No new content available");
        }
    }

    private void createNotification(Supplier<Context> contextSupplier, String title , String content) {
        NotificationHelper.crateNotification(contextSupplier.get() , o->{
            o.setContentTitle(title);
            o.setContentText(content);
            Intent intent = new Intent(contextSupplier.get(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(contextSupplier.get(), 0, intent, 0);
            o.setContentIntent(pendingIntent);
        });
        Log.i(TAG, "notifications setted");
    }

}
