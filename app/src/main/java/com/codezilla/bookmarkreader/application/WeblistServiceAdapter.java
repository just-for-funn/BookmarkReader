package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.domainmodel.Change;
import com.codezilla.bookmarkreader.domainmodel.IArticleExtractor;
import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;
import com.codezilla.bookmarkreader.exception.RecordExistsException;
import com.codezilla.bookmarkreader.weblist.IWebListService;
import com.codezilla.bookmarkreader.weblist.WebSiteInfo;

import java.util.ArrayList;
import java.util.List;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/27/2017.
 */

public class WeblistServiceAdapter implements IWebListService {
    private final IArticleExtractor articleExtractor;
    IWebUnitRepository realmFacade;

    public WeblistServiceAdapter(IWebUnitRepository realmFacade , IArticleExtractor articleExtractor) {
        this.realmFacade = realmFacade;
        this.articleExtractor = articleExtractor;
    }

    @Override
    public List<WebSiteInfo> getWebSitesInfos() {
        List<WebUnit> wuts= realmFacade.webUnits();
        List<WebSiteInfo> wsinfs = new ArrayList<>(wuts.size());
        for (int i = 0; i <wuts.size(); i++) {
                wsinfs.add(convert(wuts.get(i)));
        }
        return wsinfs;
    }

    @Override
    public void add(String url) {
        if(realmFacade.exists(url))
            throw new RecordExistsException();
        WebUnit wu = new WebUnit();
        wu.setUrl(url);
        wu.setFaviconUrl(faviconOf(url));
        realmFacade.add(wu);
    }

    @Override
    public long count() {
        return realmFacade.count();
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

    private WebSiteInfo convert(WebUnit webUnit) {
        WebSiteInfo inf = new WebSiteInfo();
        inf.setUrl(webUnit.getUrl());
        inf.setStatus(WebSiteInfo.Status.CHANGED );
        inf.setFaviconUrl(webUnit.getFaviconUrl());
        inf.setSummary(getSummaryFrom(webUnit));
        return inf;
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
            return myApp().getApplicationContext().getString(R.string.error);
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
