package com.davutozcan.bookmarkreader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.annimon.stream.Optional;
import com.davutozcan.bookmarkreader.R;

import static com.davutozcan.bookmarkreader.util.GmailUser.EMAIL;
import static com.davutozcan.bookmarkreader.util.GmailUser.GMAIL_PHOTO_URL;
import static com.davutozcan.bookmarkreader.util.GmailUser.GMAIL_USER_NAME;
import static com.davutozcan.bookmarkreader.util.GmailUser.GOOGLE_ID;
import static com.davutozcan.bookmarkreader.util.GmailUser.SURNAME;


public class SessionManager {

    private Editor editor;
    private SharedPreferences pref;

    public SessionManager(Context context) {
        String PREF_NAME = context.getResources().getString(R.string.app_name);
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setStringDataByKey(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringDataByKey(String key) {
        return pref.getString(key, null);
    }

    public void save(GmailUser user){
        this.setStringDataByKey(GMAIL_USER_NAME , user.getName());
        this.setStringDataByKey( SURNAME , user.getSurname());
        this.setStringDataByKey(GMAIL_PHOTO_URL, user.getPhotoUrl() );
        this.setStringDataByKey( GOOGLE_ID , user.getGoogleId());
        this.setStringDataByKey( EMAIL , user.email);
    }


    public Optional<GmailUser> getUser(){
        String googleId = getStringDataByKey(GOOGLE_ID);
        if(googleId == null || googleId.length() == 0)
            return Optional.empty();
        GmailUser gmailUser = new GmailUser();
        gmailUser.setGoogleId(googleId);
        gmailUser.setName( getStringDataByKey(GMAIL_USER_NAME));
        gmailUser.setSurname(getStringDataByKey(SURNAME));
        gmailUser.setPhotoUrl(getStringDataByKey(GMAIL_PHOTO_URL) );
        gmailUser.setEmail(getStringDataByKey(EMAIL));
        return Optional.of(gmailUser);
    }
}
