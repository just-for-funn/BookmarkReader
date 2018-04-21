package com.davutozcan.bookmarkreader.views.edit;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.dialog.SubmitCancelDialog;
import com.davutozcan.bookmarkreader.exception.DomainException;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

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
                    EditText editText = (EditText) w.findViewById(R.id.txtUrl);
                    myApp().getWebListService().add(fixUrl(editText.getText().toString()));
                    editFragment.reload();
                }catch (DomainException e)
                {
                    editFragment.getModel().isBusy.set(false);
                    Snackbar.make(editFragment.getView(), e.getMsg(), Snackbar.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onCancel(View w) {

            }
        });
        dialog.show(editFragment.getFragmentManager() , ADD_NEW_FRAGMENT_DIALOG);
    }

    private String fixUrl(String url) {
        if(!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://"+url;
        return url;
    }
}
