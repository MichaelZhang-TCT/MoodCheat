package com.zanexu.xuliao.data.remote.signature;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zanexu on 2017/7/8.
 */

public interface SignatureService {
    String BASE_URL = "http://119.29.118.55/index.php/secret/";

    //fetch the signature
    @GET("getsigncode")
    Observable<SignatureBean> fetchSignature();
}
