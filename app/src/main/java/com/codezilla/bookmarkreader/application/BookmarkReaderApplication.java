package com.codezilla.bookmarkreader.application;

import android.app.Application;

import com.codezilla.bookmarkreader.article.ArticleServiceImp;
import com.codezilla.bookmarkreader.article.IArticleService;
import com.codezilla.bookmarkreader.cache.ICacheService;
import com.codezilla.bookmarkreader.cache.SimpleLruCache;
import com.codezilla.bookmarkreader.domainmodel.ILogRepository;
import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;
import com.codezilla.bookmarkreader.domainmodel.RealmLogRepositoryImp;
import com.codezilla.bookmarkreader.domainmodel.RealmRepositoryImp;
import com.codezilla.bookmarkreader.login.IUserService;
import com.codezilla.bookmarkreader.sync.BoilerplateArticlConverterAdapter;
import com.codezilla.bookmarkreader.weblist.IWebListService;

/**
 * Created by davut on 7/22/2017.
 */

public class BookmarkReaderApplication extends Application{
    private static BookmarkReaderApplication self;
    IUserService userService;
    IWebListService webListService;
    ICacheService cacheService;
    IArticleService articleService;
    private IWebUnitRepository realmFacade;
    private IApplicationState applicationState;
    private ILogRepository logRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        initilizeServices();
    }

    public void initilizeServices() {
        this.realmFacade = new RealmRepositoryImp(getApplicationContext());
        userService = new MockUserService();
        webListService = new WeblistServiceAdapter(realmFacade ,  new BoilerplateArticlConverterAdapter());
        logRepository = new RealmLogRepositoryImp(getApplicationContext());
        cacheService = new SimpleLruCache();
        articleService = new ArticleServiceImp(new ArticleRepositoryAdapter(realmFacade) , new ArticleLoaderImp(realmFacade , new OkHttpClientImp(), this));
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


    public void setLogRepository(ILogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public ILogRepository getLogRepository() {
        return logRepository;
    }

    public IWebUnitRepository getRealmFacade() {
        return realmFacade;
    }
}
