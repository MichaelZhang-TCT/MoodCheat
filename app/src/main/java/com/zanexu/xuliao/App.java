package com.zanexu.xuliao;

import android.app.Application;

import com.zanexu.xuliao.im.CustomTextMessageItemProvider;

import io.rong.imkit.RongIM;

/**
 * Created by zanexu on 2017/7/7.
 */

public class App extends Application{
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RongIM.init(this);
        RongIM.registerMessageTemplate(new CustomTextMessageItemProvider());
    }

    public static App getInstance() {
        return instance;
    }
}
