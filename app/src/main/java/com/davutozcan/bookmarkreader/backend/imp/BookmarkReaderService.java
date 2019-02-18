package com.davutozcan.bookmarkreader.backend.imp;

import com.davutozcan.bookmarkreader.backend.Result;
import com.davutozcan.bookmarkreader.backend.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookmarkReaderService {
    String URL = "https://bookmarkreader.info/backend/";
    @POST("users")
    Call<Result> post(@Body User user);

    @GET("users/{googleId}")
    Call<User> get(@Path("googleId") String googleId);

    @POST("users/{googleId}/bookmarks")
    Call<User> addBookmarks(@Path("googleId")String googleId ,@Body List<String> bookmarks);

    @HTTP(method = "DELETE", path = "users/{googleId}/bookmarks", hasBody = true)
    Call<User> deleteBookmarks(@Path("googleId")String googleId ,@Body List<String> bookmarks);
}
