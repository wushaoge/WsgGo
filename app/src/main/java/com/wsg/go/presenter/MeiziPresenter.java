package com.wsg.go.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wsg.go.entity.MeiziData;
import com.wsg.go.okhttp.BaseDefaultCallback;
import com.wsg.go.utils.Constants;
import com.wsg.go.utils.RequestUrl;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-10 09:35
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MeiziPresenter extends BasePresenter<IMeiziView>{


    public MeiziPresenter(Context context,IMeiziView view) {
        super(context,view);
    }

    public void getMeiziData(){
        String url = RequestUrl.getInstance().getMeizi();

        OkGo.<MeiziData>get(url).tag(mContext).execute(new BaseDefaultCallback<MeiziData>(mContext, MeiziData.class, Constants.GANK_BASEURL, false, mView, mView, true){
            @Override
            public void onSuccess(Response<MeiziData> response) {
                super.onSuccess(response);
                mView.getMeiziData(response.body());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.stopRefresh();
            }
        });


    }








}
