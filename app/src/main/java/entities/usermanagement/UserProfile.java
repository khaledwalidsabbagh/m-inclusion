package entities.usermanagement;

import java.util.List;

/**
 * Created by cylic on 2/18/18.
 */

public class UserProfile {
    // unique id generated by the system
    // TODO Lisanu determine how
    private String userId;
    // name of the user
    private String name;
    // nick name to show
    private String userNickName;
    // path to the profile Pic
    private String profilePicURI;
    // connection of this user
    // TODO Lisanu determine how do we actually ad connections (FB,...)
    private List<UserProfile> connections;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(String profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public List<UserProfile> getConnections() {
        return connections;
    }

    public void setConnections(List<UserProfile> connections) {
        this.connections = connections;
    }
}
