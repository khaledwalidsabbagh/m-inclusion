package db;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Vowel extends RealmObject {

    @Required
    private String id;
    @Required
    private String name;
    @Required
    private String type;
    @Required
    private String videoPath;

    private String orderInWord;

    @Required
    private String wordId;

    public Integer getId() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderInWord() {
        return orderInWord;
    }

    public void setOrderInWord(String orderInWord) {
        this.orderInWord = orderInWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public static List<Vowel> getAll() {
        Realm realm = Realm.getDefaultInstance();
        List<Vowel> v = realm.where(Vowel.class).distinct("name").sort("name", Sort.ASCENDING);
        realm.close();
        return v;
    }

    public List<Vowel> getVowelsinWord(String wordId, String vowel, String vowelType) { // public List<Vowel> getVowelsinWord(String wordId, String vowel, String vowelType) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Vowel> query = realm.where(Vowel.class).equalTo("wordId", wordId)
                .equalTo("name", vowel)
                .equalTo("type", vowelType);
        List<Vowel> v = query.findAllSorted("orderInWord", Sort.ASCENDING);
        realm.close();
        return v;
    }

    public static Vowel get(Integer id) {
        Realm realm = Realm.getDefaultInstance();
        Vowel v = realm.where(Vowel.class).equalTo("id", Integer.toString(id)).findFirst();
        realm.close();
        return v;
    }

    public static boolean getPosition(String wordId, String order, String vowelType) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Vowel> query = realm.where(Vowel.class).equalTo("wordId", wordId)
                .equalTo("orderInWord", order)
                .equalTo("type", vowelType);

        boolean isFound = false;

        if (query.count() > 0)
            isFound = true;

        realm.close();
        return isFound;
    }
}

