package com.codezilla.bookmarkreader.domainmodel;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.domainmodel.exception.CustomRealException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by davut on 8/21/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RealmFacadeTest  {
    public static final String ANY_URL = "www.test.com";
    public static final String ANY_CONTENT = "sample content";
    RealmFacade realmFacade;
    @Before
    public void init()
    {
        realmFacade = new RealmFacade(InstrumentationRegistry.getTargetContext());
        realmFacade.clearWebSites();
    }

    @After
    public void close()
    {
        realmFacade.close();
    }

    @Test
    public void shouldAddElements()
    {
        List<WebUnit> sites =  realmFacade.webUnits();
        assertThat(sites.size() , equalTo(0));
        realmFacade.addSite(ANY_URL);
        sites = realmFacade.webUnits();
        assertThat( sites.size() , equalTo(1));
        assertThat(sites.get(0).getUrl() , equalTo(ANY_URL ));
    }

    @Test(expected = CustomRealException.class)
    public void shouldThrowExceptionWhenNoUnitExists()
    {
        realmFacade.addSiteContent(ANY_URL , ANY_CONTENT);
    }

    @Test
    public void shouldAddContent()
    {
        realmFacade.addSite(ANY_URL);
        realmFacade.addSiteContent(ANY_URL ,  ANY_CONTENT);
        assertThat(realmFacade.getWebUnitContent(ANY_URL) , equalTo(ANY_CONTENT));
    }


    @Test
    public void shouldAllowOperationOnOtherThreads() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                realmFacade.addSite(ANY_URL);
                realmFacade.addSiteContent(ANY_URL ,  ANY_CONTENT);
                latch.countDown();
            }
        });
        t.start();
        latch.await(1 , TimeUnit.SECONDS);
    }

    @Test
    public void shouldCheckExisting()
    {
        assertThat(realmFacade.exists(ANY_URL) , equalTo(false));
        realmFacade.addSite(ANY_URL);
        assertThat(realmFacade.exists(ANY_URL) , equalTo(true));
    }
}