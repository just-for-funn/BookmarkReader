package com.codezilla.bookmarkreader.application;

/**
 * Created by davut on 8/14/2017.
 */

class MockArticleService implements com.codezilla.bookmarkreader.article.IArticleService {
    @Override
    public String getArticle(String url) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  "article service not implemened for "+url;
    }
}
