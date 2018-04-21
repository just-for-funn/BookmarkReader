package com.davutozcan.bookmarkreader.domainmodel;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by Lenovo on 9/6/2017.
 */
@RealmClass
public class Log implements RealmModel {
    public final static int INFO = 0;
    public final static int WARNING = 1;
    public final static int ERROR = 2;

    @Required
    Date date;

    int type;

    @Required
    String msg;

    public Log()
    {
    }

    public Log(String message, int type, Date date) {
        this.msg = message;
        this.type =type;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
