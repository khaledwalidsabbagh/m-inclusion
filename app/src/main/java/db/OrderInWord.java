package db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.Sort;
import io.realm.annotations.Required;

public class OrderInWord extends RealmObject {

//    added to break the many to many relationship between words and consonants.

    @Required
    private String id;
    @Required
    private String order;
    @Required
    private String consonantId;
    @Required
    private String wordId;
    @Required
    private String videoPath;

    public Integer getId() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getConsonantId() {
        return consonantId;
    }

    public void setConsonantId(String consonantId) {
        this.consonantId = consonantId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public static List<OrderInWord> getAll() {
        Realm realm = Realm.getDefaultInstance();
        List<OrderInWord> c = realm.where(OrderInWord.class).distinct("order");
        realm.close();
        return c;
    }


}

