package com.codezilla.bookmarkreader.weblist;


import android.support.test.runner.AndroidJUnit4;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;
import com.codezilla.bookmarkreader.async.CustomAsyncTaskExecutor;
import com.codezilla.bookmarkreader.exception.DomainException;
import com.codezilla.bookmarkreader.exception.UnexpectedException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Callable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by davut on 7/29/2017.
 */
@RunWith(AndroidJUnit4.class)
public class CustomAsyncTaskExecutorTest
{
    @Mock
    Consumer<String> onSuccess;
    @Mock
    Consumer<DomainException> onError;
    @Mock
    Supplier<String> callable;
    CustomAsyncTaskExecutor<String> customAsyncTaskExecutor = null;

    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        this.customAsyncTaskExecutor = new CustomAsyncTaskExecutor<String>(callable)
            .onSuccess(onSuccess)
            .onError(onError);
    }





    @Test
    public void shouldInvokeOnFinishWhenNoErrorOccured() throws Exception {
        when(callable.get()).thenReturn("OK");
        customAsyncTaskExecutor.execute();
        verify(onSuccess, timeout(500)).accept("OK");
    }

    @Test
    public void shouldInvokeUnexpectedExceptionOnNonDomainException() throws Exception {
        when(callable.get()).thenThrow(new RuntimeException("test"));
        customAsyncTaskExecutor.execute();
        verify(onError , timeout(250)).accept(any(UnexpectedException.class));
    }

    @Test
    public void shouldInvokeRelatedDomainExceptionError() throws Exception
    {
        when(callable.get()).thenThrow(new MyUniqueDomainException());
        customAsyncTaskExecutor.execute();
        verify(onError , timeout(250)).accept(any(MyUniqueDomainException.class));
    }

    static class MyUniqueDomainException extends DomainException
    {

        public MyUniqueDomainException() {
            super(null);
        }
    }
}