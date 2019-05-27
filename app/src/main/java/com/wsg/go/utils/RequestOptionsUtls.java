package com.wsg.go.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.bumptech.glide.request.RequestOptions;
import com.wsg.go.R;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-14 09:32
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RequestOptionsUtls {

    public static RequestOptions getRequestOptions(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_pic_loading)
                .error(R.drawable.ic_pic_error)
                .fallback(new ColorDrawable(Color.GRAY))
                .fitCenter();
        return  requestOptions;
    }

    public static RequestOptions getRequestOptionsHead(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_head)
                .error(R.drawable.ic_head)
                .fallback(new ColorDrawable(Color.GRAY))
                .fitCenter();
        return  requestOptions;
    }

    public static RequestOptions getRequestOptionsBanner(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.holder)
                .error(R.mipmap.holder)
                .fallback(new ColorDrawable(Color.GRAY))
                .fitCenter();
        return  requestOptions;
    }

    public static RequestOptions getRequestImgOptions(){
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_pic_error)
                .fallback(new ColorDrawable(Color.GRAY))
                .fitCenter();
        return  requestOptions;
    }

}
