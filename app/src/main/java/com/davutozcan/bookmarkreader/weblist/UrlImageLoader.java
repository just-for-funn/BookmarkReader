package com.davutozcan.bookmarkreader.weblist;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.davutozcan.bookmarkreader.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;



import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.cacheService;

/**
 * Created by davut on 8/1/2017.
 */

class UrlImageLoader {
    final static String TAG = UrlImageLoader.class.getSimpleName();
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
        Log.i(TAG, "loadFromInternet: "+imageUrl);
        Picasso picasso = new Picasso.Builder(view.getContext())
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e(TAG, "onImageLoadFailed: "+uri.toString(),exception );
                    }
                })
                .downloader(new OkHttp3Downloader(view.getContext()))
                .build();

        picasso.load(imageUrl)
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
