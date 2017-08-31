package com.codezilla.bookmarkreader.application;

/**
 * Created by davut on 8/28/2017.
 */

public interface IApplicationState {
    boolean isAppInitlized();
    void saveAppInitilized(boolean initilized);
}
