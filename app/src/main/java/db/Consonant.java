package db;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.Required;

public class Consonant extends RealmObject {

    @Required
    private String id;
    @Required
    private String name;
    @Required
    private String type;
    @Required
    private String imagePath;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String mainWord;

    public String getMainWord() {
        return mainWord;
    }

    public void setMainWord(String mainWord) {
        this.mainWord = mainWord;
    }

    public Integer getId() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static List<Consonant> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Consonant> q = realm.where(Consonant.class);
        List<Consonant> c = q.distinct("name");
        realm.close();
        return c;
    }

    public static ArrayList<Word> getAllInWords(String consonantId) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Consonant> q = realm.where(Consonant.class);
        Consonant cons = q.equalTo("id", consonantId).findFirst();
        // fetch all orderinwords that contain
        Log.e("Consonant id", Integer.toString(cons.getId()));
        List<OrderInWord> s = realm.where(OrderInWord.class).contains("consonantId", cons.getId().toString())
                .distinct("wordId").sort("id");

        RealmQuery<Word> w = realm.where(Word.class);

        for (OrderInWord o : s) {
            w.or().equalTo("id", Integer.parseInt(o.getWordId()));
        }

        ArrayList<Word> list = new ArrayList(w.findAll());

        realm.close();
        return list;
    }

    public static Consonant get(Integer id) {
        Realm realm = Realm.getDefaultInstance();
        Consonant c = realm.where(Consonant.class).equalTo("id", Integer.toString(id)).findFirst();
        realm.close();
        return c;
    }
}

