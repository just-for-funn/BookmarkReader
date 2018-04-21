package com.davutozcan.bookmarkreader.sync;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by davut on 9/17/2017.
 */

public class FaviconExtractorTest {
    FaviconExtractor faviconExtractor = new FaviconExtractor();




    @Test
    public void shouldReturnNullIfContetnIsNullOrEmpty()
    {
        assertThat(faviconExtractor.faviconUrl("",null) , nullValue());
        assertThat(faviconExtractor.faviconUrl("","") , nullValue());
    }


    @Test
    public void shouldReturnGivenShortCutUrlFromHtml() throws IOException {
        String html = resource("twitter.html");
        String faviconIco = faviconExtractor.faviconUrl("",html);
        assertThat(faviconIco , equalTo("https://abs.twimg.com/favicons/favicon.ico"));
    }


    @Test
    public void shouldGetUrlIfLinkRefNameIsIcon() throws IOException {
        String html = resource("AndroidExperiments.html");
        String faviconIco = faviconExtractor.faviconUrl("https://experiments.withgoogle.com", html);
        assertThat(faviconIco , equalTo("https://experiments.withgoogle.com/assets/img/favicon.png"));
    }

    @Test
    public void shouldPrependhttpIfUrlStartsWithDoubleSlashes() throws IOException {
        String html = resource("ntvspor.html");
        String faviconIco = faviconExtractor.faviconUrl("https://www.ntvspor.com", html);
        assertThat(faviconIco , equalTo("http://cdn3.ntvspor.net/Content/dist/img/favicon/android-icon-192x192.png"));
    }


    private String resource(String resource) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        try(ByteArrayOutputStream result = new ByteArrayOutputStream())
        {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }
    }
}