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
            update(w);
        }
        if(this.changedCount == 0)
            logRepository.info("Synchronization finished. No update available");
        else
            logRepository.info(String.format("Syncronization finished. %d sites updated.",this.changedCount));
    }

    private void update(WebUnit w)
    {
        try
        {
            w.setLastDownloadCheckDate(new Date());
            this.IUpdateListener.onStart(w);
            updateSafe(w);
            w.setDownloadStatus(WebUnit.DownloadStatus.OK);
            this.IUpdateListener.onComplete(w);
        } catch (Exception e)
        {
            w.setDownloadStatus(WebUnit.DownloadStatus.ERROR);
            this.IUpdateListener.onFail(w);
            logRepository.error(String.format("Cannot update[%s] cause:%s" , w.getUrl() , e.getMessage()));
        }
        updateEntity(w);
    }

    private void updateEntity(WebUnit w) {
        try{
            realmFacade.update(w);
        }catch (Exception e){
            android.util.Log.e(getClass().getSimpleName(), "updateEntity: ",e );
        }
    }

    private void updateSafe(WebUnit w) {
        String htmlContent = httpClient.getHtmlContent(w.getUrl());
        boolean changed = comparer.isChanged(currentContent(w) , htmlContent);
        if(changed)
        {
            swapLatestContent(w);
            WebUnitContent wuc = new WebUnitContent();
            wuc.setContent(htmlContent);
            w.setLatestContent(wuc);
            wuc.setUrl(httpClient.url());
            w.setChange( toChange(comparer.newLines()));
            w.setStatus(WebUnit.Status.HAS_NEW_CONTENT);
            logRepository.info(String.format("Updated:[%s] " , w.getUrl()));
            this.changedCount+=1;
        }
        String favicon = faviconExtractor.faviconUrl(httpClient.url(), htmlContent);
        if(!isNullOrEmpty(favicon))
        {
            w.setFaviconUrl(favicon);
        }
    }

    private void swapLatestContent(WebUnit w) {
        WebUnitContent wuc = w.getLatestContent();
        w.setPreviousContent(wuc);
        w.setLatestContent(null);
    }

    private Change toChange(List<TextBlock> textBlocks) {
        Change change = new Change();
        RealmList<TextBlock> blocks = new RealmList<>();
        blocks.addAll(textBlocks );
        change.setNewBlocks(blocks);
        return change;
    }

    private String currentContent(WebUnit w) {
        if(w.getLatestContent() == null)
            return "";
        if(isNullOrEmpty(w.getLatestContent().getContent()))
            return "";
        return w.getLatestContent().getContent();
    }

    private boolean isNullOrEmpty(String content) {
        if(content == null)
            return true;
        if(content.length() == 0)
            return true;
        return false;
    }

    private boolean isChanged(int changeValue) {
        return changeValue != 0;
    }

    public void stop() {
        this.isStopped = true;
    }
}
