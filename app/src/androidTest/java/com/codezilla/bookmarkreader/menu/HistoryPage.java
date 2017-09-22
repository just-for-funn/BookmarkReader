package com.codezilla.bookmarkreader.menu;

import android.support.test.espresso.assertion.ViewAssertions;

import com.codezilla.bookmarkreader.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by davut on 9/18/2017.
 */

public class HistoryPage {
    public HistoryPage assertDisplaying() {
        onView(withId(R.id.history_view)).check(matches(isDisplayed()));
        return this;
    }

    public HistoryPage assertLogDisplaying(String log) {
        onView(withText(log)).check(matches(isDisplayed()));
        return this;
    }
}
