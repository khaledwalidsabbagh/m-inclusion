package db;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lisanu on 2/18/18.
 */

// TODO to khaled....Initially called Exercise in the UML Diagram. DELETE AFTER READING
public class ExerciseSession extends RealmObject {

    @Required
    // instructions for this session
    private String instruction;
    // list of exercises we will ask in this session
    //private List<ExerciseStep> exercisesInSession;
    //TODO: is this going to be autogenerated?... handle me! - Lisanu
    @PrimaryKey
    private Integer id;
    @Required
    // level of difficulty of this session
    private Integer level;
    // score object associated with this session
    @Required
    private Integer score;

    public ExerciseSession() {

    }

    public ExerciseSession(String instruction, List<ExerciseStep> exercisesInSession, Integer id, Integer level) {
        this.instruction = instruction;
       // this.exercisesInSession = exercisesInSession;
        this.id = id;
        this.level = level;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

 //   public List<ExerciseStep> getExercisesInSession() {
        //read from json
//        int objectCounter = 0;
//        ArrayList<ExerciseStep> exerciseSteps = new ArrayList<>();
//        InputStream inputStream = httpURLConnection.getInputStream();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//        int resId = context.getResources().getIdentifier(PUZZLEFILEPATH, "raw", ctx.getPackageName()).op;//openRawResource(R.raw.vowel);
//
//        categoryStream = context.getResources().openRawResource(resId);
//        byte[] buffer;
//
//        try {
//            buffer = new byte[categoryStream.available()];
//            categoryStream.read(buffer);
//            jsonString = new String(buffer, "UTF-8");
//        String line = "";
//        while ((line = bufferedReader.readLine()) != null) {
//            jContent = jContent + line;
//        }
//        try {
//            JSONArray postObjects = new JSONArray(jContent);
//
//
//            while (objectCounter < postObjects.length()) {
//                JSONObject postObject = (JSONObject) postObjects.get(objectCounter);
//                Post p = new Post();
//                p.setName(postObject.getString("name"));
//                p.setEmail(postObject.getString("email"));
//                p.setId(Integer.parseInt(postObject.getString("id")));
//                p.setDescription(postObject.getString("body"));
//                p.setPostId(Integer.parseInt(postObject.getString("postId")));
//                posts.add(p);
//                objectCounter++;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
      //  return exercisesInSession;
  //  }

//    public void setExercisesInSession(List<ExerciseStep> exercisesInSession) {
//        this.exercisesInSession = exercisesInSession;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}