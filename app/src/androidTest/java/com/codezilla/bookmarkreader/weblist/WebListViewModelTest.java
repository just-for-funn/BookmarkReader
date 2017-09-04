package com.codezilla.bookmarkreader.weblist;

import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.MainActivityTestBase;
import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.application.BookmarkReaderApplication;
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
import java.util.List;

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
    public static final String ANY_URL = "www.mocksite.com";
    public static final String ANY_SUMMARY = "Summary of mock site";
    public static final String ANY_NEW_URL = "http://www.newurl.com";
    public static final String NEW_URL = ANY_NEW_URL;

    @Mock
    IWebListService webListService;
    List<WebSiteInfo> staticWebSites = new ArrayList<>();

    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        BookmarkReaderApplication.getInstance().setWebListService(webListService);
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
        staticWebSites = Arrays.asList(WebSiteInfo.of( ANY_URL , ANY_SUMMARY));
        when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        launch();
        webListViewPage().assertUrlDisplaying(ANY_URL);
    }

    @Test
    public void shouldOpenAddnewDialogOnAddClick()
    {
        staticWebSites = Arrays.asList(WebSiteInfo.of( ANY_URL , ANY_SUMMARY));
        when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.assertVisible();
    }


    @Test
    public void shouldAddNewItem()
    {
        staticWebSites = Arrays.asList(WebSiteInfo.of( ANY_URL , ANY_SUMMARY));
        when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(NEW_URL).submit();
        verify(webListService).add(ANY_NEW_URL);
    }

    @Test
    public void shouldFullfillUrlOnadd()
    {
        staticWebSites = Arrays.asList(WebSiteInfo.of( ANY_URL , ANY_SUMMARY));
        when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter("www.test.com").submit();
        verify(webListService).add("http://www.test.com");
    }
    
    @Test
    public void shouldNotAddOnCancelClick()
    {
        staticWebSites.add(WebSiteInfo.of( ANY_URL , ANY_SUMMARY));
        when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(NEW_URL).cancel();
        verify(webListService , times(0)).add(anyString());
    }

    @Test
    public void shouldNewlyAddedItemAppearInList() throws InterruptedException {
        staticWebSites.add(WebSiteInfo.of( ANY_URL , ANY_SUMMARY));
        when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        Mockito.doAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                staticWebSites.add(convert((String)invocation.getArgument(0)));
                return true;
            }
        }).when(webListService).add(anyString());
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(NEW_URL).submit();
        webListViewPage().assertUrlDisplaying(NEW_URL);
    }
    
    @Test
    public void shouldShowErrorMsg()
    {
        staticWebSites.add(WebSiteInfo.of( ANY_URL , ANY_SUMMARY));
        when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new RecordExistsException();
            }
        }).when(webListService).add(anyString());
        launch();
        AddNewSitepage addNewSitepage =  webListViewPage().clickAdd();
        addNewSitepage.enter(ANY_URL).submit();
        webListViewPage().assertErrorDisplaying(R.string.already_added);
    }



    private WebSiteInfo convert(String url) {
        return WebSiteInfo.of(url , "");
    }

}