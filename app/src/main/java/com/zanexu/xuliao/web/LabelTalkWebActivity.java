package com.zanexu.xuliao.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zanexu.xuliao.Config;
import com.zanexu.xuliao.data.remote.photo.PhotoModel;
import com.zanexu.xuliao.data.sp.MySharedPre;
import com.zanexu.xuliao.im.ConnectEnginer;
import com.zanexu.xuliao.im.TalkNative;
import com.zanexu.xuliao.photo.PhotoEnginer;
import com.zanexu.xuliao.photo.PhotoNative;

import java.io.File;

/**
 * Created by zanexu on 2017/7/7.
 */

public class LabelTalkWebActivity extends BaseWebActivity{

    private TalkNative talkNative;
    private PhotoEnginer photoEnginer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        talkNative = new TalkNative(new ConnectEnginer(LabelTalkWebActivity.this));
        photoEnginer = new PhotoEnginer(this);
        String param = getIntent().getStringExtra(MainWebActivity.LABEL_PRAMA);
        mWebView.loadUrl("http://118.89.35.155:8080/#/photo-and-tag" + param);

        containers.addJSInterface("TalkNative", talkNative);
        containers.addJSInterface("PhotoNative", new PhotoNative(photoEnginer));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoEnginer.PHOTO_RESULT:
                    File file = new File(photoEnginer.getFilePath());
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
                        .append(Config.IMAGE_HOST + "file://" + photoEnginer.getFilePath())
                        .append("&expression=")
                        .append(photoBean.getData().getExpression());

                mWebView.loadUrl("http://118.89.35.155:8080/#/photo-and-tag" + sb.toString());
            } else if ("notok".equals(message)) {
                mWebView.loadUrl("http://118.89.35.155:8080/#/main-page?error=1");
            }
        });
    }
}
