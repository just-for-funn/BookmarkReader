package com.codezilla.bookmarkreader.article;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import com.codezilla.bookmarkreader.async.CustomAsyncTaskExecutor;
import com.codezilla.bookmarkreader.exception.DomainException;

import java.util.concurrent.Callable;

/**
 * Created by davut on 8/8/2017.
 */

public class ArticleDetailViewModel
{
    public final ObservableField<String> url = new ObservableField<>("");
    public final ObservableBoolean isBusy = new ObservableBoolean(true);
    public final ObservableInt progress = new ObservableInt(0);
    public final ObservableField<String> content = new ObservableField<>();
    private final IArticleService articleService;
    private final IErrorDisplay errorDisplay;
    private CustomAsyncTaskExecutor.TaskExecuteOwner<IArticleService.ArticleDetail> articleServiceCallback;

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
        this.articleServiceCallback = articleServiceCallback();
        CustomAsyncTaskExecutor<IArticleService.ArticleDetail> executor = new CustomAsyncTaskExecutor<>(this.articleServiceCallback, articleServiceCall());
        executor.execute();
    }

    @NonNull
    private CustomAsyncTaskExecutor.TaskExecuteOwner<IArticleService.ArticleDetail> articleServiceCallback() {
        return new CustomAsyncTaskExecutor.TaskExecuteOwner<IArticleService.ArticleDetail>() {
            @Override
            public void onFinish(IArticleService.ArticleDetail s) {
                 content.set(s.getContent());
                 url.set(s.getBaseUrl());
                 isBusy.set(false);
            }

            @Override
            public void onError(DomainException domainException)
            {
                errorDisplay.show(domainException.getMsg());
            }
        };
    }

    @NonNull
    private Callable<IArticleService.ArticleDetail> articleServiceCall() {
        return new Callable<IArticleService.ArticleDetail>() {
            @Override
            public IArticleService.ArticleDetail call() throws Exception {
                return articleService.getArticle(url.get());
            }
        };
    }
}
