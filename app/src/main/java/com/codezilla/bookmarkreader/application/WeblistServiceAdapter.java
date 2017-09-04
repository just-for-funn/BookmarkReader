package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.domainmodel.IRealmFacade;
import com.codezilla.bookmarkreader.domainmodel.RealmFacade;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;
import com.codezilla.bookmarkreader.exception.RecordExistsException;
import com.codezilla.bookmarkreader.weblist.IWebListService;
import com.codezilla.bookmarkreader.weblist.WebSiteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davut on 8/27/2017.
 */

public class WeblistServiceAdapter implements IWebListService {
    IRealmFacade realmFacade;

    public WeblistServiceAdapter(IRealmFacade realmFacade) {
        this.realmFacade = realmFacade;
    }

    @Override
    public List<WebSiteInfo> getWebSitesInfos() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        realmFacade.addSite(url);
    }

    private WebSiteInfo convert(WebUnit webUnit) {
        WebSiteInfo inf = new WebSiteInfo();
        inf.setUrl(webUnit.getUrl());
        inf.setSummary(webUnit.getChangeSummary());
        inf.setStatus(WebSiteInfo.Status.CHANGED );
        return inf;
    }
}
