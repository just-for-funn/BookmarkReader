package com.codezilla.bookmarkreader.sync;

import com.codezilla.bookmarkreader.domainmodel.IArticleExtractor;
import com.smartarticlereader.article.BoilerplateArticleConverter;

/**
 * Created by davut on 12/19/2017.
 */

public class BoilerplateArticlConverterAdapter implements IArticleExtractor {
    @Override
    public String convert(String html) {
        return new BoilerplateArticleConverter().convert(html);
    }
}
