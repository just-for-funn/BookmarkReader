package com.codezilla.bookmarkreader.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.codezilla.bookmarkreader.domainmodel.IUpdateListener;
import com.codezilla.bookmarkreader.domainmodel.RealmLogRepositoryImp;
import com.codezilla.bookmarkreader.domainmodel.RealmRepositoryImp;

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
