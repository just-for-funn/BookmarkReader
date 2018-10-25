package com.davutozcan.bookmarkreader;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.davutozcan.bookmarkreader.login.User;
import com.davutozcan.bookmarkreader.menu.INavigator;
import com.davutozcan.bookmarkreader.menu.MenuItemModel;
import com.davutozcan.bookmarkreader.util.Logger;
import com.davutozcan.bookmarkreader.weblist.UrlImageLoader;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragmentModel {
    INavigator navigator;
    private final MenuItemHandler menuItemHandler;
    public final ObservableField<String> userName = new ObservableField<>("Login to display name");
    public final ObservableBoolean isLogined = new ObservableBoolean(false);
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public List<MenuItemModel> menuItems;


    public SettingsFragmentModel(INavigator navigator) {
        this.navigator = navigator;
        menuItemHandler = new MenuItemHandler(navigator);
        menuItems = Arrays.asList(
                new MenuItemModel(R.string.home , R.drawable.ic_home , menuItemHandler),
                new MenuItemModel(R.string.history, R.drawable.ic_history , menuItemHandler),
                new MenuItemModel(R.string.refresh_sites , R.drawable.ic_cloud_download, menuItemHandler),
                new MenuItemModel(R.string.edit_items , R.drawable.ic_edit, menuItemHandler)
        );
    }



    public ObservableField<String> getUserName() {
        return userName;
    }






    static class MenuItemHandler implements MenuItemModel.MenuItemClickListener
    {

        private INavigator navigator;

        public MenuItemHandler(INavigator navigator) {
            this.navigator = navigator;
        }

        @Override
        public void onItemSelected(MenuItemModel mim) {
            switch (mim.name.get())
            {
                case R.string.home:
                    navigator.showHome();
                    break;
                case R.string.history:
                    navigator.showHistory();
                    break;
                case R.string.refresh_sites:
                    navigator.refresh();
                    break;
                case R.string.edit_items:
                    navigator.showEditList();
                    break;
                default:
                    break;
            }

        }
    }

    @BindingAdapter({"bind:profilePicture"})
    public static void loadImage(ImageView view, final String imageUrl)
    {
        if(imageUrl == null || imageUrl.length() == 0)
        {
            view.setImageResource(R.drawable.circular_user);
            return;
        }
        Picasso picasso = new Picasso.Builder(view.getContext())
                .listener((picasso1, uri, exception) -> Logger.e("onImageLoadFailed: "+uri.toString()+", "+exception.getMessage() ))
                .downloader(new OkHttp3Downloader(view.getContext()))
                .build();
        picasso.load(imageUrl)
                .placeholder(R.drawable.circular_user)
                .error(R.drawable.circular_user)
                .into(view);
    }
}
