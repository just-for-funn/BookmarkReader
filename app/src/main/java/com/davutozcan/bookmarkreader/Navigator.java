package com.davutozcan.bookmarkreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.davutozcan.bookmarkreader.history.HistoryView;
import com.davutozcan.bookmarkreader.menu.INavigator;
import com.davutozcan.bookmarkreader.views.download.DownloadFragment;
import com.davutozcan.bookmarkreader.views.edit.EditFragment;
import com.davutozcan.bookmarkreader.weblist.WebListView;

/**
 * Created by davut on 17.02.2018.
 */
class Navigator implements INavigator {

    private final DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    public Navigator(FragmentManager fragmentManager, DrawerLayout drawerLayout , Toolbar toolbar) {
        this.fragmentManager = fragmentManager;
        this.drawerLayout = drawerLayout;
        this.toolbar = toolbar;
    }

    @Override
    public void showHistory() {

        Fragment fragment = fragmentManager.findFragmentById(R.id.main_view_content);
        if (isRequireOpen(fragment, HistoryView.class))
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_view_content, new HistoryView(), MainActivity.TAG_HISTORY_VIEW_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        drawerLayout.closeDrawers();
        setTitle(R.string.logs);
    }

    private void setTitle(int string) {
        toolbar.setTitle(string);
    }

    private boolean isRequireOpen(Fragment currenntFragment, Class clazz) {
        return currenntFragment == null || !currenntFragment.getClass().equals(clazz);
    }

    @Override
    public void showHome() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_view_content);
        if (isRequireOpen(fragment, WebListView.class)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_view_content, new WebListView(), MainActivity.TAG_WEBLIST_FRAGMENT)
                    .commit();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        setTitle(R.string.app_name);
        drawerLayout.closeDrawers();
    }

    @Override
    public void refresh() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_view_content, new DownloadFragment(), MainActivity.TAG_DOWNLOAD_FRAGMENT)
                .addToBackStack(null)
                .commit();
        setTitle(R.string.download);
        drawerLayout.closeDrawers();
    }

    @Override
    public void showEditList() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_view_content, new EditFragment(), MainActivity.TAG_EDIT_FRAGMENT)
                .addToBackStack(null)
                .commit();
        toolbar.setTitle(R.string.edit);
        drawerLayout.closeDrawers();
    }
}
