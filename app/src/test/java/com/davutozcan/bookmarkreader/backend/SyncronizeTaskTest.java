package com.davutozcan.bookmarkreader.backend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SyncronizeTaskTest {

    public static final String GOOGLE_ID = "test-account";
    @Mock
    IBookmarkReaderService service;
    @InjectMocks
    SyncronizeTask syncronizeTask  = new SyncronizeTask();

    @Before
    public void before(){
        when(service.loadFromServerSync(eq("test-gmail") ,any() ))
                .thenReturn(user("a.com" , "b.com" , "c.com"));
    }

    private User user(String ... urls) {
        User user = new User();
        user.setBookmarks(Arrays.asList(urls));
        return user;
    }



    @Test
    public void should(){
        syncronizeTask.syncronize();
    }
}