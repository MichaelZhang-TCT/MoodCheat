package com.zanexu.xuliao.data.remote.im;

/**
 * Created by Zane on 2017/7/13.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class PeerLikeBean {

    /**
     * status : 200
     * message : success
     * isliked : true
     */

    private int status;
    private String message;
    private boolean isliked;
    private int giftCount;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsliked() {
        return isliked;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }

    public boolean isliked() {
        return isliked;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }
}
