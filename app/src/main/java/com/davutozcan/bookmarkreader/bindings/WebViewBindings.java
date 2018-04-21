package com.davutozcan.bookmarkreader.bindings;

import android.databinding.BindingAdapter;
import android.webkit.WebView;

/**
 * Created by davut on 8/27/2017.
 */

public class WebViewBindings {
    @BindingAdapter(value={"webContent" , "base_url"},requireAll = true)
    public static void setWebContent(WebView webView , String content , String baseUrl)
    {
        //webView.loadData(content , "text/html" , "utf-8");
        webView.loadDataWithBaseURL(baseUrl , content , "text/html" , "utf-8" , null );
    }
}
