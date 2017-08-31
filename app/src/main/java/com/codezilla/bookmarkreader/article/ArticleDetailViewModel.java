package com.codezilla.bookmarkreader.article;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

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
    private CustomAsyncTaskExecutor.TaskExecuteOwner<String> articleServiceCallback;

    public ArticleDetailViewModel(IArticleService articleService) {
        this.articleService = articleService;
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
        CustomAsyncTaskExecutor<String> executor = new CustomAsyncTaskExecutor<>(this.articleServiceCallback, articleServiceCall());
        executor.execute();
    }

    @NonNull
    private CustomAsyncTaskExecutor.TaskExecuteOwner<String> articleServiceCallback() {
        return new CustomAsyncTaskExecutor.TaskExecuteOwner<String>() {
            @Override
            public void onFinish(String s) {
                 content.set(s);
                 isBusy.set(false);
            }

            @Override
            public void onError(DomainException domainException)
            {
                showErrorMessage(domainException);
            }
        };
    }

    private void showErrorMessage(DomainException domainException) {
        throw new RuntimeException("NOtImplementedYet");
    }

    @NonNull
    private Callable<String> articleServiceCall() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return articleService.getArticleDetail(url.get());
            }
        };
    }
}
