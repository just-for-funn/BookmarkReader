package com.davutozcan.bookmarkreader;

import android.databinding.ObservableField;

import com.davutozcan.bookmarkreader.login.User;
import com.davutozcan.bookmarkreader.menu.INavigator;
import com.davutozcan.bookmarkreader.menu.MenuItemModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragmentModel {
    INavigator navigator;
    private final MenuItemHandler menuItemHandler;
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

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<String> getName() {
        return name;
    }

    ObservableField<String> name = new ObservableField<String>("initial");

    public ObservableField<String> getUserName() {
        return userName;
    }

    public void setUserName(ObservableField<String> userName) {
        this.userName = userName;
    }

    ObservableField<String> userName = new ObservableField<String>("Login To Syncronize");

    public void loadFrom(User user)
    {
        getUserName().set(user.getName()+" "+user.getSurname());
    }


    public List<MenuItemModel> menuItems;

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
}
