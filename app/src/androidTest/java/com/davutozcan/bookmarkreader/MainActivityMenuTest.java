package com.davutozcan.bookmarkreader;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.RelativeLayout;

import com.davutozcan.bookmarkreader.MainActivity;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.domainmodel.IWebUnitRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by davut on 29.01.2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityMenuTest extends MainActivityTestBase {

    @Test
    public void shouldOpenActivity() throws InterruptedException {
        launch();
        RelativeLayout layout = (RelativeLayout) activityTestRule.getActivity().findViewById(R.id.drawerContentLayout);
        assertTrue(layout.isShown());
    }

    @Test
    public void shouldOpenMenuWhenClickToolBarButton() throws InterruptedException {
        launch();
        mainActivityPage
                .clickToggeButton()
                .assertDisplaying();
    }
}
