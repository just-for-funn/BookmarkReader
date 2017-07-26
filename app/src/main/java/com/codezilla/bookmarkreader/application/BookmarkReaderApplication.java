package com.codezilla.bookmarkreader.application;

import android.app.Application;

import com.codezilla.bookmarkreader.login.IUserService;

/**
 * Created by davut on 7/22/2017.
 */

public class BookmarkReaderApplication extends Application
{
    private static BookmarkReaderApplication self;
    IUserService userService;
    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        userService = new MockUserService();
    }

    public static BookmarkReaderApplication getInstance()
    {
        return self;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
