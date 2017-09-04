package com.codezilla.bookmarkreader.application;

import android.content.Context;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.article.IArticleLoader;
import com.codezilla.bookmarkreader.domainmodel.IRealmFacade;
import com.codezilla.bookmarkreader.domainmodel.RealmFacade;
import com.codezilla.bookmarkreader.exception.DomainException;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 9/2/2017.
 */

class ArticleLoaderImp implements IArticleLoader {
    private final IRealmFacade realmFacade;
    private final IHttpClient httpClient;
    private final Context resourceProvider;

    public ArticleLoaderImp(IRealmFacade realmFacade , IHttpClient httpClient , Context resourceProvider) {
        this.realmFacade = realmFacade;
        this.httpClient = httpClient;
        this.resourceProvider = resourceProvider;
    }

    @Override
    public void load(String url)
    {
        String htmlContent = loadHtml(url);
        if(htmlContent == null || htmlContent.isEmpty())
            throw new DomainException(resourceProvider.getString(R.string.article_load_error));
        realmFacade.addSiteContent(url , htmlContent);
    }

    private String loadHtml(String url) {
        try {
            String content = httpClient.getHtmlContent(url);
            return content;
        }catch (Exception e)
        {
            throw new DomainException(resourceProvider.getString(R.string.connection_error));
        }
    }
}
