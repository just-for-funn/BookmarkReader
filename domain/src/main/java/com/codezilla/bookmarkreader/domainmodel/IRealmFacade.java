package com.codezilla.bookmarkreader.domainmodel;

import java.util.List;

/**
 * Created by davut on 9/2/2017.
 */

public interface IRealmFacade {
    void clearWebSites();

    List<WebUnit> webUnits();

    void addSite(String anyUrl);

    void close();

    void addSiteContent(String url, String content);

    String getWebUnitContent(String url);

    WebUnit getWebUnit(String url);

    boolean exists(String url);
}
