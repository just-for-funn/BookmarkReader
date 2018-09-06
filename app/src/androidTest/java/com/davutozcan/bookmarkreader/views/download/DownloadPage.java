package com.davutozcan.bookmarkreader.views.download;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import com.davutozcan.bookmarkreader.R;
import com.github.lzyzsd.circleprogress.ArcProgress;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.davutozcan.bookmarkreader.assertions.AsyncExtensions.invokeUntill;
import static org.awaitility.Awaitility.await;

/**
 * Created by davut on 1/14/2018.
 */

public class DownloadPage {
    public DownloadPage assertDisplaying() {
        onView(withId(R.id.download_fragment_container)).check(matches(isDisplayed()));
        return this;
    }

    public DownloadPage clickStart() {
        onView(withText("Start")).perform(ViewActions.click());
        return this;
    }

    public DownloadPage assertCompletedCount(int count)
    {
        invokeUntill(()->onView(withId(R.id.arc_total)).check(ViewAssertions.matches(arcProcess(count))));
        return this;
    }

    private Matcher<? super View> arcProcess(int count) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if(!(item instanceof ArcProgress))
                    return false;
                ArcProgress ap = (ArcProgress) item;
                return ap.getProgress() == count;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("given view is either not arc view or progress not matches "+count);
            }
        };
    }

    public DownloadPage assertNewCount(int count) {
        invokeUntill(()->        onView(withId(R.id.arc_new)).check(ViewAssertions.matches(arcProcess(count))));
        return this;
    }

    public DownloadPage assertFailedCount(int count) {
        onView(withId(R.id.arc_failed)).check(ViewAssertions.matches(arcProcess(count)));
        return this;
    }

    public DownloadPage assertTotalCount(int count) {
        invokeUntill(()->        onView(withId(R.id.arc_total)).check(ViewAssertions.matches(arcProcess(count))));
        return this;
    }

    public DownloadPage assertTextDisplaying(String txt) {
        onView(withText(txt)).check(matches(ViewMatchers.isDisplayed()));
        return this;
    }

    public DownloadPage clickStop() {
        onView(withText("Stop")).perform(ViewActions.click());
        return this;
    }

    public DownloadPage assertText(String txt)
    {
        invokeUntill(()->        onView(withText(txt)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())));
        return this;
    }
}
