// Please note : @LinkingObjects and default values are not represented in the schema and thus will not be part of the generated models
package com.minclusion.iteration1;

import io.realm.annotations.Required;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class OrderInWords extends RealmObject {
    @Required
    private String id;
    @Required
    private String order;
    @Required
    private String consonantId;
    @Required
    private String wordId;
    @Required
    private String videoPath;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getOrder() { return order; }

    public void setOrder(String order) { this.order = order; }

    public String getConsonantId() { return consonantId; }

    public void setConsonantId(String consonantId) { this.consonantId = consonantId; }

    public String getWordId() { return wordId; }

    public void setWordId(String wordId) { this.wordId = wordId; }

    public String getVideoPath() { return videoPath; }

    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }


}
