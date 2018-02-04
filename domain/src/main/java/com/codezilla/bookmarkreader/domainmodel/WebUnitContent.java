package com.codezilla.bookmarkreader.domainmodel;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.Required;

/**
 * Created by davut on 8/27/2017.
 */

public class WebUnitContent extends RealmObject
{

    private Date date = new Date(0);
    private String content;
    private String url;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        setDate(new Date(System.currentTimeMillis()));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
