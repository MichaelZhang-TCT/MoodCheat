package com.zanexu.xuliao.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.zanexu.xuliao.Utils;

import java.io.File;

/**
 * the engine to start take photo and return the file path
 * Created by zanexu on 2017/7/8.
 */

public class PhotoEnginer {
    private File tempFile;
    private Activity context;
    private Uri contentUri;
    public static final int PHOTO_RESULT = 1234;

    public PhotoEnginer(Activity context) {
        this.context = context;
        tempFile = Utils.createFile();
        contentUri = Utils.FileUri2ContentUri(context, tempFile);
    }

    public void execute() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", contentUri);
        context.startActivityForResult(intent, PHOTO_RESULT);
    }

    public String getFilePath() {
        return tempFile.getPath();
    }
}
