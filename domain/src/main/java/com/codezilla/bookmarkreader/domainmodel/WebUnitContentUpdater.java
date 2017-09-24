package com.codezilla.bookmarkreader.domainmodel;

import java.util.List;

/**
 * Created by davut on 9/5/2017.
 */

public class WebUnitContentUpdater
{

    private final IHttpClient httpClient;
    IWebUnitRepository realmFacade;
    IHtmlComparer comparer;
    ILogRepository logRepository;
    IFaviconExtractor faviconExtractor;
    private int changedCount = 0;

    public WebUnitContentUpdater(IHttpClient httpClient , IWebUnitRepository realmFacade , IHtmlComparer comparer , ILogRepository logRepository , IFaviconExtractor faviconExtractor) {
        this.httpClient = httpClient;
        this.realmFacade = realmFacade;
        this.comparer = comparer;
        this.logRepository = logRepository;
        this.faviconExtractor = faviconExtractor;
    }

    public void updateAll()
    {
        this.changedCount = 0;
        logRepository.info("Syncronization started");
        List<WebUnit> units =  realmFacade.webUnits();
        for (WebUnit w: units)
        {
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
            updateSafe(w);
        } catch (Exception e)
        {
            logRepository.error(String.format("Cannot update[%s] cause:%s" , w.getUrl() , e.getMessage()));
        }
    }

    private void updateSafe(WebUnit w) {
        String htmlContent = httpClient.getHtmlContent(w.getUrl());
        int changeResult = comparer.compare(currentContent(w) , htmlContent);
        if(isChanged(changeResult))
        {
            WebUnitContent wuc = new WebUnitContent();
            wuc.setContent(htmlContent);
            w.setLatestContent(wuc);
            w.setChangeSummary(comparer.change());
            logRepository.info(String.format("Updated:[%s] " , w.getUrl()));
            this.changedCount+=1;
        }
        String favicon = faviconExtractor.faviconUrl(httpClient.url(), htmlContent);
        if(!isNullOrEmpty(favicon))
        {
            w.setFaviconUrl(favicon);
        }
        realmFacade.update(w);
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
}
