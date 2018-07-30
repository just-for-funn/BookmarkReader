package com.davutozcan.bookmarkreader.article;


import com.davutozcan.bookmarkreader.MainActivityPage;
import com.davutozcan.bookmarkreader.MainActivityTestBase;
import com.davutozcan.bookmarkreader.article.ArticleDetailPage;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.domainmodel.WebUnitContent;
import com.davutozcan.bookmarkreader.exception.DomainException;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;


/**
 * Created by davut on 8/3/2017.
 */
public class ArticleDetailViewTest extends MainActivityTestBase
{
    public static final String ANY_URL = "www.test.url";
    public static final String ANY_SUMMARY = "Some summary";
    public static final String ANY_ERROR = "error message";




    @Before
    public void setup()
    {
        WebUnit webUnit = new WebUnit();
        WebUnitContent wuc = new WebUnitContent();
        wuc.setContent(ANY_SUMMARY);
        wuc.setUrl(ANY_URL);
        wuc.setDate(new Date());
        webUnit.setLatestContent(wuc);
        webUnit.setStatus(WebUnit.Status.HAS_NEW_CONTENT);
        webUnit.setUrl(ANY_URL);
        myApp().getRealmFacade().add(webUnit);
    }

    @Test
    public void shouldDisplayArticleViewWhenClickedToItemList() throws InterruptedException {
        launch();
        ArticleDetailPage articleDetailPage =  getMainActivityPage().webListPage().clickItem(ANY_URL);
        articleDetailPage.assertVisible();
    }


    @Test
    public void shouldReturnBackToMainMenuOnBackClicked()
    {
        launch();
        ArticleDetailPage articleDetailPage = getMainActivityPage().webListPage().clickItem(ANY_URL);
        MainActivityPage mainActivityPage =  articleDetailPage.back();
        mainActivityPage.webListPage().assertNotContentDisplaying();
    }


    @Test
    public void shouldDisplayCorrectArticle()
    {
        launch();
        getMainActivityPage()
                .webListPage()
                .clickItem(ANY_URL)
                .assertArticleDisplaying(ANY_SUMMARY);

    }



    @Test
    public void shouldShowErrorMessageWhenArticleServiceThrowsDomainExcception()
    {
        myApp().setArticleService(url -> {
            throw new DomainException(ANY_ERROR);
        });
        launch();
        getMainActivityPage()
                .webListPage()
                .clickItem(ANY_URL)
                .assertErrorDisplaying("Cannot getSummaryFor article");
    }

    @Test
    public void shouldSwitchToArticleView()
    {
        launch();
        getMainActivityPage()
                .webListPage()
                .clickItem(ANY_URL)
                .clickSwitch()
                .assertHtmlViewNotDisplaying()
                .assertArticleViewDisplaying();
    }

}