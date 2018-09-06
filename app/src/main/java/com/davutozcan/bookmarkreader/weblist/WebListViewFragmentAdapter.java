package com.davutozcan.bookmarkreader.weblist;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.bindings.recyclerview.BaseArrayListAdapter;
import com.davutozcan.bookmarkreader.bindings.recyclerview.DataBindedViewHolder;
import com.davutozcan.bookmarkreader.databinding.RowDataViewBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.rambler.libs.swipe_layout.SwipeLayout;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 18.02.2018.
 */
class WebListViewFragmentAdapter extends BaseArrayListAdapter<RowDataViewBinding , WebListRowModel>
{
    private Disposable disposable;
    Map<String , String> cachedSummaries  = new HashMap<>();
    boolean swipeEnabled = false;

    public boolean isSwipeEnabled() {
        return swipeEnabled;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }

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

    @Override
    public DataBindedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowDataViewBinding binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), parent, false);
        SwipeLayout swipeLayout = binding.getRoot().findViewById(R.id.row_view_swipe_layout);
        //swipeLayout.reset();
        swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
                if(isSwipeEnabled()) {
                    Toast.makeText(swipeLayout.getContext(), binding.getModel().title.get() + " marked read!!",
                            Toast.LENGTH_SHORT)
                            .show();
                    removeByTitle(binding.getModel().title.get());
                }
                swipeLayout.reset();
            }

            @Override
            public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
            }

            @Override
            public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
            }
        });
        //swipeLayout.setSwipeEnabled(isSwipeEnabled());
        return new DataBindedViewHolder<>(binding);
    }

    private void removeByTitle(String title) {
        Observable.just(title)
                .doOnNext(o->myApp().getWebunitService().markRead(title))
                .map(t ->
                        Stream.of(getItems()).filter(item->item.title.get().equals(t))
                                .findFirst()
                                .orElse(null))
                .subscribe(o->{
                    if(o!=null)
                        getItems().remove(o);
                        notifyDataSetChanged();
                });
    }
}
