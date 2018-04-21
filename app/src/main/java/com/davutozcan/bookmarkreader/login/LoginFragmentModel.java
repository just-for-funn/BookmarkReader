package com.davutozcan.bookmarkreader.login;


import android.databinding.ObservableField;

/**
 * Created by davut on 7/11/2017.
 */

public class LoginFragmentModel
{
    ObservableField<String> userName = new ObservableField<>("");
    ObservableField<String> password = new ObservableField<>("");

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setUserName(String username)
    {
        this.userName.set(username);
    }

    public void setPassword(String password)
    {
        this.password.set(password);
    }
}
