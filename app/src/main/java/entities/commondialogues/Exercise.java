package entities.commondialogues;

import android.content.Context;

import com.minclusion.iteration1.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise extends BaseActivity {

    private String type;
    private String question;
    private String videoPath;
    private List<Answer> answers = new ArrayList<Answer>();
    private String correctAnswer;
    private String id;
    private String level;
    private Context context;
    private InputStream categoryStream;
    private Integer resId = 0;
    private static final String VOWELFILEPATH = "vowel";
    private static final String CONSONANTFILEPATH = "consonants";
    private static final String PUZZLEFILEPATH = "fibvowel";


    public Exercise(String type)
    {
        this.type = type;
    }
    public Exercise(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Exercise(String type, String question, String id, String audio) {
        this.type = type;
        this.question = question;
        this.videoPath = videoPath;
        this.id = id;
    }

    public Exercise() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public ArrayList getAll(String level) {
        JSONObject exercise;
        String jsonString;

        ArrayList<Exercise> exercises = new ArrayList<>();
        if (type.equals("vowel"))
            resId = context.getResources().getIdentifier(VOWELFILEPATH, "raw", context.getPackageName());//openRawResource(R.raw.vowel);
        else if (type.equals("consonants"))
            resId = context.getResources().getIdentifier(CONSONANTFILEPATH, "raw", context.getPackageName());//openRawResource(R.raw.vowel);
        else
            resId = context.getResources().getIdentifier(PUZZLEFILEPATH, "raw", context.getPackageName());//openRawResource(R.raw.vowel);

        categoryStream = context.getResources().openRawResource(resId);
        byte[] buffer;

        try {
            buffer = new byte[categoryStream.available()];
            categoryStream.read(buffer);
            jsonString = new String(buffer, "UTF-8");
            exercise = new JSONObject(jsonString);

            JSONArray exercisesJ = null;
            JSONObject exerciseElem;
            exercisesJ = exercise.getJSONArray("exercises");

            for (int i = 0; i < exercisesJ.length(); i++) {
                exerciseElem = exercisesJ.getJSONObject(i);
                Exercise e = new Exercise();

                if (exerciseElem.getString("level").equals(level)) {
                    e.setType(exerciseElem.getString("type"));
                    e.setLevel(exerciseElem.getString("level"));
                    e.setQuestion(exerciseElem.getString("question"));
                    e.setVideoPath(exerciseElem.getString("video"));
                    e.setId(exerciseElem.getString("id"));

                    if (e.getLevel().equals(level) && exerciseElem.getString("type").equals("mc")) { //getting the answers for the exercise
                        Answer a = new Answer();
                        List<Answer> answers = a.getAnswers(exerciseElem, type);
                        Collections.shuffle(answers);
                        e.setAnswers(answers);
                    }
                    exercises.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.shuffle(exercises);
        try {
            categoryStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exercises;
    }
}
