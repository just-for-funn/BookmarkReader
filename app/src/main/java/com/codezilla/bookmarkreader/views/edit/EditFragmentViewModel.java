package com.codezilla.bookmarkreader.views.edit;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.annimon.stream.Stream;

import java.util.List;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 2/9/2018.
 */
public class EditFragmentViewModel
{
    public final ObservableField<String> text = new ObservableField<>("text of model");
    public final ObservableList<String> urls = new ObservableArrayList<>();

    public void load() {
        List<String> urls =  myApp().getRealmFacade().webUnitUrls();
        this.urls.clear();
        this.urls.addAll(urls);
    }

    public void remove(List<String> items)
    {
        Stream.of(items)
                .forEach(o->remove(o));
    }
    private void remove(String url)
    {
        myApp().getRealmFacade().remove(url);
        this.urls.remove(url);
    }
}