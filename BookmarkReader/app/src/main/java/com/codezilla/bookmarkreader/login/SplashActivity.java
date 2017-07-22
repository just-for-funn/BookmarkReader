package com.codezilla.bookmarkreader.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.codezilla.bookmarkreader.MainActivity;
import com.codezilla.bookmarkreader.R;
import java.util.concurrent.Callable;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    private void activityStart(Class<? extends Activity> clazz) {
        Intent i = new Intent(SplashActivity.this , clazz);
        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        SplashActivity.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilize();

    }

    private void initilize() {
        InitilizerTask initilizerTask = new InitilizerTask(new Callable() {
            @Override
            public Object call() throws Exception {
                SplashActivity.this.activityStart(MainActivity.class);
                return null;
            }
        });
        initilizerTask.execute(new Void[]{});
    }

    static class InitilizerTask extends AsyncTask<Void,Void,Void>
    {
        public InitilizerTask(Callable finishCallBack) {
            this.finishCallBack = finishCallBack;
        }

        Callable finishCallBack;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(finishCallBack != null)
                try {
                    finishCallBack.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

}
