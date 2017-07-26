package com.codezilla.bookmarkreader.login;

/**
 * Created by davut on 7/24/2017.
 */

public class User {
    public static User NULL = new User("Unknown","User");
    String name;
    String surname;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
