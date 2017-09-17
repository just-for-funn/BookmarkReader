package com.codezilla.bookmarkreader.domainmodel;

import android.content.Context;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by davut on 9/7/2017.
 */

public class RealmLogRepositoryImp implements ILogRepository {


    private final RealmConfiguration config;

    public RealmLogRepositoryImp(Context context)
    {
        Realm.init(context);
        this.config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Override
    public List<Log> logs() {
        try(Realm realm = realm())
        {
            RealmResults<Log> rr = realm().where(Log.class).findAll();
            return realm.copyFromRealm(rr);
        }
    }

    @Override
    public void info(String message) {
        add(message , Log.INFO , now());
    }

    private void add(String message, int level, Date date) {
        try(Realm realm = realm()) {
            realm.beginTransaction();
            Log logOnj = realm.createObject(Log.class);
            logOnj.setDate(date);
            logOnj.setMsg(message);
            logOnj.setType(level);
            realm.commitTransaction();
        }
    }



    private Date now() {
        return new Date(System.currentTimeMillis());
    }

    @Override
    public void warning(String message) {
            add(message , Log.WARNING , now());
    }

    @Override
    public void error(String message) {
        add(message , Log.ERROR , now());
    }


    @Override
    public void clear() {
        Realm realm =  realm();
        realm.beginTransaction();
        realm.delete(Log.class);
        realm.commitTransaction();
    }

    @Override
    public void close() {
        realm().close();
    }

    private Realm realm() {
        return Realm.getInstance(config);
    }
}
