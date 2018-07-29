package com.davutozcan.bookmarkreader.weblist;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
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



    public final ObservableField<String> title = new ObservableField<>("");
    public final ObservableField<String> description = new ObservableField<>("");
    public final ObservableField<String> faviconUrl = new ObservableField<>("");
    public final ObservableField<String> changeDate = new ObservableField<>("");
    Consumer<WebListRowModel> onClick;
    public WebListRowModel(String title, String description , String faviconUrl , Consumer<WebListRowModel> click) {
        this.title.set(title);
        this.description.set(description);
        this.faviconUrl.set(faviconUrl);
        this.onClick = click;
    }



    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(CircleImageView view, final String imageUrl)
    {
        new UrlImageLoader(view , imageUrl).load();
    }

    public void setChangeDate(Date changeDate) {
        if(changeDate != null)
            this.changeDate.set(customizedDateFormatter().format(changeDate));
    }

    public void onItemSelected()
    {
        Log.i("BookmarkReader","main activity item selected:"+this.title.get());
        if(this.onClick != null)
            onClick.accept(this);
    }
}
