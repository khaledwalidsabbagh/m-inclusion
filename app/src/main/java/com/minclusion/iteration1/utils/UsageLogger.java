package com.minclusion.iteration1.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by khaled on 2017-10-06.
 */

public final class UsageLogger {

    private static final String logFileName = "activity.txt";
    private static String userId = "noId";
    private static File logFolder;
    private static File logFile;
    private static Calendar c = Calendar.getInstance();
    private static SimpleDateFormat df = new SimpleDateFormat("hh, mm, dd, MMM, yyyy, a");


    public UsageLogger()
    {

    }
    public static void appendActivity(Context context, String data) {
        logFolder = new File(context.getFilesDir() + "/logs");
        logFile = new File(logFolder.getAbsolutePath() + "/" + logFileName);
        String formatedDate = df.format(c.getTime());
        if (!logFile.exists()) {
            if (!logFolder.exists()) {
                logFolder.mkdir();
            }
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(data +  ", " + formatedDate + ", " + userId + "\n");
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void deleteLogFile() {
//        if(logFile.exists())
//            logFile.delete();
//    }

    public static Intent emailReport(Context context) {
        logFolder = new File(context.getFilesDir() + "/logs");
        File nFile = new File (logFolder, logFileName);
        Uri uri = FileProvider.getUriForFile(context, "com.minclusion.iteration1", nFile);

        String[] TO = {"minklusion@gmail.com"};
//        String [] CC = {""};

        Intent emailIntent = new Intent(Intent.ACTION_SEND); //, Uri.fromParts("mailto", TO[0], null)
        emailIntent.setType("*/");//"vnd.android.cursor.dir/email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Usage report test");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        return Intent.createChooser(emailIntent, "Send mail...");
    }

    public static void setUserId (String id) {
        userId = id;
    }
}
