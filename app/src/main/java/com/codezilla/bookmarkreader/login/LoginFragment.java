package com.codezilla.bookmarkreader.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.FragmentLoginBinding;

/**
 * Created by davut on 7/11/2017.
 */

public class LoginFragment extends Fragment
{
    FragmentLoginBinding binding = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(binding == null)
          binding = DataBindingUtil.inflate(inflater , R.layout.fragment_login , container , false);
        return binding.getRoot();
    }
}
