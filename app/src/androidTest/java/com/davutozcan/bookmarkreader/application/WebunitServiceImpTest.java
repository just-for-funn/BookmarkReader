package com.davutozcan.bookmarkreader.application;

import android.support.test.runner.AndroidJUnit4;

import com.davutozcan.bookmarkreader.domainmodel.IArticleExtractor;
import com.davutozcan.bookmarkreader.domainmodel.IWebUnitRepository;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by davut on 9/16/2017.
 */
@RunWith(AndroidJUnit4.class)
public class WebunitServiceImpTest {
    public static final String URL = "www.test.com";
    WebunitServiceImp webunitServiceImp;
    @Mock
    IWebUnitRepository repository;
    @Mock
    IArticleExtractor extractor;
    @Captor
    ArgumentCaptor<WebUnit> captor = ArgumentCaptor.forClass(WebUnit.class);
    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        webunitServiceImp = new WebunitServiceImp(repository , extractor);
        when(repository.exists(URL)).thenReturn(false);
    }


    @Test
    public void shouldAddGivenUrl()
    {
        webunitServiceImp.add(URL);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(URL));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }




    @Test
    public void shouldExtractCorrectFaviconUrlWhenUrlIsSubUrl()
    {
        String url = URL+"/some/inner/directory?q=0";
        webunitServiceImp.add(url);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(url));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }

    @Test
    public void shouldGetFaviconIconUrlCorrectlyWhenUrlQueryExists()
    {
        String url = URL+"?q=0";
        webunitServiceImp.add(url);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(url));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }

    @Test
    public void shouldGetFaviconIconUrlCorrectlyWhenUrlPathParamsExists()
    {
        String url = URL+"/abc/def/gth";
        webunitServiceImp.add(url);
        verify(repository).add(captor.capture());
        assertThat(captor.getValue().getUrl() , equalTo(url));
        assertThat(captor.getValue().getFaviconUrl() , equalTo(URL+"/favicon.ico"));
    }

    @Test
    public void shouldSetSummaryAsNotEvaluatedYetWhenNoDownloadAvailable()
    {
        WebUnit wu = new WebUnit();
        wu.setPreviousContent( null);
        wu.setLatestContent(null);
        wu.setUrl( "test");
        when(repository.webUnits()).thenReturn(Arrays.asList(wu));
        String summary =  webunitServiceImp.getSummaryFor(webunitServiceImp.getWebSitesInfos().get(0).getUrl());
        assertThat(summary , equalTo("Content not downloaded yet"));
    }


}