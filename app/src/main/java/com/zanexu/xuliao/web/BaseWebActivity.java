package com.zanexu.xuliao.web;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.zanexu.xuliao.R;

/**
 * Created by zanexu on 2017/7/8.
 */

public abstract class BaseWebActivity extends AppCompatActivity{

    protected WebView mWebView;
    protected LinearLayout mRootView;
    protected WebContainers containers;
    private long mOldTime = 0L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        containers = new WebContainers(mWebView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mOldTime < 1500) {
                mWebView.clearHistory();

                // TODO: 2017/7/7 URL
                //mWebView.loadUrl("http://baidu.com");

            } else if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            mOldTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //防止内存泄漏
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * init WebView and RootView
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initView() {
        mRootView = (LinearLayout) findViewById(R.id.root_view);
        mWebView = new CustomWebView(this);

        WebView.setWebContentsDebuggingEnabled(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);

        mRootView.addView(mWebView);
    }
}
