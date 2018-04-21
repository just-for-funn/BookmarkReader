package com.davutozcan.bookmarkreader.weblist;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.article.ArticleDetailView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.cacheService;
import static com.davutozcan.bookmarkreader.weblist.CustomizedDateFormatter.customizedDateFormatter;

/**
 * Created by davut on 7/5/2017.
 */

public class WebListRowModel {

    String title;
    String description;
    String faviconUrl;
    String changeDate;
    Consumer<WebListRowModel> onClick;
    public WebListRowModel(String title, String description , String faviconUrl , Consumer<WebListRowModel> click) {
        this.title = title;
        this.description = description;
        this.faviconUrl = faviconUrl;
        this.onClick = click;
    }

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

    public void onItemSelected()
    {
        Log.i("BookmarkReader","main activity item selected:"+this.getTitle());
        if(this.onClick != null)
            onClick.accept(this);
    }
}
