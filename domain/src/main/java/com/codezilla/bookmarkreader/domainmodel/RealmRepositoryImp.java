package com.codezilla.bookmarkreader.domainmodel;

import android.content.Context;

import com.codezilla.bookmarkreader.domainmodel.exception.CustomRealException;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by davut on 8/21/2017.
 */

public class RealmRepositoryImp implements IWebUnitRepository {
    private final RealmConfiguration config;

    public RealmRepositoryImp(Context context) {
        Realm.init(context);
        this.config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Override
    public void clearWebUnits()
    {
        try (Realm realm = realm())
        {
            realm.beginTransaction();
            realm.delete(WebUnit.class);
            realm.commitTransaction();
        }
    }

    private Realm realm() {

        return Realm.getInstance(config);
    }


    @Override
    public List<WebUnit> webUnits() {
        try (Realm realm = realm())
        {
            RealmResults<WebUnit> wunits =  realm.where(WebUnit.class).findAll();
            return realm.copyFromRealm(wunits);
        }
    }


    @Override
    public void add(WebUnit wu) {
        try (Realm realm = realm() ){
            realm.beginTransaction();
            WebUnit realmWu = realm.copyToRealm(wu);
            realm.insertOrUpdate(realmWu);
            realm.commitTransaction();
            realm.refresh();
        }
    }

    @Override
    public  void close() {
        realm().close();
    }



    @Override
    public WebUnit getWebUnit(String url) {
        try (Realm realm = realm()) {
            WebUnit wu= queryWebUnit(url, realm);
            return realm.copyFromRealm(wu);
        }
    }

    private WebUnit queryWebUnit(String url, Realm realm) {
        WebUnit wu = realm.where(WebUnit.class).equalTo(WebUnit.COL_URL, url).findFirst();
        if(wu == null)
            throw new CustomRealException("NO webunit exists with given url");
        return wu;
    }

    @Override
    public boolean exists(String url) {
        try (Realm realm = realm()) {
            return realm.where(WebUnit.class).equalTo(WebUnit.COL_URL, url).findFirst() != null;
        }
    }



    @Override
    public void update(WebUnit wu) {
        if(!exists(wu.getUrl()))
            throw new CustomRealException(String.format("record not exists[%s]",wu.getUrl()));
        try(Realm realm = realm())
        {
            realm.beginTransaction();
            WebUnit realmWu = realm.copyToRealmOrUpdate(wu);
            realm.insertOrUpdate(realmWu);
            realm.commitTransaction();
            realm.refresh();
        }
    }

    @Override
    public long count() {
        try (Realm realm = realm()) {
            return realm.where(WebUnit.class).count();
        }
    }
}
