package com.davutozcan.bookmarkreader.sync;

import android.content.Context;

import com.davutozcan.bookmarkreader.backend.BookmarkReaderService;
import com.davutozcan.bookmarkreader.backend.Result;
import com.davutozcan.bookmarkreader.backend.SslUtils;
import com.davutozcan.bookmarkreader.backend.User;
import com.davutozcan.bookmarkreader.util.Logger;
import com.davutozcan.bookmarkreader.util.SessionManager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundTasks {
    public static void scheduleUpload(Context context){
        Logger.i("Preparing upload");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BookmarkReaderService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client(context))
                .build();

        BookmarkReaderService service = retrofit.create(BookmarkReaderService.class);
        service.post(prepareUser(context)).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                Logger.i("Upload request succeed ->" + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Logger.e("upload request failed");
                Logger.e(t.getMessage());
            }
        });
    }

    private static OkHttpClient client(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((s, sslSession) -> true);
        SSLContext sslContext = SslUtils.getSslContextForCertificateFile(context, "bookmarkreader.cer");
        builder.sslSocketFactory(sslContext.getSocketFactory() , x509TrustManager());
        return builder.build();
    }

    private static X509TrustManager x509TrustManager() {
        return new X509TrustManager(){

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }


    private static User prepareUser(Context context) {
        SessionManager sessionManager = new SessionManager(context);
        User user = new User();
        user.setName(sessionManager.getStringDataByKey(SessionManager.Keys.GMAIL_USER_NAME));
        user.setGoogleId(sessionManager.getStringDataByKey(SessionManager.Keys.GOOGLE_ID));
        user.setEmail(sessionManager.getStringDataByKey(SessionManager.Keys.EMAIL));
        user.setSurname(sessionManager.getStringDataByKey(SessionManager.Keys.SURNAME));
        user.setBookmarks(new ArrayList<>());
        return user;
    }
}
