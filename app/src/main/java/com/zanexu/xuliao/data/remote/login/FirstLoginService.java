package com.zanexu.xuliao.data.remote.login;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Zane on 2017/7/11.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface FirstLoginService {

    String BASE_URL = "http://119.29.118.55/index.php/login/";

    //judge is first time login or not
    @POST("firsttime")
    @FormUrlEncoded
    Observable<FirstLoginBean> judgeFirstLogin(@Field("openid") String openId);
}