package com.davutozcan.bookmarkreader.menu;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.davutozcan.bookmarkreader.R;

import java.util.concurrent.Callable;

/**
 * Created by davut on 9/17/2017.
 */

public class MenuItemModel {
    public final ObservableInt name = new ObservableInt(R.string.none);
    public final ObservableInt icon = new ObservableInt(R.drawable.ic_help_outline);
    public final static String TAG = MenuItemModel.class.getSimpleName();
    private MenuItemClickListener listener;

    public MenuItemModel(int stringResourceID, int icon , MenuItemClickListener mim) {
        this.name.set(stringResourceID);
        this.icon.set(icon);
        this.listener = mim;
    }



    public void selected(View w)
    {
        Log.i(TAG, "menu item selected: "+name.get());
        listener.onItemSelected(this);
    }

    public static interface MenuItemClickListener
    {
        void onItemSelected(MenuItemModel mim);
    }
}
