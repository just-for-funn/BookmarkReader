package com.codezilla.bookmarkreader.application;

import org.hamcrest.text.IsEmptyString;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
/**
 * Created by davut on 9/3/2017.
 */
public class OkHttpClientImpTest {
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
        String content = client.getHtmlContent("http://www.google.com");
        assertThat(content , not(isEmptyOrNullString()));
    }

}