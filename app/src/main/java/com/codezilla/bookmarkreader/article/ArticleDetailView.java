package com.codezilla.bookmarkreader.article;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.FragmentArticleDetailBinding;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.codezilla.bookmarkreader.article.CustomScaleGestureDetector.bindScaleGesture;
import static com.codezilla.bookmarkreader.article.TextSizeAdjuster.ITextView.from;

/**
 * Created by davut on 8/3/2017.
 */

public class ArticleDetailView extends Fragment implements IErrorDisplay {
    public static final String HTML_CONTENT_JS = "(function(){return '<html>'+document.documentElement.innerHTML+'</html>';})();";
    FragmentArticleDetailBinding binding = null;
    private ArticleDetailViewModel model;
    private WebView webView;
    private static String TAG = ArticleDetailView.class.getSimpleName();
    public ArticleDetailView() {
        model = new ArticleDetailViewModel(this ,  myApp().getArticleService());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
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
        webView = binding.getRoot().findViewById(R.id.article_webview);
        binding.setModel(model);
        webView.setWebViewClient(customWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

        TextView textView = binding.getRoot().findViewById(R.id.article_textview);
        textView.setMovementMethod(new ScrollingMovementMethod());
        TextSizeAdjuster textSizeAdjuster = new TextSizeAdjuster(from(textView));
        bindScaleGesture(textView).onScale(o->{
            Log.i(TAG, "SCALE: "+o);
            textSizeAdjuster.onScale(o);
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void load(String url) {
        model.load(url);
    }

    @Override
    public void show(String message) {
        Snackbar.make(binding.getRoot() , message , Snackbar.LENGTH_SHORT ).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.article_fragment_menu ,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_article)
        {
            Log.i(getClass().getSimpleName(), "article selected");
            if(model.isShowingHtml.get())
            {
                notifyArticleRequired();
                item.setIcon(R.drawable.icon_web);
            }
            else
            {
                item.setIcon(R.drawable.article);
            }
            model.isShowingHtml.set(!model.isShowingHtml.get());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void notifyArticleRequired()
    {
        model.isBusy.set(true);
        webView.evaluateJavascript(HTML_CONTENT_JS,
                arg -> model.onHtmlContentChanged(arg));
    }
}
