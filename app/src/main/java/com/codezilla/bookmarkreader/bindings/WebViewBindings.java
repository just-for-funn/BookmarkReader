package com.codezilla.bookmarkreader.bindings;

import android.databinding.BindingAdapter;
import android.webkit.WebView;

/**
 * Created by davut on 8/27/2017.
 */

public class WebViewBindings {
    @BindingAdapter(value = "webContent")
    public static void setWebContent(WebView webView , String content)
    {
        webView.loadData(content , "text/html" , "utf-8");
    }
}
