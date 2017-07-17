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
     * data : {"islike":false,"giftcount":0}
     */

    private int status;
    private String message;
    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * islike : false
         * giftcount : 0
         */

        private boolean islike;
        private int giftcount;

        public boolean isIslike() {
            return islike;
        }

        public void setIslike(boolean islike) {
            this.islike = islike;
        }

        public int getGiftcount() {
            return giftcount;
        }

        public void setGiftcount(int giftcount) {
            this.giftcount = giftcount;
        }
    }
}
