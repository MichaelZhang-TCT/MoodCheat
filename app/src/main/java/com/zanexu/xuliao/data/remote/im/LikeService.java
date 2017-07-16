package com.zanexu.xuliao.data.remote.im;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Zane on 2017/7/13.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface LikeService {
    String BASE_URL = "http://119.29.118.55/index.php/like/";

    @POST("likepeer")
    @FormUrlEncoded
    Observable<LikePeerBean> likePeer(@Field("myid") String myid,
                                      @Field("peerid") String peerid);

    @POST("isliked")
    @FormUrlEncoded
    Observable<PeerLikeBean> peerLike(@Field("myid") String myid,
                                      @Field("peerid") String peerid);
}