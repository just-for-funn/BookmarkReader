package com.davutozcan.bookmarkreader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.annimon.stream.Optional;
import com.davutozcan.bookmarkreader.R;



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

    public int getIntegerDataByKey(String key) {
        return pref.getInt(key, 0);
    }

    public boolean getBooleanDataByKey(String key) {
        return pref.getBoolean(key, false);
    }

    public void setBooleanDataByKey(String key, boolean isTrue) {
        editor.putBoolean(key, isTrue);
        editor.commit();
    }

    private boolean islogined() {
        Optional<String> gmailId = gmailId();
        if(!gmailId.isPresent())
            return false;
        return true;
    }

    public Optional<String> gmailId(){
        return Optional.ofNullable(getStringDataByKey(SessionManager.Keys.GOOGLE_ID));
    }

    public static class Keys{

        public static final String GMAIL_USER_NAME = "GMAIL_USER_NAME";
        public static final String GMAIL_PHOTO_URL = "GMAIL_PHOTO_URL";
        public static final String GOOGLE_ID = "GOOGLE_ID";
        public static final String EMAIL = "EMAIL";
        public static final String SURNAME = "SURNAME";

    }
}
