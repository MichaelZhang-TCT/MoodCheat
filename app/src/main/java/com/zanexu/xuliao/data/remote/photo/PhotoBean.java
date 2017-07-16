package com.zanexu.xuliao.data.remote.photo;

import java.util.List;

/**
 * Created by Zane on 2017/7/12.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class PhotoBean {

    /**
     * status : 200
     * message : ok
     * data : {"nickName":"悲伤的狐狸","tags":["求安慰","我靠","我的天啊","asdf","wsdihfui"],"expression":null}
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
         * nickName : 悲伤的狐狸
         * tags : ["求安慰","我靠","我的天啊","asdf","wsdihfui"]
         * expression : null
         */

        private String nickName;
        private Object expression;
        private List<String> tags;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getExpression() {
            return expression;
        }

        public void setExpression(Object expression) {
            this.expression = expression;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
