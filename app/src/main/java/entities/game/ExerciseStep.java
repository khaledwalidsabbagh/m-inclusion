package entities.game;

import java.util.ArrayList;
import java.util.List;

import db.Choice;
import db.Question;
import entities.commondialogues.Exercise;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lisanu on 2/18/18.
 */

// TODO NOTE to khaled: this class is initially called GameStep on the UML diagram. DELETE LINE AFTER READING
public class ExerciseStep {


    private Integer id;

    private Integer exerciseSessionId;

    // E.g by changing the question constructor you can create. Note List doesn't have a builder
    // so the example bellow won't work and need to be re-writtten
    // E.g h_j
    // question = new ArrayList().add(new Question("h", RenderingOptions.TEXT, false, null, null).
    // add(new Question("", RenderingOptions.BLANK, false, null, null)
    // add(new Question("j", RenderingOptions.BLANK, false, null, null)
    // E.g multiple choice for the video (e)
    // question = new ArrayList().add(new Question("e", RenderingOptions.VIDEO, false, null, <hint>).
    private List<Question> questions = new ArrayList<>();
    // stores choices available for the question. Choices are strings, but can be rendered
    // as image, video, string (including a single character). Choices can also be disabled,
    // removed, or permanent when they are selected (depending on the game)
    private List<Choice> choices = new ArrayList<>();


    // when the choices are free text (typing), then recording all choices is just so hard.
    // this is a short-circuit flag for that. If the choices are free text, allow all keys in the
    // keyboard and let the user type
    private Boolean isChoicesFreeText;

    // stores the answer to the question as a string. The answer is a string representation of
    // the actual answer which can be an image, video
    // or a string (single char too).
     private List<String> correctAnswers = new ArrayList<>();
    // store which question the user is attempting. If we are to help the user by solving part
    // of the question, e.g filling the first blank space, we need the currentQuestionIndex

    // stores the users attempts! These should match with <see>answer</see>
    // private List<String> userAnswer;

    public List<Question> getQuestion() {
        return questions;
    }

    public void setQuestion() {
        this.questions = Question.getAll(getId());
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public List<String> getCorrectAnswer() {
        return correctAnswers;
    }


    public void setChoices() {
        this.choices = Choice.getAll(getId());
    }


    public ExerciseStep(Integer id) {
        this.exerciseSessionId = id;
        this.choices = Choice.getAll(id);
        this.questions = Question.getAll(id);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExerciseSessionId() {
        return exerciseSessionId;
    }

    public void setExerciseSessionId(Integer exerciseSessionId) {
        this.exerciseSessionId = exerciseSessionId;

    }

//    public ExerciseStep(List<Question> question, List<Choice> choices, List<String> correctAnswers) {
//        this.question = question;
//        this.choices = choices;
//    }


    public Boolean getChoicesFreeText() {
        return isChoicesFreeText;
    }

    public void setChoicesFreeText(Boolean choicesFreeText) {
        isChoicesFreeText = choicesFreeText;
    }

    //TODO This code assumes that the answers have to be in a certain order
    // THis may not be the case, TODO fix if other type of questions come along
//    public Boolean checkAnswer() {
//
//        for (int i = 0; i < correctAnswers.size(); i++) {
//            String answer = correctAnswers.get(i);
//
//            if (userAnswer.size() >= i) {
//                if (!userAnswer.get(i).equalsIgnoreCase(answer))
//                    return false;
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }
    public static List<db.ExerciseStep> getAll(Integer session) {
        List<db.ExerciseStep> list = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(db.ExerciseStep.class);

        List<db.ExerciseStep> exerciseSteps = query.equalTo("exerciseSessionId", session).findAll();

        list.addAll(realm.copyFromRealm(exerciseSteps));

        realm.close();

        return list;
    }

//    public static List<Question> copyAll(List<ExerciseStep> exerciseSteps) {
//        public static ArrayList<Question> copyAll(List<Question> questions, String choice){
//
//            ArrayList<Question> copy = new ArrayList<>();
//
//            int i = 0;
//            for (int j = i; j < questions.size(); j++) {
//                Question question = new Question();
//                if (questions.get(j).getQuestionString().equals(choice))
//                    question.setGiven(true);
//                else
//                    question.setGiven(questions.get(j).getGiven());
//
//                question.setQuestionString(questions.get(j).getQuestionString());
//                copy.add(question);
//            }
//            return copy;
//        }
//    }
}
