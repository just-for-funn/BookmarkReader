package com.davutozcan.bookmarkreader.article;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.web.assertion.WebViewAssertions;
import android.support.test.rule.ActivityTestRule;

import com.davutozcan.bookmarkreader.MainActivity;
import com.davutozcan.bookmarkreader.MainActivityPage;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.views.download.DownloadPage;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.w3c.dom.Document;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static com.davutozcan.bookmarkreader.assertions.IsDocumentContains.isDocumentContains;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by davut on 8/6/2017.
 */

public class ArticleDetailPage {
    private final ActivityTestRule<MainActivity> activityTestRule;

    public ArticleDetailPage(ActivityTestRule<MainActivity> activityTestRule) {
        this.activityTestRule = activityTestRule;
    }

    public void assertVisible()
    {
        onView(ViewMatchers.withId(R.id.article_detail_view_container)).
                check(matches(isDisplayed()));
    }

    public MainActivityPage back() {
        Espresso.pressBack();
        return new MainActivityPage(activityTestRule);
    }

    public void assertArticleDisplaying(final String url) {
        onWebView().check(WebViewAssertions.webContent(isDocumentContains(url)));
    }

    public void assertErrorDisplaying(String msg) {
        onView(withText(msg)).check(matches(isDisplayed()));
    }

    public ArticleDetailPage clickSwitch() {
        onView(withId(R.id.action_article)).perform(click());
        return this;
    }

    public ArticleDetailPage assertHtmlViewNotDisplaying() {
        onView(withId(R.id.article_webview)).check(matches(not(isDisplayed())));
        return this;
    }

    public ArticleDetailPage assertArticleViewDisplaying() {
        onView(withId(R.id.article_textview)).check(matches(isDisplayed()));
        return this;
    }

    public ArticleDetailPage assertTextDisplaying(String text) {
        onView(withText(text)).check(matches(isDisplayed()));
        return this;
    }
}
