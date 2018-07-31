package com.davutozcan.bookmarkreader.weblist;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import com.davutozcan.bookmarkreader.MainActivity;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.article.ArticleDetailPage;
import com.davutozcan.bookmarkreader.assertions.AsyncExtensions;

import org.awaitility.Duration;
import org.awaitility.core.ThrowingRunnable;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.awaitility.Awaitility.await;
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

    public WebListViewPage assertUrlDisplaying(String url) {
        AsyncExtensions.invokeUntill(()-> onView(withText(url)).check(matches(isDisplayed())));
        return this;
    }

    public ArticleDetailPage clickItem(String title)
    {
        onView(withText(title)).perform(click());
        return new ArticleDetailPage(this.activityTestRule);
    }

    public WebListViewPage assertUrlNotDisplaying(String url) {
        await().atMost(2 , TimeUnit.SECONDS).untilAsserted(new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                onView(withText(url)).check(doesNotExist());
            }
        });
        return this;
    }

    public WebListViewPage assertNotContentDisplaying() {
         onView(withText("Nothing to read")).check(matches(isDisplayed()));
         return this;
    }
    public WebListViewPage assertErrorDisplaying(int stringResourceId) {
        await().atMost(new Duration(3 , TimeUnit.SECONDS))
                .untilAsserted(()->onView(withText(stringResourceId)).check(matches(isDisplayed())));
        return this;
    }

    public WebListViewPage clickFab() {
         onView(withId(R.id.fab_menu)).perform(click());
         return this;
    }

    public WebListViewPage clickFabItemUnread() {
        onView(withId(R.id.fab_unread_container)).perform(click());
        return this;
    }

    public WebListViewPage clickFabItemRead() {
        onView(withId(R.id.fab_read_container)).perform(click());
        return this;
    }

    public WebListViewPage clickFabItemAll() {
        onView(withId(R.id.fab_all_container)).perform(click());
        return this;
    }
}
