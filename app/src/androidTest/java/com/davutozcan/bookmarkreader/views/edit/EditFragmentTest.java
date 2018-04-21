package com.davutozcan.bookmarkreader.views.edit;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.davutozcan.bookmarkreader.MainActivityTestBase;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.davutozcan.bookmarkreader.views.download.DownloadFragmentTest.FACEBOOOK;
import static com.davutozcan.bookmarkreader.views.download.DownloadFragmentTest.GOOGLE;
import static com.davutozcan.bookmarkreader.views.download.DownloadFragmentTest.TWITTER;
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
        EditPage editPage = editPage();
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

    private EditPage editPage() {
        return getMainActivityPage()
                    .clickToggeButton()
                    .clickEdit();
    }

    @Test
    public void shouldRemoveFromListWhenRemoved()
    {
        launch();
        EditPage editPage = editPage();
        editPage.checked(GOOGLE)
                .clickDelete();
        editPage.assertNotDisplaying(GOOGLE);
    }

    @Test
    public void shouldOpenAddnewDialogOnAddClick()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        AddNewSitepage addNewSitepage =  editPage().clickAdd();
        addNewSitepage.assertVisible();
    }


    @Test
    public void shouldAddNewItem()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        EditPage editPage = editPage();
        AddNewSitepage addNewSitepage =  editPage.clickAdd();
        addNewSitepage.enter(NEW_URL).submit();
        editPage.assertUrlDisplaying(NEW_URL);
    }

    @Test
    public void shouldFullfillUrlOnadd()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        EditPage editPage = editPage();
        AddNewSitepage addNewSitepage =  editPage.clickAdd();
        addNewSitepage.enter("www.test.com").submit();
        editPage.assertUrlDisplaying("http://www.test.com");
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddOnCancelClick()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        EditPage editPage = editPage();
        AddNewSitepage addNewSitepage =  editPage.clickAdd();
        addNewSitepage.enter(NEW_URL).cancel();
        editPage.assertUrlDisplaying(NEW_URL);
    }

    @Test
    public void shouldNewlyAddedItemAppearInList() throws InterruptedException {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        EditPage editPage = editPage();
        AddNewSitepage addNewSitepage =  editPage.clickAdd();
        addNewSitepage.enter(NEW_URL).submit();
        editPage.assertUrlDisplaying(NEW_URL);
    }

    @Test
    public void shouldShowErrorMsg()
    {
        addWebUnit(ANY_URL , ANY_SUMMARY);
        launch();
        EditPage editPage = editPage();
        AddNewSitepage addNewSitepage =  editPage.clickAdd();
        addNewSitepage.enter(ANY_URL).submit();
        editPage.assertErrorDisplaying(R.string.already_added);
    }
}