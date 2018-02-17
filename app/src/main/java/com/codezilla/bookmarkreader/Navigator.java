package com.codezilla.bookmarkreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

import com.codezilla.bookmarkreader.history.HistoryView;
import com.codezilla.bookmarkreader.menu.INavigator;
import com.codezilla.bookmarkreader.views.download.DownloadFragment;
import com.codezilla.bookmarkreader.views.edit.EditFragment;
import com.codezilla.bookmarkreader.weblist.WebListView;

/**
 * Created by davut on 17.02.2018.
 */
class Navigator implements INavigator {

    private final DrawerLayout drawerLayout;
    FragmentManager fragmentManager;

    public Navigator(FragmentManager fragmentManager, DrawerLayout drawerLayout) {
        this.fragmentManager = fragmentManager;
        this.drawerLayout = drawerLayout;
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
        drawerLayout.closeDrawers();
    }

    @Override
    public void refresh() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_view_content, new DownloadFragment(), MainActivity.TAG_DOWNLOAD_FRAGMENT)
                .addToBackStack(null)
                .commit();
        drawerLayout.closeDrawers();
    }

    @Override
    public void showEditList() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_view_content, new EditFragment(), MainActivity.TAG_EDIT_FRAGMENT)
                .addToBackStack(null)
                .commit();
        drawerLayout.closeDrawers();
    }
}
