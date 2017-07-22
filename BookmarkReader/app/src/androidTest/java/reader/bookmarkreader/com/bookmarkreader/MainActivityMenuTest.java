package reader.bookmarkreader.com.bookmarkreader;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.RelativeLayout;

import com.codezilla.bookmarkreader.MainActivity;
import com.codezilla.bookmarkreader.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by davut on 29.01.2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityMenuTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp()
    {

    }

    @Test
    public void shouldOpenActivity() throws InterruptedException {
        RelativeLayout layout = (RelativeLayout) activityTestRule.getActivity().findViewById(R.id.drawerContentLayout);
        assertTrue(layout.isShown());
    }

    @Test
    public void shouldOpenMenuWhenClickToolBarButton() throws InterruptedException {
        ViewInteraction appCompatImageButton = onView(allOf(withContentDescription("BookmarkReader"), withParent(withId(R.id.toolBar)), isDisplayed()));
        appCompatImageButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.settings_container));
        textView.check(matches(isDisplayed()));
    }
}
