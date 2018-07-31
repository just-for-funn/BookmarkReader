package com.davutozcan.bookmarkreader.application;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.domainmodel.Change;
import com.davutozcan.bookmarkreader.domainmodel.IArticleExtractor;
import com.davutozcan.bookmarkreader.domainmodel.IWebUnitRepository;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.exception.RecordExistsException;
import com.davutozcan.bookmarkreader.weblist.WebUnitService;

import java.util.List;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/27/2017.
 */

public class WebunitServiceImp implements WebUnitService {
    private final IArticleExtractor articleExtractor;
    IWebUnitRepository realmFacade;

    public WebunitServiceImp(IWebUnitRepository realmFacade , IArticleExtractor articleExtractor) {
        this.realmFacade = realmFacade;
        this.articleExtractor = articleExtractor;
    }

    @Override
    public List<WebUnit> getWebSitesInfos() {
        return realmFacade.webUnits();
    }

    @Override
    public void add(String url) {
        if(realmFacade.exists(url))
            throw new RecordExistsException();
        WebUnit wu = new WebUnit();
        wu.setStatus(WebUnit.Status.HAS_NEW_CONTENT);
        wu.setUrl(url);
        wu.setFaviconUrl(faviconOf(url));
        realmFacade.add(wu);
    }

    @Override
    public long count() {
        return realmFacade.count();
    }


    @Override
    public List<WebUnit> getUnreadWebSitesInfos() {
        return realmFacade.getUnreadWebUnits();
    }

    @Override
    public void markRead(String url) {
        if(realmFacade.exists(url))
        {
            WebUnit webUnit =  realmFacade.getWebUnit(url);
            webUnit.setStatus(WebUnit.Status.ALREADY_READ);
            realmFacade.update(webUnit);
        }
    }

    @Override
    public List<WebUnit> getReadWebSites() {
        return realmFacade.getByStatus(WebUnit.Status.ALREADY_READ);
    }

    private String faviconOf(String url) {
        return getParentUrl(url)+"/favicon.ico";
    }

    private String getParentUrl(String url) {
        int extensionIndex = url.lastIndexOf(".");
        int queryIndex = Integer.MAX_VALUE , subDirectoryIndex = Integer.MAX_VALUE;
        queryIndex = url.indexOf("?" , extensionIndex);
        subDirectoryIndex = url.indexOf("/" , extensionIndex);
        if(queryIndex == -1 && subDirectoryIndex == -1)
            return url;
        if(queryIndex == -1)
            return url.substring(0,subDirectoryIndex);
        if(subDirectoryIndex == -1)
            return url.substring(0,queryIndex);
        if(queryIndex < subDirectoryIndex)
            return url.substring(0,queryIndex);
        return url.substring(0,subDirectoryIndex);
    }

    @Override
    public String getSummaryFor(String url)
    {
        WebUnit wu = realmFacade.getWebUnit(url);
        return getSummaryFrom(wu);
    }

    private String getSummaryFrom(WebUnit webUnit) {
        try{
            if(webUnit.getLatestContent() == null)
                return myApp().getApplicationContext().getString(R.string.not_evaluated);
            else if(webUnit.getPreviousContent() == null)
                return  articleExtractor.convert(webUnit.getLatestContent().getContent());
            else
                return toSummary(webUnit.getChange());
        }catch (Exception e)
        {
            return myApp().getApplicationContext().getString(R.string.no_download_available);
        }
    }

    private String toSummary(Change change) {
        if(change == null)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < change.getNewBlocks().size(); i++) {
            for (int j = 0; j < change.getNewBlocks().get(i).lines().size(); j++) {
                if(j == 0)
                    sb.append("\t");
                sb.append(change.getNewBlocks().get(i).lines().get(j));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
