package com.davutozcan.bookmarkreader.sync;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

import java.util.UUID;

import androidx.work.Worker;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

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
            setNotification(()->getApplicationContext());
            return Result.SUCCESS;
        }
        catch (Exception e)
        {
            logRepository.error("Syncronization failed:"+e.getMessage());
            return Result.RETRY;
        }
    }

    public void setNotification(Supplier<Context> contextSupplier) {
        int unreadCount = myApp().getWebunitService().getUnreadWebSitesInfos().size();
        if(unreadCount > 0)
        {
            NotificationHelper.crateNotification(contextSupplier.get() , o->{
                o.setContentTitle(myApp().getString(R.string.new_content_awailable));
                o.setContentText(String.format("You have %d unread sites. Click to start reading..", unreadCount));
                Intent intent = new Intent(contextSupplier.get(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(contextSupplier.get(), 0, intent, 0);
                o.setContentIntent(pendingIntent);
            });
        }
    }

}
