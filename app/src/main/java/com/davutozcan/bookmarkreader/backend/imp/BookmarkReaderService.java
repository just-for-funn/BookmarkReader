package com.davutozcan.bookmarkreader.backend.imp;

import com.davutozcan.bookmarkreader.backend.Result;
import com.davutozcan.bookmarkreader.backend.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookmarkReaderService {
    String URL = "https://185.52.2.180:3080/";
    @POST("users")
    Call<Result> post(@Body User user);

    @GET("users/{googleId}")
    Call<User> get(@Path("googleId") String googleId);

    @POST("users/{googleId}")
    Call<User> addBookmarks(@Path("googleId")String googleId);
}
