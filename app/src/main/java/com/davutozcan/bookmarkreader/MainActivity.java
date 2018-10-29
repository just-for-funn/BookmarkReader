package com.davutozcan.bookmarkreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.davutozcan.bookmarkreader.menu.INavigator;
import com.davutozcan.bookmarkreader.sync.BackgroundTasks;
import com.davutozcan.bookmarkreader.sync.MyJopScheduler;
import com.davutozcan.bookmarkreader.util.AppConstants;
import com.davutozcan.bookmarkreader.util.Logger;
import com.davutozcan.bookmarkreader.util.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.Task;

import kotlin.text.StringsKt;


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
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Bookmark Reader");
        this.toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        this.navigator = new Navigator(getSupportFragmentManager() , drawerLayout , toolbar );
        setSupportActionBar(toolbar);
        this.getSupportFragmentManager().addOnBackStackChangedListener(this);
        initilizeSettingsFragment();
        initilizeMainFragment();
    }

    private void initilizeMainFragment() {
        if(getSupportFragmentManager().findFragmentByTag(TAG_WEBLIST_FRAGMENT) == null)
             navigator.showHome();
    }

    private void initilizeSettingsFragment() {
        SettingsFragment sf = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(TAG_SETTINGS_FRAGMENT);
        if(sf == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settings_container , SettingsFragment.settingsFragment(navigator) , TAG_SETTINGS_FRAGMENT)
                    .commit();
        }else
            sf.setNavigator(navigator);
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        toolbar.setTitle(R.string.app_name);
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        if(canback)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }else {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.syncState();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        shouldDisplayHomeUp();
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
        if(islogined()) {
            BackgroundTasks.scheduleUpload(this);
        }
        super.onPause();
    }

    private boolean islogined() {
        String gmailId = sessionManager.getStringDataByKey(SessionManager.Keys.GOOGLE_ID);
        if(gmailId == null || gmailId.length() == 0)
            return false;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i("onActivityResult-> "+ resultCode);
        if(requestCode == AppConstants.RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            sessionManager.setStringDataByKey(SessionManager.Keys.GMAIL_USER_NAME , account.getDisplayName());
            sessionManager.setStringDataByKey(SessionManager.Keys.GMAIL_PHOTO_URL, account.getPhotoUrl().toString() );
            sessionManager.setStringDataByKey( SessionManager.Keys.GOOGLE_ID , account.getId());
            sessionManager.setStringDataByKey( SessionManager.Keys.EMAIL , account.getEmail());
            sessionManager.setStringDataByKey( SessionManager.Keys.SURNAME , account.getFamilyName());
            SettingsFragment sf = (SettingsFragment)getSupportFragmentManager().findFragmentByTag(TAG_SETTINGS_FRAGMENT);
            sf.loadLogin();
        } catch (Exception e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("BookMarkReader" , "google sign in failed" , e);
            Toast.makeText(this , "Cannot login" , Toast.LENGTH_SHORT);
        }
    }
}
