package com.wsg.go.okhttp;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.wsg.go.entity.BaseData;
import com.wsg.go.utils.Constants;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.ToastUtil;
import com.wsg.go.utils.Tools;
import com.wsg.go.view.ui.dialog.MyProgressDialog;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2016-10-11 15:40
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BaseDefaultCallback<T> extends JsonCallback<T> {

    private Context mContext;

    private IBaseUIView iBaseUIView;

    private IBaseNetView iBaseNetView;

    private boolean isShowDialog = true; //请求网络的时候默认转圈

    private boolean isMainNetInterface = false; //是否为主协议

    private MyProgressDialog dialog;

    private String cmd = "";


    public BaseDefaultCallback(Context mContext, Class<T> clazz, String cmd, IBaseUIView iBaseUIView, IBaseNetView iBaseNetView) {
        super(clazz);
        this.mContext = mContext;
        this.cmd = cmd;
        this.iBaseUIView = iBaseUIView;
        this.iBaseNetView = iBaseNetView;
    }

    public BaseDefaultCallback(Context mContext, Class<T> clazz, String cmd, boolean isShowDialog, IBaseUIView iBaseUIView, IBaseNetView iBaseNetView) {
        super(clazz);
        this.mContext = mContext;
        this.cmd = cmd;
        this.isShowDialog = isShowDialog;
        this.iBaseUIView = iBaseUIView;
        this.iBaseNetView = iBaseNetView;
    }

    public BaseDefaultCallback(Context mContext, Class<T> clazz, String cmd, boolean isShowDialog, IBaseUIView iBaseUIView, IBaseNetView iBaseNetView, boolean isMainNetInterface) {
        super(clazz);
        this.mContext = mContext;
        this.cmd = cmd;
        this.isShowDialog = isShowDialog;
        this.iBaseUIView = iBaseUIView;
        this.iBaseNetView = iBaseNetView;
        this.isMainNetInterface = isMainNetInterface;
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if(!Tools.isNetworkConnected(mContext)){
            ToastUtil.showToast(mContext, Constants.NetConstants.RESPONSE_NOWIFI);
            return;
        }

        if(isShowDialog){
            dialog = new MyProgressDialog(mContext);
            dialog.show();
        }

        if(isMainNetInterface && iBaseUIView.getBaseViewStatus() != EBaseViewStatus.SUCCESS){
            iBaseUIView.showLoadingLayout();
        }
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        try {
            if(null!=response.body()){
                T data = (T)response.body();
//                if(data instanceof BaseData){
//
//                }
//                if( (null != baseData.getStatus() && baseData.getStatus().equals("200"))){
//                    iBaseView.onSuccess(baseData,cmd);
//                    iBaseView.showSuccessLayout();
//                }else{
//                    String errStr;
//                    if(!TextUtils.isEmpty(baseData.getMsg())){
//                        errStr = baseData.getMsg();
//                    }else{
//                        errStr = "服务器异常"+response.code();
//                    }
//                    showError(baseData, errStr);
//                    //是否显示错误布局
//                    if(isMainNetInterface && iBaseView.getBaseViewStatus() != EBaseViewStatus.SUCCESS){
//                        iBaseView.showErrorLayout(errStr);
//                    }
//                }
                iBaseNetView.onSuccess(data,cmd);
                //是否显示错误布局
                if(isMainNetInterface && iBaseUIView.getBaseViewStatus() != EBaseViewStatus.SUCCESS){
                    iBaseUIView.showSuccessLayout();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(cmd+"回调失败!"+e.toString());
            LogUtils.e(Log.getStackTraceString(e));
            showError(null, "未知异常"+response.code());
        }
    }

    public void showError(BaseData baseData, String errStr){
        ToastUtil.showToast(mContext,errStr);
        //回调错误方法
        iBaseNetView.onError(baseData,cmd);
        //是否显示错误布局
        if(isMainNetInterface && iBaseUIView.getBaseViewStatus() != EBaseViewStatus.SUCCESS){
            iBaseUIView.showErrorLayout(errStr);
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        LogUtils.e("onError请求code"+response.code());
        if(!Tools.isNetworkConnected(mContext)){
            ToastUtil.showToast(mContext, Constants.NetConstants.RESPONSE_NOWIFI);
        }else{
            if(null!=response){
                Tools.showError(mContext,response.code());
            }else{
                Tools.showError(mContext,500);
            }
        }
        String errStr = "网络不稳定，请稍后再试";
        try {
            if(null!=response){
                if (response.getException() instanceof SocketTimeoutException) {
                    errStr = "网络超时，请检查网络连接";
                } else if (response.getException() instanceof UnknownHostException) {
                    errStr = "网络超时，请检查网络连接";
                } else if (response.getException() instanceof HttpException) {
                    errStr = "网络超时，请检查网络连接";
                }  else if (response.getException() instanceof JsonSyntaxException) {
                    errStr = "数据类型不匹配,请重试";
                } else {
//                    errStr = response.getException().getMessage();
                    errStr = "网络不稳定，请稍后再试";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //回调
        iBaseNetView.onError(null, cmd);

        //是否显示错误布局
        if(isMainNetInterface && iBaseUIView.getBaseViewStatus() != EBaseViewStatus.SUCCESS){
            iBaseUIView.showErrorLayout(errStr);
        }
    }

    @Override
    public void onFinish() {
        iBaseNetView.onResult(cmd);
        if(null!=dialog&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    //上传过程中的进度回调,get请求不回调,ui线程
    @Override
    public void uploadProgress(Progress progress) {

    }

    //下载过程中的进度回调 ui线程
    @Override
    public void downloadProgress(Progress progress) {

    }

}
