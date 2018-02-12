package com.codezilla.bookmarkreader.weblist;

import android.databinding.ObservableBoolean;

import com.codezilla.bookmarkreader.async.CustomAsyncTaskExecutor;
import com.codezilla.bookmarkreader.exception.DomainException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 7/5/2017.
 */

public class WebListViewModel implements CustomAsyncTaskExecutor.TaskExecuteOwner<List<WebSiteInfo>>
{
    private ObservableBoolean isCompleted = new ObservableBoolean(false);
    WeakReference<IWebListView> weblistView;

    public WebListViewModel(IWebListView weblistView) {
        this.weblistView = new WeakReference<IWebListView>(weblistView);
    }

    public final ObservableBoolean isBusy = new ObservableBoolean(false);
    List<WebSiteInfo> webSiteInfos = new ArrayList<>();
    public void loadRows()
    {
        this.isBusy.set(true);
        new CustomAsyncTaskExecutor<List<WebSiteInfo>>(this, () -> myApp().getWebListService().getUnreadWebSitesInfos()).execute();
    }

    @Override
    public void onFinish(List<WebSiteInfo> webListViews) {
        isBusy.set(false);
        if(webListViews == null)
            this.webSiteInfos = new ArrayList<>();
        else
            this.webSiteInfos = webListViews;
        if(this.weblistView.get()!= null)
            this.weblistView.get().onListChanged(this.webSiteInfos);
        isCompleted.set(webSiteInfos.size()  == 0 );
    }

    @Override
    public void onError(DomainException domainException)
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

    static interface IWebListView
    {
        void onListChanged(List<WebSiteInfo> webSiteInfos);
        void showError(String message);
    }
}
