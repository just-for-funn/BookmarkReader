package com.codezilla.bookmarkreader.article;

/**
 * Created by davut on 9/2/2017.
 */

public interface IArticleRepository {
    boolean hasArticle(String url);
    IArticleService.ArticleDetail getArticle(String url);
}
