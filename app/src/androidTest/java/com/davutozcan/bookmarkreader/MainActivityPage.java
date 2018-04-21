package com.davutozcan.bookmarkreader;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.davutozcan.bookmarkreader.weblist.WebListViewPage;

import org.junit.Rule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by davut on 7/28/2017.
 */

public class MainActivityPage
{

    private final ActivityTestRule<MainActivity> activityTestRule;

    public MainActivityPage(ActivityTestRule<MainActivity> activityTestRule) {
        this.activityTestRule = activityTestRule;
    }

    public SettingsPage clickToggeButton() {
        onView(
                allOf(withContentDescription("BookmarkReader"),
                    withParent(withId(R.id.toolBar)),
                    isDisplayed())
               ).perform(click());
        return new SettingsPage();
    }


    public WebListViewPage webListPage() {
        return new WebListViewPage(this.activityTestRule);
    }
}
