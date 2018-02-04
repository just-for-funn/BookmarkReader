package com.codezilla.bookmarkreader.weblist;

import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.MainActivityTestBase;
import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.application.BookmarkReaderApplication;
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
    public static final String ANY_URL = "http://www.mocksite.com";
    public static final String ANY_SUMMARY = "Summary of mock site";
    public static final String ANY_NEW_URL = "http://www.newurl.com";
    public static final String NEW_URL = ANY_NEW_URL;



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

    private void addWebUnit(String url, String content) {
        WebUnit webUnit = new WebUnit();
        WebUnitContent webUnitContent = new WebUnitContent();
        webUnitContent.setContent(content);
        webUnitContent.setDate(new Date(System.currentTimeMillis()));
        webUnitContent.setUrl(url);
        webUnit.setLatestContent(webUnitContent);
        webUnit.setUrl(url);
        webUnit.setStatus(WebUnit.Status.HAS_NEW_CONTENT);
        webUnit.setChange(null);
        myApp().getRealmFacade().add(webUnit);
    }

    @Test
    public void shouldOpenAddnewDialogOnAddClick()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.assertVisible();
    }


    @Test
    public void shouldAddNewItem()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(NEW_URL).submit();
        webListViewPage().assertUrlDisplaying(NEW_URL);
    }

    @Test
    public void shouldFullfillUrlOnadd()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter("www.test.com").submit();
        webListViewPage().assertUrlDisplaying("http://www.test.com");
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldNotAddOnCancelClick()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(NEW_URL).cancel();
        webListViewPage().assertUrlDisplaying(NEW_URL);
    }

    @Test
    public void shouldNewlyAddedItemAppearInList() throws InterruptedException {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(NEW_URL).submit();
        webListViewPage().assertUrlDisplaying(NEW_URL);
    }
    
    @Test
    public void shouldShowErrorMsg()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(ANY_URL).submit();
        webListViewPage().assertErrorDisplaying(R.string.already_added);
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

    private WebSiteInfo convert(String url) {
        return WebSiteInfo.of(url , "");
    }

}