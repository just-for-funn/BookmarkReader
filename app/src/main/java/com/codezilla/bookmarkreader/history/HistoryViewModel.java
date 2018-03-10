package com.codezilla.bookmarkreader.history;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.design.widget.Snackbar;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.async.CustomAsyncTaskExecutor;
import com.codezilla.bookmarkreader.domainmodel.Log;
import com.codezilla.bookmarkreader.exception.DomainException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 9/19/2017.
 */

public class HistoryViewModel{
    public final ObservableBoolean isBusy = new ObservableBoolean(false);
    public ObservableArrayList<HistoryViewRowModel> rows = new ObservableArrayList<HistoryViewRowModel>();
    final  DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public void load()
    {
        isBusy.set(true);
        CustomAsyncTaskExecutor.async(()->myApp().getLogRepository().logs())
                .onSuccess(this::onFinish)
                .onError(this::onError)
                .execute();
    }


    private void onFinish(List<Log> logs) {
        isBusy.set(false);
        rows.clear();
        rows.addAll(map(logs));
    }

    private java.util.Collection<? extends HistoryViewRowModel> map(List<Log> logs) {
        List<HistoryViewRowModel> rows = new ArrayList<>();
        for (Log log :logs) {

            rows.add(transform(log));
        }
        return rows;
    }

    private HistoryViewRowModel transform(Log log) {
        HistoryViewRowModel rowModel = new HistoryViewRowModel();
        rowModel.text.set(log.getMsg());
        rowModel.color.set(getLogColor(log));
        rowModel.date.set(format.format(log.getDate()));
        return rowModel;
    }

    private int getLogColor(Log log) {
        switch (log.getType())
        {
            case Log.INFO:
                return R.color.matte_green;
            case Log.WARNING:
                return R.color.matte_yellow;
            default:
                return R.color.matte_red;
        }
    }



    private void onError(DomainException domainException)
    {
        isBusy.set(false);
    }
}
