package com.zanexu.xuliao.login;

import android.webkit.JavascriptInterface;

import com.zanexu.xuliao.data.sp.MySharedPre;

/**
 * Created by zanexu on 2017/7/8.
 */

public class LoginNative {

    private QQLoginEnginer enginer;

    public LoginNative(QQLoginEnginer enginer) {
        this.enginer = enginer;
    }

    @JavascriptInterface
    public void login() {
        enginer.QQLogin();
    }

}
