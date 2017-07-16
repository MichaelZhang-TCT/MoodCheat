package com.zanexu.xuliao.data.remote;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * thread scheduler
 * Created by zanexu on 2017/7/8.
 */

public class SchedulerTransform<T> implements Observable.Transformer<T, T> {

    private static final String TAG = "SchedulerTransform" ;

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
