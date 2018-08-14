package com.davutozcan.bookmarkreader.sync;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.annimon.stream.function.Consumer;
import com.davutozcan.bookmarkreader.R;

import java.util.UUID;

public class NotificationHelper
{
    private static final String CHANNEL_ID = "com.davutozcan.bookmarkreader.DOWNLOAD";

    public static void crateNotification(Context context , Consumer<NotificationCompat.Builder> consumeNotification){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context ,CHANNEL_ID ).
                setSmallIcon(R.drawable.icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        consumeNotification.accept(mBuilder);

        UUID myuuid = UUID.randomUUID();
        long highbits = myuuid.getMostSignificantBits();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int)highbits, mBuilder.build());
    }

}
