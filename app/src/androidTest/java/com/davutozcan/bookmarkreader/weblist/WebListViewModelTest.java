package com.davutozcan.bookmarkreader.weblist;

import android.support.test.runner.AndroidJUnit4;

import com.davutozcan.bookmarkreader.MainActivityTestBase;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static junit.framework.Assert.fail;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

/**
 * Created by davut on 7/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class WebListViewModelTest  extends MainActivityTestBase{

    private WebListViewPage webListViewPage() {
        return getMainActivityPage().webListPage();
    }


    @Test
    public void shouldLoadItemsOnOpen() throws InterruptedException {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        webListViewPage().assertUrlDisplaying(ANY_URL);
    }





    @Test
    public void shouldDisplayErrorMessageWhenWebListServiceCrushes()
    {
        myApp().setWebunitService(null);
        launch();
        webListViewPage().assertErrorDisplaying(R.string.unexpected_error);
    }

    @Test
    public void shouldNotDisplayElementWhenItsRead()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        addWebUnit(ANY_NEW_URL , ANY_SUMMARY);
        launch();
        webListViewPage()
                .clickItem(ANY_URL)
                .back();
        webListViewPage()
                .assertUrlNotDisplaying(ANY_URL);
    }

    @Test
    public void shouldDisplayNothingToReadWhenNoContentAvailable()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY , WebUnit.Status.ALREADY_READ);
        addWebUnit(ANY_NEW_URL , ANY_SUMMARY , WebUnit.Status.ALREADY_READ);
        launch();
        webListViewPage()
                .assertNotContentDisplaying();
    }

    @Test
    public void shouldListOnlyUnreadElementsWhenFilterSelected()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY , WebUnit.Status.ALREADY_READ);
        addWebUnit(ANY_NEW_URL , ANY_SUMMARY , WebUnit.Status.HAS_NEW_CONTENT);
        launch();
        webListViewPage()
                .clickFab()
                .clickFabItemUnread()
                .assertUrlDisplaying(ANY_NEW_URL)
                .assertUrlNotDisplaying(ANY_URL);
    }

    @Test
    public void shouldListReadElementsWhenFilterIsSelected()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY , WebUnit.Status.ALREADY_READ);
        addWebUnit(ANY_NEW_URL , ANY_SUMMARY , WebUnit.Status.HAS_NEW_CONTENT);
        launch();
        webListViewPage()
                .clickFab()
                .clickFabItemRead()
                .assertUrlDisplaying(ANY_URL)
                .assertUrlNotDisplaying(ANY_NEW_URL);
    }

    @Test
    public void shouldListAllElementsWhenFilterIsSelected()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY , WebUnit.Status.ALREADY_READ);
        addWebUnit(ANY_NEW_URL , ANY_SUMMARY , WebUnit.Status.HAS_NEW_CONTENT);
        launch();
        webListViewPage()
                .clickFab()
                .clickFabItemAll()
                .assertUrlDisplaying(ANY_URL)
                .assertUrlDisplaying(ANY_NEW_URL);
    }


}