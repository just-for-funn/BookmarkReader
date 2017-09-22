package com.codezilla.bookmarkreader.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.FragmentHistoryBinding;

/**
 * Created by davut on 9/18/2017.
 */

public class HistoryView extends Fragment{
    FragmentHistoryBinding binding = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null)
        {
            this.binding =  DataBindingUtil.inflate(inflater , R.layout.fragment_history , container , false );
            binding.setModel(new HistoryViewModel());
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        binding.getModel().load();
        super.onViewCreated(view, savedInstanceState);
    }
}
