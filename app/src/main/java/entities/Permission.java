package entities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permission extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private Context context;
    private static final int WRITE_EXTERNAL_RECORD = 0;

    public Permission(Context context) {
        this.context = context;
    }

    public boolean grantWriteAudioPermission() {
        int permissionToSave = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionToRecord = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    !ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.RECORD_AUDIO)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, WRITE_EXTERNAL_RECORD);
                return false;
            }
        }
        return true;
    }
}
