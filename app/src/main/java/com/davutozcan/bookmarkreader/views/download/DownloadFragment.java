package com.davutozcan.bookmarkreader.views.download;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.databinding.FragmentDownloadBinding;
import com.github.lzyzsd.circleprogress.ArcProgress;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 1/14/2018.
 */

public class DownloadFragment extends Fragment
{
    ArcProgress arcTotal;
    ArcProgress arcCompleted;
    ArcProgress arcFailed;
    ArcProgress arcNew;

    FragmentDownloadBinding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null)
        {
            this.binding =  DataBindingUtil.inflate(inflater , R.layout.fragment_download , container , false );
            DownloadViewModel vm =  new DownloadViewModel();
            binding.setModel(vm);
            binding.setHandler(new DownloadViewHandler(vm));
            arcTotal = binding.getRoot().findViewById(R.id.arc_total);
            arcCompleted = binding.getRoot().findViewById(R.id.arc_completed);
            arcFailed= binding.getRoot().findViewById(R.id.arc_failed);
            arcNew = binding.getRoot().findViewById(R.id.arc_new);
            bind();

        }
        return this.binding.getRoot();
    }

    private void bind() {
        setMax();
        bindTo(binding.getModel().total , arcTotal);
        bindTo(binding.getModel().neww , arcNew);
        bindTo(binding.getModel().failed , arcFailed);
        bindTo(binding.getModel().successed , arcCompleted);
    }

    private void bindTo(ObservableField<Integer> field, ArcProgress arcTotal) {
        field.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                    runOnUiThread(()->arcTotal.setProgress(field.get()));
            }
        });
    }

    private void setMax() {
        int count = (int) myApp().getWebunitService().count();
        arcCompleted.setMax(count);
        arcFailed.setMax(count);
        arcTotal.setMax(count);
        arcNew.setMax(count);
    }


    void runOnUiThread(Runnable runnable)
    {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override
    public void onPause() {
        if(binding != null)
        {
            binding.getHandler().stopDownload(binding.getRoot());
        }
        super.onPause();
    }
}
