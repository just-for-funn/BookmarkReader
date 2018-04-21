package com.davutozcan.bookmarkreader.application;

import com.davutozcan.bookmarkreader.login.IUserService;
import com.davutozcan.bookmarkreader.login.User;

/**
 * Created by davut on 7/24/2017.
 */

class MockUserService implements com.davutozcan.bookmarkreader.login.IUserService {
    @Override
    public User getLastLoginedUser() {
        return User.NULL;
    }
}
