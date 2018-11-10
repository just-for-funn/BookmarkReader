package com.davutozcan.bookmarkreader.backend;

import android.content.Context;

import com.davutozcan.bookmarkreader.backend.imp.BackendSyncronizationService;


public interface IBookmarkReaderService {
    void sendToServer(User user , Context context);
    User loadFromServerSync(String gmailId , Context context);

    static IBookmarkReaderService newInstance(){
        return new BackendSyncronizationService();
    }
}
