package com.davutozcan.bookmarkreader.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.davutozcan.bookmarkreader.domainmodel.IUpdateListener;
import com.davutozcan.bookmarkreader.domainmodel.RealmLogRepositoryImp;
import com.davutozcan.bookmarkreader.domainmodel.RealmRepositoryImp;

/**
 * Created by davut on 1/30/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private BackgroundUpdaterTask bAckgroundUpdaterTask;

    @Override
    public void onReceive(Context context, Intent intent) {
        RealmLogRepositoryImp realmLogRepositoryImp =  new RealmLogRepositoryImp(context);
        realmLogRepositoryImp.info("Alarm service invoked.");
        this.bAckgroundUpdaterTask = new BackgroundUpdaterTask(context , IUpdateListener.NULL);
        bAckgroundUpdaterTask.execute();
    }
}
