package db;

import java.util.List;

import interfaces.ListItemClickListener;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Dialogue extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String imagePath;
    @Required
    private String titleAr;
    private String titleSe;
    private String descriptionSe;
    private String descriptionAr;
    private String videoUri;
    private int categoryId;
    private int orderIndex;

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getTitleSe() {
        return titleSe;
    }

    public void setTitleSe(String titleSe) {
        this.titleSe = titleSe;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescriptionSe() {
        return descriptionSe;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescriptionSe(String descriptionSe) {
        this.descriptionSe = descriptionSe;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public static List<Dialogue> getAllInCategory(int categoryId) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Dialogue.class);
        List<Dialogue> d = query.equalTo("categoryId", categoryId).findAll().sort("orderIndex");
        realm.close();
        return d;

    }
}
