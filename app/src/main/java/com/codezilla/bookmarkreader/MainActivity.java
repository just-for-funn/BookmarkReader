package com.codezilla.bookmarkreader;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.codezilla.bookmarkreader.R;

import com.codezilla.bookmarkreader.weblist.WebListView;


public class MainActivity extends FragmentActivity {
    public static final String NAVIGATION_CONTENT_DESCRIPTION = "Open Settings View";
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Bookmark Reader");

        this.toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);


        WebListView fragment = new WebListView();
        FragmentManager manager =  getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.main_view_content , fragment)
                .add(R.id.settings_container , new SettingsFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        toggle.syncState();
        super.onPostCreate(savedInstanceState);
    }
}
