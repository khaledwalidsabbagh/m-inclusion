package db;

import io.realm.annotations.RealmModule;

@RealmModule(classes = { Word.class, Statement.class, Dialogue.class, Category.class, Vowel.class })
public class ContentModule {}