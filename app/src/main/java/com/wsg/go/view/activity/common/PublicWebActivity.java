package com.wsg.go.view.activity.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wsg.go.R;
import com.wsg.go.okhttp.EBaseViewStatus;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.Tools;
import com.wsg.go.view.activity.base.BaseMVCActivity;
import com.wsg.go.view.ui.webview.MyWebView;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-28 10:59
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PublicWebActivity extends BaseMVCActivity {


    @BindView(R.id.pb_webView)
    ProgressBar pbWebView;
    @BindView(R.id.fl_webView)
    FrameLayout flWebView;

    private boolean isLoadSuccess = true;

    String title = "X5Webview";
    String url = "https://www.baidu.com/index.php?tn=monline_5_dg";

    public static final int REQUEST_SELECT_FILE = 100;
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;

    private MyWebView mWebView;

    @Override
    public void getBundleExtras(Bundle extras) {
        title = extras.getString("title", title);
        url = extras.getString("url", url);
    }

    @Override
    public String getChildTitle() {
        return title;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_public_web;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {
        mWebView = new MyWebView(this, null);
        flWebView.removeAllViews();
        flWebView.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        initProgressBar();
        mWebView.clearHistory();
        mWebView.clearCache(true);

        mWebView.setWebViewClient(new WebViewClient() {
            // 点击超链接的时候重新在原来进程上加载URL
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e(url);
                view.loadUrl(url);
                //rlClose.setVisibility(View.VISIBLE);
                return true;
            }


            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                LogUtils.e("加载出错!!!");
                if (webResourceRequest.isForMainFrame()) {
                    isLoadSuccess = false;
                    mWebView.stopLoading();
                    mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                    showWebViewError();
                }
            }

            // webview加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                LogUtils.e("加载完成!!!,isLoadSuccess=" + isLoadSuccess);
                if(null != pbWebView){
                    pbWebView.setVisibility(View.GONE);
                }
                if (isLoadSuccess) {
                    String title = view.getTitle();
                    if (title != null && !title.equals("")) {
                        updateTitle(title);
                    }
                    setBaseViewStatus(EBaseViewStatus.SUCCESS);
                } else {
                    showWebViewError();
                }

            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(null != pbWebView){
                    pbWebView.setProgress(newProgress);
                    if (newProgress >= 100) {
                        pbWebView.setVisibility(View.GONE);
                    }
                }

            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                PublicWebActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                PublicWebActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                PublicWebActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                Log.i("test", "openFileChooser 4:" + filePathCallback.toString());
                PublicWebActivity.this.uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }

        });

        if (Tools.isNetworkConnected(this) && !TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        } else {
            showWebViewError();
        }
    }

    @Override
    public void initMainNetData() {

    }

    public void showWebViewError(){
        setBaseViewStatus(EBaseViewStatus.ERROR);
        setErrorText("加载网页出错");
    }

    @Override
    public void backClick() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    /**
     * 初始化进度条
     */
    private void initProgressBar() {
        pbWebView.setMax(100);
        pbWebView.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar));
    }

    public void updateTitle(String title) {
        if (isLoadSuccess) {
            setTitle(title);
        } else {
            setTitle(this.title);
        }
    }

    /**
     * 打开文件管理器
     */
    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), REQUEST_SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    if (null != uploadFile) {
                        Uri result = intent == null ? null : intent.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = intent == null ? null : intent.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    if (null != uploadFile) {
                        uploadFile.onReceiveValue(null);
                        uploadFile = null;
                    }
                }
                break;
            default:
                break;
        }
    }

    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
