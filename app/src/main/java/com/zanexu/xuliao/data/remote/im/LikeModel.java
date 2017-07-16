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

public class LikeModel {
    private Context context;
    private LikeService serviceApi;

    private LikeModel(){
        context = App.getInstance();
        serviceApi = RetrofitProvider.getRetrofitBuilder()
                             .baseUrl(LikeService.BASE_URL)
                             .build()
                             .create(LikeService.class);
    }

    private static final class InstanceHolder{
        private final static LikeModel instance = new LikeModel();
    }

    public static LikeModel getInstance(){
        return LikeModel.InstanceHolder.instance;
    }

    public Observable<LikePeerBean> likePeer(String myid, String peerid) {
        return  TransForm.transform(serviceApi.likePeer(myid, peerid));
    }

    public Observable<PeerLikeBean> peerLike(String myid, String peerid) {
        return  TransForm.transform(serviceApi.peerLike(myid, peerid));
    }
}
