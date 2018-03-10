package com.codezilla.bookmarkreader.weblist;

import android.databinding.ObservableBoolean;
import android.util.Log;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Supplier;
import com.codezilla.bookmarkreader.async.CustomAsyncTaskExecutor;
import com.codezilla.bookmarkreader.exception.DomainException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.codezilla.bookmarkreader.weblist.WebListViewModel.Filter.UNREAD;

/**
 * Created by davut on 7/5/2017.
 */

public class WebListViewModel
{
    static enum Filter
    {
        READ, UNREAD,ALL
    }
    private Filter filter = UNREAD;
    private ObservableBoolean isCompleted = new ObservableBoolean(false);
    private final ObservableBoolean isFabOpened = new ObservableBoolean(false);
    WeakReference<IWebListView> weblistView;
    Optional<CustomAsyncTaskExecutor> lastLoadJob = Optional.ofNullable(null);
    public final ObservableBoolean isBusy = new ObservableBoolean(false);
    List<WebSiteInfo> webSiteInfos = new ArrayList<>();

    public WebListViewModel(IWebListView weblistView) {
        this.weblistView = new WeakReference<IWebListView>(weblistView);
    }


    private void onFinish(List<WebSiteInfo> webListViews) {
        if(webListViews == null)
            this.webSiteInfos = new ArrayList<>();
        else
            this.webSiteInfos = webListViews;
        if(this.weblistView.get()!= null)
            this.weblistView.get().onListChanged(this.webSiteInfos);
        isCompleted.set(webSiteInfos.size()  == 0 );
        isBusy.set(false);
    }

    private void onError(DomainException domainException)
    {
        isBusy.set(false);
        this.weblistView.get().showError(domainException.getMsg() );
    }

    public boolean isLoaded() {
        return webSiteInfos.size() > 0;
    }



    public ObservableBoolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(ObservableBoolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public ObservableBoolean getIsFabOpened() {
        return isFabOpened;
    }

    public void toggleFab()
    {
        this.isFabOpened.set(!this.isFabOpened.get());
    }

    public void loadUnreadElements()
    {
        Log.i(getClass().getSimpleName(),"loadUnreadElements");
        filter = UNREAD;
        reload();
    }

    void reload() {
        switch (filter)
        {
            case ALL:
                load(() -> myApp().getWebListService().getWebSitesInfos());
                break;
            case READ:
                load(() -> myApp().getWebListService().getReadWebSites());
                break;
            case UNREAD:
                load( () -> myApp().getWebListService().getUnreadWebSitesInfos());
                break;
        }
    }

    private void load(Supplier<List<WebSiteInfo>> job)
    {
        Log.i(getClass().getSimpleName(), "Loading new data");
        lastLoadJob.ifPresent(o->o.cancel(true));
        this.isBusy.set(true);
        this.isFabOpened.set(false);
        CustomAsyncTaskExecutor<List<WebSiteInfo>> loader =  CustomAsyncTaskExecutor.async(job);
                loader
                        .onSuccess(this::onFinish)
                        .onError(this::onError)
                        .execute();
        this.lastLoadJob = Optional.ofNullable(loader);
    }

    public void loadReadElements()
    {
        Log.i(getClass().getSimpleName() , "loadReadElements");
        filter = Filter.READ;
        reload();
    }

    public void loadAllElements()
    {
        Log.i(getClass().getSimpleName() , "loadAllElements");
        filter = Filter.ALL;
        reload();
    }


    static interface IWebListView
    {
        void onListChanged(List<WebSiteInfo> webSiteInfos);
        void showError(String message);
    }
}
