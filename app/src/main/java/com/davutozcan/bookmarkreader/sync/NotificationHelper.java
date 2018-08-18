package com.davutozcan.bookmarkreader.sync;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.annimon.stream.function.Consumer;
import com.davutozcan.bookmarkreader.R;

import java.util.UUID;

public class NotificationHelper
{
    private static final String CHANNEL_ID = "com.davutozcan.bookmarkreader.DOWNLOAD";

    public static void crateNotification(Context context , Consumer<NotificationCompat.Builder> consumeNotification){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Bookmarkreader background work notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context ,CHANNEL_ID ).
                setSmallIcon(R.drawable.icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true).setChannelId(CHANNEL_ID);
        consumeNotification.accept(mBuilder);

        UUID myuuid = UUID.randomUUID();
        long highbits = myuuid.getMostSignificantBits();
        notificationManager.notify((int)highbits, mBuilder.build());
    }

}
