package com.codezilla.bookmarkreader.article;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.exception.DomainException;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 9/2/2017.
 */

public class ArticleServiceImp implements IArticleService {
    private final IArticleRepository repository;
    private final IArticleLoader loader;

    public ArticleServiceImp(IArticleRepository articleRepository, IArticleLoader loader) {
        this.repository = articleRepository;
        this.loader = loader;
    }

    @Override
    public ArticleDetail getArticle(String url) {
        if(!repository.hasArticle(url))
            loader.load(url);
        ArticleDetail result =  repository.getArticle(url);
        if(result == null)
            throw new DomainException(myApp().getResources().getString(R.string.article_load_error));
        return result;
    }

}
