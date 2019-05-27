package com.wsg.go.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.wsg.go.R;

import java.util.List;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-10 16:45
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Tools {

    /**
     * 检测网络是否可用.
     */
    public static boolean isNetworkConnected(Context context) {
        boolean flag = false;
        if (null == context)
            return false;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            }
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            flag = networkInfo != null && networkInfo.isAvailable();
        } catch (Exception e) {
            return false;
        }
        return flag;
    }


    /**
     * 显示网络错误提示语
     *
     * @param mContext
     * @param code
     */
    public static void showError(Context mContext, int code) {
        if (code == 200) return;

        String errorMessage = Constants.NetConstants.RESPONSE_DEFAULT_ERROR;
        if (code == 404) {
            errorMessage = Constants.NetConstants.RESPONSE_404;
        } else if (code == 503) {
            errorMessage = Constants.NetConstants.RESPONSE_503;
        } else if (code == 500) {
            errorMessage = Constants.NetConstants.RESPONSE_500;
        }
        ToastUtil.showToast(mContext, errorMessage);
    }


    /**
     * 判断list是否为空
     * @param list
     * @return
     */
    public static boolean isEmptyList(List list){
        if(null == list || list.size()<=0){
            return true;
        }
        return false;
    }

    /**
     * 加载列表的空布局
     * @param context
     * @return
     */
    public static View getEmptyView(Context context){
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View emptyView = mLayoutInflater.inflate(R.layout.common_empty, null, true);
        LottieAnimationView lottieLoadingView = (LottieAnimationView) emptyView.findViewById(R.id.lottie_empty_view);
        lottieLoadingView.setAnimation("halloween_smoothymon.json");
        lottieLoadingView.setRepeatCount(ValueAnimator.INFINITE);
        lottieLoadingView.playAnimation();
        return emptyView;
    }

    /**
     * 生成从m到n的随机整数[m,n]
     * @param m
     * @param n
     * @return
     */
    public static int getRandom(int m, int n){
        int temp=m+(int)(Math.random()*(n+1-m)); //生成从m到n的随机整数[m,n]
        return temp;
    }

    /**
     * 生成100内随机整数[m,n]
     * @return
     */
    public static int getRandom100(){
        return getRandom(1,100);
    }

}
