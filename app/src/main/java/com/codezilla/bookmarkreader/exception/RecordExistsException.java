package com.codezilla.bookmarkreader.exception;

import com.codezilla.bookmarkreader.R;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/30/2017.
 */

public class RecordExistsException extends DomainException{
    public RecordExistsException()
    {
        super(myApp().getApplicationContext().getResources().getString(R.string.already_added));
    }
}
