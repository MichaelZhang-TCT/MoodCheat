package com.zanexu.xuliao.data.remote.signature;

import android.content.Context;

import com.zanexu.xuliao.App;
import com.zanexu.xuliao.data.remote.RetrofitProvider;
import com.zanexu.xuliao.data.remote.TransForm;

import rx.Observable;

/**
 * Created by zanexu on 2017/7/8.
 */

public class SignatureModel {
    private Context context;
    private SignatureService serviceApi;

    private SignatureModel(){
        context = App.getInstance();
        serviceApi = RetrofitProvider.getRetrofitBuilder()
                .baseUrl(SignatureService.BASE_URL)
                .build()
                .create(SignatureService.class);
    }

    private static final class InstanceHolder{
        private final static SignatureModel instance = new SignatureModel();
    }

    public static SignatureModel getInstance(){
        return InstanceHolder.instance;
    }

    public Observable<SignatureBean> fetchSignature() {
        return TransForm.transform(serviceApi.fetchSignature());
    }
}
