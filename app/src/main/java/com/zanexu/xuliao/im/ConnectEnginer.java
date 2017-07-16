package com.zanexu.xuliao.im;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zanexu.xuliao.App;
import com.zanexu.xuliao.data.remote.login.FirstLoginModel;
import com.zanexu.xuliao.data.remote.token.TokenBean;
import com.zanexu.xuliao.data.remote.token.TokenModel;
import com.zanexu.xuliao.data.remote.signature.SignatureBean;
import com.zanexu.xuliao.data.remote.signature.SignatureModel;
import com.zanexu.xuliao.data.sp.MySharedPre;
import com.zanexu.xuliao.web.MainWebActivity;
import com.zanexu.xuliao.web.SplashWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zanexu on 2017/7/9.
 */

public class ConnectEnginer {

    private Activity context;

    public ConnectEnginer(Activity context) {
        this.context = context;
    }

    /**
     * start cheat room
     * @param openId
     * @param nickname
     */
    public void startCheat(String openId, String nickname) {
        RongIM.getInstance().startPrivateChat(context, openId, nickname);
        context.finish();
    }


    public void connect(SplashWebActivity activity, Observable<JSONObject> observable, String openId) {
        Observable.zip(FirstLoginModel.getInstance().judgeFirstLogin(openId), observable, SignatureModel.getInstance().fetchSignature(),
                (firstLoginBean, json, signatureBean) -> {
                    MySharedPre.getInstance().setIsFirstLogin(firstLoginBean.getData().isIsfirsttime());
                    JSONObject jsonObject = (JSONObject) json;
                    initSignture((SignatureBean) signatureBean);
                    try {
                        initQQUserData(jsonObject, openId);
                        String nickname = jsonObject.getString("nickname");
                        String photoUrl = jsonObject.getString("figureurl_qq_2");
                        return openId + " " + nickname + " " + photoUrl;
                    } catch (JSONException e) {
                        Log.e("connect", "error in parse json " + e.getMessage());
                    }
                    return "";
        }).flatMap((Func1<String, Observable<?>>) s -> {
            String[] infos = s.split(" ");
            return TokenModel.getInstance().fetchToken(infos[0], infos[1], infos[2]);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
            TokenBean tokenBean = (TokenBean) o;
            connectRongIM(activity, tokenBean.getToken());
        });
    }

    private void initSignture(SignatureBean signature) {
        SignatureBean.DataBean data = signature.getData();
        MySharedPre.getInstance().setNonce(String.valueOf(data.getNonce()));
        MySharedPre.getInstance().setTimestamp(String.valueOf(data.getTimestamp()));
        MySharedPre.getInstance().setSignature(data.getSignature());
    }

    private void initQQUserData(JSONObject json, String openid) throws JSONException{
        StringBuilder sb = new StringBuilder();
        String nickname = json.getString("nickname");
        String photoUrl = json.getString("figureurl_qq_2");
        String province = json.getString("province");
        String city = json.getString("city");
        sb.append("?openid=")
                .append(openid)
                .append("&avatarurl=")
                .append(photoUrl)
                .append("&username=")
                .append(nickname)
                .append("&userprovince=")
                .append(province)
                .append("&usercity=")
                .append(city);

        MySharedPre.getInstance().setParam(sb.toString());
    }

    private void connectRongIM(SplashWebActivity activity, String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("ConnectEnginer", "--onTokenIncorrect");
                MySharedPre.getInstance().setIsConnect(false);
            }

            /**
             * 连接融云成功
             *
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("ConnectEnginer", "--onSuccess" + userid);
                MySharedPre.getInstance().setIsConnect(true);
                MySharedPre.getInstance().setLogin(true);

                activity.startActivity(new Intent(activity, MainWebActivity.class));
                activity.finish();
            }

            /**
             * 连接融云失败
             *
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("ConnectEnginer", "--onError" + errorCode);
                MySharedPre.getInstance().setIsConnect(false);
            }
        });
    }
}
