package com.zanexu.xuliao;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.zanexu.xuliao.data.sp.MySharedPre;
import com.zanexu.xuliao.im.CustomTextMessageItemProvider;
import com.zanexu.xuliao.web.MainWebActivity;
import com.zanexu.xuliao.web.SplashWebActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by zanexu on 2017/7/7.
 */

public class App extends Application{
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RongIM.init(this);
        RongIM.registerMessageTemplate(new CustomTextMessageItemProvider());

        if (!MySharedPre.getInstance().getToken().equals("")) {
            connectRongIM(MySharedPre.getInstance().getToken());
        }
    }

    public static App getInstance() {
        return instance;
    }

    private void connectRongIM(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("ConnectEnginer", "--onTokenIncorrect");
            }

            /**
             * 连接融云成功
             *
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("ConnectEnginer", "--onSuccess" + userid);

            }

            /**
             * 连接融云失败
             *
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("ConnectEnginer", "--onError" + errorCode);
            }
        });
    }
}
