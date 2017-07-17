package com.zanexu.xuliao.data.remote;

import android.util.Log;
import android.widget.Toast;

import com.zanexu.xuliao.App;

import java.util.ServiceConfigurationError;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

/**
 * Net error transformer
 * Created by zanexu on 2017/7/8.
 */

public class ErrorTransForm<T> implements Observable.Transformer<T, T>{

    private static final String TAG = "ErrorTransform";

    /**
     * 通过传递进来的错误判断来进行统一的错误处理
     * @param tObservable
     * @return
     */
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.onErrorResumeNext(throwable -> {
            //判断异常是什么类型
            String errorMessage = "";
            //通过状态码判断错误
            if (throwable instanceof HttpException) {
                HttpException response = (HttpException) throwable;
                errorMessage = response.message();
            } else if (throwable instanceof ServiceConfigurationError){
                errorMessage = "服务器错误";
            } else {
                errorMessage = "网络错误";
            }

            Toast.makeText(App.getInstance(), errorMessage, Toast.LENGTH_SHORT).show();

            return Observable.empty();
        });
    }
}
