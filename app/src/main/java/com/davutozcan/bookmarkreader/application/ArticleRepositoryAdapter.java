package com.davutozcan.bookmarkreader.application;

import com.davutozcan.bookmarkreader.article.IArticleRepository;
import com.davutozcan.bookmarkreader.article.IArticleService;
import com.davutozcan.bookmarkreader.domainmodel.IWebUnitRepository;
import com.davutozcan.bookmarkreader.domainmodel.WebUnitContent;

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
