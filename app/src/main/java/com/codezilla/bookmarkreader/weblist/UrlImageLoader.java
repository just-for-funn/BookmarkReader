package com.codezilla.bookmarkreader.weblist;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.codezilla.bookmarkreader.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.cacheService;

/**
 * Created by davut on 8/1/2017.
 */

class UrlImageLoader {

    private final ImageView view;
    private final String imageUrl;

    public UrlImageLoader(ImageView view, String imageUrl) {
        this.view = view;
        this.imageUrl = imageUrl;
    }

    public void load() {
        Bitmap bitmap = cacheService().get(imageUrl);
        if(bitmap == null)
            loadFromInternet();
        else
            view.setImageBitmap(bitmap);
    }

    private void loadFromInternet() {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_alert_circle_outline_grey)
                .error(R.drawable.ic_alert_circle_outline_grey)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess()
                    {
                        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
                        cacheService().save(bitmap , imageUrl);
                    }
                    @Override
                    public void onError()
                    {

                    }
                });
    }
}
