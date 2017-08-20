package com.codezilla.bookmarkreader.weblist;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import com.codezilla.bookmarkreader.MainActivity;
import com.codezilla.bookmarkreader.article.ArticleDetailPage;

import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by davut on 7/28/2017.
 */

public class WebListViewPage
{
    ActivityTestRule<MainActivity> activityTestRule;

    public WebListViewPage(ActivityTestRule<MainActivity> activityTestRule) {
        this.activityTestRule = activityTestRule;
    }

    public void setIsBusy(boolean busy) {
        WebListView webListView = fragmentofTag(MainActivity.TAG_WEBLIST_FRAGMENT);
        WebListViewModel model = webListView.binding.getModel();
        model.isBusy.set(true);
    }
    private <T extends Fragment> T fragmentofTag(String tag) {
        return (T) activityTestRule.getActivity().getSupportFragmentManager().findFragmentByTag(tag);
    }

    public void assertProgressDisplaying() {
        onView(withClassName(equalTo(ProgressBar.class.getName())) ).check(matches(isDisplayed()));
    }

    public void assertUrlDisplaying(String url) {
        onView(withText(url)).check(matches(isDisplayed()));
    }

    public ArticleDetailPage clickItem(String title)
    {
        onView(withText(title)).perform(ViewActions.click());
        return new ArticleDetailPage(this.activityTestRule);
    }
}
