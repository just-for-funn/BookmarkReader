package com.davutozcan.bookmarkreader;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.davutozcan.bookmarkreader.application.BookmarkReaderApplication;
import com.davutozcan.bookmarkreader.builders.WebUnitTestDataBuilder;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;

import org.junit.Before;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/6/2017.
 */

public abstract class MainActivityTestBase {

    public static final String ANY_URL = "http://www.mocksite.com";
    public static final String ANY_SUMMARY = "Summary of mock site";
    public static final String ANY_NEW_URL = "http://www.newurl.com";
    public static final String NEW_URL = ANY_NEW_URL;

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

    protected void addWebUnit(String url, String content) {
        addWebUnit(url , content , WebUnit.Status.HAS_NEW_CONTENT);
    }

    protected void addWebUnit(String url, String content, int status) {
        WebUnit webUnit =  WebUnitTestDataBuilder.webUnitBuilder()
                .withContent(WebUnitTestDataBuilder.content(url , content))
                .withUrl(url)
                .withStatus(status)
                .withChange(null)
                .build();
        myApp().getRealmFacade().add(webUnit);
    }
}
