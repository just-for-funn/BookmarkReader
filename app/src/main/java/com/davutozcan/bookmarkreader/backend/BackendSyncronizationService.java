package com.davutozcan.bookmarkreader.backend;

import android.content.Context;

import com.davutozcan.bookmarkreader.util.Logger;

import retrofit2.Call;
import retrofit2.Callback;

public class BackendSyncronizationService {
    private static final BackendSyncronizationService ourInstance = new BackendSyncronizationService();

    public static BackendSyncronizationService backendService() {
        return ourInstance;
    }

    private BackendSyncronizationService() {
    }

    public void sendToServer(User user , Context context){
        BookmarkReaderService service = BackendHttpClient.create(context);
        service.post(user).enqueue(new Callback<Result>() {
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
}
