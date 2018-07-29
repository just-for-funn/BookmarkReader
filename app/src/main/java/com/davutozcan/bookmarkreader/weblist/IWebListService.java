package com.davutozcan.bookmarkreader.weblist;

import java.util.List;

/**
 * Created by davut on 7/28/2017.
 */

public interface IWebListService
{
    List<WebSiteInfo> getWebSitesInfos();
    void add(String url);
    long count();

    List<WebSiteInfo> getUnreadWebSitesInfos();

    void markRead(String url);

    List<WebSiteInfo> getReadWebSites();

    WebSiteInfo load(String url);
}
