package com.zanexu.xuliao.im;

import android.webkit.JavascriptInterface;

/**
 * Created by zanexu on 2017/7/9.
 */

public class TalkNative {
    private ConnectEnginer enginer;

    public TalkNative(ConnectEnginer enginer) {
        this.enginer = enginer;
    }

    @JavascriptInterface
    public void talkWith(String openId, String nickname) {
        enginer.startCheat(openId, nickname);
    }
}
