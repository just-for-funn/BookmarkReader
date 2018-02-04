package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.domainmodel.IHttpClient;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by davut on 9/3/2017.
 */

public class OkHttpClientImp implements IHttpClient {

    public static final String MOBILE_USER_AGENT = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3";
    private Response response;

    @Override
    public String getHtmlContent(String url)
    {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(3 , TimeUnit.SECONDS).build();
        Call call =  client.newCall(new Request.Builder().header("User-Agent", MOBILE_USER_AGENT).url(url).build());
        try {
            this.response =  call.execute();
            if(!response.isSuccessful())
                throw new RuntimeException("Web request failed");
            return response.body().string();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String url() {
        return response.request().url().toString();
    }


}
