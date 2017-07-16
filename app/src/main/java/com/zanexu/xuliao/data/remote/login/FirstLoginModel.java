package com.zanexu.xuliao.data.remote.login;

import android.content.Context;

import com.zanexu.xuliao.App;
import com.zanexu.xuliao.data.remote.RetrofitProvider;
import com.zanexu.xuliao.data.remote.TransForm;


import rx.Observable;

/**
 * Created by Zane on 2017/7/11.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class FirstLoginModel {
    private Context context;
    private FirstLoginService serviceApi;

    private FirstLoginModel(){
        context = App.getInstance();
        serviceApi = RetrofitProvider.getRetrofitBuilder()
                .baseUrl(FirstLoginService.BASE_URL)
                .build()
                .create(FirstLoginService.class);
    }

    private static final class InstanceHolder{
        private final static FirstLoginModel instance = new FirstLoginModel();
    }

    public static FirstLoginModel getInstance(){
        return FirstLoginModel.InstanceHolder.instance;
    }

    public Observable<FirstLoginBean> judgeFirstLogin(String openId) {
        return TransForm.transform(serviceApi.judgeFirstLogin(openId));
    }
}
