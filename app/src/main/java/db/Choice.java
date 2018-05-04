package db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lisanu on 2/18/18.
 */

public class Choice extends RealmObject {

    @PrimaryKey
    private Integer id;
    @Required
    private Integer exerciseStepId;
    // string representation of the choice (
    @Required
    private String choice;
    // tells whether the specified answer is correct
    @Required
    private String renderAs;
    //what is the status of this choice item? (currently active, removed/deactivated because
    // it was selected by the user
    @Required
    private String status;

    private Boolean isCorrect;

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExerciseStepId() {
        return exerciseStepId;
    }

    public void setExerciseStepId(Integer exerciseStepId) {
        this.exerciseStepId = exerciseStepId;
    }

    public String getRenderAs() {
        return renderAs;
    }

    public void setRenderAs(String renderAs) {
        this.renderAs = renderAs;
    }

    public Choice() {

    }

    public Choice(String choice, String renderChoiceAs, String status) {
        this.choice = choice;
        this.renderAs = renderChoiceAs;
        this.status = status;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getRenderChoiceAs() {
        return renderAs;
    }

    public void setRenderChoiceAs(String renderChoiceAs) {
        this.renderAs = renderChoiceAs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static List<Choice> getAll(Integer exerciseStepId) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Choice.class);
        List<Choice> choices = query.equalTo("exerciseStepId", exerciseStepId).findAll();
        realm.close();
        return choices;

    }
}
