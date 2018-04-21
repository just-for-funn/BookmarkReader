package com.davutozcan.bookmarkreader.login;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.RelativeLayout;


import com.davutozcan.bookmarkreader.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


/**
 * Created by davut on 7/10/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest
{
    @Rule
    public ActivityTestRule<SplashActivity> activityRule = new ActivityTestRule<SplashActivity>(SplashActivity.class , true , false);

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void cleanTest()
    {
    }



    @Test
    public void shouldOpenMainContentWhenAlreadyLogined()
    {
        launch();
        onView(withId(R.id.main_view_content)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void launch() {
        activityRule.launchActivity(new Intent());
    }
}