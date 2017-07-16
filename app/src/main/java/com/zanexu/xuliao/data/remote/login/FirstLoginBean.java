package com.zanexu.xuliao.data.remote.login;

/**
 * Created by zanexu on 2017/7/12.
 */

public class FirstLoginBean {


    /**
     * status : 200
     * message : success
     * data : {"isfirsttime":true}
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
         * isfirsttime : true
         */

        private boolean isfirsttime;

        public boolean isIsfirsttime() {
            return isfirsttime;
        }

        public void setIsfirsttime(boolean isfirsttime) {
            this.isfirsttime = isfirsttime;
        }
    }
}
