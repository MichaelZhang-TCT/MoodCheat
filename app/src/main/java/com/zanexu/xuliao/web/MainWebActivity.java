package com.zanexu.xuliao.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zanexu.xuliao.Config;
import com.zanexu.xuliao.data.remote.photo.PhotoModel;
import com.zanexu.xuliao.data.sp.MySharedPre;
import com.zanexu.xuliao.login.FirstLoginNative;
import com.zanexu.xuliao.login.LoginNative;
import com.zanexu.xuliao.photo.PhotoEnginer;
import com.zanexu.xuliao.photo.PhotoNative;

import java.io.File;

/**
 * Created by zanexu on 2017/7/8.
 */

public class MainWebActivity extends BaseWebActivity{
    private PhotoEnginer enginer;
    public static final String PHOTO_FILE_PATH = "phtot_file_path";
    public static final String LABEL_PRAMA = "label_param";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNativeMethod();
        Log.i("getID", "openId: " + MySharedPre.getInstance().getOpenId());
        if (MySharedPre.getInstance().getIsFirstLogin()) {
            mWebView.loadUrl("http://118.89.35.155:8080/#/basic-info" + MySharedPre.getInstance().getParam());
        } else {
            mWebView.loadUrl("http://118.89.35.155:8080/app#/main-page?error=0&openid=" + MySharedPre.getInstance().getOpenId());
        }
    }

    private void initNativeMethod() {
        enginer = new PhotoEnginer(this);
        containers.addJSInterface("PhotoNative", new PhotoNative(enginer));
        containers.addJSInterface("FirstLoginNative", new FirstLoginNative());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoEnginer.PHOTO_RESULT:
                    File file = new File(enginer.getFilePath());
                    uploadPhoto(file);
                    break;
            }
        }
    }

    private void uploadPhoto(File file) {
        PhotoModel.getInstance().fetchLabel(MySharedPre.getInstance().getOpenId(), file).subscribe(photoBean -> {
            String message = photoBean.getMessage();
            if ("ok".equals(message)) {
                //valid photo
                StringBuilder sb = new StringBuilder();
                sb.append("?nickname=")
                        .append(photoBean.getData().getNickName())
                        .append("&tags=");
                for (String tag : photoBean.getData().getTags()) {
                    sb.append(tag).append(",");
                }
                sb.append("&photo=")
                        .append(Config.IMAGE_HOST + "file://" + enginer.getFilePath())
                        .append("&expression=")
                        .append(photoBean.getData().getExpression())
                        .append("&openid=")
                        .append(MySharedPre.getInstance().getOpenId());

                Intent intent = new Intent(MainWebActivity.this, LabelTalkWebActivity.class);
                intent.putExtra(LABEL_PRAMA, sb.toString());
                startActivity(intent);
            } else if ("notok".equals(message)) {
                mWebView.loadUrl("http://118.89.35.155:8080/#/main-page?error=1&photo=" + enginer.getFilePath());
            }
        });
    }
}
