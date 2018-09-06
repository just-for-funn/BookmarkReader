package com.davutozcan.bookmarkreader.domainmodel;

import android.util.*;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by davut on 9/5/2017.
 */

public class WebUnitContentUpdater
{
    private final IHttpClient httpClient;
    private final IUpdateListener IUpdateListener;
    IWebUnitRepository realmFacade;
    IHtmlComparer comparer;
    ILogRepository logRepository;
    IFaviconExtractor faviconExtractor;
    private int changedCount = 0;
    private boolean isStopped = false;

    public WebUnitContentUpdater(UpdateContext updateContext) {
        this.httpClient = updateContext.getHttpClient();
        this.realmFacade = updateContext.getRealmFacade();
        this.comparer = updateContext.getComparer();
        this.logRepository = updateContext.getLogRepository();
        this.faviconExtractor = updateContext.getFaviconExtractor();
        this.IUpdateListener = updateContext.getListener();
    }

    public void updateAll()
    {
        this.changedCount = 0;
        logRepository.info("Syncronization started");
        List<WebUnit> units =  realmFacade.webUnits();
        for (WebUnit w: units)
        {
            if(isStopped)
                return;
            SingleItemUpdater updater = new SingleItemUpdater(realmFacade , ()->httpClient , comparer , faviconExtractor , logRepository);
            updater.update(w ,IUpdateListener::onStart , IUpdateListener::onComplete , IUpdateListener::onFail , o->this.changedCount++ );
        }
        if(this.changedCount == 0)
            logRepository.info("Synchronization finished. No update available");
        else
            logRepository.info(String.format("Syncronization finished. %d sites updated.",this.changedCount));
    }











    private boolean isChanged(int changeValue) {
        return changeValue != 0;
    }

    public void stop() {
        this.isStopped = true;
    }
}
