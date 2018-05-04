package entities.game;

/**
 * This is just an optimization. When we add a multiuser feature, its better to have a class
 * that we can call to see what the user have scored in different exercises
 *
 * Created by Lisanu on 2/18/18.
 */

public class Score {
    // TODO decide how to identify the user... NOTE TO Khaled useful for multiuser settings!
    // Nothing to do with current task
    // Nice to have the <see>highScore</see
    private Integer userId;
    //refers to the ExercisesSession Object. ExerciseSession is a heavy object, so it will be better
    // if we just store a stub (the id) here
    private Integer exercisesSessionId;
    // stores the highScore in this game!
    private Integer highScore;
    // used by ExerciseSession class to see the current progress the user is making
    private Integer currentScore;

    public Score(Integer userId, Integer exercisesSessionId, Integer highScore, Integer currentScore) {
        this.userId = userId;
        this.exercisesSessionId = exercisesSessionId;
        this.highScore = highScore;
        this.currentScore = currentScore;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExercisesSessionId() {
        return exercisesSessionId;
    }

    public void setExercisesSessionId(Integer exercisesSessionId) {
        this.exercisesSessionId = exercisesSessionId;
    }

    public Integer getHighScore() {
        return highScore;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }
}
