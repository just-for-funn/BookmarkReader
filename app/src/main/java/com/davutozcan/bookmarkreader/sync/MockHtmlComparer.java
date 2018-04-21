package com.davutozcan.bookmarkreader.sync;

import com.davutozcan.bookmarkreader.domainmodel.IHtmlComparer;
import com.davutozcan.bookmarkreader.domainmodel.TextBlock;

import java.util.Collections;
import java.util.List;

/**
 * Created by davut on 9/9/2017.
 */

public class MockHtmlComparer implements IHtmlComparer {
    @Override
    public boolean isChanged(String oldContent, String newContent) {
        return oldContent.compareTo(newContent)!=0;
    }

    @Override
    public List<TextBlock> newLines()
    {
        return Collections.emptyList();
    }
}
