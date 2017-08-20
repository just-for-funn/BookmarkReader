package com.codezilla.bookmarkreader;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codezilla.bookmarkreader.R;

import com.codezilla.bookmarkreader.article.ArticleDetailView;
import com.codezilla.bookmarkreader.article.ArticleDetailViewModel;
import com.codezilla.bookmarkreader.weblist.RowItemSelectedCallBack;
import com.codezilla.bookmarkreader.weblist.WebListRowModel;
import com.codezilla.bookmarkreader.weblist.WebListView;

import static android.R.attr.fragment;
import static android.R.id.list;
import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;


public class MainActivity extends FragmentActivity implements RowItemSelectedCallBack {
    public static final String NAVIGATION_CONTENT_DESCRIPTION = "Open Settings View";
    public static final String TAG_WEBLIST_FRAGMENT = "WebListFragment";
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private WebListView listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Bookmark Reader");

        this.toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);




        this.listFragment = new WebListView();
        this.listFragment.setCallBack(this);
        FragmentManager manager =  getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.main_view_content , listFragment , TAG_WEBLIST_FRAGMENT)
                .add(R.id.settings_container , new SettingsFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        toggle.syncState();
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onRowItemSelected(WebListRowModel wlrm) {
        Log.i("BookmarkReader","main activity item selected:"+wlrm.getTitle());
        ArticleDetailViewModel articleDetailViewModel = new ArticleDetailViewModel(myApp().getArticleService());
        ArticleDetailView articleDetailView =  new ArticleDetailView();
        articleDetailView.setModel(articleDetailViewModel);
        articleDetailView.load(wlrm.getTitle());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view_content ,articleDetailView  , "TAG_ARTICLE_DETAIL")
                 .addToBackStack(null)
                .commit();
    }
}
