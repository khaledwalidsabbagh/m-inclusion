package entities.game;

/**
 *
 * Helper represents hints given for the user. Those hints can appear in questions or choices
 *
 * Created by Lisanu on 2/18/18.
 */

public class Helper {
    private String helperImagePath;
    private String helperAudioPath;
    private String helperText;

    public Helper(String helperImagePath, String helperAudioPath, String helperText) {
        this.helperImagePath = helperImagePath;
        this.helperAudioPath = helperAudioPath;
        this.helperText = helperText;
    }

    public String getHelperImagePath() {
        return helperImagePath;
    }

    public void setHelperImagePath(String helperImagePath) {
        this.helperImagePath = helperImagePath;
    }

    public String getHelperAudioPath() {
        return helperAudioPath;
    }

    public void setHelperAudioPath(String helperAudioPath) {
        this.helperAudioPath = helperAudioPath;
    }

    public String getHelperText() {
        return helperText;
    }

    public void setHelperText(String helperText) {
        this.helperText = helperText;
    }
}
