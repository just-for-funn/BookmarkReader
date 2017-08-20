package com.codezilla.bookmarkreader.weblist;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.MainActivity;
import com.codezilla.bookmarkreader.MainActivityTestBase;
import com.codezilla.bookmarkreader.application.BookmarkReaderApplication;

import org.awaitility.core.ThrowingRunnable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codezilla.bookmarkreader.test.TestExtensions.untilAsserted;
import static junit.framework.Assert.fail;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.pollInSameThread;

/**
 * Created by davut on 7/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class WebListViewModelTest  extends MainActivityTestBase{
    public static final String ANY_URL = "www.mocksite.com";
    public static final String ANY_SUMMARY = "Summary of mock site";

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
        Mockito.when(webListService.getWebSitesInfos()).thenReturn(staticWebSites);
        launch();
        webListViewPage().assertUrlDisplaying(ANY_URL);
    }


}