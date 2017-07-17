package com.zanexu.xuliao.data.remote;

import com.zanexu.xuliao.Config;
import com.zanexu.xuliao.data.remote.token.TokenService;
import com.zanexu.xuliao.data.sp.MySharedPre;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * this interceptors is use to add header
 * Created by zanexu on 2017/7/8.
 */

public class HeaderInterceptors implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originRequest = chain.request();

        //if request is use to fetch token from Rongcloud
        //So we shuild add some header what server needs
        if ((TokenService.BASE_URL + "getToken.json").equals(originRequest.url().toString())) {
            //add header
            Request request = originRequest
                    .newBuilder()
                    .addHeader("App-Key", Config.APP_KEY)
                    .addHeader("Nonce", MySharedPre.getInstance().getNonce())
                    .addHeader("Timestamp", MySharedPre.getInstance().getTimestamp())
                    .addHeader("Signature", MySharedPre.getInstance().getSignature())
                    .build();
            return chain.proceed(request);
        }

        return chain.proceed(originRequest);
    }
}
