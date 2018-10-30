package com.davutozcan.bookmarkreader.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.davutozcan.bookmarkreader.MainActivity;
import com.davutozcan.bookmarkreader.R;

public class SplashActivity extends AppCompatActivity implements SplashContacts.IView {

    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        getLifecycle().addObserver(viewModel);
        viewModel.init(this , this);
    }

    @Override
    public void onLoadFinished(Class<?> clazz) {
        startMainActivity(clazz);
    }

    private void startMainActivity(Class<?> clazz) {
        Intent i = new Intent(SplashActivity.this , clazz);
        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        SplashActivity.this.finish();
    }

    @Override
    public void onLoadFailed(Throwable throwable) {
        Toast.makeText(this, "Cannot syncronize data", Toast.LENGTH_LONG).show();
        doSleep();
        startMainActivity(MainActivity.class);
    }

    private void doSleep() {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
