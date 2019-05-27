package com.wsg.go.view.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wsg.go.R;


/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2016-10-12 14:14
 * 描    述：网络请求默认显示的dialog
 * 修订历史：
 * ================================================
 */
public class MyProgressDialog extends Dialog {

    protected Context mContext;

    private TextView tv_loadingmsg;

    public MyProgressDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        initView("");
    }

    public MyProgressDialog(Context context, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        initView(content);
    }

    private void initView(String content) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_progress_dialog);

        tv_loadingmsg = (TextView)findViewById(R.id.tv_loadingmsg);

        if(TextUtils.isEmpty(content)){
            tv_loadingmsg.setVisibility(View.GONE);
        }else{
            tv_loadingmsg.setVisibility(View.VISIBLE);
            tv_loadingmsg.setText(content);
        }
    }

    /**
     * 设置提示语
     * @param content
     */
    public void setLoadingMsg(String content){
        if(TextUtils.isEmpty(content)){
            tv_loadingmsg.setVisibility(View.GONE);
        }else{
            tv_loadingmsg.setVisibility(View.VISIBLE);
            tv_loadingmsg.setText(content);
        }
    }




}
