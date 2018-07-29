package com.davutozcan.bookmarkreader.domainmodel;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.davutozcan.bookmarkreader.domainmodel.exception.CustomRealException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.davutozcan.bookmarkreader.domainmodel.TextBlock.textBlock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
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
        assertTrue(realmFacade.getWebUnit(ANY_URL).getLatestContent().getDate().compareTo(date) >= 0);
    }

    private WebUnit webUnit(String anyUrl) {
        WebUnit wu = new WebUnit();
        wu.setUrl(anyUrl);
        return wu;
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
        realmFacade.add(wu);
        WebUnit updated = new WebUnit();
        updated.setUrl(ANY_URL);
        realmFacade.update(updated);
        assertThat(realmFacade.webUnits().size() , equalTo(1));
    }


    @Test
    public void shouldUpdatePreviousContent()
    {
        WebUnit wu = new WebUnit();
        wu.setUrl(ANY_URL);
        WebUnitContent webUnitContent = new WebUnitContent();
        webUnitContent.setContent("c1");
        wu.setLatestContent(webUnitContent);
        realmFacade.add(wu);

        WebUnitContent wcNew = new WebUnitContent();
        wcNew.setContent("c2");

        wu.setPreviousContent(wu.getLatestContent());
        wu.setLatestContent(wcNew);
        realmFacade.update(wu);
        assertThat(realmFacade.getWebUnit(ANY_URL).getLatestContent().getContent() , equalTo("c2")  );
        assertThat(realmFacade.getWebUnit(ANY_URL).getPreviousContent().getContent() , equalTo("c1")  );
    }

    @Test
    public void shouldAddChange()
    {

        Change change = new Change();
        change.setNewBlocks(textBlock("a","b","c") , textBlock("x","y"));
        WebUnit wu = new WebUnit();
        wu.setUrl(ANY_URL);
        wu.setChange(change);
        realmFacade.add(wu);

        WebUnit wuDb = realmFacade.getWebUnit( ANY_URL);
        Change chDB = wuDb.getChange();
        assertThat(chDB.getNewBlocks() , hasSize(2));
        assertThat(chDB.getNewBlocks().get(0).lines() , hasItems("a" , "b" , "c")  );
        assertThat(chDB.getNewBlocks().get(1).lines() , hasItems("x" , "y" )  );
    }


    @Test
    public void shouldUpdateChange()
    {
        Change change = new Change();
        change.setNewBlocks(textBlock("a","b","c") , textBlock("x","y"));
        WebUnit wu = new WebUnit();
        wu.setUrl(ANY_URL);
        wu.setChange(change);
        realmFacade.add(wu);

        wu = realmFacade.getWebUnit(ANY_URL);
        wu.getChange().setNewBlocks(textBlock("e") );
        realmFacade.update(wu);

        wu = realmFacade.getWebUnit(ANY_URL);

        assertThat(wu.getChange().getNewBlocks() , hasSize(1));
        assertThat(wu.getChange().getNewBlocks().get(0).lines() , hasItems("e"));

    }

    @Test
    public void shouldGetUnreadElements()
    {
        realmFacade.add(webUnit("a" , WebUnit.Status.ALREADY_READ));
        realmFacade.add(webUnit("b" , WebUnit.Status.HAS_NEW_CONTENT));
        realmFacade.add(webUnit("c" , WebUnit.Status.HAS_NEW_CONTENT));
        assertThat(realmFacade.getUnreadWebUnits() , hasSize(2));
        assertThat(realmFacade.getUnreadWebUnits().get(0).getUrl() , equalTo("b"));
        assertThat(realmFacade.getUnreadWebUnits().get(1).getUrl() , equalTo("c"));

    }

    @NonNull
    private WebUnit webUnit(String url, int status) {
        WebUnit webUnit = new WebUnit();
        webUnit.setStatus(status);
        webUnit.setUrl(url);
        return webUnit;
    }

    @Test
    public void shouldGetUrlList()
    {
        realmFacade.add(webUnit("a" , WebUnit.Status.ALREADY_READ));
        realmFacade.add(webUnit("b" , WebUnit.Status.HAS_NEW_CONTENT));
        realmFacade.add(webUnit("c" , WebUnit.Status.HAS_NEW_CONTENT));
        List<String> urls =  realmFacade.webUnitUrls();
        assertThat(urls , hasItems("a","b","c"));
    }

    @Test
    public void shouldPersistDownloadStatusAndDate()
    {
        WebUnit webUnit = webUnit("a" , WebUnit.Status.ALREADY_READ);
        webUnit.setDownloadStatus(WebUnit.DownloadStatus.OK);
        Date date = new Date();
        webUnit.setLastDownloadCheckDate(date);
        realmFacade.add(webUnit);
        WebUnit fromDb =  realmFacade.getWebUnit("a");
        assertThat(fromDb.getDownloadStatus() , is(WebUnit.DownloadStatus.OK) );
        assertThat(fromDb.getLastDownloadCheckDate() , equalTo(date) );
    }
}