package entities.cookandlearn;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lisanu on 1/19/18.
 */

public class Ingredient extends RealmObject {
    @Required
    @PrimaryKey
    private Integer id;
    @Required
    private String name;
    private String unit;
    private Double quantity;
    private String image;

    // Foreign key field liking this cooingstep to a dish
    @Required
    private Integer dishId;

    public Ingredient() {
    }

    public Ingredient(Integer ingredientID, String name, double quantity, Integer dishId) {
        this.id = ingredientID;
        this.name = name;
        this.quantity = quantity;
        this.dishId = dishId;
    }

    public Ingredient(Integer ingredientID, String name,
                      double quantity, String unit, String image, Integer dishId) {
        this.id = ingredientID;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.image = image;
        this.dishId = dishId;
    }

    public static List<Ingredient> getEmpty() {
        Realm realm = Realm.getDefaultInstance();
        List<Ingredient> ingredients = realm.where(Ingredient.class).equalTo("dishId", -1).findAll();
        return ingredients;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /*
     * Field linking this ingredient object to the dish it belongs to
     */
    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    /*
     * getAll returns all ingredients registered for a dish
     */
    public static List<Ingredient> getAll(Integer dishId) {
        Realm realm = Realm.getDefaultInstance();
        List<Ingredient> ingredients = realm.where(Ingredient.class).equalTo("dishId",
                dishId).findAll();
        return ingredients;
    }

    /*
     * getAll returns all ingredients registered in the system
     */
    public static List<Ingredient> getAll() {
        Realm realm = Realm.getDefaultInstance();
        List<Ingredient> ingredients = realm.where(Ingredient.class).findAll();
        return ingredients;
    }
}
