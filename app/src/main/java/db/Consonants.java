// Please note : @LinkingObjects and default values are not represented in the schema and thus will not be part of the generated models
package com.minclusion.iteration1;

import io.realm.annotations.Required;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Consonants extends RealmObject {
    @Required
    private String id;
    @Required
    private String name;
    private String type;
    private String videoPath;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getVideoPath() { return videoPath; }

    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }


}
