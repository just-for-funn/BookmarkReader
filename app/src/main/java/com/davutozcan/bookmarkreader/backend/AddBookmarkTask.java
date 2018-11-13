package com.davutozcan.bookmarkreader.backend;

import android.content.Context;

import com.davutozcan.bookmarkreader.exception.DomainException;

import java.util.List;

import io.reactivex.Observable;


public class AddBookmarkTask {

    private final IBookmarkReaderService service;
    private final Context context;

    private AddBookmarkTask(IBookmarkReaderService bookmarkReaderService, Context context) {
        this.service = bookmarkReaderService;
        this.context = context;
    }

    public static AddBookmarkTask addBookmarkTask(IBookmarkReaderService bookmarkReaderService , Context context){
        return new AddBookmarkTask(bookmarkReaderService , context);
    }

   public  Observable<User> addBookmarks(String googleId , List<String> bookmarks){
        return Observable.fromCallable(()->doWork(googleId , bookmarks));
    }

    private User doWork(String googleId, List<String> bookmarks) {
        try{
            return service.addBookmark(googleId , bookmarks , this.context );
        }catch ( Exception e){
            throw new DomainException("Cannot connect to internet");
        }
    }
}
