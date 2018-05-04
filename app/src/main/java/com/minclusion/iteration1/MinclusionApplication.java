package com.minclusion.iteration1;

import android.app.Application;
import android.widget.Toast;

import java.io.FileNotFoundException;

import db.*;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.log.RealmLog;

/**
 * Created by mihai on 6/21/17.
 */

public class MinclusionApplication extends Application {

    private Realm oneRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .assetFile("minclusion_content.realm")
                .schemaVersion(20)
                .build();
        try {
            Realm.migrateRealm(config, new ContentMigration());
        }
        catch (FileNotFoundException ignored) {
        }
        oneRealm.setDefaultConfiguration(config);

    }

}