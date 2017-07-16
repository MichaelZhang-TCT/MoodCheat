package com.zanexu.xuliao.data.remote.photo;

import android.content.Context;

import com.zanexu.xuliao.App;
import com.zanexu.xuliao.data.remote.RetrofitProvider;
import com.zanexu.xuliao.data.remote.TransForm;
import com.zanexu.xuliao.data.remote.token.TokenBean;
import com.zanexu.xuliao.data.remote.token.TokenModel;
import com.zanexu.xuliao.data.remote.token.TokenService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Zane on 2017/7/12.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class PhotoModel {
    private Context context;
    private PhotoService serviceApi;

    private PhotoModel(){
        context = App.getInstance();
        serviceApi = RetrofitProvider.getRetrofitBuilder()
                             .baseUrl(PhotoService.BASE_URL)
                             .build()
                             .create(PhotoService.class);
    }

    private static final class InstanceHolder{
        private final static PhotoModel instance = new PhotoModel();
    }

    public static PhotoModel getInstance(){
        return PhotoModel.InstanceHolder.instance;
    }

    /**
     * 上传照片
     * @param file
     */
    public Observable<PhotoBean> fetchLabel(String openId, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody openidBody = RequestBody.create(MediaType.parse("multipart/form-data"), openId);
        return TransForm.transform(serviceApi.fetchLabel(openidBody, body));
    }
}
