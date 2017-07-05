package com.codezilla.bookmarkreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.codezilla.bookmarkreader.databinding.*;
import com.codezilla.bookmarkreader.databinding.FragmentSettingsBinding;

/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingsBinding binding =  FragmentSettingsBinding.inflate(inflater , container , false);
        binding.setModel(new SettingsFragmentModel());
        return binding.getRoot();
    }
}
