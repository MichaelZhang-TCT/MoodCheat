package com.zanexu.xuliao.login;

import android.webkit.JavascriptInterface;

import com.zanexu.xuliao.data.sp.MySharedPre;

/**
 * Created by Zane on 2017/7/14.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class FirstLoginNative {

    @JavascriptInterface
    public void isLoginSuccess(boolean isSuccess) {
        MySharedPre.getInstance().setIsFirstLogin(!isSuccess);
    }

}
