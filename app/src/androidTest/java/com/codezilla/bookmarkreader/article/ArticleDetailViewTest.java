package com.codezilla.bookmarkreader.article;

import android.support.test.espresso.web.assertion.WebViewAssertions;
import android.support.test.rule.ActivityTestRule;

import com.codezilla.bookmarkreader.MainActivity;
import com.codezilla.bookmarkreader.MainActivityPage;
import com.codezilla.bookmarkreader.MainActivityTestBase;
import com.codezilla.bookmarkreader.weblist.IWebListService;
import com.codezilla.bookmarkreader.weblist.WebSiteInfo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.w3c.dom.Document;

import java.util.Arrays;

import static android.support.test.espresso.web.sugar.Web.onWebView;
import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static org.junit.Assert.*;
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
        when(articleService.getArticleDetail(anyString())).thenReturn(ANY_CONTENT);
        launch();
        getMainActivityPage()
                .webListPage()
                .clickItem(webSiteInfo.getUrl())
                .assertArticleDisplaying(ANY_CONTENT);

    }
}