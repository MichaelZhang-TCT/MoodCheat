package com.zanexu.xuliao.data.remote.token;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zanexu on 2017/7/10.
 */

public interface TokenService {

    String BASE_URL = "http://api.cn.ronghub.com/user/";

    @POST("getToken.json")
    @FormUrlEncoded
    Observable<TokenBean> fetchToken(@Field("userId") String openId,
                                     @Field("name") String name,
                                     @Field("portraitUri") String portaitUri);
}
