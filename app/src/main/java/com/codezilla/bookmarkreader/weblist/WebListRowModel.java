package com.codezilla.bookmarkreader.weblist;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.codezilla.bookmarkreader.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.cacheService;

/**
 * Created by davut on 7/5/2017.
 */

public class WebListRowModel {

    String title;
    String description;
    String faviconUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WebListRowModel(String title, String description , String faviconUrl) {
        this.title = title;
        this.description = description;
        this.faviconUrl = faviconUrl;
    }



    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(CircleImageView view, final String imageUrl)
    {
        new UrlImageLoader(view , imageUrl).load();
    }
}
