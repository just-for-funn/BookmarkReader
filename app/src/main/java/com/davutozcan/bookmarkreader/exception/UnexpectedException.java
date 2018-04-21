package com.davutozcan.bookmarkreader.exception;

import com.davutozcan.bookmarkreader.exception.DomainException;

/**
 * Created by davut on 7/29/2017.
 */

public class UnexpectedException extends DomainException {
    private final Exception wrappedException;

    public UnexpectedException(Exception e) {
        super("Something went terribly wrong");
        this.wrappedException = e;
    }
}
