package com.codezilla.bookmarkreader.application;

import org.hamcrest.text.IsEmptyString;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
/**
 * Created by davut on 9/3/2017.
 */
public class OkHttpClientImpTest {
    public static final String A_URL_REDIRECTS = "http://www.androidexperiments.com";
    public static final String REDIRECTED_URL = "https://experiments.withgoogle.com/android";
    public static final String NON_REDIRECT_URL = "https://httpbin.org/";
    OkHttpClientImp client = new OkHttpClientImp();

    @Test(expected = RuntimeException.class)
    public void shouldThrowExcepitonOnConnectionFailure()
    {
        client.getHtmlContent("someinvalidchar");
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenUrlIsNull()
    {
        client.getHtmlContent(null);
    }

    @Test
    public void shouldGetWebContentWhenUrlIsValid()
    {
        String content = client.getHtmlContent(NON_REDIRECT_URL);
        assertThat(content , not(isEmptyOrNullString()));
    }

    @Test
    public void shouldReturnRedirectedUrlWhenRedicted()
    {
        client.getHtmlContent(A_URL_REDIRECTS);
        assertThat(client.url() , equalTo(REDIRECTED_URL));
    }

    @Test
    public void shouldReturnUrl()
    {
        client.getHtmlContent(NON_REDIRECT_URL);
        assertThat(client.url() , equalTo(NON_REDIRECT_URL));
    }

}