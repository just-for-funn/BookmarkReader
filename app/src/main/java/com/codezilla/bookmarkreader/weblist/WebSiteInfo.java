package com.codezilla.bookmarkreader.weblist;

import java.util.Date;

/**
 * Created by davut on 7/28/2017.
 */

public class WebSiteInfo {
    String url;
    String summary;
    String faviconUrl;
    private Date changeDate;
    private int status;

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


    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
