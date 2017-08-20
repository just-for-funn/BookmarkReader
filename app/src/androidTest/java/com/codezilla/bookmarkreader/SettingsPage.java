package com.codezilla.bookmarkreader;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by davut on 7/28/2017.
 */

public class SettingsPage
{
    public SettingsPage assertDisplaying() {
        ViewInteraction settingsView = onView(withId(R.id.settings_container));
        settingsView.check(matches(isDisplayed()));
        return this;
    }

    public void assertNameSurnameDisplaying(String name, String surname) {
        onView(withText(name+" "+surname)).check(ViewAssertions.matches(isDisplayed()));
    }
}
