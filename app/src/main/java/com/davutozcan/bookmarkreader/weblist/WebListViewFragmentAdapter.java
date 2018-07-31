package com.davutozcan.bookmarkreader.weblist;

import android.util.Log;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.bindings.recyclerview.BaseArrayListAdapter;
import com.davutozcan.bookmarkreader.databinding.RowDataViewBinding;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        o.description.set(myApp().getWebunitService().getSummaryFor(o.title.get()));
    }


    public void onPause() {
        disposeBackgroundJob();
    }
}
