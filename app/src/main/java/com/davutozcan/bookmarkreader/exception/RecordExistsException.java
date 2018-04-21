package com.davutozcan.bookmarkreader.exception;

import com.davutozcan.bookmarkreader.R;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/30/2017.
 */

public class RecordExistsException extends DomainException{
    public RecordExistsException()
    {
        super(myApp().getApplicationContext().getResources().getString(R.string.already_added));
    }
}
