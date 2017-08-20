package com.codezilla.bookmarkreader.article;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.FragmentArticleDetailBinding;
import com.codezilla.bookmarkreader.weblist.WebListRowModel;

/**
 * Created by davut on 8/3/2017.
 */

public class ArticleDetailView extends Fragment {
    FragmentArticleDetailBinding binding = null;
    ArticleDetailViewModel model;
    private WebView webView;

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
        model.setWebView(webView);
        webView.getSettings().setJavaScriptEnabled(true);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model.onViewCreated();
    }

    public void load(String url) {
        model.load(url);
    }

    public void setModel(ArticleDetailViewModel model) {
        this.model = model;
    }
}
