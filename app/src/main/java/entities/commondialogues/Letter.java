package entities.commondialogues;

/**
 * Created by khaled1 on 2018-02-13.
 */

public class Letter {
    // name
    private String name;

    //consonant or vowel
    private String type;

    private String audioFilePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }
}
