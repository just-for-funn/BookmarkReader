package com.codezilla.bookmarkreader.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.codezilla.bookmarkreader.application.OkHttpClientImp;
import com.codezilla.bookmarkreader.domainmodel.ILogRepository;
import com.codezilla.bookmarkreader.domainmodel.RealmLogRepositoryImp;
import com.codezilla.bookmarkreader.domainmodel.RealmRepositoryImp;
import com.codezilla.bookmarkreader.domainmodel.WebUnitContentUpdater;
import com.codezilla.bookmarkreader.exception.DomainException;

import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;

/**
 * Created by davut on 9/7/2017.
 */

public class BackgroundUpdaterTask extends AsyncTask<Void ,Void , Boolean > {
    final static String TAG = BackgroundUpdaterTask.class.getSimpleName();
    Context context;

    public BackgroundUpdaterTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        ILogRepository logRepository = new LogCatDecorator(new RealmLogRepositoryImp(context));
        try
        {
            WebUnitContentUpdater contentUpdater = new WebUnitContentUpdater(new OkHttpClientImp(),
                    new RealmRepositoryImp( context) ,
                    new MockHtmlComparer() , logRepository ,
                    new FaviconExtractor(),
                    new BoilerplateArticlConverterAdapter()
            );
            contentUpdater.updateAll();
            return new Boolean(true);
        }
        catch (Exception e)
        {
            logRepository.error("Syncronization failed:"+e.getMessage());
            return new Boolean(false);
        }
    }

}
