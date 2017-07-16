package com.zanexu.xuliao.data.sp;

import android.content.Context;

import com.zanexu.xuliao.App;

/**
 * Created by zanexu on 2017/7/7.
 */

public class MySharedPre {
    private static final String TAG = MySharedPre.class.getSimpleName();
    private Context context;
    private SecurePreferences preferences;
    private SecurePreferences.Editor editor;

    private MySharedPre() {
        this.context = App.getInstance();
        preferences = new SecurePreferences(this.context);
        editor = preferences.edit();
    }

    private static class SingletonHolder{
        private static final MySharedPre instance = new MySharedPre();
    }

    public static MySharedPre getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * this is first time to launch the app or not
     * @param isFirstStart
     */
    public void setIsFirstStart(boolean isFirstStart){
        editor.putBoolean("isFirstStart", isFirstStart);
        editor.commit();
    }

    public boolean getIsFirstStart(){
        return preferences.getBoolean("isFirstStart", true);
    }

    /**
     * user has login in or not
     * @param data
     */
    public void setLogin(boolean data){
        editor.putBoolean("login", data);
        editor.commit();
    }

    public boolean getLogin(){
        return preferences.getBoolean("login", false);
    }


    /**
     * user has connect RongCloud or not
     * @param isConnect
     */
    public void setIsConnect(boolean isConnect){
        editor.putBoolean("isConnect", isConnect);
        editor.commit();
    }

    public boolean getConnect(){
        return preferences.getBoolean("isConnect", false);
    }

    /**
     * nonce from self server
     * @param nonce
     */
    public void setNonce(String nonce){
        editor.putString("nonce", nonce);
        editor.commit();
    }

    public String getNonce(){
        return preferences.getString("nonce", "");
    }

    /**
     * timestamp from self server
     * @param timestamp
     */
    public void setTimestamp(String timestamp){
        editor.putString("timestamp", timestamp);
        editor.commit();
    }

    public String getTimestamp(){
        return preferences.getString("timestamp", "");
    }

    /**
     * signature from self server
     * @param signature
     */
    public void setSignature(String signature){
        editor.putString("signature", signature);
        editor.commit();
    }

    public String getSignature(){
        return preferences.getString("signature", "");
    }

    /**
     * this param is use to post to Web (provices, city.....)
     * @param param
     */
    public void setParam(String param){
        editor.putString("param", param);
        editor.commit();
    }

    public String getParam(){
        return preferences.getString("param", "");
    }

    /**
     * trust the SSL or not
     * @param trust
     */
    public void setTrustSSL(boolean trust) {
        editor.putBoolean("trust", trust);
        editor.commit();
    }

    public boolean getTrustSSL() {
        return preferences.getBoolean("trust", false);
    }

    /**
     * judge the user is first time to login or not
     * @param isFirstLogin
     */
    public void setIsFirstLogin(boolean isFirstLogin) {
        editor.putBoolean("firstlogin", isFirstLogin);
        editor.commit();
    }

    public boolean getIsFirstLogin() {
        return preferences.getBoolean("firstlogin", true);
    }

    /**
     * OPENID
     * @param openId
     */
    public void setOpenId(String openId) {
        editor.putString("openId", openId);
        editor.commit();
    }

    public String getOpenId() {
        return preferences.getString("openId", "");
    }
}
