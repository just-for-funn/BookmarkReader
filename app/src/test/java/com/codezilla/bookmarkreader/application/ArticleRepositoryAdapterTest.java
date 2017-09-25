package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;
import com.codezilla.bookmarkreader.domainmodel.WebUnitContent;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by davut on 9/2/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArticleRepositoryAdapterTest {
    public static final String ANY_URL = "www.test.com";
    public static final String ANY_CONTENT = "some content";
    @Mock
    IWebUnitRepository realmFacade;
    ArticleRepositoryAdapter repositoryAdapter;
    WebUnit webUnit = new WebUnit();
    @Before
    public void before()
    {
        repositoryAdapter = new ArticleRepositoryAdapter(realmFacade);
        Mockito.when(realmFacade.getWebUnit(anyString())).thenReturn(webUnit);
    }

    @Test
    public void shouldReturnFalseWhenArticleIsNull()
    {
        webUnit.setLatestContent(null);
        assertThat(repositoryAdapter.hasArticle(ANY_URL) , CoreMatchers.is(false));
    }

    @Test
    public void shouldReturnTrueWhenContentexists()
    {
        WebUnitContent webUnitContent = new WebUnitContent();
        webUnitContent.setContent(ANY_CONTENT);
        webUnit.setLatestContent(webUnitContent);
        assertThat(repositoryAdapter.hasArticle(ANY_URL) , is(true));
    }

    @Test
    public void shouldReturnContent()
    {
        WebUnitContent webUnitContent = new WebUnitContent();
        webUnitContent.setContent(ANY_CONTENT);
        webUnit.setLatestContent(webUnitContent);
        assertThat(repositoryAdapter.getArticle(ANY_URL).getContent() , is(ANY_CONTENT));
    }
}