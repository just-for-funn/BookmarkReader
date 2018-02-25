package com.codezilla.bookmarkreader.weblist;

import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.MainActivityTestBase;
import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.application.BookmarkReaderApplication;
import com.codezilla.bookmarkreader.builders.WebUnitTestDataBuilder;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;
import com.codezilla.bookmarkreader.domainmodel.WebUnitContent;
import com.codezilla.bookmarkreader.exception.RecordExistsException;

import org.awaitility.core.ThrowingRunnable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.codezilla.bookmarkreader.test.TestExtensions.untilAsserted;
import static junit.framework.Assert.fail;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by davut on 7/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class WebListViewModelTest  extends MainActivityTestBase{




    @Before
    public void before()
    {
        myApp().onCreate();
        myApp().getRealmFacade().clearWebUnits();
    }

    @Test
    public void shouldDisplayBusyWhenModelChanged() throws InterruptedException {
         launch();
         webListViewPage().setIsBusy(true);
         untilAsserted(new ThrowingRunnable() {
             @Override
             public void run() throws Throwable {
                 webListViewPage().assertProgressDisplaying();
             }
         });
    }

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
        myApp().setWebListService(null);
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

    private WebSiteInfo convert(String url) {
        return WebSiteInfo.of(url , "");
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