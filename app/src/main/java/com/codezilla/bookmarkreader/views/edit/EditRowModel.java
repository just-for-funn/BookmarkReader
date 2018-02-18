package com.codezilla.bookmarkreader.views.edit;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.util.Log;


/**
 * Created by davut on 10.02.2018.
 */

public class EditRowModel
{
    public final ObservableField<String> url = new ObservableField<>("");
    public final ObservableField<Boolean> checked = new ObservableField<>(false);
    private static final String TAG = EditRowModel.class.getSimpleName();
    public EditRowModel(String url, boolean checked) {
        this.url.set(url);
        this.url.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i(TAG , "Url value :"+ EditRowModel.this.url.get());
            }
        });
        this.checked.set(checked);
        this.checked.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i(TAG, "onPropertyChanged: "+EditRowModel.this.checked.get());
            }
        });
    }

    public void checked(Boolean checked)
    {
        this.checked.set(checked);
    }
}
