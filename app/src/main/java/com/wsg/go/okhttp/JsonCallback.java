package com.wsg.go.okhttp;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lzy.okgo.callback.AbsCallback;
import com.wsg.go.BuildConfig;
import com.wsg.go.utils.LogUtils;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2016-10-11 14:55
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Class<T> clazz;

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(Response response) throws Exception {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)) return null;

        if(BuildConfig.DEBUG){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(responseData);
            String jsonFormat = gson.toJson(je);
            LogUtils.e(jsonFormat);
        }

        if (clazz != null) {
            final T object = new Gson().fromJson(responseData, clazz);
            return object;
        }
        return null;
    }
}