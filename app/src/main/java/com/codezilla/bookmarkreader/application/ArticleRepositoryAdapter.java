package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.article.IArticleRepository;
import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;

/**
 * Created by davut on 9/2/2017.
 */

class ArticleRepositoryAdapter implements IArticleRepository {
    private final IWebUnitRepository realmFacade;

    public ArticleRepositoryAdapter(IWebUnitRepository realmFacade) {
        this.realmFacade = realmFacade;
    }

    @Override
    public boolean hasArticle(String url) {
        return realmFacade.getWebUnit(url).getLatestContent() != null;
    }

    @Override
    public String getArticle(String url) {
        return realmFacade.getWebUnit(url).getLatestContent().getContent();
    }
}
