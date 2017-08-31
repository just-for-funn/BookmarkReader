package com.codezilla.bookmarkreader.weblist;

import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.article.ArticleDetailView;
import com.codezilla.bookmarkreader.article.ArticleDetailViewModel;
import com.codezilla.bookmarkreader.dialog.SubmitCancelDialog;
import com.codezilla.bookmarkreader.exception.DomainException;

import java.util.concurrent.Callable;

import static com.codezilla.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 8/29/2017.
 */

public class WeblistViewHandler {

    public static final String ADD_NEW_FRAGMENT_DIALOG = "ADD_NEW_FRAGMENT_DIALOG";
    private final View parent;
    FragmentManager fragmentManager;
    WebListViewModel model;
    public WeblistViewHandler(FragmentManager fragmentManager , WebListViewModel model , View parent ) {
        this.fragmentManager = fragmentManager;
        this.model = model;
        this.parent = parent;
    }

    public  void onAddnew(View w)
    {
        SubmitCancelDialog dialog = new SubmitCancelDialog(R.layout.add_new_website , new SubmitCancelDialog.OnClickListener()
        {

            @Override
            public void onSubmit(View w) {
               try
               {
                   model.isBusy.set(true);
                   EditText editText = (EditText) w.findViewById(R.id.txtUrl);
                   myApp().getWebListService().add(editText.getText().toString());
                   model.loadRows();
               }catch (DomainException e)
               {
                   model.isBusy.set(false);
                   Snackbar.make(parent, e.getMsg(), Snackbar.LENGTH_LONG)
                           .show();
               }
            }

            @Override
            public void onCancel(View w) {

            }
        });
        dialog.show(fragmentManager , ADD_NEW_FRAGMENT_DIALOG);
    }

    public void onItemSelected(WebListRowModel wlrm)
    {
        Log.i("BookmarkReader","main activity item selected:"+wlrm.getTitle());
        ArticleDetailViewModel articleDetailViewModel = new ArticleDetailViewModel(myApp().getArticleService());
        ArticleDetailView articleDetailView =  new ArticleDetailView();
        articleDetailView.setModel(articleDetailViewModel);
        articleDetailView.load(wlrm.getTitle());
        fragmentManager.beginTransaction().replace(R.id.main_view_content ,
                articleDetailView  , "TAG_ARTICLE_DETAIL")
                .addToBackStack(null)
                .commit();
    }
}
