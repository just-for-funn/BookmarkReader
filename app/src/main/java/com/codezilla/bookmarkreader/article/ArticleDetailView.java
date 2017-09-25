package com.codezilla.bookmarkreader.article;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.FragmentArticleDetailBinding;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/3/2017.
 */

public class ArticleDetailView extends Fragment implements IErrorDisplay {
    FragmentArticleDetailBinding binding = null;
    private ArticleDetailViewModel model;
    private WebView webView;

    public ArticleDetailView() {
        model = new ArticleDetailViewModel(this ,  myApp().getArticleService());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null)
            bind(inflater , container , savedInstanceState);
        return binding.getRoot();
    }

    private void bind(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_article_detail , container , false);
        webView = (WebView) binding.getRoot().findViewById(R.id.article_webview);
        binding.setModel(model);
        webView.setWebViewClient(customWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
    }

    private WebViewClient customWebViewClient() {
        return new WebViewClient()
        {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }



            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

        };
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model.onViewCreated();
    }

    public void load(String url) {
        model.load(url);
    }

    @Override
    public void show(String message) {
        Snackbar.make(binding.getRoot() , message , Snackbar.LENGTH_SHORT ).show();
    }
}
