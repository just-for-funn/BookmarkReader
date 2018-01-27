package com.codezilla.bookmarkreader.weblist;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import org.junit.Test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codezilla.bookmarkreader.weblist.CustomizedDateFormatter.customizedDateFormatter;
import static com.codezilla.bookmarkreader.weblist.CustomizedDateFormatter.formatted;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by davut on 28.01.2018.
 */
public class CustomizedDateFormatterTest {

    @Test
    public void shouldReturnMinuteWhenDateIsLowerThan_1HoutAgo()
    {
        assertThat(formatted(before(DateUtils.MINUTE_IN_MILLIS * 2)) , equalTo("2 Mins"));
        assertThat(formatted(before(DateUtils.MINUTE_IN_MILLIS * 11)) , equalTo("11 Mins"));
        assertThat(formatted(before(DateUtils.MINUTE_IN_MILLIS * 43)) , equalTo("43 Mins"));
        assertThat(formatted(before(DateUtils.MINUTE_IN_MILLIS * 59)) , equalTo("59 Mins"));
    }

    @Test
    public void shouldReturnMinutesWhenDateIsLoverThan_24Hours()
    {
        assertThat(formatted(before(DateUtils.HOUR_IN_MILLIS*1)) , equalTo("1 Hour"));
        assertThat(formatted(before(DateUtils.HOUR_IN_MILLIS*5)) , equalTo("5 Hours"));
        assertThat(formatted(before(DateUtils.HOUR_IN_MILLIS*23)) , equalTo("23 Hours"));
    }

    @Test
    public void shouldReturnDaysWhenDateIsLoverThan_7Days()
    {
        assertThat(formatted(before(DateUtils.DAY_IN_MILLIS)) , equalTo("1 Day"));
        assertThat(formatted(before(DateUtils.DAY_IN_MILLIS*3)) , equalTo("3 Days"));
        assertThat(formatted(before(DateUtils.DAY_IN_MILLIS*6)) , equalTo("6 Days"));
        assertThat(formatted(before(DateUtils.DAY_IN_MILLIS*7)) , equalTo("7 Days"));
    }

    @Test
    public void shouldReturnDateFormatIftimeDiffIsHigherThanAWeek() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        format.parse(formatted(before(DateUtils.DAY_IN_MILLIS*8)));
    }

    @NonNull
    private Date before(long diff) {
        return new Date(System.currentTimeMillis() - diff);
    }


}