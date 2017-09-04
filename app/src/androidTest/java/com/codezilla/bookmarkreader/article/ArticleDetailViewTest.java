package com.codezilla.bookmarkreader.article;

import com.codezilla.bookmarkreader.MainActivityPage;
import com.codezilla.bookmarkreader.MainActivityTestBase;
import com.codezilla.bookmarkreader.exception.DomainException;
import com.codezilla.bookmarkreader.weblist.IWebListService;
import com.codezilla.bookmarkreader.weblist.WebSiteInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static android.support.test.espresso.web.sugar.Web.onWebView;
import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by davut on 8/3/2017.
 */
public class ArticleDetailViewTest extends MainActivityTestBase
{
    public static final String ANY_URL = "www.test.url";
    public static final String ANY_SUMMARY = "Some summary";
    public static final String ANY_CONTENT = "Non text article";
    public static final String ANY_ERROR = "error message";
    @Mock
    IWebListService webListService;
    @Mock
    IArticleService articleService;
    WebSiteInfo webSiteInfo;
    @Before
    public void before()
    {
        webSiteInfo = new WebSiteInfo();
        webSiteInfo.setUrl(ANY_URL);
        webSiteInfo.setSummary(ANY_SUMMARY);
        webSiteInfo.setStatus(WebSiteInfo.Status.CHANGED);
        initMocks(this);
        when(webListService.getWebSitesInfos()).thenReturn(Arrays.asList(webSiteInfo));
        myApp().setWebListService(webListService);
        myApp().setArticleService(articleService);
    }

    @Test
    public void shouldDisplayArticleViewWhenClickedToItemList() throws InterruptedException {
        launch();
        ArticleDetailPage articleDetailPage =  getMainActivityPage().webListPage().clickItem(webSiteInfo.getUrl());
        articleDetailPage.assertVisible();
    }


    @Test
    public void shouldReturnBackToMainMenuOnBackClicked()
    {
        launch();
        ArticleDetailPage articleDetailPage = getMainActivityPage().webListPage().clickItem(webSiteInfo.getUrl());
        MainActivityPage mainActivityPage =  articleDetailPage.back();
        mainActivityPage.webListPage().assertUrlDisplaying(webSiteInfo.getUrl());
    }


    @Test
    public void shouldDisplayCorrectArticle()
    {
        when(articleService.getArticle(anyString())).thenReturn(ANY_CONTENT);
        launch();
        getMainActivityPage()
                .webListPage()
                .clickItem(webSiteInfo.getUrl())
                .assertArticleDisplaying(ANY_CONTENT);

    }



    @Test
    public void shouldShowErrorMessageWhenArticleServiceThrowsDomainExcception()
    {
        when(articleService.getArticle(anyString())).then(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                throw new DomainException(ANY_ERROR);
            }
        });
        launch();
        getMainActivityPage()
                .webListPage()
                .clickItem(webSiteInfo.getUrl())
                .assertErrorDisplaying(ANY_ERROR);
    }

}