package com.codezilla.bookmarkreader.application;

import android.content.Context;

import com.codezilla.bookmarkreader.domainmodel.IRealmFacade;
import com.codezilla.bookmarkreader.exception.DomainException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Created by davut on 9/3/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArticleLoaderImpTest {
    public static final String ANY_URL = "www.test.com";
    public static final String ANY_NON_EMPTY_CONTENT = "article content";
    @Mock
    IRealmFacade realmFacade;
    @Mock
    IHttpClient httpClient;
    @Mock
    Context context;
    ArticleLoaderImp articleLoaderImp;

    @Before
    public void init()
    {
        articleLoaderImp = new ArticleLoaderImp(realmFacade , httpClient,context);
        when(context.getString(anyInt())).thenReturn("message");
    }

    @Test(expected = DomainException.class)
    public void shouldThrowDomainExceptionOnHttpConnectionFailure()
    {
        when(httpClient.getHtmlContent(anyString())).then(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                throw new RuntimeException();
            }
        });
        articleLoaderImp.load(ANY_URL);
    }

    @Test(expected = DomainException.class)
    public void shouldThrowDomainExceptionWhenContentIsNullOrEmpty()
    {
        when(httpClient.getHtmlContent(ANY_URL)).thenReturn("");
        articleLoaderImp.load(ANY_URL);
    }

    @Test
    public void shouldSetRelatedArticleOnLoad()
    {
        when(httpClient.getHtmlContent(anyString())).thenReturn(ANY_NON_EMPTY_CONTENT);
        articleLoaderImp.load(ANY_URL);
        verify(realmFacade).addSiteContent(ANY_URL , ANY_NON_EMPTY_CONTENT);
    }
}