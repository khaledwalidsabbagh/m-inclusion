package db;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Word extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String se;
    @Required
    private String ar;
    @Required
    private String videoPath;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int statementId;

    public int getId() {
        return id;
    }

    public String getSe() {
        return se;
    }

    public String getAr() {
        return ar;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public int getStatementId() {
        return statementId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setStatementId(int statementId) {
        this.statementId = statementId;
    }

    public static Word getWord(int statementId) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Word> query = realm.where(Word.class);
        Word w = query.equalTo("statementId", statementId).findFirst();
        realm.close();
        return w;
    }

//    public static List<Word> getAllWords() {
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults<Word> query = realm.where(Word.class).distinct("se").sort("vowel", Sort.DESCENDING);
//        List<Word> s = query;
//        return s;
//    }

    public static ArrayList<Word> getWordsPerLetter(String vowelName) {

        Realm realm = Realm.getDefaultInstance();
        List<Word> s = realm.where(Word.class).equalTo("type", "vowel").contains("se", vowelName, Case.SENSITIVE)
                .distinct("se").sort("se");

        Pattern p = Pattern.compile("[a-z*äåö]*");
        ArrayList<Word> sLowerCase = new ArrayList<Word>();

        int limit = s.size();
        if (s.size() >= 12)
            limit = 12;

        for (int i = 0; i < limit; i++) {
            if (p.matcher(s.get(i).getSe()).matches()) {
                sLowerCase.add(s.get(i));
            }
        }

        Collections.shuffle(sLowerCase);
        realm.close();
        return sLowerCase;
    }

}

