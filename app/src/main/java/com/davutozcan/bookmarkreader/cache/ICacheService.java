package com.davutozcan.bookmarkreader.cache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by davut on 8/1/2017.
 */

public interface ICacheService
{
    public void save(@NonNull Bitmap bitmap ,@NonNull String key);
    @Nullable
    Bitmap get(String key);

}
