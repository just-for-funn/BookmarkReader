package com.davutozcan.bookmarkreader.history;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.util.Date;

/**
 * Created by davut on 9/19/2017.
 */

public class HistoryViewRowModel
{
    public final ObservableInt color = new ObservableInt(0);
    public final ObservableField<String> text = new ObservableField<>("");
    public final ObservableField<String>  date = new ObservableField<>("");
}
