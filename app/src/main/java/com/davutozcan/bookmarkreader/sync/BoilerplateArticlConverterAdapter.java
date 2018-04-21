package com.davutozcan.bookmarkreader.sync;

import com.davutozcan.bookmarkreader.domainmodel.IArticleExtractor;
import com.davutozcan.bookmarkreader.articlelib.BoilerplateArticleConverter;

/**
 * Created by davut on 12/19/2017.
 */

public class BoilerplateArticlConverterAdapter implements IArticleExtractor {
    @Override
    public String convert(String html) {
        return new BoilerplateArticleConverter().convert(html);
    }
}
