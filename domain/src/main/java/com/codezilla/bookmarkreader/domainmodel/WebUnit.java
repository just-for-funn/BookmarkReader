package com.codezilla.bookmarkreader.domainmodel;

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
}
