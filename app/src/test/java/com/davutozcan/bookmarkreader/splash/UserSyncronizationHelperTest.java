package com.davutozcan.bookmarkreader.splash;

import com.davutozcan.bookmarkreader.backend.User;
import com.davutozcan.bookmarkreader.domainmodel.IWebUnitRepository;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.weblist.WebUnitService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSyncronizationHelperTest {


    @Mock
    WebUnitService webUnitRepository;

    @InjectMocks
    UserSyncronizationHelper userSyncronizationHelper;

    @Test
    public void shouldAddNewlyAddedSites(){
        when(webUnitRepository.getWebSitesInfos()).thenReturn(Arrays.asList(webunit("a.com")) );
        userSyncronizationHelper.syncronize(user("a.com" ,"b.com","c.com"));
        verify(webUnitRepository).add("b.com");
        verify(webUnitRepository).add("c.com");
    }

    @Test
    public void shouldRemoveIfRemovedFromBackend(){
        when(webUnitRepository.getWebSitesInfos()).thenReturn(Arrays.asList(webunit("a.com") , webunit("b.com") , webunit("c.com")) );
        userSyncronizationHelper.syncronize(user("a.com"));
        verify(webUnitRepository).remove("b.com");
        verify(webUnitRepository).remove("c.com");
    }


    private User user(String ... urls) {
        User user  =new User();
        user.setBookmarks(Arrays.asList(urls));
        return user;
    }

    private WebUnit webunit(String url) {
        WebUnit webUnit = new WebUnit();
        webUnit.setUrl(url);
        return webUnit;
    }
}