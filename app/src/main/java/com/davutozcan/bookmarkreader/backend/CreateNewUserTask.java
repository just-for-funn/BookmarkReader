package com.davutozcan.bookmarkreader.backend;

import android.content.Context;

import com.annimon.stream.function.Supplier;
import com.davutozcan.bookmarkreader.util.GmailUser;

import java.util.List;

import io.reactivex.Observable;

public class CreateNewUserTask {


    private final IBookmarkReaderService service;
    private final Context context;

    public CreateNewUserTask(IBookmarkReaderService service , Context context) {
        this.service = service;
        this.context = context;
    }

    public static CreateNewUserTask createNewUserTask(IBookmarkReaderService service , Context context){
        return new CreateNewUserTask(service , context);
    }

    public Observable<GmailUser> execute(GmailUser user , Supplier<List<String>> bookmarks)
    {
        return Observable.fromCallable(()->this.doWork(user , bookmarks));
    }

    private GmailUser doWork(GmailUser gUser, Supplier<List<String>> bookmarks) throws InterruptedException {
        User user = createUser(gUser , bookmarks.get());
        this.service.sendToServer(user , context);
        return gUser;
    }

    private User createUser(GmailUser gUser, List<String> bookmarks) {
        User user = new User();
        user.setName(gUser.getName());
        user.setSurname(gUser.getSurname());
        user.setEmail(gUser.getEmail());
        user.setGoogleId(gUser.getGoogleId());
        user.setBookmarks(bookmarks);
        return user;
    }
}
