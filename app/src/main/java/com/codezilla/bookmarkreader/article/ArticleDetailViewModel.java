package com.codezilla.bookmarkreader.article;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;

import com.annimon.stream.function.Supplier;
import com.codezilla.bookmarkreader.async.CustomAsyncTaskExecutor;
import com.codezilla.bookmarkreader.exception.DomainException;
import com.codezilla.bookmarkreader.sync.BoilerplateArticlConverterAdapter;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.codezilla.bookmarkreader.async.CustomAsyncTaskExecutor.async;

/**
 * Created by davut on 8/8/2017.
 */

public class ArticleDetailViewModel
{
    public final ObservableField<String> url = new ObservableField<>("");
    public final ObservableBoolean isBusy = new ObservableBoolean(true);
    public final ObservableInt progress = new ObservableInt(0);
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableBoolean isShowingHtml = new ObservableBoolean(true);
    public final ObservableField<String> rawContent = new ObservableField<>();

    private final IArticleService articleService;
    private final IErrorDisplay errorDisplay;

    public ArticleDetailViewModel(IErrorDisplay errorDisplay , IArticleService articleService) {
        this.articleService = articleService;
        this.errorDisplay = errorDisplay;
    }

    public void load(String url) {
        isBusy.set(true);
        progress.set(0);
        this.url.set(url);
    }

    public void onViewCreated()
    {
        this.isBusy.set(true);
        CustomAsyncTaskExecutor<IArticleService.ArticleDetail> executor = async(articleServiceCall());
        executor.onSuccess(this::articleLoaded)
                .onError(this::articleLoadFailed)
                .execute();
    }

    private void articleLoadFailed(DomainException e)
    {
        errorDisplay.show(e.getMsg());
        isBusy.set(false);
    }

    private void articleLoaded(IArticleService.ArticleDetail s) {
        myApp().getWebListService().markRead(url.get());
        content.set(s.getContent());
        url.set(s.getBaseUrl());
        isBusy.set(false);
    }

    private Spanned htmlToText(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(content ,Html.FROM_HTML_MODE_COMPACT);
        }else{
            return Html.fromHtml(content);
        }
    }

    @NonNull
    private Supplier<IArticleService.ArticleDetail> articleServiceCall() {
        return () -> articleService.getArticle(url.get());
    }

    public void onHtmlContentChanged(String arg)
    {
        isBusy.set(true);
        async(()->{
            String unescaped = org.apache.commons.text.StringEscapeUtils.unescapeJava(arg);
            String article = new BoilerplateArticlConverterAdapter().convert(unescaped);
            return article;
                }).onSuccess(this::articleLoaded)
                .onError(this::articleLoadFailed)
                .execute();
    }

    private void articleLoaded(String article) {
        rawContent.set(article);
        isBusy.set(false);
    }
}
