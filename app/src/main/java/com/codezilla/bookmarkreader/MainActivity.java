package com.codezilla.bookmarkreader;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.codezilla.bookmarkreader.menu.INavigator;
import com.codezilla.bookmarkreader.sync.MyJopScheduler;


public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    public static final String NAVIGATION_CONTENT_DESCRIPTION = "Open Settings View";
    public static final String TAG_WEBLIST_FRAGMENT = "WebListFragment";
    public static final String TAG_HISTORY_VIEW_FRAGMENT = "historyViewFragment";
    private static final String TAG_SETTINGS_FRAGMENT = "settingsFragment";
    public static final String TAG_DOWNLOAD_FRAGMENT = "downLoadFragment";
    public static final String TAG_EDIT_FRAGMENT = "editFragment";
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    INavigator navigator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Bookmark Reader");
        this.toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        this.navigator = new Navigator(getSupportFragmentManager() , drawerLayout , toolbar );

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_container , new SettingsFragment(navigator) , TAG_SETTINGS_FRAGMENT)
                .commit();
        setSupportActionBar(toolbar);
        this.getSupportFragmentManager().addOnBackStackChangedListener(this);
        this.navigator.showHome();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        if(canback)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }else {
            toolbar.setTitle(R.string.app_name);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.syncState();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        toggle.syncState();
        super.onPostCreate(savedInstanceState);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home )
        {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                onBackPressed();
                return true;
            }if (toggle.onOptionsItemSelected(item))
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        new MyJopScheduler(getApplicationContext()).cancel();
        super.onResume();
    }

    @Override
    protected void onPause() {
        new MyJopScheduler(getApplicationContext()).schedule();
        super.onPause();
    }

}
