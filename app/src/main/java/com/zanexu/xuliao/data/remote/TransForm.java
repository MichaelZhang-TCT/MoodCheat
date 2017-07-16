package com.zanexu.xuliao.data.remote;

import rx.Observable;

/**
 * Created by zanexu on 2017/7/8.
 */

public final class TransForm {
    public static <T> Observable<T> transform(Observable<T> tObservable){
        return tObservable
                .compose(new SchedulerTransform<>())
                .compose(new ErrorTransForm<>());
    }
}