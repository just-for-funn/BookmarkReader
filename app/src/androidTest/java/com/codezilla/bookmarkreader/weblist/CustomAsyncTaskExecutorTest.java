package com.codezilla.bookmarkreader.weblist;


import android.support.test.runner.AndroidJUnit4;

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
    CustomAsyncTaskExecutor.TaskExecuteOwner<String> owner;
    @Mock
    Callable<String> callable;
    CustomAsyncTaskExecutor<String> customAsyncTaskExecutor = null;

    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        this.customAsyncTaskExecutor = new CustomAsyncTaskExecutor<>(owner , callable);
    }





    @Test
    public void shouldInvokeOnFinishWhenNoErrorOccured() throws Exception {
        when(callable.call()).thenReturn("OK");
        customAsyncTaskExecutor.execute();
        verify(owner, timeout(500)).onFinish("OK");
    }

    @Test
    public void shouldInvokeUnexpectedExceptionOnNonDomainException() throws Exception {
        when(callable.call()).thenThrow(new RuntimeException("test"));
        customAsyncTaskExecutor.execute();
        verify(owner , timeout(250)).onError(any(UnexpectedException.class));
    }

    @Test
    public void shouldInvokeRelatedDomainExceptionError() throws Exception
    {
        when(callable.call()).thenThrow(new MyUniqueDomainException());
        customAsyncTaskExecutor.execute();
        verify(owner , timeout(250)).onError(any(MyUniqueDomainException.class));
    }

    static class MyUniqueDomainException extends DomainException
    {

        public MyUniqueDomainException() {
            super(null);
        }
    }
}