package com.zanexu.xuliao.data.remote.im;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Zane on 2017/7/13.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface GiftService {
    String BASE_URL = "http://119.29.118.55/index.php/value/";

    @GET("addGift/{openid}/{targetId}")
    Observable<GiftBean> sendGift(@Path("openid") String openId, @Path("targetId") String targetId);
}
