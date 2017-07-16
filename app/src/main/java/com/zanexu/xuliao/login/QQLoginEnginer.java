package com.zanexu.xuliao.login;


import android.util.Log;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zanexu.xuliao.App;
import com.zanexu.xuliao.Config;
import com.zanexu.xuliao.data.sp.MySharedPre;
import com.zanexu.xuliao.im.ConnectEnginer;
import com.zanexu.xuliao.web.SplashWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by zanexu on 2017/7/8.
 */

public class QQLoginEnginer {

    private static Tencent mTencent = Tencent.createInstance(Config.APP_ID, App.getInstance());
    private MyUIListener listener;
    private SplashWebActivity activity;

    public QQLoginEnginer(SplashWebActivity activity, MyUIListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void QQLogin() {
        mTencent.login(activity, "all", listener);
    }

    public static class MyUIListener implements IUiListener {
        private ConnectEnginer connectEnginer;
        private SplashWebActivity activity;

        public MyUIListener(SplashWebActivity activity) {
            this.connectEnginer = new ConnectEnginer(activity);
            this.activity = activity;
        }

        @Override
        public void onComplete(Object o) {
            //这个openid可以用来实现QQ分享之类的，他是每一个qq用户的唯一标识码
            String openidString = null;
            try {
                openidString = ((JSONObject) o).getString("openid");
                MySharedPre.getInstance().setOpenId(openidString);

                String access_token = ((JSONObject) o).getString("access_token");

                //这个url是获取用户信息的接口地址
                StringBuilder sb = new StringBuilder("https://graph.qq.com/user/get_user_info");
                sb.append("?access_token=")
                        .append(access_token)
                        .append("&oauth_consumer_key=")
                        .append(Config.APP_ID)
                        .append("&openid=")
                        .append(openidString)
                        .append("&format=json");

                //judgeFirstLogin(openidString);

                //fetch the QQ user info and pass the request chain to Talk module
                connectEnginer.connect(activity, fetchQQUserInfo(sb.toString()), openidString);
            } catch (JSONException e) {
                Log.e("QQ", "Error in request user info: " + e.getMessage());
            }
        }

        @Override
        public void onError(UiError uiError) {
            Log.i("login", "error" + uiError.errorMessage);
        }

        @Override
        public void onCancel() {
        }

        private Observable<JSONObject> fetchQQUserInfo(String url) {
            return Observable.create(subscriber -> {
                try {
                    JSONObject jsonObject = mTencent.request(url, null, Constants.HTTP_GET);
                    subscriber.onNext(jsonObject);
                } catch (Exception e) {
                    Log.i("testResult", e.getMessage());
                    subscriber.onError(e);
                }
            });
        }

//        /**
//         * judge the user is the first time to login our self server or not
//         * @param openId
//         */
//        private Observable<FirstLoginBean> judgeFirstLogin(String openId) {
//            return Observable.create(subscriber -> {
//                FirstLoginBean firstLoginBean = FirstLoginModel.getInstance().judgeFirstLogin(openId)
//            })
//            if (true) {
//                activity.startActivity(new Intent(activity, MainWebActivity.class));
//            } else {
//                activity.startActivity(new Intent(activity, LabelTalkWebActivity.class));
//            }
//            activity.finish();
//            FirstLoginModel.getInstance().judgeFirstLogin(openId)
//                    .subscribe(firstLoginBean -> {
//                        boolean isFirstLogin = firstLoginBean.getData().isFirstLogin();
//                        MySharedPre.getInstance().setIsFirstLogin(firstLoginBean.getData().isFirstLogin());
//                        Log.i("testLogin", "intent");
//                        if (isFirstLogin) {
//                            activity.startActivity(new Intent(activity, MainWebActivity.class));
//                        } else {
//                            activity.startActivity(new Intent(activity, LabelTalkWebActivity.class));
//                        }
//                        activity.finish();
//                    });

    }
}



