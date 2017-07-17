package com.zanexu.xuliao.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zanexu.xuliao.Config;
import com.zanexu.xuliao.Utils;
import com.zanexu.xuliao.data.sp.MySharedPre;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zanexu on 2017/7/7.
 */

public class CustomWebView extends WebView{

    private Context mContext;

    public CustomWebView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * custom the webview
     */
    private void init() {
        getSettings().setUseWideViewPort(true);
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setAllowContentAccess(true);


        saveData(getSettings());
        newWin(getSettings());
        setWebViewClient(webViewClient);
        setWebChromeClient(webChromeClient);
    }

    /**
     * 多窗口的问题
     * html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
     * 然后 复写 WebChromeClient的onCreateWindow方法
     */
    private void newWin(WebSettings mWebSettings) {
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        if (Utils.isConnected(mContext)) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        File cacheDir = mContext.getCacheDir();
        if (cacheDir != null){
            String appCachePath  = cacheDir.getAbsolutePath();
            mWebSettings.setDomStorageEnabled(true);
            mWebSettings.setDatabaseEnabled(true);
            mWebSettings.setAppCachePath(appCachePath);
        }
    }

    /**
     * 保持url在同一个webview中加载，以http://image/为host的url需要按照照片来拦截处理
     * 注意API 21和15的函数兼容问题
     */
    WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            if (MySharedPre.getInstance().getTrustSSL()) {
                handler.proceed();
            } else {
                handler.cancel();
            }
        }

        @Override
        @TargetApi(21)
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return customWebResponse(request.getUrl().toString());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return customWebResponse(url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        @TargetApi(21)
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    };

    /**
     * custom the response
     * @param url
     * @return
     */
    private WebResourceResponse customWebResponse(String url) {
        WebResourceResponse response = null;
        if (url.contains(Config.IMAGE_HOST) && url.startsWith("http")) {
            try {
                String imgPath = url.replace(Config.IMAGE_HOST, "");
                imgPath = Uri.parse(imgPath).getPath();
                InputStream localCopy = new FileInputStream(imgPath);
                response = new WebResourceResponse("image/png", "UTF-8", localCopy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    WebChromeClient webChromeClient = new WebChromeClient() {
        /**
         * 加载web页面的百分比
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }
    };
}