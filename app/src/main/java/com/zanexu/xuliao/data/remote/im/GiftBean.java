package com.zanexu.xuliao.data.remote.im;

/**
 * Created by Zane on 2017/7/13.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class GiftBean {

    /**
     * status : 200
     * message : ok
     * data : null
     */

    private int status;
    private String message;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
