package com.zanexu.xuliao.data.remote.photo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Zane on 2017/7/12.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface PhotoService {
    String BASE_URL = "http://119.29.118.55/index.php/mood/";

    @POST("uploadPhoto")
    @Multipart
    Observable<PhotoBean> fetchLabel(@Part("openid") RequestBody openid,
                                     @Part MultipartBody.Part uploadPhoto);
}
