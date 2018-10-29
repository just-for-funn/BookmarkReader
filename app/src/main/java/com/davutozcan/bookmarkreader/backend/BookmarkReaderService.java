package com.davutozcan.bookmarkreader.backend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookmarkReaderService {
    String URL = "https://185.52.2.180:3080/";
    @POST("users")
    Call<Result> post(@Body User user);
}
