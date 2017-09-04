package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.article.IArticleRepository;
import com.codezilla.bookmarkreader.domainmodel.IRealmFacade;
import com.codezilla.bookmarkreader.domainmodel.RealmFacade;

/**
 * Created by davut on 9/2/2017.
 */

class ArticleRepositoryAdapter implements IArticleRepository {
    private final IRealmFacade realmFacade;

    public ArticleRepositoryAdapter(IRealmFacade realmFacade) {
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
