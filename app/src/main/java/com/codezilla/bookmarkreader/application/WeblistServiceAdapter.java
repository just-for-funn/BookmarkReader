package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;
import com.codezilla.bookmarkreader.exception.RecordExistsException;
import com.codezilla.bookmarkreader.weblist.IWebListService;
import com.codezilla.bookmarkreader.weblist.WebSiteInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davut on 8/27/2017.
 */

public class WeblistServiceAdapter implements IWebListService {
    IWebUnitRepository realmFacade;

    public WeblistServiceAdapter(IWebUnitRepository realmFacade) {
        this.realmFacade = realmFacade;
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
        inf.setSummary(webUnit.getChangeSummary());
        inf.setStatus(WebSiteInfo.Status.CHANGED );
        inf.setFaviconUrl(webUnit.getFaviconUrl());
        return inf;
    }

}
