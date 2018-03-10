package com.codezilla.bookmarkreader.exception;

/**
 * Created by davut on 7/29/2017.
 */

public class DomainException extends RuntimeException {
    String msg;
    public DomainException(String message) {
        super(message);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

}
