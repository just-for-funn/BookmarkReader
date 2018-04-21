package com.davutozcan.bookmarkreader.domainmodel;

/**
 * Created by davut on 9/17/2017.
 */

public interface IFaviconExtractor {
    String faviconUrl(String url , String html);
}
