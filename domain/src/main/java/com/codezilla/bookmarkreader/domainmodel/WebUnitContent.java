package com.codezilla.bookmarkreader.domainmodel;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by davut on 8/27/2017.
 */

public class WebUnitContent extends RealmObject
{
    @Index
    private String url;
    private String content;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
