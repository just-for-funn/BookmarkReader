package com.davutozcan.bookmarkreader.backend;

import com.davutozcan.bookmarkreader.util.GmailUser;

import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class CreateNewUserTaskTest {

    public static final String ANY_USER = "test-name";
    public static final String A_SURNAME = "test-surname";
    public static final String A_GMAIL_ID = "test@gmail.com123";
    public static final String AN_EMAIL = "test@gmail.com";
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    @Mock
    IBookmarkReaderService backendService;

    CreateNewUserTask task ;

    @Before
    public void before(){
        task = new CreateNewUserTask(backendService , null );
    }

    @Test
    public void shouldSendUserToBackend() throws InterruptedException {
        GmailUser user = new GmailUser();
        user.setName(ANY_USER);
        user.setSurname(A_SURNAME);
        user.setGoogleId(A_GMAIL_ID);
        user.setEmail(AN_EMAIL);
        task.execute(user ,()-> Arrays.asList("a.com" , "b.com" , "c.com" ))
                .subscribe();

        verify(backendService).sendToServer(this.userCaptor.capture(),any());

        assertThat(userCaptor.getValue().getName() , IsEqual.equalTo("test-name"));
        assertThat(userCaptor.getValue().getSurname() , IsEqual.equalTo("test-surname"));
        assertThat(userCaptor.getValue().getEmail() , IsEqual.equalTo("test@gmail.com"));
        assertThat(userCaptor.getValue().getGoogleId() , IsEqual.equalTo("test@gmail.com123"));
        assertThat(userCaptor.getValue().getBookmarks() , IsCollectionContaining.hasItems("a.com","b.com" , "c.com"));
    }
}