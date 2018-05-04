package entities.cookandlearn;

import java.util.ArrayList;
import java.util.List;

import entities.commondialogues.Figure;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lisanu on 1/21/18.
 */

public class CookingStep extends RealmObject {
    @Required
    @PrimaryKey
    private Integer id;

    //represents the step number E.g. 1, 2, 3
    @Required
    private Integer stepNumber;
    //stores the instruction for that step (E.b boil the egg for 5 mins)
    private String text;

    // Foreign key field liking this "cooing step" to a dish
    private Integer dishId;

    // image to be displayed in the cooking step
    private String image;

    //List of figures for this step
    @Ignore
    private List<Figure> figures = new ArrayList<>();

    // List of ingredients needed for this step
    @Ignore
    private List<Ingredient> ingredients = new ArrayList<>();

    public CookingStep() {
    }

    public CookingStep(Integer stepNumber, String text, Integer dishId) {
        this.stepNumber = stepNumber;
        this.text = text;
        this.dishId = dishId;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addFigure(Figure fig){
        figures.add(fig);
    }

    public void addIngredient(Ingredient ing){
        ingredients.add(ing);
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static List<CookingStep> getAll(Integer dishId) {
        Realm realm = Realm.getDefaultInstance();
        List<CookingStep> steps = realm.where(CookingStep.class).equalTo("dishId",
                                                dishId).findAll();
        return steps;
    }

    @Override
    public String toString() {
        return "CookingStep{" +
                "stepNumber=" + stepNumber +
                ", text='" + text + '\'' +
                '}';
    }
}
