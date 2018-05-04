package db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Category extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String se;
    @Required
    private String ar;

    public int getId() {
        return id;
    }

    public String getSe() {
        return se;
    }

    public String getAr() {
        return ar;
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

    public static List<Category> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Category.class);
        List<Category> categories = query.findAll();
        return categories;
    }
}
