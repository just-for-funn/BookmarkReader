package com.davutozcan.bookmarkreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davutozcan.bookmarkreader.application.BookmarkReaderApplication;
import com.davutozcan.bookmarkreader.databinding.*;
import com.davutozcan.bookmarkreader.databinding.FragmentSettingsBinding;
import com.davutozcan.bookmarkreader.google_sign_in.GoogleSignInHelper;
import com.davutozcan.bookmarkreader.login.User;
import com.davutozcan.bookmarkreader.menu.INavigator;
import com.davutozcan.bookmarkreader.util.Logger;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.userService;

/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragment extends Fragment {
    private INavigator navigator;
    private FragmentSettingsBinding binding;

    public SettingsFragment() {
    }

    public SettingsFragment(INavigator navigator) {
        this.navigator = navigator;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.binding == null)
        {
            this.binding =  FragmentSettingsBinding.inflate(inflater , container , false);
            binding.setModel(new SettingsFragmentModel(navigator));
            SignInButton signInButton = binding.getRoot().findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_WIDE);
            signInButton.setOnClickListener(o->this.signInClicked(o));
        }
        return binding.getRoot();
    }

    private void signInClicked(View o) {
        Logger.e("Sigin in clicked from fragment");
        GoogleSignInHelper.requestSignIn(getActivity());
    }


    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(binding != null)
        {
            binding.getModel().loadFrom(userService().getLastLoginedUser());
        }
    }

    public void setNavigator(INavigator navigator) {
        this.navigator = navigator;
    }

    public SettingsFragmentModel getModel(){
        return this.binding.getModel();
    }
}
