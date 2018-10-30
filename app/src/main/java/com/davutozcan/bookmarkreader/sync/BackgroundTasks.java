package com.davutozcan.bookmarkreader.sync;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.davutozcan.bookmarkreader.backend.User;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.util.Logger;
import com.davutozcan.bookmarkreader.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.davutozcan.bookmarkreader.backend.BackendSyncronizationService.backendService;

public class BackgroundTasks {
    //TODO move this method to more appropriate place
    public static void scheduleUpload(Context context){
        Logger.i("Preparing upload");
        backendService().sendToServer(prepareUser(context) , context);
    }



    private static User prepareUser(Context context) {
        SessionManager sessionManager = new SessionManager(context);
        User user = new User();
        user.setName(sessionManager.getStringDataByKey(SessionManager.Keys.GMAIL_USER_NAME));
        user.setGoogleId(sessionManager.getStringDataByKey(SessionManager.Keys.GOOGLE_ID));
        user.setEmail(sessionManager.getStringDataByKey(SessionManager.Keys.EMAIL));
        user.setSurname(sessionManager.getStringDataByKey(SessionManager.Keys.SURNAME));
        user.setBookmarks(new ArrayList<>());

        List<String> urls = Stream.of(myApp().getWebunitService().getWebSitesInfos())
                .map(WebUnit::getUrl)
                .collect(Collectors.toList());
        user.setBookmarks(urls);
        return user;
    }
}
