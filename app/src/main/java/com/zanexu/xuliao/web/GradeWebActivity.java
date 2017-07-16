package com.zanexu.xuliao.web;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zanexu.xuliao.im.CheatActivity;

/**
 * Created by zanexu on 2017/7/8.
 */

public class GradeWebActivity extends BaseWebActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String params = getIntent().getStringExtra(CheatActivity.GRADE_PAPRAM);
        mWebView.loadUrl("http://118.89.35.155:8080/app#/evaluate" + params);
    }
}
