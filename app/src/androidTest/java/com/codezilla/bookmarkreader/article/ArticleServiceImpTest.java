package com.codezilla.bookmarkreader.article;

import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.exception.DomainException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
/**
 * Created by davut on 9/2/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ArticleServiceImpTest
{
    public static final String ANY_URL = "www.sample.com";
    public static final IArticleService.ArticleDetail ANY_ARTICLE = new IArticleService.ArticleDetail( "article for sample.com" , "www.sample.com");
    @Mock
    IArticleRepository articleRepository;

    @Mock
    IArticleLoader loader;

    ArticleServiceImp service;
    @Before
    public void before()
    {
       MockitoAnnotations.initMocks(this);
       service = new ArticleServiceImp(articleRepository , loader);
    }


    @Test
    public void shouldGetArticleFromDatabaseIfExists()
    {
        when(articleRepository.hasArticle(ANY_URL)).thenReturn(true);
        Mockito.when(articleRepository.getArticle(ANY_URL)).thenReturn(ANY_ARTICLE);
        assertThat(service.getArticle(ANY_URL) , equalTo(ANY_ARTICLE) );
    }


    @Test
    public void shouldLoadFirstIfArticleNotFetched()
    {
        when(articleRepository.hasArticle(ANY_URL)).thenReturn(false);
        when(articleRepository.getArticle(ANY_URL)).thenReturn(ANY_ARTICLE);
        service.getArticle(ANY_URL);
        verify(loader).load(ANY_URL);
    }



    @Test(expected = DomainException.class)
    public void shouldThrowExceptionWhenArticleNotExistInDbAndCannotLoad()
    {
        when(articleRepository.hasArticle(ANY_URL)).thenReturn(true);
        when(articleRepository.getArticle(ANY_URL)).thenReturn(null);
        service.getArticle(ANY_URL);
    }
}