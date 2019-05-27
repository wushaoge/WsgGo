package com.wsg.go.okhttp;

import android.content.Context;
import android.media.MediaScannerConnection;

import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.view.ui.dialog.MyProgressDialog;

import java.io.File;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018年09月03日10:38:39
 * 描    述：文件下载
 * 修订历史：
 * ================================================
 */
public class DownLoadFileCallback extends FileCallback {

    private Context mContext;

    private MyProgressDialog dialog;


    public DownLoadFileCallback(Context mContext, String destFileName) {
        super(destFileName);
        this.mContext = mContext;
    }

    public DownLoadFileCallback(Context mContext, String destFileDir, String destFileName) {
        super(destFileDir, destFileName);
        this.mContext = mContext;
    }

    @Override
    public void onStart(Request<File, ? extends Request> request) {
        dialog = new MyProgressDialog(mContext);
        dialog.show();
    }

    @Override
    public void onSuccess(Response<File> response) {
        // file 即为文件数据，文件保存在指定目录
        LogUtils.e(response.body().getAbsolutePath());
        String[] paths = {response.body().getAbsolutePath()};
        MediaScannerConnection.scanFile(mContext.getApplicationContext(), paths, null, null);
//        Tools.showToast(mContext, "保存成功");
        LogUtils.e("保存成功"+response.body().getAbsolutePath());
//        FileUtils.openFile(mContext , response.body().getAbsolutePath());
    }

    @Override
    public void onError(Response<File> response) {
//        Tools.showToast(mContext, "保存失败");
    }

    @Override
    public void downloadProgress(Progress progress) {
        LogUtils.e("下载进度"+progress.fraction * 100 + "%");
        dialog.setLoadingMsg("下载进度"+progress.fraction * 100 + "%");
    }

    @Override
    public void onFinish() {
        if(null!=dialog&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
