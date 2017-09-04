package com.codezilla.bookmarkreader.domainmodel;

import android.content.Context;

import com.codezilla.bookmarkreader.domainmodel.exception.CustomRealException;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by davut on 8/21/2017.
 */

public class RealmFacade implements IRealmFacade {
    private final RealmConfiguration config;

    public RealmFacade(Context context) {
        Realm.init(context);
        this.config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Override
    public void clearWebSites()
    {
        Realm realm = realm();
        realm.beginTransaction();
        realm.delete(WebUnit.class);
        realm.commitTransaction();
    }

    private Realm realm() {
       return Realm.getInstance(config);
    }


    @Override
    public List<WebUnit> webUnits() {
        return realm().where(WebUnit.class).findAll();
    }

    @Override
    public void addSite(String anyUrl) {
        Realm realm = realm();
        realm.beginTransaction();
        WebUnit webSite = realm.createObject(WebUnit.class);
        webSite.setUrl(anyUrl);
        realm.commitTransaction();
    }

    @Override
    public  void close() {
        realm().close();
    }

    @Override
    public void addSiteContent(String url, String content) {
        Realm realm = realm();
        WebUnit wu = getWebUnit(url);
        realm.beginTransaction();
        WebUnitContent webUnitContent =  realm.createObject(WebUnitContent.class);
        webUnitContent.setContent(content);
        wu.setLatestContent(webUnitContent);
        realm.commitTransaction();
    }

    @Override
    public String getWebUnitContent(String url) {
        WebUnit wu = getWebUnit(url);
        WebUnitContent wuc= wu.getLatestContent();
        return wuc.getContent();
    }

    @Override
    public WebUnit getWebUnit(String url) {
        WebUnit wu = realm().where(WebUnit.class).equalTo(WebUnit.COL_URL, url).findFirst();
        if(wu == null)
            throw new CustomRealException("NO webunit exists with given url");
        return wu;
    }

    @Override
    public boolean exists(String url) {
        return realm().where(WebUnit.class).equalTo(WebUnit.COL_URL , url).findFirst() != null;
    }
}
