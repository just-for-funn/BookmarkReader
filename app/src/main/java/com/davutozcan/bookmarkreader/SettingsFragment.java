package com.davutozcan.bookmarkreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.davutozcan.bookmarkreader.databinding.FragmentSettingsBinding;
import com.davutozcan.bookmarkreader.google_sign_in.GoogleSignInHelper;
import com.davutozcan.bookmarkreader.menu.INavigator;
import com.davutozcan.bookmarkreader.util.GmailUser;
import com.davutozcan.bookmarkreader.util.Logger;
import com.davutozcan.bookmarkreader.util.SessionManager;
import com.google.android.gms.common.SignInButton;


/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragment extends Fragment {
    private INavigator navigator;
    private FragmentSettingsBinding binding;

    public SettingsFragment() {
    }

    public static SettingsFragment settingsFragment(INavigator navigator) {
        SettingsFragment fragment = new SettingsFragment();
        fragment.setNavigator(navigator);
        return fragment;
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
            signInButton.setColorScheme(SignInButton.COLOR_DARK);
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
            //binding.getModel().loadFrom(userService().getLastLoginedUser());
            loadLogin();
        }
    }

    public void setNavigator(INavigator navigator) {
        this.navigator = navigator;
    }

    public SettingsFragmentModel getModel(){
        return this.binding.getModel();
    }

    public void loadLogin() {
        SessionManager sessionManager = new SessionManager(getActivity());
        Optional<GmailUser> loginedUser= sessionManager.getUser();
        if(loginedUser.isPresent()){
            binding.getModel().isLogined.set(true);
            binding.getModel().userName.set(loginedUser.get().getName()+" "+loginedUser.get().getSurname());
            binding.getModel().imageUrl.set(loginedUser.get().getPhotoUrl());
        }
    }
}
