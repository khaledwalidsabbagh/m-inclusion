package interfaces;
import android.media.MediaPlayer;
import java.io.IOException;
import db.Statement;

public interface IMediaHelper {
     void startRecording(int tagId);
     void replayRecording(Statement statement);
     void stopRecording();
     void playRecording(MediaPlayer mPlayer, int speed);
}
