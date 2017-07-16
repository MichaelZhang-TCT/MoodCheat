package com.zanexu.xuliao.data.remote.signature;

/**
 * Signature used to get the token from RongCloud
 * Created by zanexu on 2017/7/10.
 */

public class SignatureBean {


    /**
     * status : 200
     * message : ok
     * data : {"timestamp":1499688970,"nonce":527853557,"signature":"c07d61628d10b8b8dc43eaca7dabee87a0af7893"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * timestamp : 1499688970
         * nonce : 527853557
         * signature : c07d61628d10b8b8dc43eaca7dabee87a0af7893
         */

        private int timestamp;
        private int nonce;
        private String signature;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public int getNonce() {
            return nonce;
        }

        public void setNonce(int nonce) {
            this.nonce = nonce;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
