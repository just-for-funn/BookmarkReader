package com.davutozcan.bookmarkreader.views.edit;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;

import com.annimon.stream.Optional;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.backend.AddBookmarkTask;
import com.davutozcan.bookmarkreader.backend.IBookmarkReaderService;
import com.davutozcan.bookmarkreader.backend.User;
import com.davutozcan.bookmarkreader.dialog.SubmitCancelDialog;
import com.davutozcan.bookmarkreader.exception.DomainException;
import com.davutozcan.bookmarkreader.util.GmailUser;
import com.davutozcan.bookmarkreader.util.Logger;
import com.davutozcan.bookmarkreader.util.SessionManager;

import java.util.Arrays;

import androidx.work.impl.Scheduler;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.observable.ObservableAll;
import io.reactivex.schedulers.Schedulers;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;
import static com.davutozcan.bookmarkreader.backend.AddBookmarkTask.addBookmarkTask;

/**
 * Created by davut on 17.02.2018.
 */

public class EditViewHandler {

    public static final String ADD_NEW_FRAGMENT_DIALOG = "ADD_NEW_FRAGMENT_DIALOG";
    private final EditFragment editFragment;

    public EditViewHandler(EditFragment editFragment) {
        this.editFragment = editFragment;
    }

    public  void onAddnew(View w)
    {
        SubmitCancelDialog dialog = new SubmitCancelDialog(R.layout.add_new_website , new SubmitCancelDialog.OnClickListener()
        {

            @Override
            public void onSubmit(View w) {
                try
                {
                    editFragment.getModel().isBusy.set(true);
                    EditText editText = w.findViewById(R.id.txtUrl);
                    EditViewHandler.this.addBookmark(editText.getText().toString());
                    editFragment.reload();
                }catch (Exception e)
                {
                   EditViewHandler.this.onFailed(e);
                }
            }

            @Override
            public void onCancel(View w) {

            }
        });

        dialog.show(editFragment.getFragmentManager() , ADD_NEW_FRAGMENT_DIALOG);
    }

    private void addBookmark(String url) {
        String fixedUrl = this.fixUrl(url);

        Observable.concat(this.sendToBackend(fixedUrl) , this.addUrlToLocal(fixedUrl) )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(u-> Logger.i("Url added "+fixedUrl) , this::onFailed);

    }

    private Observable<User> sendToBackend(String url) {
        SessionManager sessionManager = new SessionManager(myApp());
        Optional<GmailUser> user = sessionManager.getUser();
        if(user.isPresent()) {
            return addBookmarkTask(IBookmarkReaderService.newInstance() ,myApp() )
                    .addBookmarks(user.get().getGoogleId() , Arrays.asList(url));
        }else {
            return Observable.empty();
        }
    }


    Observable<Boolean> addUrlToLocal(String url){
        return Observable.fromCallable(()->{
            myApp().getWebunitService().add(url);
            return true;
        });
    }

    private void onFailed(Throwable e) {
        String message = e instanceof DomainException ? e.getMessage() : "Failed";
        Logger.e(e.getMessage());
        editFragment.getModel().isBusy.set(false);
        Snackbar.make(editFragment.getView(), message, Snackbar.LENGTH_LONG)
                .show();
    }

    private String fixUrl(String url) {
        if(!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://"+url;
        return url;
    }
}
