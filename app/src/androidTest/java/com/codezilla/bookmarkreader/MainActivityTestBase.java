package com.codezilla.bookmarkreader;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.codezilla.bookmarkreader.application.BookmarkReaderApplication;

import org.junit.Before;

/**
 * Created by davut on 8/6/2017.
 */

public abstract class MainActivityTestBase {
    protected  ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class , true , false);
    protected MainActivityPage mainActivityPage = new MainActivityPage(activityTestRule);

    public MainActivityPage getMainActivityPage() {
        return mainActivityPage;
    }

    public ActivityTestRule<MainActivity> getActivityTestRule() {
        return activityTestRule;
    }

    public void launch() {
        activityTestRule.launchActivity(new Intent());
    }

    @Before
    public final void beforeTest()
    {
        BookmarkReaderApplication.getInstance().setWebListService(null);
        //BookmarkReaderApplication.getInstance().setUserService(null);
        //BookmarkReaderApplication.getInstance().setCacheService(null);
    }
}
