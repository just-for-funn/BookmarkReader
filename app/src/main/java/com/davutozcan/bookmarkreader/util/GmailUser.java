package com.davutozcan.bookmarkreader.util;

import com.davutozcan.bookmarkreader.backend.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public class GmailUser {
    public static final String GMAIL_USER_NAME = "GMAIL_USER_NAME";
    public static final String GMAIL_PHOTO_URL = "GMAIL_PHOTO_URL";
    public static final String GOOGLE_ID = "GOOGLE_ID";
    public static final String EMAIL = "EMAIL";
    public static final String SURNAME = "SURNAME";
    String name;
    String surname;
    String googleId;
    String photoUrl;
    String email;

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

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static GmailUser fromGmailAccount(GoogleSignInAccount account){
        GmailUser user = new GmailUser();
        user.setName(account.getGivenName());
        user.setSurname(account.getFamilyName() );
        user.setGoogleId(account.getId());
        user.setEmail(account.getEmail() );
        user.setPhotoUrl(account.getPhotoUrl().toString());
        return user;
    }
}
