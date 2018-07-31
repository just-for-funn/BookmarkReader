package com.davutozcan.bookmarkreader.weblist;

import android.util.Log;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.bindings.recyclerview.BaseArrayListAdapter;
import com.davutozcan.bookmarkreader.databinding.RowDataViewBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Map<String , String> cachedSummaries  = new HashMap<>();

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
                .subscribe();
    }

    private void disposeBackgroundJob() {
        if(this.disposable != null)
            this.disposable.dispose();
    }
    private void loadData(WebListRowModel o){
        Log.i("Observable", "loadData " + Thread.currentThread().getName());
        if(!cachedSummaries.containsKey(o.title.get())){
            String summary = myApp().getWebunitService().getSummaryFor(o.title.get());
            cachedSummaries.put(o.title.get() , summary );
        }
        o.description.set(cachedSummaries.get(o.title.get()));
    }


    public void onPause() {
        disposeBackgroundJob();
    }
}
