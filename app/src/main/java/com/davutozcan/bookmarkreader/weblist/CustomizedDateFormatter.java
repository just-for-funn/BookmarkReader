package com.davutozcan.bookmarkreader.weblist;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by davut on 28.01.2018.
 */

class CustomizedDateFormatter {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static  CustomizedDateFormatter instance = new CustomizedDateFormatter();
    public static CustomizedDateFormatter customizedDateFormatter() {
        return instance;
    }

    public static  String formatted(Date date)
    {
        return instance.format(date);
    }

    public String format(Date changeDate) {
        long current = System.currentTimeMillis();
        long diff = current - changeDate.getTime();
        if(diff < DateUtils.HOUR_IN_MILLIS)
        {
            return getString((diff / DateUtils.MINUTE_IN_MILLIS),"Min");
        }
        if(diff < DateUtils.DAY_IN_MILLIS)
        {
            return getString((diff / DateUtils.HOUR_IN_MILLIS), "Hour");
        }

        if(diff < DateUtils.DAY_IN_MILLIS * 7+1)
        {
            return getString((diff / DateUtils.DAY_IN_MILLIS), "Day");
        }

        return DATE_FORMAT.format(changeDate);
    }

    @NonNull
    private String getString(long count , String type) {
        return count +" "+type+(count == 1 ? "":"s");
    }
}