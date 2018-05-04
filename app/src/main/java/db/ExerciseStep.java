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

// TODO NOTE to khaled: this class is initially called GameStep on the UML diagram. DELETE LINE AFTER READING
public class ExerciseStep extends RealmObject {
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

    @PrimaryKey
    private Integer id;
    @Required
    private Integer exerciseSessionId;

    @Required
    private Boolean isChoicesFreeText;

    private String exerciseStepAudioPath;

    public String exerciseStepAudioPath() {
        return exerciseStepAudioPath;
    }

    public void exerciseStepAudioPath(String exerciseStepAudiPath) {
        this.exerciseStepAudioPath = exerciseStepAudiPath;
    }

    public ExerciseStep() {
        //Here is how you create some sample exercise steps
        //1. Multiple choice question (where we show the user a video
    }

    public ExerciseStep(List<Question> question, List<Choice> choices, List<String> correctAnswers) {
//        this.question = question;
//        this.choices = choices;
        //     this.correctAnswers = correctAnswers;
    }

   /* public List<Question> getQuestion() {
        return question;
    }*/

//    public void setQuestion(List<Question> question) {
//        this.question = question;
//    }
//
//    public List<Choice> getChoices() {
//        return choices;
//    }
//
//    public void setChoices(List<Choice> choices) {
//        this.choices = choices;
//    }

//    public List<String> getCorrectAnswers() {
//        return correctAnswers;
//    }
//
//    public void setCorrectAnswers(List<String> correctAnswers) {
//        this.correctAnswers = correctAnswers;
//    }

//    public Integer getCurrentQuestionIndex() {
//        return currentQuestionIndex;
//    }
//
//    public void setCurrentQuestionIndex(Integer currentQuestionIndex) {
//        this.currentQuestionIndex = currentQuestionIndex;
//    }

//    public List<String> getUserAnswer() {
//        return userAnswer;
//    }
//
//    public void setUserAnswer(List<String> userAnswer) {
//        this.userAnswer = userAnswer;
//    }

    public Boolean getChoicesFreeText() {
        return isChoicesFreeText;
    }

    public void setChoicesFreeText(Boolean choicesFreeText) {
        isChoicesFreeText = choicesFreeText;
    }

    /**
     * are multiple correctAnswers allowed for this question
     *
     * @return
     */
//    public Boolean multipleChoiceAllowed() {
//        return correctAnswers.size() > 1;
//    }

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
//    public static List<ExerciseStep> getAll(Integer session) {
//        // The Exercise Session class should provide an array list of all exercise sessions
//        Realm realm = Realm.getDefaultInstance();
//        RealmQuery query = realm.where(ExerciseStep.class);
//        List<ExerciseStep> exerciseSteps = query.equalTo("exerciseSessionId", session).findAll();
//        realm.close();
//        return exerciseSteps;
//        // This method should extract and parse all questions in the arraylist returned by ExerciseSession
//    }
}
