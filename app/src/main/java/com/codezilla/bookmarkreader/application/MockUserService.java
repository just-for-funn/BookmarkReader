package com.codezilla.bookmarkreader.application;

import com.codezilla.bookmarkreader.login.IUserService;
import com.codezilla.bookmarkreader.login.User;

/**
 * Created by davut on 7/24/2017.
 */

class MockUserService implements com.codezilla.bookmarkreader.login.IUserService {
    @Override
    public User getLastLoginedUser() {
        return User.NULL;
    }
}
