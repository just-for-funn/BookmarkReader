package com.codezilla.bookmarkreader.application;

import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by davut on 9/16/2017.
 */
@RunWith(AndroidJUnit4.class)
public class WeblistServiceAdapterTest {
    public static final String URL = "www.test.com";
    WeblistServiceAdapter weblistServiceAdapter;
    @Mock
    IWebUnitRepository repository;
    @Captor
    ArgumentCaptor<WebUnit> captor = ArgumentCaptor.forClass(WebUnit.class);
    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        weblistServiceAdapter = new WeblistServiceAdapter(repository);
        when(repository.exists(URL)).thenReturn(false);
    }


    @Test
    public void shouldAddGivenUrl()
    {
        weblistServiceAdapter.add(URL);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(URL));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }




    @Test
    public void shouldExtractCorrectFaviconUrlWhenUrlIsSubUrl()
    {
        String url = URL+"/some/inner/directory?q=0";
        weblistServiceAdapter.add(url);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(url));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }

    @Test
    public void shouldGetFaviconIconUrlCorrectlyWhenUrlQueryExists()
    {
        String url = URL+"?q=0";
        weblistServiceAdapter.add(url);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(url));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }

    @Test
    public void shouldGetFaviconIconUrlCorrectlyWhenUrlPathParamsExists()
    {
        String url = URL+"/abc/def/gth";
        weblistServiceAdapter.add(url);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(url));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }

}