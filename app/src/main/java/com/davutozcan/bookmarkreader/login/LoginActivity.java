package com.davutozcan.bookmarkreader.login;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.davutozcan.bookmarkreader.R;

public class LoginActivity extends FragmentActivity
{

    public static final String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.login_frame_container ,  new LoginFragment(), LOGIN_FRAGMENT_TAG)
                .commit();
    }
}
