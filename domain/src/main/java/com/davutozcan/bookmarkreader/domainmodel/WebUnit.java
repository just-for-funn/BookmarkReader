package com.davutozcan.bookmarkreader.domainmodel;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by davut on 8/21/2017.
 */

public class WebUnit extends RealmObject {
    public static final String COL_URL = "url";
    @Index
    @PrimaryKey
    private String url;
    private String faviconUrl;

    WebUnitContent latestContent;
    WebUnitContent previousContent;
    Change change;
    private int status;
    private int downloadStatus = DownloadStatus.ERROR;
    Date lastDownloadCheckDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebUnitContent getLatestContent() {
        return latestContent;
    }

    public void setLatestContent(WebUnitContent latestContent) {
        this.latestContent = latestContent;
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public WebUnitContent getPreviousContent() {
        return previousContent;
    }

    public void setPreviousContent(WebUnitContent previousContent) {
        this.previousContent = previousContent;
    }

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public Date getLastDownloadCheckDate() {
        return lastDownloadCheckDate;
    }

    public void setLastDownloadCheckDate(Date lastDownloadCheckDate) {
        this.lastDownloadCheckDate = lastDownloadCheckDate;
    }


    public boolean isDownloadFailed()
    {
        return getDownloadStatus() == DownloadStatus.ERROR;
    }

    public static class Status
    {
        public static final int ALREADY_READ = 0;
        public static final int HAS_NEW_CONTENT = 1;
    }
    public static class DownloadStatus
    {
        public static final int OK = 0;
        public static final int ERROR = 1;

    }
}
