package entities.commondialogues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Answer {

    private String id;
    private String answerAr;
    private String answerSe;
    private boolean correct;
    private String soundFile;

    public Answer(String id, String answerAr, String answerSe, String correct, String soundFile) {

        this.answerAr = answerAr;
        this.answerSe = answerSe;
        this.correct = Boolean.parseBoolean(correct);
        this.soundFile = soundFile;
        this.id = id;
    }

    public Answer() {
    }

    public String getAnswerAr() {
        return answerAr;
    }

    public void setAnswerAr(String answerAr) {
        this.answerAr = answerAr;
    }

    public String getAnswerSe() {
        return answerSe;
    }

    public void setAnswerSe(String answerSe) {
        this.answerSe = answerSe;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = Boolean.parseBoolean(correct);
    }

    public String getSoundFile() {
        return soundFile;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<Answer> getAnswers(JSONObject exerciseElem, String type) {
        List<Answer> answers = new ArrayList<Answer>();
        JSONObject answerElem = null;
        JSONArray answersJ = null;
        try {
            answersJ = exerciseElem.getJSONArray("answers");
            for (int j = 0; j < answersJ.length(); j++) {
                answerElem = answersJ.getJSONObject(j);
                Answer a = new Answer();
                a.setId(answerElem.getString("id"));
                a.setAnswerAr(answerElem.getString("ar"));
                a.setAnswerSe(answerElem.getString("se"));
                a.setCorrect(answerElem.getString("answer"));
                if (type.equals("vowel"))
                    a.setSoundFile(answerElem.getString("mp3"));
                answers.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answers;
    }
}
