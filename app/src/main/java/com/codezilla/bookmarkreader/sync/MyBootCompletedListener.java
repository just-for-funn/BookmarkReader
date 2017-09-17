package com.codezilla.bookmarkreader.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by davut on 9/4/2017.
 */

public class MyBootCompletedListener extends BroadcastReceiver {
    final static String TAG = "SAR.BootListener";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "BootListener.onReceive");
        new MyJopScheduler(context).schedule();
    }


}
