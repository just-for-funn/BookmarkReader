package com.codezilla.bookmarkreader;

import android.databinding.ObservableField;

import com.codezilla.bookmarkreader.login.User;

/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragmentModel {

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<String> getName() {
        return name;
    }

    ObservableField<String> name = new ObservableField<String>("initial");

    public ObservableField<String> getUserName() {
        return userName;
    }

    public void setUserName(ObservableField<String> userName) {
        this.userName = userName;
    }

    ObservableField<String> userName = new ObservableField<String>("Login To Syncronize");

    public void loadFrom(User user)
    {
        getUserName().set(user.getName()+" "+user.getSurname());
    }
}
