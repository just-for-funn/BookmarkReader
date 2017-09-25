package com.codezilla.bookmarkreader.article;

/**
 * Created by davut on 8/14/2017.
 */

public interface IArticleService {
    ArticleDetail getArticle(String url);


    public static class ArticleDetail
    {
        private String content;
        private String baseUrl;

        public ArticleDetail(String content, String baseUrl) {
            this.content = content;
            this.baseUrl = baseUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }
}
