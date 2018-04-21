package com.davutozcan.bookmarkreader.share;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShareActivityTest {
    ShareActivity sa = new ShareActivity();


    @Test
    public void shouldMatchUrls()
    {
        assertFalse(sa.isUrl("invalid"));
        assertFalse(sa.isUrl(null));
        assertFalse(sa.isUrl(""));
        assertFalse(sa.isUrl("123455"));
    }

    @Test
    public void shouldMatchValidUrls()
    {
        assertTrue(sa.isUrl("https://www.abc.com"));
        assertTrue(sa.isUrl("http://www.abc.com"));
    }

    @Test
    public void shouldAcceptUrlsWithoutProtocol()
    {
        assertFalse(sa.isUrl("www.abc.com"));
    }



}