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
    private String changeSummary;

    WebUnitContent latestContent;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChangeSummary() {
        return changeSummary;
    }

    public void setChangeSummary(String changeSummary) {
        this.changeSummary = changeSummary;
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
}
