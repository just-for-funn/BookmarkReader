package com.davutozcan.bookmarkreader.application;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by davut on 8/27/2017.
 */

public class ApplicationStateSharedPrefencesImp implements IApplicationState {
    public static final String IS_INITILIZED = "IsInitilized";
    private final Context context;
    public ApplicationStateSharedPrefencesImp(Context context) {
        this.context = context;
    }

    public void deleteAll() {
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.clear();
        editor.commit();
    }

    public boolean isAppInitlized() {
        SharedPreferences sp = sharedPreferences();
        return sp.getBoolean(IS_INITILIZED, false);
    }

    @Override
    public void saveAppInitilized(boolean initilized) {
        SharedPreferences sp = sharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_INITILIZED , initilized);
        editor.commit();
    }



    private SharedPreferences sharedPreferences() {
        return context.getSharedPreferences("BookmarkReaderAppPreferences" , Context.MODE_PRIVATE);
    }
}
