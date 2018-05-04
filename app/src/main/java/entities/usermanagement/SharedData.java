package entities.usermanagement;

import java.util.List;

/**
 * Created by Lisanu on 2/18/18.
 */

public class SharedData {
    // the suser sharing this data
    // TODO can be replaced with userId only! (the PK of UserProfile class)
    private UserProfile user;

    // data we want to share
    private String data;

    // if the shared data has a picture;
    private String pictureURI;

    // if the shared data has a video
    private String videoURI;

    private List<UserProfile> sharedWith;


    public SharedData() {
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPictureURI() {
        return pictureURI;
    }

    public void setPictureURI(String pictureURI) {
        this.pictureURI = pictureURI;
    }

    public String getVideoURI() {
        return videoURI;
    }

    public void setVideoURI(String videoURI) {
        this.videoURI = videoURI;
    }

    public List<UserProfile> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(List<UserProfile> sharedWith) {
        this.sharedWith = sharedWith;
    }
}
