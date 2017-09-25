package com.codezilla.bookmarkreader.application;

import android.content.Context;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.article.IArticleLoader;
import com.codezilla.bookmarkreader.domainmodel.IHttpClient;
import com.codezilla.bookmarkreader.domainmodel.IWebUnitRepository;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;
import com.codezilla.bookmarkreader.domainmodel.WebUnitContent;
import com.codezilla.bookmarkreader.exception.DomainException;

/**
 * Created by davut on 9/2/2017.
 */

class ArticleLoaderImp implements IArticleLoader {
    private final IWebUnitRepository realmFacade;
    private final IHttpClient httpClient;
    private final Context resourceProvider;
    private String baseUrl;

    public ArticleLoaderImp(IWebUnitRepository realmFacade , IHttpClient httpClient , Context resourceProvider) {
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
        WebUnit wu = realmFacade.getWebUnit(url);
        WebUnitContent wuc = new WebUnitContent();
        wuc.setContent(htmlContent);
        wu.setUrl(baseUrl);
        wu.setLatestContent(wuc);
        realmFacade.update(wu);
    }

    private String loadHtml(String url) {
        try {
            String content = httpClient.getHtmlContent(url);
            this.baseUrl = httpClient.url();
            return content;
        }catch (Exception e)
        {
            throw new DomainException(resourceProvider.getString(R.string.connection_error));
        }
    }
}
