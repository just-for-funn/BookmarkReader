package com.codezilla.bookmarkreader.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.codezilla.bookmarkreader.R;



/**
 * Created by davut on 8/29/2017.
 */

public class SubmitCancelDialog extends DialogFragment
{
    private final OnClickListener onclickListener;
    int layout_resource_id = 0;

    private View view;

    public SubmitCancelDialog(int layout_resource_id, OnClickListener onClickListener) {
        this.layout_resource_id = layout_resource_id;
        this.onclickListener = onClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        createView();
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_new)
                .setMessage(" ")
                .setView(view)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onclickListener.onSubmit(view);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onclickListener.onCancel(view);
                    }
                })
                .create();
    }



    private void createView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        this.view = inflater.inflate(this.layout_resource_id ,null);
    }

    public static interface OnClickListener
    {
        void onSubmit(View w);
        void onCancel(View w);
    }
}
