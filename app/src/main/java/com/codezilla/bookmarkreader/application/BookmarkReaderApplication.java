package com.codezilla.bookmarkreader.application;

import android.app.Application;

import com.codezilla.bookmarkreader.article.IArticleService;
import com.codezilla.bookmarkreader.cache.ICacheService;
import com.codezilla.bookmarkreader.cache.SimpleLruCache;
import com.codezilla.bookmarkreader.domainmodel.RealmFacade;
import com.codezilla.bookmarkreader.login.IUserService;
import com.codezilla.bookmarkreader.weblist.IWebListService;

/**
 * Created by davut on 7/22/2017.
 */

public class BookmarkReaderApplication extends Application
{
    private static BookmarkReaderApplication self;
    IUserService userService;
    IWebListService webListService;
    ICacheService cacheService;
    IArticleService articleService;
    private RealmFacade realmFacade;
    private IApplicationState applicationState;
    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        this.realmFacade = new RealmFacade(getApplicationContext());
        userService = new MockUserService();
        webListService = new UserServiceAdapter(realmFacade);
        cacheService = new SimpleLruCache();
        articleService = new MockArticleService();
        applicationState = new ApplicationStateSharedPrefencesImp(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        realmFacade.close();
        super.onTerminate();
    }

    public static BookmarkReaderApplication getInstance()
    {
        return self;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }


    public static IUserService userService()
    {
        return getInstance().getUserService();
    }

    public static ICacheService cacheService()
    {
        return getInstance().getCacheService();
    }

    public IWebListService getWebListService() {
        return webListService;
    }

    public void setWebListService(IWebListService webListService) {
        this.webListService = webListService;
    }

    public ICacheService getCacheService() {
        return cacheService;
    }

    public void setCacheService(ICacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void setArticleService(IArticleService articleService) {
        this.articleService = articleService;
    }

    public IArticleService getArticleService() {
        return articleService;
    }

    public static BookmarkReaderApplication myApp()
    {
        return getInstance();
    }

    public IApplicationState getState()
    {
        return this.applicationState;
    }


}
