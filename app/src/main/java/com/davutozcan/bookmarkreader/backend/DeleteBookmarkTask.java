package com.davutozcan.bookmarkreader.backend;

import android.content.Context;

import com.davutozcan.bookmarkreader.exception.DomainException;
import com.davutozcan.bookmarkreader.util.Logger;

import java.util.List;

import io.reactivex.Observable;

public class DeleteBookmarkTask {

    IBookmarkReaderService service;
    Context context;

    public DeleteBookmarkTask(IBookmarkReaderService service, Context context) {
        this.service = service;
        this.context = context;
    }

    public static DeleteBookmarkTask deleteBookmarkTask(IBookmarkReaderService service , Context context){
        return new DeleteBookmarkTask(service , context);
    }


    public Observable<User> doWork(String googleId , List<String> bookmarks) {
        return Observable.fromCallable(()->{
            try{
                return service.deleteBookmarks(googleId , bookmarks , context );
            }catch (Exception e)
            {
                Logger.e(e);
                throw new DomainException("Network error");
            }
        });
    }
}
