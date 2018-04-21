package com.davutozcan.bookmarkreader.views.edit;

import com.davutozcan.bookmarkreader.R;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by davut on 10.02.2018.
 */

public class EditPage {
    public EditPage checked(String url)
    {
        onView( withTagValue(equalTo(url))).perform(click());
        return this;
    }

    public EditPage clickDelete() {
        onView(withId(R.id.action_delete)).perform(click());
        return this;
    }

    public EditPage assertNotDisplaying(String url) {
        await().atMost(2 , TimeUnit.SECONDS).until(()->{
            onView( withTagValue(equalTo(url))).check(doesNotExist());
            return true;
        });
        return this;
    }

    public AddNewSitepage clickAdd() {
        onView(withId(R.id.fab_add)).perform(click());
        return new AddNewSitepage();
    }

    public EditPage assertUrlDisplaying(String url) {
        onView(withText(url)).check(matches(isDisplayed()));
        return this;
    }

    public EditPage assertErrorDisplaying(int stringResourceId) {
        onView(withText(stringResourceId)).check(matches(isDisplayed()));
        return this;
    }
}
