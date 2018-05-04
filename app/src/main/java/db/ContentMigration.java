package db;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.internal.Table;

/**
 * Created by khaled on 2017-11-14.
 */

public class ContentMigration implements RealmMigration {

    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();
        Log.e("version number: ", String.valueOf(oldVersion));
//        Realm realm;
//        if(oldVersion==11)
//        {
//            schema.get("Consonant").renameField("videoPath", "imagePath");
//            schema.get("Consonant").addField("mainWord", String.class);
//            schema.get("Dialogue").renameField("culturalNoteAr", "descriptionAr");
//            schema.get("Dialogue").renameField("culturalNoteSe", "descriptionSe");
//
//            oldVersion++;
//
//        }


        if (oldVersion == 16) {
            Log.e("old version number: ", String.valueOf(oldVersion));
            schema.get("Choice").addField("isCorrect", Boolean.class);
            oldVersion++;
            Log.e("new version number: ", String.valueOf(oldVersion));
        }

        if (oldVersion == 17) {
            Log.e("old version number: ", String.valueOf(oldVersion));
            schema.get("ExerciseStep").addField("exerciseStepAudioPath", String.class);
            oldVersion++;
        }
        if (oldVersion == 18) {
            Log.e("old version number: ", String.valueOf(oldVersion));
            schema.get("Dialogue").addField("videoUri", String.class);
            oldVersion++;
        }

        if (oldVersion == 19) {
            Log.e("old version number: ", String.valueOf(oldVersion));
            schema.get("CookingStep").addField("image", String.class);
            oldVersion++;
        }

//        if (oldVersion == 14) {
//
//            Log.e("msg", "reached");
//            schema.create("ExerciseSession")
//                    .addField("id", Integer.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("instruction", String.class, FieldAttribute.REQUIRED)
//                    .addField("level", Integer.class, FieldAttribute.REQUIRED)
//                    .addField("score", Integer.class, FieldAttribute.REQUIRED);
//
//
//            schema.create("ExerciseStep")
//                    .addField("id", Integer.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("exerciseSessionId", Integer.class, FieldAttribute.REQUIRED)
//                    .addField("isChoicesFreeText", Boolean.class, FieldAttribute.REQUIRED);
////                    .addField("level", Integer.class, FieldAttribute.REQUIRED);
//
//            schema.create("Question")
//                    .addField("id", Integer.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("exerciseStepId", Integer.class, FieldAttribute.REQUIRED)
//                    .addField("questionString", String.class, FieldAttribute.REQUIRED)
//                    .addField("isGiven", Boolean.class, FieldAttribute.REQUIRED)
//                    .addField("translation", String.class, FieldAttribute.REQUIRED)
//                    .addField("hint", String.class, FieldAttribute.REQUIRED)
//                    .addField("renderAs", String.class, FieldAttribute.REQUIRED);
//
//            schema.create("Choice")
//                    .addField("id", Integer.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("exerciseStepId", Integer.class, FieldAttribute.REQUIRED)
//                    .addField("choice", String.class, FieldAttribute.REQUIRED)
//                    .addField("status", String.class, FieldAttribute.REQUIRED)
//                    .addField("renderAs", String.class, FieldAttribute.REQUIRED);
//
//            oldVersion++;
//
//        }
//        if (oldVersion == 4) {
//            schema.create("Consonant")
//                    .addField("id", String.class, FieldAttribute.REQUIRED)
//                    .addField("name", String.class, FieldAttribute.REQUIRED)
//                    .addField("type", String.class)
//                    .addField("videoPath", String.class);
//
//            schema.create("OrderInWord")
//                    .addField("id", String.class, FieldAttribute.REQUIRED)
//                    .addField("order", String.class, FieldAttribute.REQUIRED)
//                    .addField("consonantId", String.class, FieldAttribute.REQUIRED)
//                    .addField("wordId", String.class, FieldAttribute.REQUIRED)
//                    .addField("videoPath", String.class, FieldAttribute.REQUIRED);
//
//            schema.get("Word").removeField("vowel");
//            schema.get("Word").removeField("vowelType");
//            oldVersion++;
//
//        }

//        if (oldVersion == 9) {
////            schema.get("Consonant").addPrimaryKey("id");
////            schema.get("OrderInWord").addPrimaryKey("id");
////
//            Log.e("Deleting... ", "Dish table");
//            schema.remove("Dish");
//
//            oldVersion++;
////        }

    }

    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof ContentMigration);
    }

}
