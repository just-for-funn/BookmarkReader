package com.davutozcan.bookmarkreader.cache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

/**
 * Created by davut on 8/1/2017.
 */

public class SimpleLruCache implements ICacheService {

    private LruCache<String, Bitmap> mMemoryCache;

    public SimpleLruCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap)
            {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    public void save(@NonNull Bitmap bitmap, @NonNull String key) {
        if(bitmap == null || key == null)
            throw new RuntimeException("Neither bitmap nor key cannot be null");
        if(mMemoryCache.get(key) == null)
            mMemoryCache.put(key , bitmap);
    }

    @Nullable
    @Override
    public Bitmap get(String key) {
        if(key == null)
            return null;
        return mMemoryCache.get(key);
    }
}
