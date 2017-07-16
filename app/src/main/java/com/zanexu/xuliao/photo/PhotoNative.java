package com.zanexu.xuliao.photo;

import android.webkit.JavascriptInterface;

/**
 * 拍照端的Jsbridge对象
 * Created by zanexu on 2017/7/8.
 */

public class PhotoNative {

    private PhotoEnginer photoEnginer;

    public PhotoNative(PhotoEnginer enginer) {
        this.photoEnginer = enginer;
    }

    @JavascriptInterface
    public void takePhoto() {
        photoEnginer.execute();
    }
}
