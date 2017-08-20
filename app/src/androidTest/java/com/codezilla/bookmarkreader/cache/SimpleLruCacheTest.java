package com.codezilla.bookmarkreader.cache;

import android.support.test.runner.AndroidJUnit4;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

/**
 * Created by davut on 8/1/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SimpleLruCacheTest
{
    SimpleLruCache simpleLruCache = new SimpleLruCache();

    @Test
    public void shouldReturnNullForNonExistingElements()
    {
        assertThat(simpleLruCache.get("InvalidKey") , is(nullValue()));
    }

    @Test
    public void shoulReturnInsertedElement()
    {
        fail("NotYetImplemented");
    }

    @Test
    public void shouldThrowExceptionWhenBitmapNull()
    {
        fail("NotYetImplemented");
    }
    @Test
    public void shouldThrowExceptionWhenKey()
    {
        fail("NotYetImplemented");
    }
}