package com.davutozcan.bookmarkreader.backend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.davutozcan.bookmarkreader.backend.AddBookmarkTask.addBookmarkTask;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AddBookmarkTaskTest {

    @Mock
    IBookmarkReaderService service;


    private AddBookmarkTask task;

    @Before
    public void before(){
        this.task = addBookmarkTask (service , null );
    }

    @Test
    public void shouldReturnToErrorChannelOnException(){
        when(service.addBookmark(any() , any() , any() ))
            .thenThrow(new RuntimeException("test"));
        AtomicBoolean called = new AtomicBoolean(false);
        this.task.addBookmarks("" , Collections.emptyList() )
                .doOnError(e-> called.set(true))
                .subscribe();
        assertTrue(called.get());
    }

    @Test
    public void shouldCallBackendService(){
        this.task.addBookmarks("test" , Arrays.asList("a.com") )
                .subscribe();
        verify(service).addBookmark("test" , Arrays.asList("a.com") , null );
    }

    @Test
    public void shouldReturnResultFromBackend(){
        User user = new User();
        when(service.addBookmark(any() , any() , any() ))
                .thenReturn(user);
        User fromBackend =  this.task.addBookmarks("test" , Collections.emptyList())
                .blockingSingle(new User());
        assertEquals(user , fromBackend );
    }

}