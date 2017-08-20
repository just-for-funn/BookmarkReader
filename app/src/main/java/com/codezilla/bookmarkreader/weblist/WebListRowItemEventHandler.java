package com.codezilla.bookmarkreader.weblist;

import android.util.Log;
import android.widget.Toast;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/8/2017.
 */

public interface WebListRowItemEventHandler
{
    public void onItemSelected(WebListRowModel webListRowModel);
}
