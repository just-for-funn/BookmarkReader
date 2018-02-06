package com.codezilla.bookmarkreader.domainmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by davut on 9/5/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WebUnitContentUpdaterTest
{
    public static final String URL_1 = "www.sample.com";
    public static final String URL_2 = "www.sample_2.com";
    public static final String OLD_CONTENT = "old content";
    public static final String NEW_CONTENT = "new content";
    public static final String CONTENT_CHANGE = "CONTENT_CHANGE";
    public static final String FAVICON_URL = "www.test.com/favicon.ico";


    @Mock
    IWebUnitRepository realmFacade;

    @Mock
    IHttpClient httpClient;

    @Mock
    IHtmlComparer httpComparer;
    @Mock
    ILogRepository logRepository;

    @Mock
    IFaviconExtractor faviconExtractor;

    @Mock
    IUpdateListener updateListener;

    WebUnitContentUpdater webUnitContentUpdater;
    ArgumentCaptor<WebUnit> webUnitArgumentCaptor = ArgumentCaptor.forClass(WebUnit.class);
    List<WebUnit> webUnits = Arrays.asList(of(URL_1) , of(URL_2));
    private WebUnit webUnit;

    private WebUnit of(String url) {
        WebUnit webUnit = new WebUnit();
        webUnit.setUrl(url);
        webUnit.setLatestContent(new WebUnitContent());
        return webUnit;
    }

    @Before
    public void before()
    {
        mockSingleElement();
        when(httpClient.getHtmlContent(URL_1)).thenReturn(NEW_CONTENT);
        when(httpComparer.isChanged(anyString() , anyString())).thenReturn(true);
        when(httpComparer.newLines()).thenReturn(asTextBlock(CONTENT_CHANGE));
        webUnitContentUpdater = new WebUnitContentUpdater(new UpdateContext(httpClient, realmFacade, httpComparer, logRepository, faviconExtractor, updateListener));

    }

    private List<TextBlock> asTextBlock(String contentChange) {
        return Arrays.asList(TextBlock.textBlock(contentChange));
    }

    @Test
    public void shouldDownloadHtmlContentForEachWebUnit()
    {
        when(realmFacade.webUnits()).thenReturn(webUnits);
        webUnitContentUpdater.updateAll();
        Mockito.verify(httpClient).getHtmlContent(URL_1);
        Mockito.verify(httpClient).getHtmlContent(URL_2);
    }



    @Test
    public void shouldCompareGivenComponents()
    {
        webUnitContentUpdater.updateAll();
        Mockito.verify(httpComparer).isChanged(OLD_CONTENT , NEW_CONTENT);
    }

    @Test
    public void shouldCompareWithEmptysStringWhenLastContentIsNull()
    {
        realmFacade.webUnits().get(0).setLatestContent(null);
        webUnitContentUpdater.updateAll();
        verify(httpComparer).isChanged("" , NEW_CONTENT);
    }

    @Test
    public void shouldUpdateContentOnChange()
    {

        webUnitContentUpdater.updateAll();
        Mockito.verify(realmFacade).update(webUnitArgumentCaptor.capture());
        assertThat(webUnitArgumentCaptor.getValue().getLatestContent().getContent() , equalTo(NEW_CONTENT) );
    }

    private void mockSingleElement() {
        this.webUnit = of(URL_1);
        WebUnitContent wuc = new WebUnitContent();
        wuc.setContent(OLD_CONTENT);
        webUnit.setLatestContent(wuc);
        webUnit.setStatus(WebUnit.Status.ALREADY_READ);
        when(realmFacade.webUnits()).thenReturn(Arrays.asList(webUnit));
        when(realmFacade.getWebUnit(URL_1)).thenReturn(webUnit);
    }

    @Test
    public void shouldNotUpdateIfNotChanged()
    {
        when(httpComparer.isChanged(anyString() , anyString())).thenReturn(false);
        webUnitContentUpdater.updateAll();
        Mockito.verify(realmFacade).update(webUnitArgumentCaptor.capture());
        assertThat(webUnitArgumentCaptor.getValue().getLatestContent().getContent() , equalTo(OLD_CONTENT) );
    }


    @Test
    public void shouldUpdateFaviconIfExists()
    {
        when(faviconExtractor.faviconUrl(anyString(), anyString())).thenReturn(FAVICON_URL);
        webUnitContentUpdater.updateAll();
        verify(realmFacade).update(webUnitArgumentCaptor.capture());
        assertThat(webUnitArgumentCaptor.getValue().getFaviconUrl() , equalTo(FAVICON_URL));
    }

    @Test
    public void shouldNotUpdateFaviconIFNotExists()
    {
        String existingFaviconUrl = "www.test.com/oldfavicon.ico";
        webUnit.setFaviconUrl(existingFaviconUrl);
        when(faviconExtractor.faviconUrl(anyString(), anyString())).thenReturn(null);
        webUnitContentUpdater.updateAll();
        verify(realmFacade).update(webUnitArgumentCaptor.capture());
        assertThat(webUnitArgumentCaptor.getValue().getFaviconUrl() , equalTo(existingFaviconUrl));
    }

    @Test
    public void shouldUpdateChange()
    {
        webUnitContentUpdater.updateAll();
        Mockito.verify(realmFacade).update(webUnitArgumentCaptor.capture());
        WebUnit wu = webUnitArgumentCaptor.getValue();
        assertThat(wu.getChange().getNewBlocks() , hasSize(1));
        assertThat(wu.getChange().getNewBlocks().get(0).lines() , hasItems(CONTENT_CHANGE));
    }

    @Test
    public void shouldCallbackUpdateListener()
    {
        webUnitContentUpdater.updateAll();
        Mockito.verify(updateListener).onStart(any(WebUnit.class));
        Mockito.verify(updateListener).onComplete(any(WebUnit.class));
    }

    @Test
    public void shouldCallbackUpdateListenerOnFail()
    {
        when(httpClient.getHtmlContent(URL_1)).thenThrow(new RuntimeException());
        webUnitContentUpdater.updateAll();
        Mockito.verify(updateListener).onStart(any(WebUnit.class));
        Mockito.verify(updateListener).onFail(any(WebUnit.class));
    }

    @Test
    public void shouldNotUpdateRemainingElementsWhenStopped()
    {
        when(httpClient.getHtmlContent(URL_1)).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                webUnitContentUpdater.stop();
                return "";
            }
        });
        webUnitContentUpdater.updateAll();
        verify(httpClient , times(1)).getHtmlContent(anyString());
    }

    @Test
    public void shouldUpdateStatusWhenNewContentAvailable() {
        assertEquals(realmFacade.getWebUnit(URL_1).getStatus() , WebUnit.Status.ALREADY_READ);
        webUnitContentUpdater.updateAll();
        assertEquals(realmFacade.getWebUnit(URL_1).getStatus() , WebUnit.Status.HAS_NEW_CONTENT);
    }

    @Test
    public void shouldUpdatePreviousContent()
    {
        WebUnitContent wuc = new WebUnitContent();
        wuc.setContent("old-content");
        this.webUnit.setLatestContent(wuc);
        assertNull(realmFacade.getWebUnit(URL_1).getPreviousContent());
        webUnitContentUpdater.updateAll();
        assertThat(webUnit.getPreviousContent().getContent() ,equalTo("old-content") );
    }
}