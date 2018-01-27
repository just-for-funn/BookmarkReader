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

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.cacheService;
import static com.codezilla.bookmarkreader.weblist.CustomizedDateFormatter.customizedDateFormatter;

/**
 * Created by davut on 7/5/2017.
 */

public class WebListRowModel {

    String title;
    String description;
    String faviconUrl;
    String changeDate;

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

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(CircleImageView view, final String imageUrl)
    {
        new UrlImageLoader(view , imageUrl).load();
    }

    public void setChangeDate(Date changeDate) {
        if(changeDate != null)
            setChangeDate(customizedDateFormatter().format(changeDate));
    }
}
