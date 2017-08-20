package com.codezilla.bookmarkreader.exception;

import com.codezilla.bookmarkreader.exception.DomainException;

/**
 * Created by davut on 7/29/2017.
 */

public class UnexpectedException extends DomainException {
    private final Exception wrappedException;

    public UnexpectedException(Exception e) {
        super();
        this.wrappedException = e;
    }
}
