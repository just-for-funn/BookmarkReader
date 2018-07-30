package com.davutozcan.bookmarkreader.history;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.async.CustomAsyncTaskExecutor;
import com.davutozcan.bookmarkreader.domainmodel.Log;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.exception.DomainException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

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
                .onSuccess(this::onLogsLoaded)
                .onError(this::onError)
                .execute();
    }

    private void onLogsLoaded(List<Log> logs) {
        isBusy.set(false);
        rows.clear();
        rows.addAll(Stream.of(logs).map(log -> {
            HistoryViewRowModel model = new HistoryViewRowModel();
            model.text.set(log.getMsg());
            model.color.set(R.color.matte_green);
            model.date.set(format.format(log.getDate()));
            return model;
        }).collect(Collectors.toList()));
    }

    private void onFinish(List<WebUnit> logs) {
        isBusy.set(false);
        rows.clear();
        rows.addAll(map(logs));
    }

    private java.util.Collection<? extends HistoryViewRowModel> map(List<WebUnit> logs) {
        List<HistoryViewRowModel> rows = new ArrayList<>();
        for (WebUnit log :logs) {

            rows.add(transform(log));
        }
        return rows;
    }

    private HistoryViewRowModel transform(WebUnit log) {
        HistoryViewRowModel rowModel = new HistoryViewRowModel();
        rowModel.text.set(log.getUrl());
        rowModel.color.set(getLogColor(log));
        if(log.getLastDownloadCheckDate()!= null)
            rowModel.date.set(format.format(log.getLastDownloadCheckDate()));
        return rowModel;
    }

    private int getLogColor(WebUnit log) {
        if(log.isDownloadFailed())
            return R.color.matte_red;
        return R.color.matte_green;
    }



    private void onError(DomainException domainException)
    {
        isBusy.set(false);
    }
}
