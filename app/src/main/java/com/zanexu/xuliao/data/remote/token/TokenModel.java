package com.zanexu.xuliao.data.remote.token;

import android.content.Context;

import com.zanexu.xuliao.App;
import com.zanexu.xuliao.data.remote.RetrofitProvider;
import com.zanexu.xuliao.data.remote.TransForm;

import rx.Observable;

/**
 * Created by zanexu on 2017/7/10.
 */

public class TokenModel {
    private Context context;
    private TokenService serviceApi;

    private TokenModel(){
        context = App.getInstance();
        serviceApi = RetrofitProvider.getRetrofitBuilder()
                .baseUrl(TokenService.BASE_URL)
                .build()
                .create(TokenService.class);
    }

    private static final class InstanceHolder{
        private final static TokenModel instance = new TokenModel();
    }

    public static TokenModel getInstance(){
        return TokenModel.InstanceHolder.instance;
    }

    public Observable<TokenBean> fetchToken(String openId, String name, String portraitUri) {
        return TransForm.transform(serviceApi.fetchToken(openId, name, portraitUri));
    }
}
