package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.article.IArticleRepository;
import com.codezilla.bookmarkreader.article.IArticleService;
import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;
import com.codezilla.bookmarkreader.domainmodel.WebUnitContent;

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
    public IArticleService.ArticleDetail getArticle(String url) {
        WebUnitContent wuc =  realmFacade.getWebUnit(url).getLatestContent();
        return new IArticleService.ArticleDetail(wuc.getContent() , wuc.getUrl());
    }
}
