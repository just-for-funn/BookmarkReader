package com.davutozcan.bookmarkreader.views.download;

import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.application.OkHttpClientImp;
import com.davutozcan.bookmarkreader.domainmodel.IUpdateListener;
import com.davutozcan.bookmarkreader.domainmodel.SingleItemUpdater;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.summary.HtmlComparerImp;
import com.davutozcan.bookmarkreader.sync.BackgroundUpdaterTask;
import com.davutozcan.bookmarkreader.sync.FaviconExtractor;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 1/20/2018.
 */

public class DownloadViewHandler
{
    private static final String TAG = DownloadViewHandler.class.getSimpleName();
    DownloadViewModel downloadViewModel;
    private Disposable activeDisposable;

    public DownloadViewHandler(DownloadViewModel downloadViewModel) {
        this.downloadViewModel = downloadViewModel;
    }


    public void startDownload(View w)
    {
        android.util.Log.i(TAG, "startDownload: ");
        clearFields();
        List<WebUnit> webUnitList = myApp().getWebunitService().getWebSitesInfos();
        downloadViewModel.isInprogress.set(true);
        this.activeDisposable =  Flowable.fromIterable(webUnitList)
                .parallel()
                .runOn(Schedulers.computation())
                .doOnNext(this::updateItem)
                .sequential()
                .subscribeOn(AndroidSchedulers.mainThread())
                .reduce(new Object(), (o, webUnit) -> o)
                .subscribe(o->{
                    downloadViewModel.isInprogress.set(false);
                });


    }

    private void updateItem(WebUnit wu)
    {
        SingleItemUpdater sig = new SingleItemUpdater(myApp().getRealmFacade() ,()->new OkHttpClientImp() ,new HtmlComparerImp() , new FaviconExtractor() , myApp().getLogRepository() );
        sig.update(wu,
                o->downloadViewModel.downloading.set(o.getUrl()),
                this::onComplete,
                this::onFail,
                o->{}
        );
    }

    private void onFail(WebUnit webUnit) {
        inc( downloadViewModel.failed);
        inc(downloadViewModel.total);
    }

    private void onComplete(WebUnit wu) {
        if(wu.getStatus() == WebUnit.Status.HAS_NEW_CONTENT)
            inc( downloadViewModel.neww);
        else
            inc( downloadViewModel.successed);
        inc( downloadViewModel.total);
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
        if(this.activeDisposable != null)
        {
            if(downloadViewModel.isInprogress.get())
            {
                this.activeDisposable.dispose();
                downloadViewModel.isInprogress.set(false);
                downloadViewModel.downloading.set("Stopped");
                Snackbar.make(w , R.string.download_stopped , Snackbar.LENGTH_SHORT ).show();
            }
        }
    }

}
