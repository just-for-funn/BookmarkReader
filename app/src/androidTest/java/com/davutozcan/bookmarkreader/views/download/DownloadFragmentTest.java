package com.davutozcan.bookmarkreader.views.download;

import com.davutozcan.bookmarkreader.MainActivityTestBase;
import com.davutozcan.bookmarkreader.domainmodel.IWebUnitRepository;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.weblist.IWebListService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 1/14/2018.
 */
public class DownloadFragmentTest extends MainActivityTestBase
{
    public static final String GOOGLE = "https://www.google.com";
    public static final String TWITTER = "https://twitter.com/";
    public static final String FACEBOOOK = "https://facebook.com/";

    @Before
    public void  setUp()
    {
        myApp().getRealmFacade().clearWebUnits();
    }

    @After
    public void after()
    {
        myApp().getRealmFacade().clearWebUnits();
    }

    @Test
    public void shouldOpenDownlaodPage()
    {
        launch();
        DownloadPage downloadPage =  getMainActivityPage()
                .clickToggeButton()
                .clickDownloadAll();
        downloadPage.assertDisplaying();
    }

    @Test
    public void shouldDownloadSites()
    {
        addSites(GOOGLE, TWITTER);
        launch();
        DownloadPage downloadPage =  getMainActivityPage()
                .clickToggeButton()
                .clickDownloadAll();
        downloadPage
                .clickStart()
                .assertCompletedCount(2)
                .assertNewCount(2);
    }

    @Test
    public void shouldSetFailedCount()
    {
        addSites("a" , "b" , "c");
        launch();
        getMainActivityPage().clickToggeButton()
                .clickDownloadAll()
                .clickStart()
                .assertFailedCount(3)
                .assertTotalCount(3);
    }


    @Test
    public void shouldDisplayDownloadingItem()
    {

        addSites(TWITTER);
        launch();
        getMainActivityPage()
                .clickToggeButton()
                .clickDownloadAll()
                .clickStart()
                .assertTextDisplaying(TWITTER);
    }


    private void addSites(String ... urls)
    {
        for (String url:urls) {
            myApp().getRealmFacade().add(asWebUnit(url));
        }
    }

    private WebUnit asWebUnit(String url)
    {
        WebUnit webUnit = new WebUnit();
        webUnit.setUrl(url);
        return webUnit;
    }

    @Test
    public void shouldFixButtonStateAfterFinish()
    {
        addSites(GOOGLE , TWITTER,"a","b","c");
        launch();
        getMainActivityPage()
                .clickToggeButton()
                .clickDownloadAll()
                .clickStart()
                .assertText("Start");
    }
}