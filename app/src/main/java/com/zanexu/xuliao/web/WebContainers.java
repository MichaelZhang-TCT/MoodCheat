package com.zanexu.xuliao.web;

import android.annotation.SuppressLint;
import android.webkit.WebView;

/**
 * Created by zanexu on 2017/7/7.
 */

public class WebContainers {
    private WebView mWebView;

    public WebContainers(WebView webview) {
        this.mWebView = webview;
    }

    /**
     * register the native method
     * @param name
     * @param JSInterface
     */
    @SuppressLint("JavascriptInterface")
    public void addJSInterface(String name, Object JSInterface) {
        mWebView.addJavascriptInterface(JSInterface, name);
    }

    /**
     * invoke the Js method
     * @param method
     * @param arg
     */
    public void invoke(String method, String... arg) {
        String url = "javascript:";
        url += method;
        url += "(";
        for (int i = 0; i < arg.length - 1; i++) {
            url += arg[0] + ",";
        }
        url += arg[arg.length-1];
        url += ")";
        mWebView.loadUrl(url);
    }
}
