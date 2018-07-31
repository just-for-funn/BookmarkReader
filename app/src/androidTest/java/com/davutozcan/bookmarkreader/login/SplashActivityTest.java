package com.davutozcan.bookmarkreader.login;

import android.content.Intent;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.application.BookmarkReaderApplication;
import com.davutozcan.bookmarkreader.assertions.AsyncExtensions;
import com.davutozcan.bookmarkreader.splash.SplashActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static org.hamcrest.Matchers.is;


/**
 * Created by davut on 7/10/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {
    @Rule
    public ActivityTestRule<SplashActivity> activityRule = new ActivityTestRule<SplashActivity>(SplashActivity.class, true, false);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        myApp().getRealmFacade().clearWebUnits();
        myApp().getState().saveAppInitilized(false);
    }

    @After
    public void cleanTest() {
    }

    @Test
    public void shouldOpenMainContentWhenAlreadyLogined() {
        launch();
        AsyncExtensions.invokeUntill(()->onView(withId(R.id.main_view_content))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed())) , 7000);
    }
    
    @Test
    public void should_InitialPayloadCorrect() {
        final int expected = 64;
        launch();
        AsyncExtensions.invokeUntill(()->onView(withId(R.id.main_view_content))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed())) , 7000);
        int actual = (int) myApp().getRealmFacade().count();
        assertThat(actual, is(expected));
    }

    private void launch() {
        activityRule.launchActivity(new Intent());
    }
}