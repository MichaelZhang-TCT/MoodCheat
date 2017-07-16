package com.zanexu.xuliao.data.remote;

import com.zanexu.xuliao.App;
import com.zanexu.xuliao.data.sp.MySharedPre;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * provide the Retrofit.Builder to build a custom retrofit
 * Created by zanexu on 2017/7/8.
 */

public class RetrofitProvider {

    private RetrofitProvider(){}
    private static final Retrofit.Builder RetrofitBuilderInstance = provideRetrofit();
    private static final String CER_NAME = "srca.cer";

    private static OkHttpClient provideOkHttpClient(){
        //添加body日志打印，http，stetho调试的拦截器，管理cookie

        OkHttpClient.Builder build =  new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new HeaderInterceptors());

        SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
        if (sslSocketFactory != null) {
            MySharedPre.getInstance().setTrustSSL(true);
            build.sslSocketFactory(sslSocketFactory, Platform.get().trustManager(sslSocketFactory));
        }

        return build.build();
    }

    private static Retrofit.Builder provideRetrofit(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(provideOkHttpClient());
    }

    //获取通过自签名证书构建的socket
    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            InputStream inputStream = App.getInstance().getAssets().open(CER_NAME);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(inputStream));
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Retrofit.Builder getRetrofitBuilder(){
        return RetrofitBuilderInstance;
    }
}
