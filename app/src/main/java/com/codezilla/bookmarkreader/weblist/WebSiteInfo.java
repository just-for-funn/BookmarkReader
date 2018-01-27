package com.codezilla.bookmarkreader.weblist;

import java.util.Date;

/**
 * Created by davut on 7/28/2017.
 */

public class WebSiteInfo {
    String url;
    String summary;
    Status status;
    String faviconUrl;
    private Date changeDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public static  WebSiteInfo of(String url, String summary) {
        WebSiteInfo info = new WebSiteInfo();
        info.setUrl(url);
        info.setSummary(summary);
        return info;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public static enum Status
    {
        NOTHING_CHANGED,
        CHANGED
    }
}
