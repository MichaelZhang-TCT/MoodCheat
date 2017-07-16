package com.zanexu.xuliao.data.remote.im;

import android.content.Context;

import com.zanexu.xuliao.App;
import com.zanexu.xuliao.data.remote.RetrofitProvider;
import com.zanexu.xuliao.data.remote.TransForm;

import rx.Observable;

/**
 * Created by Zane on 2017/7/13.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class GiftModel {
    private Context context;
    private GiftService serviceApi;

    private GiftModel(){
        context = App.getInstance();
        serviceApi = RetrofitProvider.getRetrofitBuilder()
                             .baseUrl(GiftService.BASE_URL)
                             .build()
                             .create(GiftService.class);
    }

    private static final class InstanceHolder{
        private final static GiftModel instance = new GiftModel();
    }

    public static GiftModel getInstance(){
        return GiftModel.InstanceHolder.instance;
    }

    public Observable<GiftBean> sendGift(String openId, String targetId) {
        return TransForm.transform(serviceApi.sendGift(openId, targetId));
    }
}
