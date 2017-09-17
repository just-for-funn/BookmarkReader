package com.codezilla.bookmarkreader.domainmodel;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.domainmodel.exception.CustomRealException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by davut on 8/21/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RealmRepositoryImpTest {
    public static final String ANY_URL = "www.test.com";
    public static final String ANY_CONTENT = "sample content";
    public static final String NEW_CHANGE = "new value";
    RealmRepositoryImp realmFacade;
    @Before
    public void init()
    {
        realmFacade = new RealmRepositoryImp(InstrumentationRegistry.getTargetContext());
        realmFacade.clearWebUnits();
    }

    @Test(expected = CustomRealException.class)
    public void shouldThrowExceptionWhenNoUnitExists()
    {
        realmFacade.update(webUnit(ANY_URL));
    }



    @Test
    public void shouldAllowOperationOnOtherThreads() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                realmFacade.add(webUnit(ANY_URL));
                WebUnitContent wuc = new WebUnitContent();
                wuc.setContent(ANY_CONTENT);
                WebUnit wu = realmFacade.getWebUnit(ANY_URL);
                wu.setLatestContent(wuc);
                realmFacade.update(wu);
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
        realmFacade.add(webUnit(ANY_URL));
        assertThat(realmFacade.exists(ANY_URL) , equalTo(true));
    }

    @Test
    public void shouldUpdateContent()
    {
        realmFacade.add(webUnit(ANY_URL));
        Date date = new Date(System.currentTimeMillis());
        WebUnit wu = realmFacade.getWebUnit(ANY_URL);
        WebUnitContent wuc = new WebUnitContent();
        wuc.setContent(NEW_CHANGE);
        wu.setLatestContent(wuc);
        realmFacade.update(wu);
        assertThat(realmFacade.getWebUnit(ANY_URL).getLatestContent().getContent() , equalTo(NEW_CHANGE));
        assertTrue(realmFacade.getWebUnit(ANY_URL).getLatestContent().getDate().after(date));
    }

    private WebUnit webUnit(String anyUrl) {
        WebUnit wu = new WebUnit();
        wu.setUrl(anyUrl);
        return wu;
    }

    @Test
    public void shouldUpdateChange()
    {
        realmFacade.add(webUnit(ANY_URL) );
        WebUnit wu = realmFacade.getWebUnit(ANY_URL);
        wu.setChangeSummary(NEW_CHANGE);
        realmFacade.update(wu);
        assertThat(realmFacade.getWebUnit(ANY_URL).getChangeSummary() , equalTo(NEW_CHANGE));
    }


    @Test
    public void shouldAddObject()
    {
        String faviconUrl = ANY_URL+"/favicon.ico";
        WebUnit wu = new WebUnit();
        wu.setUrl(ANY_URL);
        wu.setFaviconUrl(faviconUrl);
        realmFacade.add(wu);
        assertThat(realmFacade.getWebUnit(ANY_URL).getUrl(),equalTo(ANY_URL));
        assertThat(realmFacade.getWebUnit(ANY_URL).getFaviconUrl(),equalTo(faviconUrl));
    }


    @Test
    public void shouldUpdate()
    {
        WebUnit wu = new WebUnit();
        wu.setUrl(ANY_URL);
        wu.setChangeSummary("old");
        realmFacade.add(wu);
        WebUnit updated = new WebUnit();
        updated.setUrl(ANY_URL);
        updated.setChangeSummary("new");
        realmFacade.update(updated);
        assertThat(realmFacade.webUnits().size() , equalTo(1));
        assertThat(realmFacade.getWebUnit(ANY_URL).getChangeSummary() , equalTo("new") );
    }
}