package com.codezilla.bookmarkreader.domainmodel;

import java.util.List;

/**
 * Created by davut on 9/5/2017.
 */

public interface IHtmlComparer {
    boolean isChanged(String oldContent, String newContent);
    List<TextBlock> newLines();


}
