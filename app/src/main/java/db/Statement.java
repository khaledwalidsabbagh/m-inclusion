package db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Statement extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String se;
    @Required
    private String ar;
    @Required
    private String soundPath;
    private int dialogueId;
    private int orderIndex;

    public int getId() {
        return id;
    }

    public String getSe(boolean isPractice) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Word> query = realm.where(Word.class);
        Word word = query.equalTo("statementId", this.id).findFirst();

        if (word != null) {
            if (!isPractice) {
                return se.replace(word.getSe(), "<font color=#ee6e73>" + word.getSe() + "</font>");
            } else {
                return se.replace(word.getSe(), "<font color=#ee6e73> ---------- </font>");
            }
        }
        return se;
    }

    public String getAr() {
        return ar;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public int getDialogueId() {
        return dialogueId;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public void setDialogueId(int dialogueId) {
        this.dialogueId = dialogueId;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public static List<Statement> getStatementsInDialogue(int dialogueId) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Statement> query = realm.where(Statement.class);
        List<Statement> s = query.equalTo("dialogueId", dialogueId).findAll().sort("orderIndex");
        realm.close();
        return s;
    }
}