package com.codezilla.bookmarkreader.domainmodel;

/**
 * Created by davut on 9/3/2017.
 */

public interface IHttpClient {
    String getHtmlContent(String url);
    String url();
}
