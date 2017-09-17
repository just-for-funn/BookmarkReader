package com.codezilla.bookmarkreader.domainmodel;

/**
 * Created by davut on 9/5/2017.
 */

public interface IHtmlComparer {
    int compare(String oldContent, String newContent);
    String change();
}
