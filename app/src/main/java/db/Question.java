package db;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lisanu on 2/18/18.
 */

public class Question extends RealmObject {
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

    @PrimaryKey
    private Integer id;
    @Required
    private Integer exerciseStepId;
    @Required
    //stores the question as a string (can be video, image, string (or a char))
    private String questionString;
    @Required
    // what information is questionString supposed to represent?
    private String renderAs;
    @Required
    // is this question a given. E.g h_j is composed of three questions (h, _, and j). and h, j
    // are given
    private Boolean isGiven;
    @Required
    // some games need to show the translation of the question in arabic?
    // E.g a fill in the blank with ingredients question may show to the user the arabic word,
    // and ask for a swedish
    private String translation;
    //TODO: Not sure if this is going to be required, but doesn't hurt to think ahead
    @Required
    private String hint;


    public Question() {
    }

    public Question(String questionString, String renderQuestionStringAs,
                    Boolean isGiven, String translation, String hint) {
        this.questionString = questionString;
        this.renderAs = renderQuestionStringAs;
        this.isGiven = isGiven;
        this.translation = translation;
        this.hint = hint;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getRenderQuestionStringAs() {
        return renderAs;
    }

    public void setRenderQuestionStringAs(String renderQuestionStringAs) {
        this.renderAs = renderQuestionStringAs;
    }

    public Boolean getGiven() {
        return isGiven;
    }

    public void setGiven(Boolean given) {
        isGiven = given;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public static List<Question> getAll(Integer step) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Question.class);
        List<Question> questions = query.equalTo("exerciseStepId", step).findAll();
        realm.close();
        return questions;
    }

    public static ArrayList<Question> copyAll(List<Question> questions, String choice){

        ArrayList<Question> copy = new ArrayList<>();

        //int i = 0;
        for (int j = 0; j < questions.size(); j++) {
            Question question = new Question();
            if (questions.get(j).getQuestionString().equals(choice))
                question.setGiven(true);
            else
                question.setGiven(questions.get(j).getGiven());

            question.setQuestionString(questions.get(j).getQuestionString());
            copy.add(question);
        }
        return copy;
    }
}
