package com.codezilla.bookmarkreader.views.download;

import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.domainmodel.IUpdateListener;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;
import com.codezilla.bookmarkreader.sync.BackgroundUpdaterTask;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 1/20/2018.
 */

public class DownloadViewHandler
{
    private static final String TAG = DownloadViewHandler.class.getSimpleName();
    DownloadViewModel downloadViewModel;
    private BackgroundUpdaterTask updaterTask;

    public DownloadViewHandler(DownloadViewModel downloadViewModel) {
        this.downloadViewModel = downloadViewModel;
    }

    public void startDownload(View w)
    {
        android.util.Log.i(TAG, "startDownload: ");
        clearFields();
        downloadViewModel.isInprogress.set(true);
        this.updaterTask = new BackgroundUpdaterTask(myApp().getApplicationContext(), new IUpdateListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                downloadViewModel.isInprogress.set(false);
            }

            @Override
            public void onStart(WebUnit wu) {
                downloadViewModel.downloading.set(wu.getUrl());
            }

            @Override
            public void onFail(WebUnit wu)
            {
                inc( downloadViewModel.failed);
                inc(downloadViewModel.total);
            }

            @Override
            public void onComplete(WebUnit wu) {
                if(wu.getStatus() == WebUnit.Status.HAS_NEW_CONTENT)
                    inc( downloadViewModel.neww);
                else
                    inc( downloadViewModel.successed);
                inc( downloadViewModel.total);
            }
        });
        updaterTask.execute();
    }

    private void clearFields() {
        downloadViewModel.failed.set(0);
        downloadViewModel.total.set(0);
        downloadViewModel.successed.set(0);
        downloadViewModel.neww.set(0);
    }

    private void inc(ObservableField<Integer> arg) {
       arg.set(arg.get() +1);
    }

    public void stopDownload(View w)
    {
        android.util.Log.i(TAG, "stopDownload: ");
        if(updaterTask != null)
        {
            if(updaterTask.getStatus() == AsyncTask.Status.RUNNING)
            {
                this.updaterTask.stop();
                this.updaterTask.stop();
                this.updaterTask.cancel(true);
                downloadViewModel.isInprogress.set(false);
                downloadViewModel.downloading.set("Stopped");
                Snackbar.make(w , R.string.download_stopped , Snackbar.LENGTH_SHORT ).show();
            }
            updaterTask = null;
        }
    }

}
