package com.zanexu.xuliao.web;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.tencent.tauth.Tencent;
import com.zanexu.xuliao.data.sp.MySharedPre;
import com.zanexu.xuliao.login.LoginNative;
import com.zanexu.xuliao.login.QQLoginEnginer;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Only the first time when use launch the app will start this Activity
 *
 * If this is the first time user open the app, So we load splash url
 *
 * If user have already login in, So we load he photo url
 *
 * If use have not login in, We load the login url
 *
 * Created by zanexu on 2017/7/7.
 */

public class SplashWebActivity extends BaseWebActivity{

    private QQLoginEnginer.MyUIListener listener;
    private QQLoginEnginer enginer;

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_STORAGE = 1;
    private static final int REQUEST_LOCATION = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        //checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA);
        //checkPermission(Manifest.permission_group.LOCATION, REQUEST_LOCATION);
        //checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_STORAGE);
        //checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 3);

        enginer = new QQLoginEnginer(this, new QQLoginEnginer.MyUIListener(this));
        if (MySharedPre.getInstance().getIsFirstStart()) {
            MySharedPre.getInstance().setIsFirstStart(false);
            containers.addJSInterface("LoginNative", new LoginNative(enginer));
            mWebView.loadUrl("http://118.89.35.155:8080/app#/hello");
        } else if (MySharedPre.getInstance().getLogin()){
            startActivity(new Intent(SplashWebActivity.this, MainWebActivity.class));
            finish();
        } else {
            enginer.QQLogin();
        }
    }

    private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                permission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                //checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA);
                break;
            case REQUEST_LOCATION:
                //checkPermission(Manifest.permission_group.LOCATION, REQUEST_LOCATION);
                break;
            case REQUEST_STORAGE:
                //checkPermission(Manifest.permission_group.STORAGE, REQUEST_STORAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }
}
