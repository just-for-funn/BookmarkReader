package com.codezilla.bookmarkreader.views.download;


import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by davut on 1/18/2018.
 */

public class DownloadViewModel
{
    private static final String TAG = DownloadViewModel.class.getSimpleName();
    public final ObservableField<Integer> neww = new ObservableField<>(0);
    public final ObservableField<Integer> failed = new ObservableField<>(0);
    public final ObservableField<Integer> successed = new ObservableField<>(0);
    public final ObservableField<Integer> total = new ObservableField<>(0);
    public final ObservableField<String> downloading = new ObservableField<>("");
    public final ObservableField<Boolean> isInprogress = new ObservableField<>(false);

}
