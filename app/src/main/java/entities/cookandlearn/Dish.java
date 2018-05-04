package entities.cookandlearn;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.OrderInWord;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import static io.realm.Case.INSENSITIVE;

/*
 * Dish is a DAO class that handles getting the list of dishes stored in the datastore
 * using realm
 */
public class Dish extends RealmObject {
    @Required
    @PrimaryKey
    private Integer id;
    @Required
    private String name;
    private String description;
    private Double costToPrepare;
    private String type;
    private String image;

    /*
     * Default constructor for retrieving dishes from the data store through
     * a dish object
     */
    public Dish(){ }

    public Dish(Integer id, String name, double costToPrepare) {
        this.id = id;
        this.name = name;
        this.costToPrepare = costToPrepare;
    }

    public Dish(Integer id, String name, String description,
                double costToPrepare, String type, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.costToPrepare = costToPrepare;
        this.type = type;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCostToPrepare() {
        return costToPrepare;
    }

    public void setCostToPrepare(Double costToPrepare) {
        this.costToPrepare = costToPrepare;
    }

    public static List<Dish> getAll() {
        Realm realm = Realm.getDefaultInstance();
        List<Dish> dishes = realm.where(Dish.class).findAll();
        //Log.e("Number of dishes : ", String.valueOf(dishes.size()));
        return dishes;
    }

    /**
     * returns the list of dishes that are of a certain type (meal, appetizers, fika)
     * @param dishType type os dish
     * @return dishes of that type stored in the system
     */
    public static List<Dish> getDishesByType(String dishType) {
        Realm realm = Realm.getDefaultInstance();
        List<Dish> dishes = realm.where(Dish.class).contains("type", dishType, INSENSITIVE)
                .findAll();
        //Log.e("Number of : " + dishType + "s", String.valueOf(dishes.size()));
        return dishes;
    }
}