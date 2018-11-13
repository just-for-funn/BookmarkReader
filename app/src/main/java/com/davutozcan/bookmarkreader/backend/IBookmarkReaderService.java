package com.davutozcan.bookmarkreader.backend;

import android.content.Context;

import com.davutozcan.bookmarkreader.backend.imp.BackendSyncronizationService;

import java.util.List;


public interface IBookmarkReaderService {
    void sendToServer(User user , Context context);
    User loadFromServerSync(String gmailId , Context context);
    User addBookmark(String googleId , List<String> bookmark , Context context);
    User deleteBookmarks(String googleId , List<String> bookmark , Context context);

    static IBookmarkReaderService newInstance(){
        return new BackendSyncronizationService();
    }
}
