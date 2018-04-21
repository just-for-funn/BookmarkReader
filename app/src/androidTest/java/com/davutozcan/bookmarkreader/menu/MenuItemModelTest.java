package com.davutozcan.bookmarkreader.menu;

import android.support.test.runner.AndroidJUnit4;

import com.davutozcan.bookmarkreader.MainActivityTestBase;
import com.davutozcan.bookmarkreader.domainmodel.ILogRepository;
import com.davutozcan.bookmarkreader.domainmodel.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by davut on 9/18/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MenuItemModelTest extends MainActivityTestBase{

    public static final String INFO_LOG = "info log";
    public static final String ERROR_LOG = "error log";
    public static final String WARNING_LOG = "warning log";
    @Mock
    ILogRepository logRepository;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        when(logRepository.logs()).thenReturn(
                Arrays.asList(new Log(INFO_LOG, Log.INFO , now()),
                new Log(ERROR_LOG, Log.ERROR, now()),
                new Log(WARNING_LOG, Log.WARNING , now()))
        );
        myApp().setLogRepository(logRepository);
    }

    private Date now() {
        return new Date(System.currentTimeMillis());
    }

    @Test
    public void shouldDisplayHistoryView()
    {
        launch();
        mainActivityPage
                .clickToggeButton()
                .clickHistory()
                .assertDisplaying();
    }

    @Test
    public void shouldDisplayLogs()
    {
        launch();
        HistoryPage hip =  mainActivityPage.clickToggeButton().clickHistory();
                hip.assertLogDisplaying(INFO_LOG)
                .assertLogDisplaying(WARNING_LOG)
                .assertLogDisplaying(ERROR_LOG);
    }
}