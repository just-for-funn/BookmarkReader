package com.codezilla.bookmarkreader.views.edit;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.codezilla.bookmarkreader.MainActivityTestBase;
import com.codezilla.bookmarkreader.domainmodel.WebUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.codezilla.bookmarkreader.views.download.DownloadFragmentTest.FACEBOOOK;
import static com.codezilla.bookmarkreader.views.download.DownloadFragmentTest.GOOGLE;
import static com.codezilla.bookmarkreader.views.download.DownloadFragmentTest.TWITTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

/**
 * Created by davut on 10.02.2018.
 */
public class EditFragmentTest extends MainActivityTestBase
{
    @Before
    public void  setUp()
    {
        myApp().getRealmFacade().clearWebUnits();
        addSites(FACEBOOOK , TWITTER , GOOGLE);
    }

    @After
    public void after()
    {
        myApp().getRealmFacade().clearWebUnits();
    }

    private void addSites(String ... urls)
    {
        for (String url:urls) {
            myApp().getRealmFacade().add(asWebUnit(url));
        }
    }

    private WebUnit asWebUnit(String url)
    {
        WebUnit webUnit = new WebUnit();
        webUnit.setUrl(url);
        return webUnit;
    }

    @Test
    public void shouldDeleteSelectedElements()
    {
        launch();
        EditPage editPage =  getMainActivityPage()
                .clickToggeButton()
                .clickEdit();
        editPage.checked(GOOGLE)
                .clickDelete();
        List<String> urls =  Stream.of(myApp()
                .getRealmFacade()
                .webUnits())
                .map(o->o.getUrl())
        .collect(Collectors.toList());
        assertThat(urls , hasSize(2));
        assertThat(urls , hasItems(FACEBOOOK , TWITTER ));
    }

    @Test
    public void shouldRemoveFromListWhenRemoved()
    {
        launch();
        EditPage editPage =  getMainActivityPage()
                .clickToggeButton()
                .clickEdit();
        editPage.checked(GOOGLE)
                .clickDelete();
        editPage.assertNotDisplaying(GOOGLE);
    }
}