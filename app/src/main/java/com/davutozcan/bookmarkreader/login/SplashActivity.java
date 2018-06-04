package com.davutozcan.bookmarkreader.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.davutozcan.bookmarkreader.MainActivity;
import com.davutozcan.bookmarkreader.R;
import java.util.concurrent.Callable;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

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
                if(!myApp().getState().isAppInitlized())
                {
                    initWebSites();
                    myApp().getState().saveAppInitilized(true);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void initWebSites() {
            addWebUnit("https://developers.google.com/training/android/");
            addWebUnit("https://www.androidhive.info/");
            addWebUnit("https://medium.freecodecamp.org/25-new-android-libraries-which-you-definitely-want-to-try-at-the-beginning-of-2017-45878d5408c0");
            addWebUnit("http://tomazkovacic.com/blog/2011/06/09/evaluating-text-extraction-algorithms/");
            addWebUnit("https://code.google.com/archive/p/google-diff-match-patch/");
            addWebUnit("https://github.com/pmahsky/FloatingActionMenuAndroid/blob/master/app/src/main/java/com/app/fabmenu/MainActivity.java");
            addWebUnit("https://icons8.com/icon/set/list/material");
            addWebUnit("https://romannurik.github.io/AndroidAssetStudio/");
            addWebUnit("http://news.cnet.com/");
            addWebUnit("https://medium.com/@georgemount007");
            addWebUnit("http://www.javaworld.com/");
            addWebUnit("http://codurance.com/");
            addWebUnit("https://www.androidexperiments.com/");
            addWebUnit("http://www.javamagazine.mozaicreader.com/#&pageSet=0&page=0&contentItem=0");
            addWebUnit("https://community.oracle.com/community/java/javaone/dukes-choice-awards/dukes-choice/content");
            addWebUnit("https://dzone.com/");
            addWebUnit("https://cleancoders.com/category/clean-code");
            addWebUnit("http://iteratrlearning.com/");
            addWebUnit("https://blog.fogcreek.com/");
            addWebUnit("http://www.joelonsoftware.com/");
            addWebUnit("http://yosefk.com/blog/");
            addWebUnit("https://blog.codinghorror.com/");
            addWebUnit("http://highscalability.com/");
            addWebUnit("https://www.hackerrank.com/");
            addWebUnit("http://www.softwarearchitectures.com/index.html");
            addWebUnit("http://www.melihsakarya.com/");
            addWebUnit("https://www.javacodegeeks.com/");
            addWebUnit("http://tomek.kaczanowscy.pl/");
            addWebUnit("http://dddcommunity.org/");
            addWebUnit("http://tomek.kaczanowscy.pl/");
            addWebUnit("https://dddeurope.com/2017/");
            addWebUnit("http://devnot.com/");
            addWebUnit("https://testing.googleblog.com/search/label/Java");
            addWebUnit("https://testing.googleblog.com/");
            addWebUnit("https://sourcemaking.com/");
            addWebUnit("http://www.swtestacademy.com/");
            addWebUnit("http://codereview.stackexchange.com/");
            addWebUnit("http://codekata.com/");
            addWebUnit("http://www.javaworld.com/article/3123117/development-tools/open-source-java-projects-jenkins-with-docker-part-1.html");
            addWebUnit("https://michaelfeathers.silvrback.com/");
            addWebUnit("http://www.headfirstlabs.com/index.php");
            addWebUnit("https://developer.oracle.com/");
            addWebUnit("https://community.oracle.com/welcome");
            addWebUnit("https://community.oracle.com/community/java/javaone/dukes-choice-awards");
            addWebUnit("http://ryanstutorials.net/linuxtutorial/");
            addWebUnit("https://msdn.microsoft.com/en-us/magazine/mt668395");
            addWebUnit("https://msdn.microsoft.com/en-us/magazine/");
            addWebUnit("http://daron.yondem.com/software/blog/");
            addWebUnit("http://www.gokhanatil.com/");
            addWebUnit("http://www.turhaltemizer.com/");
            addWebUnit("http://daltinkurt.com/Sayfa/3/Hakkimda.aspx");
            addWebUnit("http://www.buraksenyurt.com/");
            addWebUnit("https://aykuttasdelen.wordpress.com/");
            addWebUnit("http://blog.cleancoder.com/");
            addWebUnit("https://distrowatch.com/");
            addWebUnit("https://www.linux.com/");
            addWebUnit("https://simpleprogrammer.com/");
            addWebUnit("https://simpleprogrammer.com/");
            addWebUnit("https://caitiem.com/");
            addWebUnit("https://samnewman.io/");
            addWebUnit("https://www.evernote.com/Home.action?securityRegCode=chromestore#n=d36a12b7-42d5-42a3-9c1a-410e9a09a1a6&s=s460&ses=4&sh=2&sds=2&");
            addWebUnit("http://www.codingthearchitecture.com/");
            addWebUnit("https://javaee-guardians.io/blog/");
            addWebUnit("https://crosp.net/blog/software-architecture/clean-architecture-part-1-databse-vs-domain/");
            addWebUnit("https://spring.io/blog");
            addWebUnit("https://www.geeksforgeeks.org/");
        }

        private void addWebUnit(String url) {
            if(!myApp().getRealmFacade().exists(url))
                myApp().getWebListService().add(url);
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
