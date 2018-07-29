package com.davutozcan.bookmarkreader.weblist;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.davutozcan.bookmarkreader.MainActivity;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.bindings.recyclerview.BaseArrayListAdapter;
import com.davutozcan.bookmarkreader.databinding.RowDataViewBinding;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 18.02.2018.
 */
class WebListViewFragmentAdapter extends BaseArrayListAdapter<RowDataViewBinding , WebListRowModel>
{
    private Disposable disposable;

    public WebListViewFragmentAdapter(List<WebListRowModel> rowElements) {
        super(rowElements);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.row_data_view;
    }


    @Override
    public void setItems(List<WebListRowModel> items) {
        super.setItems(items);
        settleBackgroundJob();
    }

    private void settleBackgroundJob() {
        disposeBackgroundJob();
        scheduleBackgroundJob();
    }

    private void scheduleBackgroundJob() {
        this.disposable =  Observable.fromIterable(getItems())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .doOnNext(o->loadData(o))
                .map(o->getItems().indexOf(o))
                .subscribe();
    }

    private void disposeBackgroundJob() {
        if(this.disposable != null)
            this.disposable.dispose();
    }
    private void loadData(WebListRowModel o){
        Log.i("Observable", "loadData " + Thread.currentThread().getName());
        WebSiteInfo wsi =  myApp().getWebListService().load(o.title.get());
        o.description.set(wsi.getSummary());
        o.setChangeDate(wsi.getChangeDate());
    }


    public void onPause() {
        disposeBackgroundJob();
    }
}
