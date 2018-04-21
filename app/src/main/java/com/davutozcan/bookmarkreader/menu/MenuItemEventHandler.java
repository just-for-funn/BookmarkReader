package com.davutozcan.bookmarkreader.menu;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.davutozcan.bookmarkreader.BR;
import com.davutozcan.bookmarkreader.R;

import java.util.List;

/**
 * Created by davut on 9/17/2017.
 */

public class MenuItemEventHandler {
    @BindingAdapter({"menuitems"})
    public static void setMenuItems(ViewGroup parent  , List<MenuItemModel> items )
    {
        parent.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < items.size(); i++) {
            MenuItemModel entry = items.get(i);
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.sidemenu_navigation_item, parent, true);
            binding.setVariable(BR.item, entry);
        }
    }


    @BindingAdapter({"imgsc"})
    public static void setImageResource(ImageView img ,  int resourceId)
    {
        img.setImageResource(resourceId);
    }
}
