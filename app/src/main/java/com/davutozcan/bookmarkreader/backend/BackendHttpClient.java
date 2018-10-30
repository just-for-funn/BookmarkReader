package com.davutozcan.bookmarkreader.backend;

import android.content.Context;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class BackendHttpClient {
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


    static BookmarkReaderService create (Context context){
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(BookmarkReaderService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client(context))
                .build();
        return retrofit.create(BookmarkReaderService.class);
    }


}
