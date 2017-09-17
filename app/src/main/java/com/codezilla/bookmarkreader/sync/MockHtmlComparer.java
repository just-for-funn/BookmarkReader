package com.codezilla.bookmarkreader.sync;

import com.codezilla.bookmarkreader.domainmodel.IHtmlComparer;

/**
 * Created by davut on 9/9/2017.
 */

public class MockHtmlComparer implements IHtmlComparer {
    @Override
    public int compare(String oldContent, String newContent) {
        return oldContent.compareTo(newContent);
    }

    @Override
    public String change() {
        return "Not Implemented Yet...";
    }
}
